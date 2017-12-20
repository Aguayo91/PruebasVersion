package com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;

import java.util.List;

/**
 * Created by oemy9 on 09/06/2017.
 */

public class AdapterCatalogoEmpleado extends ArrayAdapter<SearchBoxItem> implements SpinnerAdapter{
    private Context context;
    private List<SearchBoxItem>items;
    private LayoutInflater layoutInflater;
    private String delimitador;
    private String tituloDialogo;

    public AdapterCatalogoEmpleado(@NonNull Context context, @LayoutRes int resource, @NonNull List<SearchBoxItem> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
        if(!this.items.isEmpty()) {
            if (this.items.get(0).getId().equals(SearchBoxDialog.TODOS)) {
                this.items.remove(0);
            }
        }
        this.layoutInflater=LayoutInflater.from(context);


    }

    public void setTituloDialogo(String tituloDialogo) {
        this.tituloDialogo = tituloDialogo;
    }

    @Override
    public SearchBoxItem getItem(int position) {
        return this.items.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.spinner_textview,parent,false);
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        String titulo;
        String nombreUsuario=Utils.toTitleCase(this.getItem(position).getValue());
        titulo=  delimitador!=null?Utils.toUppperCaseFirst(delimitador.concat(" ")).concat(nombreUsuario): nombreUsuario;
        tvTitulo.setText(titulo);
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.spinner_opciones_plantilla,parent,false);
        convertView.setBackgroundColor(ContextCompat.getColor(this.context,R.color.colorPrimary));
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        TextView tvTituloSpinner=(TextView)convertView.findViewById(R.id.tvTituloSpinner);
        TextView tvUserId=(TextView)convertView.findViewById(R.id.tvUserId);
        RelativeLayout header=(RelativeLayout)convertView.findViewById(R.id.header);
        if(tituloDialogo!=null){
            tvTituloSpinner.setText(tituloDialogo);
           // tvTituloSpinner.setBackgroundColor(Color.WHITE);
        }
        header.setVisibility(position==0?View.VISIBLE:View.GONE);
        tvTitulo.setText(Utils.toTitleCase(this.getItem(position).getValue()));
        tvUserId.setText(getContext().getString(com.sociomas.core.R.string.idEmpleado).concat( com.sociomas.core.Utils.Utils.toTitleCase(this.getItem(position).getId())));
        return convertView;
    }
    public void setDelimitador(String delimitador) {
        this.delimitador = delimitador;
    }
}
