package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sociomas.aventones.R;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoDialog extends RecyclerView.Adapter<AdapterAutoDialog.ViewHolder> implements IAdapterBase {

    private List<Vehiculo> autosRecycler;
    private RecyclerItemClickListener listener;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterAutoDialog(Context context, List<Vehiculo> userModelList) {
        this.autosRecycler = userModelList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto_dialogo, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public  void setAutosRecycler(List<Vehiculo>list){
        this.autosRecycler=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvAutoModelo.setText(getItem(position).getModelo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
       //             holder.radioButtonSelected.setChecked(!holder.radioButtonSelected.isChecked());
                    listener.OnItemClickListener(position,getItem(position));
                }
            }
        });


    }

    public String getHexColor(String color){
       return color.indexOf("#")!=-1?  color: "#".concat(color);
    }

    @Override
    public int getItemCount() {
        return autosRecycler.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.listener=itemClickListener;

    }

    @Override
    public Vehiculo getItem(int position) {
        return autosRecycler.get(position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAutoModelo;
        private RadioButton radioButtonSelected;
        public ViewHolder(View v) {
            super(v);

            tvAutoModelo = (TextView) v.findViewById(R.id.tvAutoModelo);
            radioButtonSelected=(RadioButton)v.findViewById(R.id.radioButtonSelected);

        }
    }
}