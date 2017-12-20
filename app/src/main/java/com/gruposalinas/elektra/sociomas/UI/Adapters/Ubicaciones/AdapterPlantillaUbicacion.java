package com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Date.TimeUtils;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by oemy9 on 02/05/2017.
 */

public class AdapterPlantillaUbicacion extends BaseAdapter implements Filterable {
    private HashMap<String,String>hashGeocoding=new HashMap<>();
    public static class ViewHolder{
        public CircleImageView ImgEmpleado;
        public TextView tvName;
        public TextView tvLatLong;
        public TextView tvAddress;
        public TextView tvDate;
        public ImageView imgType;
        public ImageView imgBattery;
        public TextView  tvVelocidad;
        public TextView tvBattery;
        public ImageView imgStatus;
        public ImageView imgActividad;
        public TextView tvAccuracy;

    }
    private ItemFilter mFilter;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Ubicaciones> lisUbicaciones;

    public AdapterPlantillaUbicacion(Context context, ArrayList<Ubicaciones> lisUbicaciones){
        this.lisUbicaciones=lisUbicaciones;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.mFilter=new ItemFilter();
    }

    public void setLisUbicaciones(ArrayList<Ubicaciones> lisUbicaciones) {
        if(!lisUbicaciones.isEmpty()) {
            this.lisUbicaciones = lisUbicaciones;
            notifyDataSetChanged();
        }
    }



    @Override
    public int getCount() {
        return lisUbicaciones.size();
    }

    @Override
    public Ubicaciones getItem(int position) {
        return lisUbicaciones.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_empleado_ubicacion, parent, false);
            holder.tvLatLong=(TextView)convertView.findViewById(R.id.tvLatLong);
            holder.tvAddress=(TextView)convertView.findViewById(R.id.tvDireccion);
            holder.tvName=(TextView)convertView.findViewById(R.id.tvName);
            holder.ImgEmpleado=(CircleImageView)convertView.findViewById(R.id.imgEmpleado);
            holder.tvDate =(TextView)convertView.findViewById(R.id.tvStatus);
            holder.imgType=(ImageView)convertView.findViewById(R.id.img_type);
            holder.imgBattery=(ImageView)convertView.findViewById(R.id.img_battery);
            holder.tvVelocidad=(TextView)convertView.findViewById(R.id.tv_velocidad);
            holder.tvBattery=(TextView)convertView.findViewById(R.id.tvBattery);
            holder.imgStatus=(ImageView)convertView.findViewById(R.id.img_status);
            holder.imgActividad=(ImageView)convertView.findViewById(R.id.img_actividad);
            holder.tvAccuracy=(TextView)convertView.findViewById(R.id.tv_accuracy);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        final Ubicaciones movimiento=getItem(position);
        holder.tvName.setText(movimiento.getNombre().toUpperCase(Locale.ENGLISH));
        holder.tvLatLong.setText("Lat/Lng: "+movimiento.getLatitud() + "/" + movimiento.getLongitud());
        holder.tvBattery.setText(movimiento.getBateria().concat(" %"));
        Double velocidad=Double.parseDouble(movimiento.getVelocidad());
        holder.tvVelocidad.setText((velocidad>1?String.format("%1.2f",velocidad): "0.0") +" km/h");
        holder.tvAccuracy.setText(movimiento.getAccuracy()+ " mts.");
        Picasso.with(context).load(R.drawable.perfil_ekt).into(holder.ImgEmpleado);
        Picasso.with(context).load(AdapterUtils.getIconoByActividad(movimiento.getActividad())).into(holder.imgActividad);
        Picasso.with(context).load(AdapterUtils.getImageBattery(Double.parseDouble(movimiento.getBateria()))).into(holder.imgBattery);
        Picasso.with(context).load(AdapterUtils.getImageByDispositivo(movimiento.getTipoDispositivo())).into(holder.imgType);
        Picasso.with(context).load(AdapterUtils.getImagenByMovimiento(movimiento)).into(holder.imgStatus);
        Picasso.with(context).load(AdapterUtils.getImagenActionByString(movimiento.getActividad())).into(holder.imgActividad);

        if (movimiento.getPosicionValida()==1 && movimiento.getNombrePosicionValida() != null) {
           holder.tvAddress.setText(movimiento.getNombrePosicionValida().toUpperCase(Locale.ENGLISH));
        }
        else{
            if(!hashGeocoding.containsKey(movimiento.getIdNumEmpleado())) {

                GeocodingAsync geocodingAsync = new GeocodingAsync(this.context);
                geocodingAsync.setGeocodingInterface(new GeocodingAsync.GeocodingInterface() {
                    @Override
                    public void OnGeocodingFinish(String result) {
                        hashGeocoding.put(movimiento.getIdNumEmpleado(),result);
                        holder.tvAddress.setText(hashGeocoding.get(movimiento));
                    }
                });
                geocodingAsync.execute(new Double[]{movimiento.getLatitud(), movimiento.getLongitud()});
            }
            else{
                holder.tvAddress.setText(hashGeocoding.get(movimiento.getIdNumEmpleado()));
            }
        }

        holder.tvDate.setText(getFechaFormato(movimiento.getFechaHora()));
        return  convertView;
    }

    private String getFechaFormato(String fecha) {
        String retorno="";
        try {
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
            Date fechaObtenida = format.parse(fecha);
            TimeUtils timeUtils=new TimeUtils(this.context);
            retorno=timeUtils.getDateDifferenceForDisplay(fechaObtenida);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
    class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase(Locale.ENGLISH);
            FilterResults results = new FilterResults();
            final ArrayList<Ubicaciones> nlist = new ArrayList<Ubicaciones>();
            for(Ubicaciones item: lisUbicaciones){
                if(item.getNombre().toLowerCase(Locale.ENGLISH).startsWith(filterString)|| item.getNombre().toLowerCase(Locale.ENGLISH).contains(filterString)
                        || item.getIdNumEmpleado().startsWith(filterString)|| item.getNombre().toLowerCase(Locale.ENGLISH).indexOf(filterString)>=0){
                    nlist.add(item);
                }
            }
            results.values = nlist.size()>0? nlist: lisUbicaciones;
            results.count = nlist.size()>0? nlist.size(): lisUbicaciones.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Ubicaciones> filteredData = (ArrayList<Ubicaciones>) results.values;
            lisUbicaciones=filteredData;
            notifyDataSetChanged();
        }

    }


}
