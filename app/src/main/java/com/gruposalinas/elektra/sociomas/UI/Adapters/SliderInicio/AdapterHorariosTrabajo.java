package com.gruposalinas.elektra.sociomas.UI.Adapters.SliderInicio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.ControlDiaSem;
import com.gruposalinas.elektra.sociomas.UI.Controls.ControlHorarioPicker;
import com.sociomas.core.UI.Controls.Dialogs.TimePickerV2;
import com.sociomas.core.WebService.Model.Request.Alta.Dias;
import com.sociomas.core.WebService.Model.Request.Alta.Horarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GiioToledo on 17/11/17.
 */

public class AdapterHorariosTrabajo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Horarios> horarios;
    private final LayoutInflater li;
    Map<String, List<Dias>> mapHorarios = new HashMap<>();

    public AdapterHorariosTrabajo(Context context, List<Horarios> horarios) {
        this.context = context;
        this.horarios = horarios;
        this.li = LayoutInflater.from(this.context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_horarios_trabajo, null);
        HolderHorario holderHorario = new HolderHorario(view);
        return holderHorario;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HolderHorario holderH = (HolderHorario) holder;
        List<Dias> diasList;
        if (!mapHorarios.containsKey("Horario" + position)) {
            diasList = defaultListDias();
            mapHorarios.put("Horario" + position, diasList);
        } else {
            diasList = mapHorarios.get("Horario" + position);
        }
        for (Dias d : diasList) {
            if (d.getDia().contentEquals(context.getString(R.string.Lunes))) {
                if (d.isActivo()) {
                    holderH.ctrLunes.check();
                } else {
                    holderH.ctrLunes.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Martes))) {
                if (d.isActivo()) {
                    holderH.ctrMartes.check();
                } else {
                    holderH.ctrMartes.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Miercoles))) {
                if (d.isActivo()) {
                    holderH.ctrMiercoles.check();
                } else {
                    holderH.ctrMiercoles.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Jueves))) {
                if (d.isActivo()) {
                    holderH.ctrJueves.check();
                } else {
                    holderH.ctrJueves.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Viernes))) {
                if (d.isActivo()) {
                    holderH.ctrViernes.check();
                } else {
                    holderH.ctrViernes.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Sabado))) {
                if (d.isActivo()) {
                    holderH.ctrSabado.check();
                } else {
                    holderH.ctrSabado.noCheck();
                }
            } else if (d.getDia().contentEquals(context.getString(R.string.Domingo))) {
                if (d.isActivo()) {
                    holderH.ctrDomingo.check();
                } else {
                    holderH.ctrDomingo.noCheck();
                }
            }
        }
        holderH.ctrLunes.setTag(position);
        holderH.ctrMartes.setTag(position);
        holderH.ctrMiercoles.setTag(position);
        holderH.ctrJueves.setTag(position);
        holderH.ctrViernes.setTag(position);
        holderH.ctrSabado.setTag(position);
        holderH.ctrDomingo.setTag(position);

        holderH.ctrLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
            }
        });
        holderH.ctrMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderH.ctrMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderH.ctrJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderH.ctrViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderH.ctrSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderH.ctrDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

    public void addElement(Horarios horario) {
        horarios.add(horario);
        notifyDataSetChanged();
    }

    public List<Dias> defaultListDias() {
        List<Dias> diasList = new ArrayList<>();
        diasList.add(new Dias(context.getString(R.string.Lunes), 1));
        diasList.add(new Dias(context.getString(R.string.Martes), 1));
        diasList.add(new Dias(context.getString(R.string.Miercoles), 0));
        diasList.add(new Dias(context.getString(R.string.Jueves), 1));
        diasList.add(new Dias(context.getString(R.string.Viernes), 0));
        diasList.add(new Dias(context.getString(R.string.Sabado), 1));
        diasList.add(new Dias(context.getString(R.string.Domingo), 1));
        return diasList;
    }

    public static class HolderHorario extends RecyclerView.ViewHolder {

        private final ControlHorarioPicker ctrPicker;
        //region HorarioPicker ControlsInside
        private Context context;
        private ControlDiaSem ctrLunes, ctrMartes, ctrMiercoles, ctrJueves, ctrViernes, ctrSabado, ctrDomingo;
        private ExpandableRelativeLayout expandablePicker, expandablePicker2, expandablePicker3;
        private TextView tvEntrada, tvEntradaHoraPicker, tvSalidaHoraPicker, tvSalida;
        private ImageView imgClose;
        private Button anadir, anadir2;
        private TimePickerV2 timeEntrada, timeSalida;
        private RelativeLayout relativeEntrada, relativeSalida, relativeTitle, relativePadre;
        //endregion

        public HolderHorario(View itemView) {
            super(itemView);
            ctrPicker = (ControlHorarioPicker) itemView.findViewById(R.id.ctrPicker);
            //region controles dentro de horariopicker
            tvEntrada = (TextView) ctrPicker.findViewById(R.id.tvEntrada);
            tvSalida = (TextView) ctrPicker.findViewById(R.id.tvSalida);
            imgClose = (ImageView) ctrPicker.findViewById(R.id.imgClose);
            anadir = (Button) ctrPicker.findViewById(R.id.btnAnadir);
            anadir2 = (Button) ctrPicker.findViewById(R.id.btnAnadir2);
            relativeEntrada = (RelativeLayout) ctrPicker.findViewById(R.id.RelativeEntrada);
            relativeSalida = (RelativeLayout) ctrPicker.findViewById(R.id.RelativeSalida);
            relativeTitle = (RelativeLayout) ctrPicker.findViewById(R.id.RelativeExpandableTitle);
            relativePadre = (RelativeLayout) ctrPicker.findViewById(R.id.relativePadre);
            ctrLunes = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrLunes);
            ctrMartes = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrMartes);
            ctrMiercoles = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrMiercoles);
            ctrJueves = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrJueves);
            ctrViernes = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrViernes);
            ctrSabado = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrSabado);
            ctrDomingo = (ControlDiaSem) ctrPicker.findViewById(R.id.ctrDomingo);
            timeEntrada = (TimePickerV2) ctrPicker.findViewById(R.id.timeEntrada);
            timeSalida = (TimePickerV2) ctrPicker.findViewById(R.id.timeSalida);
            expandablePicker = (ExpandableRelativeLayout) ctrPicker.findViewById(R.id.expandablePicker);
            expandablePicker2 = (ExpandableRelativeLayout) ctrPicker.findViewById(R.id.expandablePicker2);
            expandablePicker3 = (ExpandableRelativeLayout) ctrPicker.findViewById(R.id.expandablePicker3);
            tvEntradaHoraPicker = (TextView) ctrPicker.findViewById(R.id.tvEntradaHoraPicker);
            tvSalidaHoraPicker = (TextView) ctrPicker.findViewById(R.id.tvSalidaHoraPicker);
            //endregion
        }
    }
}
