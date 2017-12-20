package com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Response.Incidencia.Catalogo;

import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 06/06/2017.
 */

public class AdapterCatalogoIncidencia extends ArrayAdapter<Catalogo> implements SpinnerAdapter{
    private Context context;
    private List<Catalogo>items;
    private LayoutInflater layoutInflater;
    public AdapterCatalogoIncidencia(@NonNull Context context, @LayoutRes int resource, @NonNull List<Catalogo> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public Catalogo getItem(int position) {
        return this.items.get(position);
    }

    public int getPositionByDescription(String description){int selectedPostion=0;
       for(int j=0;j<items.size();j++){
           if(items.get(j).getDescripcion().toLowerCase(Locale.ENGLISH).equals(description.toLowerCase(Locale.ENGLISH))){
                 selectedPostion=j;
                 break;
           }
       }
       return selectedPostion;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(this.getItem(position).getDescripcion());
        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView=layoutInflater.inflate(R
                .layout.spinner_opciones,parent,false);
        RelativeLayout header=(RelativeLayout)convertView.findViewById(R.id.header);
        header.setVisibility(View.GONE);
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        tvTitulo.setText(this.getItem(position).getDescripcion());
        return convertView;
    }
}
