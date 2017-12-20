package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sociomas.aventones.R;

import java.lang.ref.Reference;
import java.util.ArrayList;

/**
 * Created by jmarquezu on 10/10/2017.
 */

public class AdapterQr
        extends RecyclerView.Adapter<AdapterQr.ViewHolder> {

    private ArrayList<String> idViaje;
    private ArrayList<Bitmap> destinos;


    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView tvIdViaje;
        ImageView imgQr;

        public ViewHolder(View itemView) {
            super(itemView);

            tvIdViaje = (TextView) itemView.findViewById(R.id.tvIdViaje);
            imgQr = (ImageView)itemView.findViewById(R.id.imageQr);
        }
        public void bindString(String idViaje, Bitmap qr){
            tvIdViaje.setText("Id de Viaje" + idViaje);
            imgQr.setImageBitmap(qr);
        }
    }

    public AdapterQr (ArrayList idViaje,ArrayList destinos){
        this.idViaje = idViaje;
        this.destinos=destinos;
    }


    @Override
    public AdapterQr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qr_id,parent,false);
        ViewHolder vH = new ViewHolder(item);
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String item = idViaje.get(position);
        final Bitmap bitmap = destinos.get(position);
        holder.bindString(item,bitmap);
    }

    @Override
    public int getItemCount() {
        return destinos.size();
    }
}
