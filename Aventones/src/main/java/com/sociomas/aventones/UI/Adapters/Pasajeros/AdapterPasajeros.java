package com.sociomas.aventones.UI.Adapters.Pasajeros;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.IAdapterBase;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Pasajeros.PasajerosList;

import java.util.ArrayList;
import java.util.concurrent.Delayed;

import okhttp3.internal.Util;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class AdapterPasajeros extends RecyclerView.Adapter<ViewHolderPasajeros> implements IAdapterBase {
    public static final int PENDIENTE_APROBAR=1;
    public static final int ACEPTAR=2;
    public static final int CANCELAR=3;

    public interface onPasajerosListener{
        void onAceptarRechazarAventon(boolean aceptar,int idRelAsientosReservados);
    }
    private onPasajerosListener listener;
    private ArrayList<PasajerosList>listPasajeros;
    private Context context;
    private LayoutInflater layoutInflater;
    private int lastPosition = -1;



    public AdapterPasajeros(Context context, ArrayList<PasajerosList>listPasajeros){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.listPasajeros=listPasajeros;
    }

    public void setListenerPasajeros(onPasajerosListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolderPasajeros onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderPasajeros(layoutInflater.inflate(R.layout.item_mis_pasajeros,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolderPasajeros holder, int position) {
       final PasajerosList item=getItem(position);
        holder.tvNombreUsuario.setText(item.getNombreEmpleado());
        holder.tvIdEmpleado.setText(item.getIdNumEmpleado());
        holder.tvTelefono.setText(item.getNumTelEmpleado());

        holder.tvTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgTelefono.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shake));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String telefono = (String) holder.tvTelefono.getText();
                        Utils.callPhone(context,telefono);
                    }
                },500);

            }
        });
        holder.imgTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgTelefono.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shake));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String telefono = (String) holder.tvTelefono.getText();
                        Utils.callPhone(context,telefono);
                    }
                },500);

            }
        });
        switch (item.getIdEstatusSolicitudAventon()){
            case PENDIENTE_APROBAR:
                holder.fltOk.setVisibility(View.VISIBLE);
                holder.flCancel.setVisibility(View.VISIBLE);
                break;
            case ACEPTAR:
                holder.fltOk.setVisibility(View.VISIBLE);
                holder.flCancel.setVisibility(View.GONE);
                break;
            case CANCELAR:
                holder.fltOk.setVisibility(View.GONE);
                holder.flCancel.setVisibility(View.VISIBLE);
                break;
        }
        holder.fltOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null && item.getIdEstatusSolicitudAventon()==PENDIENTE_APROBAR){
                    listener.onAceptarRechazarAventon(true,item.getIdRelUsuarioAsientosReservados());
                }
            }
        });
        holder.flCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null && item.getIdEstatusSolicitudAventon()==PENDIENTE_APROBAR){
                    listener.onAceptarRechazarAventon(false,item.getIdRelUsuarioAsientosReservados());
                }
            }
        });
        setAnimation(holder.itemView, position);

    }
    @Override
    public int getItemCount() {
        return listPasajeros.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
    }

    @Override
    public PasajerosList getItem(int position) {
        return listPasajeros.get(position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_transparent);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
