package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.http.PUT;

/**
 * Created by oemy9 on 27/11/2017.
 */

public class AdapterJustificacionV2 extends RecyclerView.Adapter<AdapterJustificacionV2.ViewHolder> implements IAdapterBase {

    private List<ListadoIncidencias> listJustificaciones;
    private RecyclerItemClickListener listener;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat dateFormatTo=new SimpleDateFormat(Constants.DAY_FORMAT);
    private SimpleDateFormat dateFormatFrom=new SimpleDateFormat(Constants.DATE_FORMAT);
    private SimpleDateFormat hourFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);


    private Calendar c=Calendar.getInstance();

    public AdapterJustificacionV2(List<ListadoIncidencias> listJustificaciones, Context context) {
        this.listJustificaciones = listJustificaciones;
        this.context = context;
        this.inflater=LayoutInflater.from(this.context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_mis_asistencias_v2, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListadoIncidencias item=getItem(position);
        EnumAsistencia tipoAsistencia=EnumAsistencia.fromString(item.getIncidencia());
        if(tipoAsistencia!=null) {

            String dayName = getDayName(item.getFechaReporte());
            String dateFormato=toStringDate(item.getFechaReporte());
            holder.tvFecha.setText(dayName == null && dateFormato==null ? item.getFechaReporte() : dateFormato.concat("\n").concat(dayName));
            holder.tvHoraEntrada.setText(tipoAsistencia != EnumAsistencia.FALTA ? getHour(item.getFechaOcurrencia()) : context.getString(R.string.falta));
            holder.tvHoraEntrada.setTextColor(tipoAsistencia != EnumAsistencia.FALTA ? Color.BLACK : ContextCompat.getColor(context, R.color.rojo_falta));
            holder.tvHoraSalida.setText(tipoAsistencia!=EnumAsistencia.FALTA? getHour(item.getFechaSalidaOcurrencia()):"");


            switch (tipoAsistencia) {
                case FALTA:
                    Picasso.with(context).load(R.drawable.ic_falta).into(holder.imgStatus);
                    holder.linearDinamic.setBackground(obtenerShape(ContextCompat.getColor(context, R.color.rojo_falta)));
                    break;
                case ASISTENCIA_CORRECTA:
                    Picasso.with(context).load(R.drawable.ic_asistencia_correcta).into(holder.imgStatus);
                    holder.linearDinamic.setBackground(obtenerShape(ContextCompat.getColor(context, R.color.verde_llegada_temprano)));
                    break;
                case SALIDA_ANTES_HORARIO:
                    Picasso.with(context).load(R.drawable.ic_salidas).into(holder.imgStatus);
                    holder.linearDinamic.setBackground(obtenerShape(ContextCompat.getColor(context, R.color.naranja_salida_temprano)));
                    break;
                case ENTRADA_DESPUES_HORA_LIMITE:
                    Picasso.with(context).load(R.drawable.img_clock_yellow).into(holder.imgStatus);
                    holder.linearDinamic.setBackground(obtenerShape(ContextCompat.getColor(context, R.color.colorAmarilloEntrada)));
                    break;
                case RETARDO:
                    Picasso.with(context).load(R.drawable.ic_clock_tarde).into(holder.imgStatus);
                    holder.linearDinamic.setBackground(obtenerShape(ContextCompat.getColor(context, R.color.naranja_llegada_tarde)));
                    break;
            }
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        listener.OnItemClickListener(position, item);
                    }
                }
            });

    }


    @Override
    public int getItemCount() {
        return listJustificaciones.size();
    }

    public String getDayName(String inputDate){
        String day=null;
        try {
            c.setTime(dateFormatFrom.parse(inputDate));
            day= Utils.toUppperCaseFirst(new SimpleDateFormat("EEEE",new Locale("es")).format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public String getHour(String inputDate){
        String hour = null;
        if(inputDate!=null) {
            try {
                c.setTime(dateFormatFrom.parse(inputDate));
                hour = hourFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return hour;
    }



    public String toStringDate(String inputDate) {
        String date=null;
        try {
            c.setTime(dateFormatFrom.parse(inputDate));
            date=dateFormatTo.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public ShapeDrawable obtenerShape(@ColorInt int color){
        RectShape rectShape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(rectShape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.STROKE);
        drawable.getPaint().setStrokeWidth(5);
        return  drawable;
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;
    }

    @Override
    public ListadoIncidencias getItem(int position) {
        return listJustificaciones.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFecha;
        private TextView tvHoraEntrada;
        private TextView tvHoraSalida;
        private ImageView imgStatus;
        private LinearLayout linearDinamic;

        public ViewHolder(View v) {
            super(v);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            tvHoraEntrada = (TextView) v.findViewById(R.id.tvHoraEntrada);
            tvHoraSalida = ( TextView) v.findViewById(R.id.tvHoraSalida);
            imgStatus = (ImageView) v.findViewById(R.id.imgStatus);
            linearDinamic=(LinearLayout)v.findViewById(R.id.LinearDinamic);
        }
    }
}
