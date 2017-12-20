package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.MisAventones.AventonesPublicados;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventone;
import com.sociomas.core.WebService.Model.Response.Aventones.DiasAventon;
import java.util.List;

import vn.luongvo.widget.iosswitchview.SwitchView;

public class AdapterMisPublicados extends RecyclerView.Adapter<AdapterMisPublicados.ViewHolder> implements IAdapterBase {

    private List<Aventone> publycaRecycler;
    private RecyclerItemClickListener listener;
    private Context context;

    public AdapterMisPublicados(List<Aventone> userModelList) {
        this.publycaRecycler = userModelList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aventones_publicados, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Integer asientosDisponibles = publycaRecycler.get(position).getAsientos();
        holder.asientos.setText(asientosDisponibles.toString());
        String destino = publycaRecycler.get(position).getDescDestino();
        holder.Destino.setText(destino);
        String origen = publycaRecycler.get(position).getDescOrigen();
        holder.Origen.setText(origen);
        String horaOrigen=publycaRecycler.get(position).getHoraSalida();
        holder.tvHoraOrigen.setText(horaOrigen);
        String horaDestino=publycaRecycler.get(position).getHoraLlegada();
        holder.tvHoraDestino.setText(horaDestino);
        boolean viajeRedondo=publycaRecycler.get(position).getViajeRedondo();
        holder.switchView.setChecked(viajeRedondo);
        holder.tvDias.setText(getDiasSeleccionados(publycaRecycler.get(position).getDiasAventon()));
        holder.btnMisPasajeros.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener!=null){
                    listener.OnItemClickListener(position,getItem(position));
                }
            }
        });
    }

    public String getDiasSeleccionados(List<DiasAventon>list){
        StringBuilder stringBuilder=new StringBuilder();
        if(list!=null) {
            int countSeleccionado=0;
            for (DiasAventon dia :list) {
                if (dia.getActivo() > 0) {
                    stringBuilder.append(countSeleccionado>0?",":" ");
                    stringBuilder.append(dia.getDia().substring(0,3));
                    countSeleccionado++;
                }
            }
        }
        return stringBuilder.toString();
    }


    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
            this.listener=itemClickListener;
    }

    @Override
    public Aventone getItem(int position) {
        return publycaRecycler.get(position);
    }

    @Override
    public int getItemCount() {
        return publycaRecycler.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Origen;
        private TextView Destino;
        private TextView tvHoraOrigen;
        private TextView tvHoraDestino;
        private TextView Diasem;
        private TextView DescTipoTrayecto;
        private TextView asientos;
        private TextView tvDias;
        private SwitchView switchView;
        private Button btnMisPasajeros;

        public ViewHolder(View v) {
            super(v);
            Origen = (TextView) v.findViewById(R.id.tvOrigen);
            Destino = (TextView) v.findViewById(R.id.tvDestino);
            Diasem = (TextView) v.findViewById(R.id.tvDiaSem);
            tvHoraOrigen=(TextView) v.findViewById(R.id.tvHoraOrigen);
            tvHoraDestino=(TextView)v.findViewById(R.id.tvHoraDestino);
            switchView=(SwitchView) v.findViewById(R.id.switchAndroidD);
            asientos=(TextView)v.findViewById(R.id.tvAsientosDisp);
            tvDias=(TextView)v.findViewById(R.id.tvDiaSem);
            btnMisPasajeros=(Button)v.findViewById(R.id.btnMisPasajeros);
        }
    }
}