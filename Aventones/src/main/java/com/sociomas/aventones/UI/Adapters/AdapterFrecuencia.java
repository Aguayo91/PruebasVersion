package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.UI.ViewHolder.ViewHolderFrecuencia;
import com.sociomas.core.Listeners.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class AdapterFrecuencia extends RecyclerView.Adapter<ViewHolderFrecuencia> implements IAdapterBase {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Dia>listDias;
    private RecyclerItemClickListener itemClickListener;
    private View itemView;
    public  AdapterFrecuencia(Context context,ArrayList<Dia>listDias){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(this.context);
        this.listDias=listDias;
    }

    @Override
    public ViewHolderFrecuencia onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = layoutInflater.inflate(R.layout.item_frecuencia, parent, false);
        return new ViewHolderFrecuencia(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderFrecuencia holder, final  int position) {
            final Dia selectedDia=getItem(position);
            holder.tvDia.setText(selectedDia.getNombre());
            holder.checkDia.setChecked(selectedDia.isChecked());
            if(position!=0) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedDia.setChecked(!holder.checkDia.isChecked());
                        holder.checkDia.setChecked(selectedDia.isChecked());
                        itemClickListener.OnItemClickListener(position, getItem(position));
                    }
                });
                holder.checkDia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedDia.setChecked(holder.checkDia.isChecked());
                        //holder.checkDia.setChecked(selectedDia.isChecked());
                        itemClickListener.OnItemClickListener(position, getItem(position));
                    }
                });
            }
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
    }

    public Dia getItem(int position){
        return listDias.get(position);
    }

    @Override
    public int getItemCount() {
        return listDias.size();
    }
}
