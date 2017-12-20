package com.sociomas.aventones.UI.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sociomas.aventones.R;

import java.util.ArrayList;

import static android.system.Os.close;

/**
 * Created by jmarquezu on 22/09/2017.
 */

public class AdapterColor
        extends RecyclerView.Adapter<AdapterColor.ViewHolder> {


    private ArrayList<String> colors;
    public ColorListener colorListener;
    private ArrayList<String> colorName;




    //TextView tvColor;


    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView tvColor, tvName;
        int posicion;
        Button btAvanzado;

        public ViewHolder(View itemView) {
            super(itemView);

            tvColor = (TextView) itemView.findViewById(R.id.tvTextDialog);
            btAvanzado =(Button)itemView.findViewById(R.id.btAvanzadas);
            tvName = (TextView)itemView.findViewById(R.id.tvName);

        }
        public void bindString(String color,String name){

            tvColor.setBackgroundColor(Color.parseColor(color));
            tvName.setText(name);

        }

    }

    public AdapterColor(ArrayList colors, ArrayList colorName) {
        this.colors = colors;
        this.colorName = colorName;
    }



    public interface ColorListener{
         void onSelectedColor(String selectedColor);
    }

    public void setColorListener(ColorListener listen) {
        this.colorListener = listen;
    }

    @Override
    public AdapterColor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_placas, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String item = colors.get(position);
        final String nombre = colorName.get(position);
        holder.bindString(item,nombre);
        holder.tvColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colorListener!=null){
                    colorListener.onSelectedColor(item);
                }
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colorListener!=null){
                    colorListener.onSelectedColor(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}
