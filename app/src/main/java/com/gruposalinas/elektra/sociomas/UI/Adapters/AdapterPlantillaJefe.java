
package com.gruposalinas.elektra.sociomas.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.ListaEmpleado.LstEmpleado;

import java.util.ArrayList;

/**
 * Created by jromeromar on 28/11/2017.
 */
public class AdapterPlantillaJefe extends RecyclerView.Adapter<AdapterPlantillaJefe.ViewHolder> implements IAdapterBase {

    private ArrayList<LstEmpleado> plantillaRecycler;
    private RecyclerItemClickListener listener;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterPlantillaJefe(Context context, ArrayList<LstEmpleado> userModelList) {
        this.plantillaRecycler = userModelList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plantilla_jefe, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public  void setPlantillaRecycler(ArrayList<LstEmpleado>list){
        this.plantillaRecycler=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvNombre.setText(getItem(position).getNombreCompleto());
        holder.tvUserId.setText(getItem(position).getIdNumEmpleado());
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
        return plantillaRecycler.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;

    }

    @Override
    //cambiar por objeto
    public LstEmpleado getItem(int position) {
        return plantillaRecycler.get(position);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvUserId;
        private ImageView imgUserAvatar;
        private ImageView imgUserYellow;

        public ViewHolder(View v) {
            super(v);
            tvNombre = (TextView) v.findViewById(R.id.tvNombre);
            tvUserId = (TextView) v.findViewById(R.id.tvUserId);
            imgUserAvatar = (ImageView) v.findViewById(R.id.imgUserAvatar);
            imgUserYellow = (ImageView) v.findViewById(R.id.imgUserYellow);
        }
    }
}
