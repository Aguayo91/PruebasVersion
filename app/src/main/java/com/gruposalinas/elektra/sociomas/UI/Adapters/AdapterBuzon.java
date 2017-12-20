package com.gruposalinas.elektra.sociomas.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones.NotificacionesExtendidas;
import com.gruposalinas.elektra.sociomas.UI.Presenters.BuzonPresenter;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogConfirmarNotificaciones;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jmarquezu on 27/11/2017.
 */

public class AdapterBuzon extends RecyclerView.Adapter<AdapterBuzon.ViewHolder> {

    private BuzonPresenter presenter;
    private List<NotificacionResponse> list;
    private LayoutInflater li;
    public ArrayList<String> mensaje;
    public ArrayList<Calendar> calendar;
    public ArrayList<Boolean> leido;
    public Context context;
    public boolean checkbox = false;
    int i;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensaje, tvHora, tvFecha;
        TextView viewLeido;
        int posicion;
        CheckBox cbSeleccionar;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMensaje = (TextView) itemView.findViewById(R.id.tvMensaje);
            tvHora = (TextView) itemView.findViewById(R.id.tvHora);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            viewLeido = (TextView) itemView.findViewById(R.id.viewLeido);
            cbSeleccionar = (CheckBox) itemView.findViewById(R.id.cbSeleccionar);
        }

        public void bindString(String mensaje, Calendar calendar, boolean leido) {
            SimpleDateFormat dfHour = new SimpleDateFormat("HH:mm");
            String hora = dfHour.format(calendar.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yyyy");
            String fecha = df.format(calendar.getTime());
            tvHora.setText(hora);
            tvMensaje.setText(mensaje);
            tvFecha.setText(fecha);
            if (leido) {
                viewLeido.setVisibility(View.GONE);
            }
            if (checkbox) {
                cbSeleccionar.setVisibility(View.VISIBLE);
            } else {
                cbSeleccionar.setVisibility(View.GONE);
            }
        }

        public void bindData(NotificacionResponse notificacionResponse) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yy");
            SimpleDateFormat sdfTIme = new SimpleDateFormat("HH:mm:ss");
            String fecha;
            String hora;
            Date date;
            Date time;
            String[] splited = notificacionResponse.getFecha_hora().split("\\s+");
            try {
                date = sdfDate.parse(splited[0]);
                fecha = sdfDate.format(date);
                time = sdfTIme.parse(splited[1]);
                hora = sdfTIme.format(time);
            } catch (Exception e) {
                Log.e("bindData", e.toString());
                fecha = splited[0];
                hora = splited[1];
            }
            tvFecha.setText(String.valueOf(fecha));
            tvHora.setText(String.valueOf(hora));
            if (notificacionResponse.getId_estatus_notificacion() == EnumEstatusNotificacion.ENVIADA.getValue()) {
                viewLeido.setVisibility(View.VISIBLE);

            } else if (notificacionResponse.getId_estatus_notificacion() == EnumEstatusNotificacion.LEIDA.getValue()) {
                viewLeido.setVisibility(View.GONE);
            }
            tvMensaje.setText(notificacionResponse.getMsg_notificacion());

            if (checkbox) {
                cbSeleccionar.setVisibility(View.VISIBLE);
            } else {
                cbSeleccionar.setVisibility(View.GONE);
            }
        }
    }

    public AdapterBuzon(Context context, ArrayList mensajes, ArrayList calendarios, ArrayList leidos) {
        this.mensaje = mensajes;
        this.calendar = calendarios;
        this.leido = leidos;
        this.context = context;
    }

    public AdapterBuzon(Context context, List<NotificacionResponse> list, BuzonPresenter presenter) {
        this.context = context;
        this.li = LayoutInflater.from(this.context);
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public AdapterBuzon.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buzon, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        final String item = mensaje.get(position);
//        final Calendar calendario = calendar.get(position);
//        final boolean leidos = leido.get(position);
//        holder.bindString(item, calendario, leidos);
        holder.bindData(list.get(position));
//        if (!checkbox) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificacionResponse nr = list.get(position);
                    if (nr.getId_estatus_notificacion() != EnumEstatusNotificacion.LEIDA.getValue()) {
                        presenter.modificarEstatusNotificacion(context, nr);
                    } else {
                        Intent detalleNotify = new Intent(nr.getClickAction());
                        detalleNotify.putExtra(Constants.DATA_SEND, nr.getDatos_movil());
                        detalleNotify.putExtra(Constants.ID_NOTIFICACION, nr.getId_estatus_notificacion());
                        Bundle bundle = new Bundle();
                        bundle.putString("titulo", nr.getTitulo());
                        bundle.putString("mensaje", nr.getMsg_notificacion());
                        bundle.putSerializable(ViewEvent.ENTRY, nr);
                        detalleNotify.putExtras(bundle);
                        context.startActivity(detalleNotify);
                    }
                    //Siguiente Actividad
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //holder.cbSeleccionar.setVisibility(View.VISIBLE);
                    i = 1;
                    DialogConfirmarNotificaciones dialog = new DialogConfirmarNotificaciones(context);
                    dialog.show();
                    dialog.setListener(new DialogConfirmarNotificaciones.OnClickLDialogConfirmarListener() {
                        @Override
                        public void onOkListener() {
//                            Toast.makeText(context,"Borrrar desde adapter",Toast.LENGTH_SHORT).show();
                            presenter.borrarNotificacion(context, list.get(position));
                        }
                    });
                    //holder.cbSeleccionar.setChecked(true);
                    //checkbox = true;
                    notifyDataSetChanged();
                    return false;
                }
            });
        /*}else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.cbSeleccionar.isChecked()) {
                        holder.cbSeleccionar.setChecked(false);
                        i = i - 1;
                        Log.v("MyApp", "" + i);
                        if (i == 0) {
                            checkbox = false;
                            notifyDataSetChanged();
                        }
                    } else {
                        holder.cbSeleccionar.setChecked(true);
                        i = i + 1;
                    }
                }
            });
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
