package com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mbanje.kurt.fabbutton.FabButton;

/**
 * Created by oemy9 on 22/05/2017.
 */

@SuppressWarnings("unused")
public  class ViewHolderBase {
    public CircleImageView ImgEmpleado;
    public TextView tvName;
    public TextView tvStatus;
    public TextView tvIncidencia;
    public TextView tvComentario;
    public TextView tvDate;
    public TextView tvAutorizado;
    public TextView tvPendiente;
    public TextView tvSinJustificar;
    public ImageView ImgJustificacion;
    public ImageView ImgIncidencia;
    public FabButton fabDescarga;
    public Button btnVerMas;
    public RelativeLayout layoutImgIncidencia;
}
