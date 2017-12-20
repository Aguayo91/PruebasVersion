package com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.aventones.UI.Adapters.IAdapterBase;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;
import java.util.List;

public class AdapterPermisoV3 extends RecyclerView.Adapter<AdapterPermisoV3.ViewHolder> implements IAdapterBase {

    private List<ListExclusiones> listIncidencias;
    private RecyclerItemClickListener listener;
    private Context context;

    public AdapterPermisoV3(Context context, List<ListExclusiones> userModelList) {
        this.listIncidencias = userModelList;
        this.context = context;

    }

    @Override
    public AdapterPermisoV3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permiso_tres, parent, false);
        AdapterPermisoV3.ViewHolder viewHolder = new AdapterPermisoV3.ViewHolder(v);
        return viewHolder;
    }
    public  void setAdapterPermisoV3(List<ListExclusiones> list){
        this.listIncidencias=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AdapterPermisoV3.ViewHolder holder, int position) {

        final ListExclusiones selectedItem=listIncidencias.get(position);
        StringBuilder builder = new StringBuilder();
        builder.append("Del: ");
        builder.append(selectedItem.getFechaHoraInicial());
        builder.append("\n Al: ");
        builder.append(selectedItem.getFechaHoraFinal());
        String stringPeriodoPermiso = selectedItem.getDescripcionExclusion();
        holder.tvPeriodoPermiso.setText(builder.toString());
        String stringAsuntoPermiso = selectedItem.getDescripcionExclusion();
        holder.tvAsuntoPermiso.setText(stringPeriodoPermiso);

    }

    @Override
    public int getItemCount() {
        return listIncidencias.size();
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
     //this.listIncidencias=itemClickListener;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPeriodoPermiso;
        private TextView tvAsuntoPermiso;

        public ViewHolder(View v) {
            super(v);
            tvPeriodoPermiso = (TextView) v.findViewById(R.id.tvPeriodo);
            tvAsuntoPermiso = (TextView) v.findViewById(R.id.tvAsunto);
        }
    }
}