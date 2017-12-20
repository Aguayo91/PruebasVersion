package com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Response.Contacto.Clave;

import java.util.ArrayList;

/**
 * Created by oemy9 on 11/05/2017.
 */

public class AdapterSpinnerPais extends ArrayAdapter<Clave> {
    private int resouce;
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Clave>listPaises;



    public AdapterSpinnerPais(Context context, int resource,ArrayList<Clave> listPaises) {
        super(context, resource);
        this.context=context;
        this.listPaises=listPaises;
        Clave clave=new Clave();
        clave.setPais("País");
        clave.setId(0);
        clave.setCodigo("9");
        this.listPaises.add(clave);
        this.resouce=resource;
        this.layoutInflater= LayoutInflater.from(this.context);
    }
    @Override
    public int getCount(){
        return listPaises.size()-1;
    }

    public Clave getItem(int position){
        return listPaises.get(position);
    }
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.item_pais_contacto, parent, false);
        ImageView imagenPais=(ImageView)convertView.findViewById(R.id.imagenPais);
        TextView tvOpcionesItem=(TextView)convertView.findViewById(R.id.tvOpcionesItem);

        if(position==getCount()){
            imagenPais.setVisibility(View.VISIBLE);
            tvOpcionesItem.setVisibility(View.GONE);
        }
        else{
            imagenPais.setVisibility(View.GONE);
            tvOpcionesItem.setVisibility(View.VISIBLE);
            tvOpcionesItem.setText(this.getItem(position).getPais());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        convertView = layoutInflater.inflate(resouce, parent, false);
        ImageView imagenPais=(ImageView)convertView.findViewById(R.id.imagenPais);
        TextView  tvPaisNombre=(TextView)convertView.findViewById(R.id.tvPaisNombre);
        TextView  tvCodigo=(TextView)convertView.findViewById(R.id.tvCodigo);
        int id= Integer.valueOf(getItem(position).getId());
        imagenPais.setImageResource(getResourceFile(id));
        tvPaisNombre.setText(getItem(position).getPais());
        tvCodigo.setText(getItem(position).getCodigo().toString());

        return convertView;
    }



    public int getResourceFile(int id)
    {
        int bandera=0;
        switch (id){
            case 2:
                bandera=R.drawable.ic_mex;
                break;
            case 1:
                bandera=R.drawable.peru;
                break;
            case 3:
                bandera=R.drawable.ic_eua;
                break;
            case 4:
                bandera=R.drawable.ic_guatemala;
                break;
            case 5:
                bandera=R.drawable.ic_salvador;
                break;
            case 6:
                bandera=R.drawable.ic_honduras;
                break;
            case 7:
                bandera=R.drawable.ic_panama;
                break;
            case 8:
                bandera=R.drawable.ic_chile;
                break;
            case 9:
                bandera=R.drawable.ic_colombia;
                break;
            case 10:
                bandera=R.drawable.ic_ecuador;
                break;
            case 11:
                bandera=R.drawable.ic_spain;
                break;
        }
        return  bandera;
    }

}

