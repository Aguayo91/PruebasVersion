package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sociomas.aventones.R;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AutosRecyclerAdapter extends RecyclerView.Adapter<AutosRecyclerAdapter.ViewHolder> implements IAdapterBase {

    private List<Vehiculo> autosRecycler;
    private RecyclerItemClickListener listener;
    private Context context;

    public AutosRecyclerAdapter(Context context, List<Vehiculo> userModelList) {
        if (userModelList == null) {
            userModelList = new ArrayList<>();
        }
        this.autosRecycler = userModelList;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_automoviles, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public  void setAutosRecycler(ArrayList<Vehiculo>list){
        this.autosRecycler=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Vehiculo selectedItem=autosRecycler.get(position);

        String auto = selectedItem.getModelo();
        holder.Auto.setText(auto);

        String placas = selectedItem.getPlacas();
        holder.Placas.setText(placas);

        String color = selectedItem.getCodigoColor();
        holder.Color.setBackgroundColor(Utils.getParseColor(color));

        int capacidad = selectedItem.getNumeroAsientos();
        holder.Capacidad.setText(String.valueOf(capacidad));

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.OnItemClickListener(position,selectedItem);
                }
            }
        });

        //Se cambia el tamaño del auto para un mejor performance
        Picasso.with(context).load(R.drawable.ic_auto_socio).resize(50,30).into(holder.imgAuto);
        ;

    }
    //Concatenamos de el #
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
    public Object getItem(int position) {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Auto;
        private TextView Placas;
        private ImageView Color;
        private TextView Capacidad;
        private ImageView imgEdit;
        private ImageView imgAuto;
        public ViewHolder(View v) {
            super(v);
            Auto = (TextView) v.findViewById(R.id.tvAuto);
            Placas = (TextView) v.findViewById(R.id.tvPlacas);
            Color = (ImageView) v.findViewById(R.id.imgQuadColor);
            Capacidad = (TextView) v.findViewById(R.id.tvCapacidad);
            imgAuto=(ImageView)v.findViewById(R.id.imgAuto);
            imgEdit=(ImageView)v.findViewById(R.id.imgEdit);
        }
    }
}