package com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by oemy9 on 08/06/2017.
 */


public class AdapterPermisoV2 extends BaseAdapter {

    @SuppressWarnings("unused")
    public static class ViewHolder {
        public TextView tvAsuntoPermiso;
        public TextView tvPeriodoPermiso;
        public ImageView ImgTipo;
        public LinearLayout linearContenedor;
    }

    private ArrayList<ListExclusiones> listIndidencias;
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterPermisoV2(Context context, ArrayList<ListExclusiones> listIndidencias) {
        this.context = context;
        this.listIndidencias = listIndidencias;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listIndidencias != null ? listIndidencias.size() : 0;
    }

    @Override
    public ListExclusiones getItem(int i) {
        return listIndidencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_permiso_v2, viewGroup, false);
            holder.tvAsuntoPermiso = (TextView) view.findViewById(R.id.tvAsuntoPermiso);
            holder.tvPeriodoPermiso = (TextView) view.findViewById(R.id.tvPeriodoPermiso);
            holder.ImgTipo = (ImageView) view.findViewById(R.id.img_tipo);
            holder.linearContenedor = (LinearLayout) view.findViewById(R.id.contenedor);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ListExclusiones selectedItem=getItem(position);

        Picasso.with(this.context).load(AdapterUtils.getResourceFileByTipoJustificacion(selectedItem.getDescripcionEstatusExclusion())).resize(60, 60).into(holder.ImgTipo);
        holder.tvAsuntoPermiso.setText(selectedItem.getDescripcionExclusion());
        StringBuilder builder=new StringBuilder();
        builder.append("Del: ");
        builder.append(selectedItem.getFechaHoraInicial());
        builder.append("\n Al: ");
        builder.append(selectedItem.getFechaHoraFinal());
        holder.tvPeriodoPermiso.setText(builder.toString());
        return view;
    }



}
