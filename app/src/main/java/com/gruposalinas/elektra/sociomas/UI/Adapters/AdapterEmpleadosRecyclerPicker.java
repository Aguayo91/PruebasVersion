package com.gruposalinas.elektra.sociomas.UI.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.FiltroEmpleados;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarquezu on 28/11/2017.
 */

public class AdapterEmpleadosRecyclerPicker extends RecyclerView.Adapter<AdapterEmpleadosRecyclerPicker.ViewHolder>
        implements Filterable, IAdapterBase {
    private static final String TAG = "AdapterEmpleadosRecycle";
    private Context context;
    private ArrayList<Plantilla> listPlantilla,filterList;
    private FiltroEmpleados filter;
    private RecyclerItemClickListener listener;

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;
    }

    @Override
    public Plantilla getItem(int position) {
        return listPlantilla.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNombre,tvNumero;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = (TextView)itemView.findViewById(R.id.tvOpcionesItem);
            tvNumero = (TextView)itemView.findViewById(R.id.tvUserId);
        }
    }

    public AdapterEmpleadosRecyclerPicker(Context context, ArrayList<Plantilla> plantilla){
        //Variables necesarias
        this.context=context;
        this.listPlantilla=plantilla;
        this.filterList=plantilla;
    }

    public void setPlantilla(ArrayList<Plantilla>listPlantilla) {
        if(listPlantilla!=null && (!listPlantilla.isEmpty())) {
            this.listPlantilla=listPlantilla;
            notifyDataSetChanged();
        }
    }

    @Override
    public AdapterEmpleadosRecyclerPicker.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mi_plantilla,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       holder.tvNumero.setText("Socio:"+listPlantilla.get(position).getIdEmpleado());
       holder.tvNombre.setText(listPlantilla.get(position).getNombreEmpleado());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(listener!=null){
                   listener.OnItemClickListener(position,getItem(position));
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return listPlantilla.size();
    }

    @Override
    public Filter getFilter() {
        return new FiltroEmpleados();
    }

    class FiltroEmpleados extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results=new FilterResults();
            ArrayList<Plantilla> filteredEmpleados=new ArrayList<>();
            final String query = constraint.toString().toLowerCase();
            if(!TextUtils.isEmpty(query)&& (query.length()>3)){
                for (Plantilla p : filterList)
                {
                    if (p.getIdEmpleado().equals(query) || p.getIdEmpleado().contains(query)
                            || p.getNombreEmpleado().toLowerCase().startsWith(query) || p.getNombreEmpleado().toLowerCase().contains(query)
                            || p.getNombreEmpleado().toLowerCase().equalsIgnoreCase(query)) {
                        filteredEmpleados.add(p);
                    }
                }
            }
            else{
                filteredEmpleados.clear();
                filteredEmpleados.addAll(filterList);
            }
            results.count=filteredEmpleados.size();
            results.values=filteredEmpleados;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
           listPlantilla=(ArrayList<Plantilla>) results.values;
           notifyDataSetChanged();
        }
    }
}
