package com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Enums.EnumContacto;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionContacto;

import java.util.ArrayList;

/**
 * Created by oemy9 on 19/08/2017.
 */

public class AdapterContacto extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<PromocionContacto>listContacto;

    public AdapterContacto(Context context,ArrayList<PromocionContacto>listContacto) {
        this.context = context;
        this.listContacto=listContacto;
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return listContacto.size();
    }

    @Override
    public PromocionContacto getItem(int i) {
        return listContacto.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = this.layoutInflater.inflate(R.layout.item_promocion_contacto, viewGroup, false);
        TextView tvDetalle=(TextView)view.findViewById(R.id.tvDetalle);
        TextView tvDescripcion=(TextView)view.findViewById(R.id.tvDescripcion);
        ImageView imageContacto=(ImageView)view.findViewById(R.id.imageContacto);
        PromocionContacto item=getItem(position);
        tvDescripcion.setText(item.getTipo().toString());
        tvDetalle.setText(item.getDetalle());
        imageContacto.setImageResource(getImagenDetalle(item));
        if(item.getTipo()== EnumContacto.Condiciones){
            tvDetalle.setTextSize(14);
        }
        return view;
    }
    private int getImagenDetalle(PromocionContacto item){
        int imagen=0;
        if(item.getTipo()==EnumContacto.Telefono){
            imagen=R.mipmap.ic_phone_black;
        }
        else if(item.getTipo()==EnumContacto.Domicilio){
            imagen=R.mipmap.ic_building_action;
        }
        else{
            imagen=R.mipmap.ic_condiciones;
        }
        return imagen;
    }
}
