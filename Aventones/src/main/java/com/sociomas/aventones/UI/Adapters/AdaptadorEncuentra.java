package com.sociomas.aventones.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.ProhibitedImage;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.DiasAventon;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorEncuentra extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapterBase {

    private final List<Aventones> aventonesList;
    private final Context context;
    private onRutaAdapterClickListener listener;
    private onLlamada llamadaListener;
    private final LayoutInflater li;
    private boolean mostrarInfoAdicional=false;
    private String numeroTelefono;


    public interface onRutaAdapterClickListener extends RecyclerItemClickListener {
        void onMostrarRuta(Object selectedItem);
        void onMostrarObservaciones(Object selectedItem);
    }

    public interface onLlamada {
        void onLlamar(int position , Object selectedItem,String numero);
    }

    public AdaptadorEncuentra(Context context, List<Aventones> aventonesList) {
        this.aventonesList = aventonesList;
        this.context = context;
        this.li = LayoutInflater.from(this.context);
    }

    public void setMostrarInfoAdicional(boolean mostrarInfoAdicional) {
        this.mostrarInfoAdicional = mostrarInfoAdicional;
    }

    public boolean isMostrarInfoAdicional() {
        return mostrarInfoAdicional;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = li.inflate(R.layout.item_encuentra_aventon, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        if (aventonesList != null) {
            if (aventonesList.get(position) != null) {
                final Aventones item = aventonesList.get(position);

                holder1.btnVerAventon.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnItemClickListener(position, item);
                        }

                    }
                });

                holder1.bindDatos(item, this.context,isMostrarInfoAdicional());
                holder1.imgVerRuta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onMostrarRuta(item);
                        }
                    }
                });

                holder1.imgObservaciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener!=null){
                            listener.onMostrarObservaciones(item);
                        }
                    }
                });
                holder1.tvTelefono.setOnClickListener(new View.OnClickListener() {
                    final String numero = (String) holder1.tvTelefono.getText();

                    @Override
                    public void onClick(View v) {
                        if(llamadaListener!=null)
                            llamadaListener.onLlamar(position,item, numero);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return aventonesList != null ? aventonesList.size() : 0;
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
    }

    public void setItemClickListener(onRutaAdapterClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public void setonLlamadaClickListener (onLlamada itemClickListener){
        this.llamadaListener = itemClickListener;
    }


    @Override
    public Aventones getItem(int position) {
        return null;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        RelativeLayout rlHeader;
        Button btnVerAventon;
        ImageView ivMap;
        ImageView ivOrigen;
        ImageView ivDestino;
        ImageView ivAsientos;
        ImageView imgVerRuta;
        ImageView imgObservaciones;
        ImageView imgCoche;
        CircleImageView imgAvatar;
        ProhibitedImage imgProhibidoFumar;
        ProhibitedImage imgProhibidoComer;
        ProhibitedImage imgProhibidoBultos;
        ProhibitedImage imgProhibidoMascotas;
        TextView tvNameCompleto;
        TextView tvOrigen;
        TextView tvHorarioOrigen;
        TextView tvDestino;
        TextView tvHorarioDestino;
        TextView tvAsientosText;
        TextView tvDias;
        TextView tvMinutos;
        TextView tvAsientosDisponibles;
        TextView tvTelefono;
        TextView tvAuto;
        TextView tvPlacas;


        public ViewHolder(View itemView) {
            super(itemView);
            rlHeader = (RelativeLayout) itemView.findViewById(R.id.rlHeader);

            btnVerAventon = (Button) itemView.findViewById(R.id.btnVerAventon);
            ivMap = (ImageView) itemView.findViewById(R.id.ivMap);
            tvAsientosDisponibles = (TextView) itemView.findViewById(R.id.tvAsientosDisponibles);
            ivOrigen = (ImageView) itemView.findViewById(R.id.ivOrigen);
            ivDestino = (ImageView) itemView.findViewById(R.id.ivDestino);
            ivAsientos = (ImageView) itemView.findViewById(R.id.imgAsientos);
            imgCoche = (ImageView)itemView.findViewById(R.id.imgCoche);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            imgProhibidoFumar = (ProhibitedImage) itemView.findViewById(R.id.imgProhibidoFumar);
            imgProhibidoComer = (ProhibitedImage) itemView.findViewById(R.id.imgProhibidoComer);
            imgProhibidoBultos = (ProhibitedImage) itemView.findViewById(R.id.imgProhibidoBultos);
            imgProhibidoMascotas = (ProhibitedImage) itemView.findViewById(R.id.imgProhibidoMascotas);
            imgObservaciones=(ImageView)itemView.findViewById(R.id.imgObservaciones);
            tvNameCompleto = (TextView) itemView.findViewById(R.id.tvNameCompleto);
            tvOrigen = (TextView) itemView.findViewById(R.id.tvOrigen);
            tvHorarioOrigen = (TextView) itemView.findViewById(R.id.tvHorarioOrigen);
            tvDestino = (TextView) itemView.findViewById(R.id.tvDestino);
            tvHorarioDestino = (TextView) itemView.findViewById(R.id.tvHorarioDestino);
            tvAsientosText = (TextView) itemView.findViewById(R.id.tvAsientosText);
            tvDias=(TextView)itemView.findViewById(R.id.tvDias);
            tvMinutos=(TextView)itemView.findViewById(R.id.tvMinutos);
            imgVerRuta=(ImageView)itemView.findViewById(R.id.imgVerRuta);
            tvTelefono = (TextView)itemView.findViewById(R.id.tvTelefono);
            tvAuto = (TextView)itemView.findViewById(R.id.tvAuto);
            tvPlacas = (TextView)itemView.findViewById(R.id.tvPlacas);

            tvDias = (TextView) itemView.findViewById(R.id.tvDias);
            tvMinutos = (TextView) itemView.findViewById(R.id.tvMinutos);
            imgVerRuta = (ImageView) itemView.findViewById(R.id.imgVerRuta);
        }

        public void bindDatos(Aventones d, Context context, boolean mostrarInfoAdicional) {
            tvNameCompleto.setText(d.getNombre_completo());
            tvOrigen.setText(d.getDesc_origen());
            tvDestino.setText(d.getDesc_destino());
            String urlMapa = Utils.getMapaUbicacionRezised(d.getLatitud_origen(), d.getLongitud_origen(), 800, 200);
            Picasso.with(context).load(urlMapa).fit().into(ivMap);
            tvHorarioOrigen.setText(d.getHora_salida().substring(0,5));
            tvHorarioDestino.setText(d.getHora_llegada().substring(0,5));
            tvMinutos.setText(String.valueOf(Utils.getTiempoTolerancia(d.getTiempo_tolerancia())).concat("\n Min"));
            tvAsientosDisponibles.setText(String.valueOf(d.getAsientos()));
            String diaSeleccionado = "";
            for (DiasAventon dia : d.getDiasAventonList()) {
                if (dia.getActivo() > 0) {
                    diaSeleccionado += dia.getDia().substring(0, 3).concat(" ");
                    tvDias.setText(diaSeleccionado);
                }
            }

            Log.i("ADAPTER", diaSeleccionado);

            imgProhibidoFumar.setIsChecked(d.isFumar());
            imgProhibidoComer.setIsChecked(d.isAlimentos());
            imgProhibidoBultos.setIsChecked(d.isEquipaje());
            imgProhibidoMascotas.setIsChecked(d.isMascotas());
            imgProhibidoFumar.desactivarClick();
            imgProhibidoComer.desactivarClick();
            imgProhibidoBultos.desactivarClick();
            imgProhibidoMascotas.desactivarClick();
            if (d.isViajeRedondo()) {
                // viaje ida
                if (d.getIdTipoAventon() == 1) {
                    btnVerAventon.setText("SOLICITAR AVENTÓN DE IDA");
                } else if (d.getIdTipoAventon() == 2) {
                    btnVerAventon.setText("SOLICITAR AVENTÓN DE VUELTA");
                } else {
                    // Other types
                }
            } else {
                // viaje
                btnVerAventon.setText("SOLICITAR AVENTÓN");
            }

            if(mostrarInfoAdicional){
                tvTelefono.setVisibility(View.VISIBLE);
                tvAuto.setVisibility(View.VISIBLE);
                tvPlacas.setVisibility(View.VISIBLE);
                imgCoche.setVisibility(View.VISIBLE);
                btnVerAventon.setText(d.getEstatusDescripcion());
                if(d.getIdaventon()==1){
                    btnVerAventon.setBackgroundColor(Color.YELLOW);
                }else if(d.getIdaventon()==2){
                    btnVerAventon.setBackgroundColor(Color.GREEN);
                }else {
                    btnVerAventon.setBackgroundColor(Color.RED);
                    tvTelefono.setVisibility(View.GONE);
                    tvAuto.setVisibility(View.GONE);
                    tvPlacas.setVisibility(View.GONE);
                    imgCoche.setVisibility(View.GONE);
                }
                tvAuto.setText(d.getModelo());
                tvPlacas.setText(d.getPlacas());
                tvAsientosDisponibles.setText(String.valueOf(d.getAsientos()));
                String telefono = "tel: "+d.getTelefono();
                tvTelefono.setText(telefono);
                imgProhibidoFumar.setCheck(false);
                imgProhibidoComer.setCheck(false);
                imgProhibidoBultos.setCheck(false);
                imgProhibidoMascotas.setCheck(false);

            }
        }
    }
}
