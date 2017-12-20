package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;

/**
 * Created by oemy9 on 17/11/2017.
 */

public class AdapterLugarTFlip extends RecyclerView.Adapter<AdapterLugarTFlip.ViewHolder> implements IAdapterBase {

    private Context ctx;
    private ArrayList<LugarTrabajo>listLugarTrabajo;
    private boolean showDescription=true;
    private RecyclerItemClickListener listener;
    private LayoutInflater layoutInflater;
    public static final int MAX_SIZE=20;

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
            this.listener=itemClickListener;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public interface  NotifyListenerLugaresTrabajo{
        void onLimiteSuperado();
        void onClear();
    }

    private NotifyListenerLugaresTrabajo listenerLugaresTrabajo;


    public void clearItems(){
        if(this.listLugarTrabajo!=null){
            listLugarTrabajo.clear();
            notifyDataSetChanged();
        }
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    public AdapterLugarTFlip(Context ctx, ArrayList<LugarTrabajo> listLugarTrabajo) {
        this.ctx = ctx;
        this.listLugarTrabajo = listLugarTrabajo;
        this.layoutInflater=LayoutInflater.from(this.ctx);
    }

    public void setListenerLugaresTrabajo(NotifyListenerLugaresTrabajo listenerLugaresTrabajo) {
        this.listenerLugaresTrabajo = listenerLugaresTrabajo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_lugar_trabajo_flip,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LugarTrabajo t=listLugarTrabajo.get(position);
        if(t!=null) {
            String title = t.getVaNombrePos() != null ? t.getVaNombrePos() : t.getVaDescripcionZnPosic();
            TextDrawable drawable = TextDrawable.builder().beginConfig().textColor(Color.BLACK).endConfig()
                    .buildRound(getIniciales(title), ContextCompat.getColor(ctx,R.color.colorPrimary));
            holder.tvLugarTrabajo.setText(Utils.toTitleCase(title));
            holder.tvLugarTrabajo.setVisibility(!showDescription? View.INVISIBLE:View.VISIBLE);
            holder.imgLugar.setImageDrawable(drawable);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                    if(listenerLugaresTrabajo!=null){
                        listenerLugaresTrabajo.onClear();
                    }
                }
            });
        }
    }

    public void remove(int position) {
        try {
            listLugarTrabajo.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            listLugarTrabajo.clear();
            notifyDataSetChanged();
        }
    }

    private String getIniciales(String nombrePos) {
        String[] pos = nombrePos.split(" ");
        int count=0;
        String iniciales = "";
        if (pos.length > 0) {
            for (String p : pos) {
                if (!p.isEmpty()) {
                    if(count<2) {
                        iniciales += p.substring(0, 1);
                        count++;
                    }
                    else{
                        break;
                    }
                }
            }
        }
        return iniciales;
    }

    public void addOrRemove(LugarTrabajo t,int position) {
        if(!listLugarTrabajo.contains(t)) {
            if(listLugarTrabajo.size()>=MAX_SIZE){
                if(listenerLugaresTrabajo!=null){
                    listenerLugaresTrabajo.onLimiteSuperado();
                }
            }
            else {
                listLugarTrabajo.add(t);
                notifyItemInserted(position);
            }
        }
        else {

            int index = listLugarTrabajo.indexOf(t);
            if (index != -1) {
                listLugarTrabajo.remove(index);
                notifyItemRemoved(index);
            }
        }

            /*
            for(int j=0; j<listLugarTrabajo.size(); j++){
                if(listLugarTrabajo.get(j).equals(t)){
                    listLugarTrabajo.remove(j);
                    notifyItemRemoved(j);
                }
            }*/

    }

    @Override
    public int getItemCount() {
        return listLugarTrabajo.size();
    }



    public ArrayList<LugarTrabajo> getListLugarTrabajo() {
        return listLugarTrabajo;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView tvLugarTrabajo;
        public ImageView imgLugar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLugarTrabajo =(TextView)itemView.findViewById(R.id.tvLugarTrabajo);
            imgLugar=(ImageView) itemView.findViewById(R.id.imgLugar);
        }
    }
}
