package com.gruposalinas.elektra.sociomas.UI.Adapters.SliderInicio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.BadgeControl;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Controls.Model.DrawerItem;

import java.util.List;

/**
 * Created by GiioToledo on 16/11/17.
 */

public class AdapterOpcionesHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater li;
    private final View.OnClickListener clickListener;
    private List<DrawerItem> items;

    public AdapterOpcionesHome(Context context, List<DrawerItem> items, View.OnClickListener clickListener) {
        this.context = context;
        this.items = items;
        this.li = LayoutInflater.from(this.context);
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_home_opciones, parent, false);
        HolderOpciones holderOpciones= new HolderOpciones(view);
        return holderOpciones;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HolderOpciones holderOpc = (HolderOpciones) holder;
        holderOpc.ivOpcion.setImageResource(items.get(position).getIcon());
        holderOpc.tvOpcion.setText(Utils.toUppperCaseFirst(items.get(position).getDescription()));
        holderOpc.rlCuadro.setTag(items.get(position));
        holderOpc.rlCuadro.setOnClickListener(clickListener);
        holderOpc.badgeControl.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class HolderOpciones extends RecyclerView.ViewHolder {

        RelativeLayout rlCuadro;
        ImageView ivOpcion;
        TextView tvOpcion;
        BadgeControl badgeControl;

        public HolderOpciones(View view) {
            super(view);
            this.rlCuadro = (RelativeLayout) view.findViewById(R.id.rlCuadro);
            this.ivOpcion = (ImageView) view.findViewById(R.id.ivOpcion);
            this.tvOpcion = (TextView) view.findViewById(R.id.tvOpcion);
            this.badgeControl=(BadgeControl)view.findViewById(R.id.imgBadge);

        }
    }
}
