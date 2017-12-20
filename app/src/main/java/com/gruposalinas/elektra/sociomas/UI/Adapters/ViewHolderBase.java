package com.gruposalinas.elektra.sociomas.UI.Adapters;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import mbanje.kurt.fabbutton.FabButton;

/**
 * Created by oemy9 on 22/05/2017.
 */

/*TODO UNIFICAR ESTE VIEWHOLDER CON EL OTRO*/
@SuppressWarnings("unused")
public  class ViewHolderBase{
    public CircleImageView ImgEmpleado;
    public TextView tvName;
    public TextView tvStatus;
    public TextView tvIncidencia;
    public TextView tvAutorizado;
    public TextView tvPendiente;
    public TextView tvSinJustificar;
    public TextView tvDate;
    public ImageView ImgJustificacion;
    public ImageView ImgIncidencia;
    public FabButton fabDescarga;
    public RelativeLayout layoutImgIncidencia,layoutComentarios,layoutAutorizar;
    public TextView tvComentario;
    public EditText txtComentarioIncidencia;
    public Button btnAutorizar,btnRechazar,btnAutorizarDirecto;
}
