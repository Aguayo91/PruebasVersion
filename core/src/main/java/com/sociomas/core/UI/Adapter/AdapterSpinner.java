package com.sociomas.core.UI.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.R;
import com.sociomas.core.Utils.Utils;

import java.util.List;

/**
 * Created by oemy9 on 09/06/2017.
 */

public class AdapterSpinner extends ArrayAdapter<Plantilla> implements SpinnerAdapter{
    private Context context;
    private List<Plantilla>items;
    private LayoutInflater layoutInflater;
    private String delimitador;
    private String tituloDialogo;

    public AdapterSpinner(@NonNull Context context, @LayoutRes int resource, @NonNull List<Plantilla> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void setTituloDialogo(String tituloDialogo) {
        this.tituloDialogo = tituloDialogo;
    }

    @Override
    public Plantilla getItem(int position) {
        return this.items.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.spinner_textview,parent,false);
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        String titulo;
        String nombreUsuario= Utils.toTitleCase(this.getItem(position).getNombreEmpleado());
        titulo=  delimitador!=null?Utils.toUppperCaseFirst(delimitador.concat(" ")).concat(nombreUsuario): nombreUsuario;
        tvTitulo.setText(titulo);
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.spinner_opciones,parent,false);
        convertView.setBackgroundColor(ContextCompat.getColor(this.context,R.color.colorPrimary));
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        TextView tvTituloSpinner=(TextView)convertView.findViewById(R.id.tvTituloSpinner);
        RelativeLayout header=(RelativeLayout)convertView.findViewById(R.id.header);
        if(tituloDialogo!=null){
            tvTituloSpinner.setText(tituloDialogo);
        }
        header.setVisibility(position==0?View.VISIBLE:View.GONE);
        tvTitulo.setText(Utils.toTitleCase(this.getItem(position).getNombreEmpleado()));
        return convertView;
    }
    public void setDelimitador(String delimitador) {
        this.delimitador = delimitador;
    }
}
