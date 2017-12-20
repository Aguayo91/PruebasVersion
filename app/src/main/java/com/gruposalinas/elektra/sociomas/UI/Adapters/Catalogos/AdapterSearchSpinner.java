package com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by oemy9 on 02/02/2017.
 */

@SuppressWarnings("unused")
public class AdapterSearchSpinner extends ArrayAdapter<EmpleadoIncidencia> implements SpinnerAdapter {
public static final String TODOS="TODOS";
    private HashMap<String,EmpleadoIncidencia>hashEmpleados;
    private Context context;
    private ArrayList<EmpleadoIncidencia>listItems;
    private LayoutInflater inflater;
    public AdapterSearchSpinner(Context ctx, HashMap<String,EmpleadoIncidencia>hashEmpleados){
        super(ctx,0);
        this.context=ctx;
        this.inflater= LayoutInflater.from(this.context);
        this.listItems=new ArrayList<>();
        this.hashEmpleados=hashEmpleados;
        hashEmpleadosToArrayList();

    }

    private void hashEmpleadosToArrayList(){
         listItems.add(new EmpleadoIncidencia(TODOS,null));
         for(EmpleadoIncidencia item:hashEmpleados.values()){
             listItems.add(item);
         }
    }
    public String getIdEmpleado(String texto){
            String idEmpleado=null;
            for(Map.Entry<String,EmpleadoIncidencia> entry: hashEmpleados.entrySet()){
                if(entry.getValue().getDecripcionEmpleado().equals(texto)){
                    idEmpleado=entry.getKey();
                }
            }
            return idEmpleado;
    }

    public int getCount(){
        return listItems.size();
    }
    public EmpleadoIncidencia getItem(int position){
        return listItems.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        if(!getItem(position).getDecripcionEmpleado().equals(TODOS))
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        label.setText(this.getItem(position).getDecripcionEmpleado());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView=inflater.inflate(R
                .layout.spinner_opciones_plantilla,parent,false);
        RelativeLayout header=(RelativeLayout)convertView.findViewById(R.id.header);
        header.setVisibility(View.GONE);
        TextView tvTitulo=(TextView)convertView.findViewById(R.id.tvOpcionesItem);
        tvTitulo.setText(this.getItem(position).getDecripcionEmpleado());
        return convertView;
    }




}
