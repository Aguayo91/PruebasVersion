package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Dialogs.TimePickerV2;
import com.sociomas.core.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jromeromar on 14/11/2017.
 */

public class ControlHorarioPicker extends RelativeLayout implements View.OnClickListener {

    private Context context;
    public ControlDiaSem ctrLunes, ctrMartes, ctrMiercoles, ctrJueves, ctrViernes, ctrSabado, ctrDomingo;
    private ExpandableRelativeLayout expandablePicker, expandablePicker2, expandablePicker3;
    private TextView tvEntrada, tvEntradaHoraPicker, tvSalidaHoraPicker, tvSalida;
    private ImageView imgClose;
    private Button anadir, anadir2;
    public TimePickerV2 timeEntrada, timeSalida;
    private RelativeLayout relativeEntrada, relativeSalida, relativeTitle, relativePadre;

    public ControlHorarioPicker(Context context) {
        super(context);
        inflateLayouts(context, null);
    }

    public ControlHorarioPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context, attrs);
    }

    private void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_control_horario, this);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        tvEntrada = (TextView) findViewById(R.id.tvEntrada);
        tvSalida = (TextView) findViewById(R.id.tvSalida);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        anadir = (Button) findViewById(R.id.btnAnadir);
        anadir2 = (Button) findViewById(R.id.btnAnadir2);
        relativeEntrada = (RelativeLayout) findViewById(R.id.RelativeEntrada);
        relativeSalida = (RelativeLayout) findViewById(R.id.RelativeSalida);
        relativeTitle = (RelativeLayout) findViewById(R.id.RelativeExpandableTitle);
        relativePadre = (RelativeLayout) findViewById(R.id.relativePadre);
        ctrLunes = (ControlDiaSem) findViewById(R.id.ctrLunes);
        ctrMartes = (ControlDiaSem) findViewById(R.id.ctrMartes);
        ctrMiercoles = (ControlDiaSem) findViewById(R.id.ctrMiercoles);
        ctrJueves = (ControlDiaSem) findViewById(R.id.ctrJueves);
        ctrViernes = (ControlDiaSem) findViewById(R.id.ctrViernes);
        ctrSabado = (ControlDiaSem) findViewById(R.id.ctrSabado);
        ctrDomingo = (ControlDiaSem) findViewById(R.id.ctrDomingo);
        timeEntrada = (TimePickerV2) findViewById(R.id.timeEntrada);
        timeSalida = (TimePickerV2) findViewById(R.id.timeSalida);
        expandablePicker = (ExpandableRelativeLayout) findViewById(R.id.expandablePicker);
        expandablePicker2 = (ExpandableRelativeLayout) findViewById(R.id.expandablePicker2);
        expandablePicker3 = (ExpandableRelativeLayout) findViewById(R.id.expandablePicker3);
        tvEntradaHoraPicker = (TextView) findViewById(R.id.tvEntradaHoraPicker);
        tvSalidaHoraPicker = (TextView) findViewById(R.id.tvSalidaHoraPicker);
        relativeSalida.setOnClickListener(this);
        relativeTitle.setOnClickListener(this);
        anadir.setOnClickListener(this);
        relativeEntrada.setOnClickListener(this);
        anadir.setOnClickListener(this);
        anadir2.setOnClickListener(this);
        expandablePicker.expand();
        expandablePicker2.expand();
        ctrLunes.setDia(context.getString(R.string.Lunes));
        ctrMartes.setDia(context.getString(R.string.Martes));
        ctrMiercoles.setDia(context.getString(R.string.Miercoles));
        ctrJueves.setDia(context.getString(R.string.Jueves));
        ctrViernes.setDia(context.getString(R.string.Viernes));
        ctrSabado.setDia(context.getString(R.string.Sabado));
        ctrDomingo.setDia(context.getString(R.string.Domingo));
        timeEntrada.setTipoPicker(TimePickerV2.TYPE_PICKER.SALIDA);
//        obtenerHoraEntrada();
        //timeEntrada.setTipoPicker(9,0,0);
        timeSalida.setTipoPicker(TimePickerV2.TYPE_PICKER.LLEGADA);
