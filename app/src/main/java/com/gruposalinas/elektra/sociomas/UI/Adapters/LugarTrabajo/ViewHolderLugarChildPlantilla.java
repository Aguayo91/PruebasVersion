package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

/**
 * Created by oemy9 on 19/09/2017.
 */

public class ViewHolderLugarChildPlantilla extends ChildViewHolder {

    public ImageView imgMapa;
    public TextView tvNombre,tvLugarTrabajo;
    public Button btnAutorizar,btnRechazar;
    public ImageView imageCircleNombre;
    public EditText txtComentario;
    public ViewHolderLugarChildPlantilla(View itemView) {
        super(itemView);
        imgMapa=(ImageView)itemView.findViewById(R.id.imgMapa);
        tvNombre=(TextView)itemView.findViewById(R.id.tvNombre);
        btnAutorizar=(Button)itemView.findViewById(R.id.btnAutorizar);
        btnRechazar=(Button)itemView.findViewById(R.id.btnRechazar);
        tvLugarTrabajo=(TextView)itemView.findViewById(R.id.tvLugarTrabajo);
        imageCircleNombre=(ImageView)itemView.findViewById(R.id.imageCircleNombre);
        txtComentario=(EditText)itemView.findViewById(R.id.txtComentarioLugar);
    }
}
