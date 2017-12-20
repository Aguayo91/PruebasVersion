package com.gruposalinas.elektra.sociomas.UI.Adapters.Log;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.RegistroGPS;

import java.util.ArrayList;

/**
 * Created by oemy9 on 04/04/2017.
 */

public class AdapterLog extends BaseAdapter {

     static  class ViewHolder{
         public   TextView tvLat;
         public   TextView tvFecha;
         public   TextView tvEnviado;
    }


    public static final String TAG="ADAPTER_LOG";
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<RegistroGPS>registroGPSArrayList;
    public AdapterLog(Context context, ArrayList<RegistroGPS>registroGPSArrayList){
        this.context=context;
        this.registroGPSArrayList=registroGPSArrayList;
        this.layoutInflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return this.registroGPSArrayList.size();
    }

    @Override
    public RegistroGPS getItem(int position) {
        return this.registroGPSArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            holder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_log, parent, false);
            holder.tvLat=(TextView)convertView.findViewById(R.id.tvLat_Long);
            holder.tvFecha=(TextView)convertView.findViewById(R.id.tvDate3);
            holder.tvEnviado=(TextView)convertView.findViewById(R.id.tvEnviado);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvLat.setText(" "+getItem(i).getLatitud() + "||" + getItem(i).getLongitud());
        holder.tvFecha.setText(getItem(i).getFecha() + " " + getItem(i).getHora());
        holder.tvEnviado.setTextColor(getItem(i).isSuccess()? Color.BLUE:Color.RED);
        holder.tvEnviado.setText(getItem(i).isSuccess()?"ENVIADO":"NO ENVIADO");
        return convertView;

    }
}
