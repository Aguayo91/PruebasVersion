package com.sociomas.aventones.UI.Adapters.Pasajeros;

import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class ViewHolderPasajeros extends RecyclerView.ViewHolder {

    public TextView tvNombreUsuario;
    public TextView tvIdEmpleado;
    public TextView tvTelefono;
    public FloatingActionButton fltOk;
    public FloatingActionButton flCancel;
    public ImageView imgTelefono;

    public ViewHolderPasajeros(View itemView) {
        super(itemView);
        tvNombreUsuario=(TextView)itemView.findViewById(R.id.tvNombreUsuario);
        tvIdEmpleado=(TextView)itemView.findViewById(R.id.tvIdEmpleado);
        tvTelefono=(TextView)itemView.findViewById(R.id.tvTelefono);
        fltOk=(FloatingActionButton)itemView.findViewById(R.id.fltOk);
        flCancel=(FloatingActionButton)itemView.findViewById(R.id.flCancel);
        imgTelefono = (ImageView)itemView.findViewById(R.id.imgTelefono);
    }
}
