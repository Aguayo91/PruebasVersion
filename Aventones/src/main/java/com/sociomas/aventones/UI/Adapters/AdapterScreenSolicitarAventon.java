package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.sociomas.aventones.R;
import com.sociomas.core.WebService.Model.Response.Aventones.PreferenciasSolicitud;
import java.util.List;

/**
 * Created by jromeromar on 06/10/2017.
 */

public class AdapterScreenSolicitarAventon extends RecyclerView.Adapter<AdapterScreenSolicitarAventon.ViewHolder>  {

    private List<PreferenciasSolicitud>items;
    private Context context;

    public AdapterScreenSolicitarAventon(Context context, List<PreferenciasSolicitud> userModelList) {
        this.items = userModelList;
        this.context=context;
    }

    @Override
    public AdapterScreenSolicitarAventon.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicita_aventon, parent, false);
        AdapterScreenSolicitarAventon.ViewHolder viewHolder = new AdapterScreenSolicitarAventon.ViewHolder(v);
        return viewHolder;
    }

    public AdapterScreenSolicitarAventon(List<PreferenciasSolicitud>items ) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(AdapterScreenSolicitarAventon.ViewHolder holder, final int position) {
        final PreferenciasSolicitud selectedItem=items.get(position);

        int imagenPOS = selectedItem.getImagenPos();
        holder.imagen.setImageResource(imagenPOS);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        public ViewHolder(View v) {
            super(v);
            imagen=(ImageView)v.findViewById(R.id.imgProhibido);

        }
    }
}