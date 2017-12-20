package com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.IAdapterBase;
import com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders.ViewHolderContactos;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.Contacto.Contacto;

import java.util.ArrayList;

/**
 * Created by oemy9 on 07/09/2017.
 */

public class AdapterContactos extends RecyclerView.Adapter<ViewHolderContactos> implements IAdapterBase {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Contacto>listContactos;

    public AdapterContactos(Context context, ArrayList<Contacto>listContactos){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(this.context);
        this.listContactos=listContactos;
    }

    @Override
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {

    }

    @Override
    public ViewHolderContactos onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_contacto_sos, parent, false);
        return new ViewHolderContactos(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderContactos holder, int position) {
            holder.tvNombreTel.setText(context.getString(R.string.nombre_contacto_formato,
                    getItem(position).getNombre(),getItem(position).getTelefono()));
    }

    @Override
    public int getItemCount() {
        return listContactos.size();
    }

    @Override
    public Contacto getItem(int position) {
        return listContactos.get(position);
    }
}
