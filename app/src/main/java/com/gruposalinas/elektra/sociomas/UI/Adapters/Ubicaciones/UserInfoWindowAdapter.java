package com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.IO.AsyncTasks.GeocodingAsync;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.util.HashMap;

/**
 * Created by oemy9 on 24/04/2017.
 */

public class UserInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private HashMap<String,String> hashGeocoding=new HashMap<>();
    private Boolean streetView;
    private Context context;
    private final  View  contentView;

    public  UserInfoWindowAdapter(View contentView, Context context, Boolean streetView){
        this.contentView=contentView;
        this.context=context;
        this.streetView=streetView;
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        //FINDVIEWS BY ID
        TextView tvName = (TextView) contentView.findViewById(R.id.tvName);
        TextView tvDate = (TextView)contentView.findViewById(R.id.tvStatus);
        TextView tvLatLong = (TextView)contentView.findViewById(R.id.tvLatLong);
        final TextView tvAddress = (TextView)contentView.findViewById(R.id.tvDireccion);
        ImageView img_icon = (ImageView)contentView.findViewById(R.id.imgActividad);
        TextView tvVelocidad=(TextView)contentView.findViewById(R.id.tv_velocidad);
        TextView tvBateria=(TextView)contentView.findViewById(R.id.tvBattery);
        TextView tvAccuracy=(TextView)contentView.findViewById(R.id.tvAccuracy);
        ImageView imgBattery=(ImageView)contentView.findViewById(R.id.img_battery);
        ImageView imgActividad=(ImageView)contentView.findViewById(R.id.img_actividad);
        ImageView imgType=(ImageView)contentView.findViewById(R.id.img_type);
        ImageView imgFlecha=(ImageView)contentView.findViewById(R.id.imgFlecha);

        final ProgressBar progressUbicacion=(ProgressBar)contentView.findViewById(R.id.progressUbicacion);
        final Ubicaciones movimiento=new Gson().fromJson(marker.getSnippet(),Ubicaciones.class);

        if(null!=movimiento) {
            tvName.setText(movimiento.getNombre());
            tvLatLong.setText("Lat/Lng: "+movimiento.getLatitud() + "/" + movimiento.getLongitud());
            tvDate.setText(movimiento.getFechaHora());
            Double velocidad=Double.parseDouble(movimiento.getVelocidad());
            tvVelocidad.setText((velocidad>1?String.format("%1.2f",velocidad): "0.0") +" km/h");
            tvBateria.setText(movimiento.getBateria().concat("%"));
            Double  accuracy= movimiento.getAccuracy()!=null?
                    Double.valueOf(movimiento.getAccuracy()): 0.0;
            tvAccuracy.setText(accuracy.toString().concat(" Mts."));
            img_icon.setImageResource(R.drawable.perfil_ekt);
            imgBattery.setImageResource(AdapterUtils.getImageBattery(Double.parseDouble(movimiento.getBateria())));
            imgActividad.setImageResource(AdapterUtils.getImagenActionByString(movimiento.getActividad()));
            imgType.setImageResource(AdapterUtils.getImageByDispositivo(movimiento.getTipoDispositivo()));
            imgFlecha.setImageResource(streetView? R.drawable.ic_street_view: R.drawable.ic_ruta_dos);

            final String hashMapKey=movimiento.getIdNumEmpleado().concat(String.valueOf(movimiento.getLongitud()));


            if (movimiento.getPosicionValida()==1 && movimiento.getNombrePosicionValida() != null) {
                tvAddress.setText(movimiento.getNombrePosicionValida());
                tvAddress.setTypeface(null, Typeface.BOLD);
                tvAddress.setVisibility(View.VISIBLE);
                progressUbicacion.setVisibility(View.GONE);

            }

            else if(hashGeocoding.containsKey(hashMapKey)){
                tvAddress.setText(hashGeocoding.get(hashMapKey));
                tvAddress.setVisibility(View.VISIBLE);
                progressUbicacion.setVisibility(View.GONE);
            }

            else if(movimiento.getPosicionValida()==0){
                GeocodingAsync geocodingAsync = new GeocodingAsync(this.context);
                geocodingAsync.setGeocodingInterface(new GeocodingAsync.GeocodingInterface() {
                    @Override
                    public void OnGeocodingFinish(String result) {
                         marker.hideInfoWindow();
                         hashGeocoding.put(hashMapKey,result);
                         tvAddress.setText(result);
                         tvAddress.setVisibility(View.VISIBLE);
                         progressUbicacion.setVisibility(View.GONE);
                         marker.showInfoWindow();
                    }
                });
                geocodingAsync.execute(new Double[]{movimiento.getLatitud(), movimiento.getLongitud()});
            }
        }

        return contentView;

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


}


