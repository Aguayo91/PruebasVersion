package com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.vipulasri.timelineview.TimelineView;
import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders.ViewHolderTimeLine;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.VectorDrawableUtils;
import com.gruposalinas.elektra.sociomas.IO.AsyncTasks.GeocodingAsync;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by oemy9 on 31/05/2017.
 */

public class AdapterTimeLine extends RecyclerView.Adapter<ViewHolderTimeLine> {
    public  interface ItemClickListener{
        void OnClickItem(int position);
    }
    private ItemClickListener itemClickListener;
    private Context context;
    private ArrayList<Ubicaciones>listUbicaciones;
    private HashMap<Integer,String> hashGeocoding=new HashMap<>();
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormatHora;
    private Date dateSalida;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterTimeLine(ArrayList<Ubicaciones>listUbicaciones){
        this.listUbicaciones=listUbicaciones;
        this.simpleDateFormat=new SimpleDateFormat(Constants.DATE_FORMAT);
        this.simpleDateFormatHora=new SimpleDateFormat(Constants.HOUR_FORMAT);

    }
    @Override
    public void onBindViewHolder(final ViewHolderTimeLine holder, final int position) {
             final Ubicaciones item=listUbicaciones.get(position);

             if(!item.isConsolidado()) {
                 holder.progressUbicacion.setVisibility(View.VISIBLE);
                 holder.tvDireccion.setVisibility(View.INVISIBLE);
                 holder.tvDate.setText(item.getFechaHora());
                 if(position!=0){
                     holder.mTimelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker_circle), ContextCompat.getColor(context, R.color.colorPrimary));
                 }
                 else{
                     holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, R.color.colorPrimary));
                 }

                 if (hashGeocoding.containsKey(position)){
                     holder.tvDireccion.setText(hashGeocoding.get(position));
                     holder.tvDireccion.setVisibility(View.VISIBLE);
                     holder.progressUbicacion.setVisibility(View.GONE);
                 }

                 else if(item.getPosicionValida()==0){
                     GeocodingAsync geocodingAsync = new GeocodingAsync(this.context);
                     geocodingAsync.setGeocodingInterface(new GeocodingAsync.GeocodingInterface() {
                         @Override
                         public void OnGeocodingFinish(String result) {
                             hashGeocoding.put(position, result);
                             holder.tvDireccion.setText(result);
                             holder.progressUbicacion.setVisibility(View.GONE);
                             holder.tvDireccion.setVisibility(View.VISIBLE);
                         }
                     });
                     geocodingAsync.execute(new Double[]{item.getLatitud(), item.getLongitud()});
                 }

                 Picasso.with(this.context).load(AdapterUtils.getImagenActionByString(item.getActividad())).into(holder.imgActividad);
             }


             else {
                 holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary));
                 holder.progressUbicacion.setVisibility(View.GONE);
                 holder.tvDireccion.setVisibility(View.VISIBLE);
                 holder.tvDireccion.setText(item.getZonaValidaConsolidada().toUpperCase(Locale.ENGLISH));

                 holder.tvDate.setText(Html.fromHtml("DE  <b>"+ convertToHourString(item.getFechaEntradaConsolidada())+ "</b> A <b>" +
                         ""+convertToHourString(item.getFechaSalidaConsolidada())+"</b>"));


                 Picasso.with(this.context).load(AdapterUtils.getImagenActionByString(Constants.OFICINA_CASA)).into(holder.imgActividad);

             }

            holder.tvBattery.setText(item.getBateria().concat(" %"));
            Double velocidad = Double.parseDouble(item.getVelocidad());
            holder.tvVelocidad.setText((velocidad>1?String.format("%1.2f",velocidad): "0.0") + " km/h");
            Picasso.with(context).load(AdapterUtils.getImageByDispositivo(item.getTipoDispositivo())).into(holder.imgType);
            Picasso.with(context).load(AdapterUtils.getImageBattery(Double.parseDouble(item.getBateria()))).into(holder.imgBattery);


            holder.cardViewTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (itemClickListener != null) {
                                itemClickListener.OnClickItem(position);
                            }
                        }
                    });

    }

    private String convertToHourString(String fechaHora){
        String salida="";
        try {
            dateSalida=simpleDateFormat.parse(fechaHora);
            salida=simpleDateFormatHora.format(dateSalida);
        } catch (ParseException e) {
            e.printStackTrace();
            salida=fechaHora;
        }
        return salida;
    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public int getItemCount() {
        return listUbicaciones.size();
    }

    @Override
    public ViewHolderTimeLine onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_timeline, parent, false);

        return new ViewHolderTimeLine(itemView);
    }
}
