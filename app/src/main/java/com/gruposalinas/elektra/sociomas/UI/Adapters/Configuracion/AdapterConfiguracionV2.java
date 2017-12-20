package com.gruposalinas.elektra.sociomas.UI.Adapters.Configuracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.ConfiguracionItem;

import java.util.ArrayList;

/**
 * Created by oemy9 on 21/07/2017.
 */

public class AdapterConfiguracionV2 extends BaseAdapter {

    private ArrayList<ConfiguracionItem>listItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterConfiguracionV2(Context context,ArrayList<ConfiguracionItem>listItems){
        this.context=context;
        this.listItems=listItems;
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public ConfiguracionItem getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.item_configuracion, parent, false);
        TextView textView=(TextView)view.findViewById(R.id.tipo);
        ImageView imageView=(ImageView) view.findViewById(R.id.justificacion);
        imageView.setImageResource(listItems.get(position).getImagen());
        textView.setText(listItems.get(position).getDescripcion());
        return view;
    }
}