//        obtenerHoraSalida();

        super.onFinishInflate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RelativeExpandableTitle: {
                expandablePicker.toggle();
                expandablePicker2.toggle();
                expandablePicker3.toggle();
            }
            break;
            case R.id.btnAnadir: {

                   obtenerHoraEntrada();
                   expandablePicker2.toggle();
                   expandablePicker3.toggle();

            }
            break;
            case R.id.RelativeEntrada: {

                if(expandablePicker3.isExpanded()&& expandablePicker2.isExpanded()) {

                    expandablePicker2.toggle();
                    expandablePicker3.toggle();

                }else{
                    expandablePicker3.toggle();
                    expandablePicker2.toggle();

                }
            }
            break;
            case R.id.RelativeSalida: {

                if(expandablePicker3.isExpanded()&& !expandablePicker2.isExpanded()) {

                    expandablePicker.toggle();

                }else if(expandablePicker3.isExpanded()&& expandablePicker2.isExpanded()) {

                    expandablePicker2.toggle();

                }else{

                    expandablePicker3.toggle();

                }
            }
            break;

            case R.id.btnAnadir2: {

                obtenerHoraSalida();
                expandablePicker3.toggle();
                expandablePicker.toggle();

            }
            break;
        }
    }

    public void desactivarClick() {

        relativeTitle.setClickable(false);
        ctrLunes.desactivarClick();
        ctrLunes.noCheck();
        ctrMartes.desactivarClick();
        ctrMartes.noCheck();
        ctrMiercoles.desactivarClick();
        ctrMiercoles.noCheck();
        ctrJueves.desactivarClick();
        ctrJueves.noCheck();
        ctrViernes.desactivarClick();
        ctrViernes.noCheck();
        ctrSabado.desactivarClick();
        ctrSabado.noCheck();
        ctrDomingo.desactivarClick();
        ctrDomingo.noCheck();
        relativePadre.setBackgroundColor(getResources().getColor(R.color.gris_info_transparente));

        if (expandablePicker.isExpanded()) {
            expandablePicker.toggle();
        }
    }

    public void activarClick() {

        relativeTitle.setClickable(true);
        ctrLunes.activarClick();
        ctrLunes.check();
        ctrMartes.activarClick();
        ctrMartes.check();
        ctrMiercoles.activarClick();
        ctrMiercoles.check();
        ctrJueves.activarClick();
        ctrJueves.check();
        ctrViernes.activarClick();
        ctrViernes.check();
        ctrSabado.activarClick();
        ctrSabado.check();
        ctrDomingo.activarClick();
        ctrDomingo.check();
        relativePadre.setBackgroundColor(getResources().getColor(android.R.color.white));

    }

    public String obtenerHoraEntrada() {

        String horaEntrada = String.valueOf(timeEntrada.getHora());
        horaEntrada = (horaEntrada.length() == 1) ? ("0" + horaEntrada) : horaEntrada;
        String minutosEntrada = String.valueOf(timeEntrada.getMinutos());
        minutosEntrada = (minutosEntrada.length() == 1) ? ("0" + minutosEntrada) : minutosEntrada;
        tvEntradaHoraPicker.setText(horaEntrada.concat(":").concat(minutosEntrada));
        tvEntrada.setText("Entrada ".concat(horaEntrada.concat(":").concat(minutosEntrada)));
        return horaEntrada.concat(":").concat(minutosEntrada).concat(":00");
    }

    public String obtenerHoraSalida() {
//        if (timeSalida.getHora() <= timeEntrada.getHora()) {
//            Toast.makeText(context, "La hora de salida no debe ser menor o igual a la de entrada.", Toast.LENGTH_SHORT).show();
//            String horaSalida = String.valueOf(timeSalida.getHora());
//            horaSalida = (horaSalida.length() == 1) ? ("0" + horaSalida) : horaSalida;
//            timeSalida.setHora(timeEntrada.getHora() + 1);
//            String minutosSalida = String.valueOf(timeSalida.getMinutos());
//            minutosSalida = (minutosSalida.length() == 1) ? ("0" + minutosSalida) : minutosSalida;
//            tvSalidaHoraPicker.setText(horaSalida.concat(":").concat(minutosSalida));
//            tvSalida.setText("Salida ".concat(horaSalida.concat(":").concat(minutosSalida)));
//            expandablePicker.toggle();
//            return horaSalida.concat(":").concat(minutosSalida).concat(":00");
//        } else {
        String horaSalida = String.valueOf(timeSalida.getHora());
        horaSalida = (horaSalida.length() == 1) ? ("0" + horaSalida) : horaSalida;
        String minutosSalida = String.valueOf(timeSalida.getMinutos());
        minutosSalida = (minutosSalida.length() == 1) ? ("0" + minutosSalida) : minutosSalida;
        tvSalidaHoraPicker.setText(horaSalida.concat(":").concat(minutosSalida));
        tvSalida.setText("Salida ".concat(horaSalida.concat(":").concat(minutosSalida)));
//        expandablePicker.toggle();
        return horaSalida.concat(":").concat(minutosSalida).concat(":00");
//        }
    }

    public void setHeight(int wrapContent) {

    }

    public boolean validar () {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.HOUR_FORMAT1);
        //timeEntrada.sett
        boolean validacion;
        if (timeEntrada.getCalendar().getTime().after(timeSalida.getCalendar().getTime())){
            validacion = false;
        }else if(timeEntrada.getCalendar().getTime().getHours()==timeSalida.getCalendar().getTime().getHours()&&
                timeEntrada.getCalendar().getTime().getMinutes()==timeSalida.getCalendar().getTime().getMinutes()){
            validacion=false;
        }else
            validacion=true;

        return validacion;
    }
}
