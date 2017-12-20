package com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.Pais;

import java.util.ArrayList;

/**
 * Created by oemy9 on 18/04/2017.
 */

@SuppressWarnings("unused")
public class AdaptadorPaises extends BaseAdapter {

    private Context context;
    private ArrayList<Pais>listPaises;
    private LayoutInflater layoutInflater;

    public AdaptadorPaises(Context context, ArrayList<Pais>listPaises){
        this.listPaises=listPaises;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return this.listPaises.size();
    }

    @Override
    public Pais getItem(int position) {
        return this.listPaises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_pais, parent, false);
        ImageView imagenPais=(ImageView)convertView.findViewById(R.id.imagenPais);
        TextView  tvPaisNombre=(TextView)convertView.findViewById(R.id.tvPaisNombre);
        TextView  tvCodigo=(TextView)convertView.findViewById(R.id.tvCodigo);
        int codigo= Integer.valueOf(getItem(position).getCodigo());
        imagenPais.setImageResource(getResourceFile(codigo));
        tvPaisNombre.setText(getItem(position).getNombre());
        tvCodigo.setText(getLada(getItem(position).getCodigo()).toString());

        return  convertView;
    }
     public Integer getLada(String codigo){
         Integer lada=0;
         switch (Integer.parseInt(codigo)){
             case 2:
                 lada=52;
                 break;
             case 1:
                 lada=51;
                 break;
             case 3:
                 lada=1;
                 break;
             default:
                 lada=502;
         }
        return  lada;
     }

    public int getResourceFile(int codigo)
    {
        int bandera=0;
        switch (codigo){
            case 2:
                bandera=R.drawable.mexico;
                break;
            case 1:
                bandera=R.drawable.peru;
                break;
            case 3:
                bandera=R.drawable.eu;
                break;
            default:
                bandera=R.drawable.guatemala;
                break;
        }
        return  bandera;
    }
}
