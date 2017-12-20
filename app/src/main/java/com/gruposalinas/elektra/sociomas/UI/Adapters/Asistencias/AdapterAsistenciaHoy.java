package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.AsistenciaResponse;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 02/10/2017.
 */

public class AdapterAsistenciaHoy extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapterBase {
    private static final int CHILD_ASISTENCIA_HEADER = 0;
    private static final int CHILD_ASISTENCIA = 1;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ResultadoAsistencia>list;
    private RecyclerItemClickListener listener;
    public AdapterAsistenciaHoy(Context context, ArrayList<ResultadoAsistencia>list) {
        this.context = context;
        this.layoutInflater=LayoutInflater.from(this.context);
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case CHILD_ASISTENCIA_HEADER: {
                View v = layoutInflater.inflate(R.layout.item_header_asistencia, parent, false);
                viewHolder= new ViewHolderHeaderChildAsistencia(v);
            }
            break;
            case CHILD_ASISTENCIA:{
                View itemView=layoutInflater.inflate(R.layout.item_asistencia_hoy,parent,false);
                viewHolder=new ViewHolderAsistenciaHoy(itemView);
            }
            break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        ResultadoAsistencia item=getItem(position);
        return  item.isHeader()? CHILD_ASISTENCIA_HEADER: CHILD_ASISTENCIA;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ResultadoAsistencia item=getItem(position);
        switch (getItemViewType(position)) {

            case CHILD_ASISTENCIA_HEADER: {
                ViewHolderHeaderChildAsistencia holderChild=(ViewHolderHeaderChildAsistencia)holder;
                holderChild.tvFechaAsistencia.setText("Nombre");
                holderChild.tvFechaAsistencia.setAllCaps(true);
                holderChild.tvEntradaAsistencia.setAllCaps(true);
                holderChild.tvSalidaAsistencia.setAllCaps(true);
            }
            break;

            case CHILD_ASISTENCIA: {
                ViewHolderAsistenciaHoy holderAsisencia=(ViewHolderAsistenciaHoy)holder;
                AsistenciaContador asistenciaContador = new AsistenciaContador();
                holderAsisencia.tvNombre.setText(Utils.toTitleCase(item.getNombreEmpleado()));
                holderAsisencia.tvEntrada.setText(item.getHoraEntrada());
                holderAsisencia.tvSalida.setText(item.getHoraSalida());
                holderAsisencia.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnItemClickListener(position, item);
                        }
                    }
                });
                try {
                    Picasso.with(this.context).load(asistenciaContador.getHashIconos()
                            .get(item.getImagen().toLowerCase(Locale.ENGLISH)))
                            .into(holderAsisencia.imageStatusAsistencia);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            break;



        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {

    }

    @Override
    public ResultadoAsistencia getItem(int position) {
        return list.get(position);
    }
    public AsistenciaContador getContador(ArrayList<ResultadoAsistencia> listItems) {
        AsistenciaContador asistenciaContador = new AsistenciaContador();
        for (ResultadoAsistencia item : listItems) {
            if (!item.isHeader() && (item.getImagen()!=null))
                asistenciaContador.countStatus(item.getImagen().toLowerCase());
        }
        return asistenciaContador;
    }
}
