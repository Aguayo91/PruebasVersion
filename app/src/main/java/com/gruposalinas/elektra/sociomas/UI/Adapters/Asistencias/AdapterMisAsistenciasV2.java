package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;

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
import com.sociomas.aventones.UI.Adapters.IAdapterBase;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 24/11/2017.
 */

public class AdapterMisAsistenciasV2  extends RecyclerView.Adapter<AdapterMisAsistenciasV2.ViewHolder> implements IAdapterBase {

    private List<ResultadoAsistencia> listAsistencias;
    private RecyclerItemClickListener listener;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat dayFormat=new SimpleDateFormat(Constants.DAY_FORMAT);
    private Calendar c=Calendar.getInstance();


    public AdapterMisAsistenciasV2(Context context, List<ResultadoAsistencia> userModelList) {
        this.listAsistencias = userModelList;
        this.inflater=LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_mis_asistencias_v2, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public String getDayName(String inputDate){
        String day=null;
        try {
            c.setTime(dayFormat.parse(inputDate));
            day= Utils.toUppperCaseFirst(new SimpleDateFormat("EEEE",new Locale("es")).format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResultadoAsistencia item = listAsistencias.get(position);
        EnumAsistencia tipoAsistencia=EnumAsistencia.fromValue(item.getIdEstado());
        String dayName=getDayName(item.getFechaReporte());
        holder.tvFecha.setText(dayFormat==null? item.getFechaReporte(): item.getFechaReporte().concat("\n").concat(dayName));
        holder.tvHoraEntrada.setText(tipoAsistencia!=EnumAsistencia.FALTA? item.getHoraEntrada(): context.getString(R.string.default_hora));
        holder.tvHoraSalida.setText(tipoAsistencia!=EnumAsistencia.FALTA? item.getHoraSalida(): context.getString(R.string.default_hora));

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


    public void AdapterMisAsistenciasV2(ArrayList<ResultadoAsistencia> list) {
        this.listAsistencias = list;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return listAsistencias.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;

    }

    @Override
    public Object getItem(int position) {
        return null;
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




