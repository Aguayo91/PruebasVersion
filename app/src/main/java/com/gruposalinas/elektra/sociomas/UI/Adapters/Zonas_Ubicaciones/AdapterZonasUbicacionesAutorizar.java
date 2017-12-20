package com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gruposalinas.elektra.sociomas.R;


import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by oemy9 on 23/06/2017.
 */

public class AdapterZonasUbicacionesAutorizar extends ArrayAdapter<LugarTrabajo> implements StickyListHeadersAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<LugarTrabajo>listPosicionesZonas=new ArrayList<>();

    public void clearItems(){
        this.listPosicionesZonas.clear();
    }

    public void setPosicionesZonas(List<LugarTrabajo>listPosiciones, boolean zona){
        ArrayList<LugarTrabajo>itemsRemove=new ArrayList<>();
        if(listPosiciones!=null && (!listPosiciones.isEmpty())) {
            for (LugarTrabajo item : listPosiciones) {
                if(!item.getBitValida() && !item.getIdEstatusPosic().equals("R")) {
                    item.setTipoPosicion(zona ? Constants.ZONAS : Constants.UBICACIONES);
                }
                else{
                    itemsRemove.add(item);
                }
            }
            listPosiciones.removeAll(itemsRemove);
            listPosicionesZonas.addAll(listPosiciones);
            notifyDataSetChanged();
        }
    }


    @SuppressWarnings("unused")
    public static class ViewHolder{
        TextView tvZonaNombre;
        ImageView imageViewCircleNombre;
        ImageView imageFlecha;
    }
    public static class HeaderViewHolder {
        TextView tvHeader;
    }

    public AdapterZonasUbicacionesAutorizar(@NonNull Context context, @LayoutRes int resource, @NonNull List<LugarTrabajo> listPosicionesZonas) {
        super(context, resource, listPosicionesZonas);
        this.context=context;
        this.listPosicionesZonas=listPosicionesZonas;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listPosicionesZonas.size();
    }

    @Nullable
    @Override
    public LugarTrabajo getItem(int position) {
       return this.listPosicionesZonas.get(position);
    }

    public boolean isUbicacion(LugarTrabajo lugarTrabajo){
        return lugarTrabajo.getTipoPosicion().equals(Constants.UBICACIONES);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_zona_ubicacion_autorizar, parent, false);
            viewHolder.tvZonaNombre=(TextView)convertView.findViewById(R.id.tvZonaNombre);
            viewHolder.imageViewCircleNombre=(ImageView)convertView.findViewById(R.id.imageCircleNombre);
            viewHolder.imageFlecha=(ImageView)convertView.findViewById(R.id.imageFlecha);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ColorGenerator generator=ColorGenerator.MATERIAL;
        int selectedColor=generator.getRandomColor();
        final LugarTrabajo selectedItem=getItem(position);
        final boolean isUbicacion=selectedItem.getTipoPosicion().equals(Constants.UBICACIONES);
        String title=isUbicacion? selectedItem.getVaNombrePos(): selectedItem.getVaDescripcionZnPosic();
        viewHolder.tvZonaNombre.setText(title);
        TextDrawable drawable = TextDrawable.builder().buildRound(title.substring(0, 1), selectedColor);
        viewHolder.imageViewCircleNombre.setImageDrawable(drawable);
        return convertView;
    }



    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new HeaderViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_header_zona_ubicacion, parent, false);
            viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        viewHolder.tvHeader.setText(getItem(position).getTipoPosicion());
        return  convertView;
    }

    @Override
    public long getHeaderId(int position){
        return  getItem(position).getTipoPosicion().hashCode();
    }
}
