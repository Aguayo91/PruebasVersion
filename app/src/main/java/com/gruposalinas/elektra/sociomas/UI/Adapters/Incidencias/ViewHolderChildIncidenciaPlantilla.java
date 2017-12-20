package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;
import mbanje.kurt.fabbutton.FabButton;

/**
 * Created by oemy9 on 23/10/2017.
 */

public class ViewHolderChildIncidenciaPlantilla extends ChildViewHolder {
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


    public ViewHolderChildIncidenciaPlantilla(View view) {
        super(view);
        ImgJustificacion=(ImageView) view.findViewById(R.id.img_justificacion);
        tvStatus=(TextView)view.findViewById(R.id.tvStatus);
        tvName=(TextView)view.findViewById(R.id.tvName);
        ImgEmpleado=(CircleImageView)view.findViewById(R.id.imgEmpleado);
        tvIncidencia=(TextView)view.findViewById(R.id.tvIncidencia);
        tvSinJustificar=(TextView)view.findViewById(R.id.tvSinJustificar);
        tvAutorizado=(TextView)view.findViewById(R.id.tvAutorizados);
        tvPendiente=(TextView)view.findViewById(R.id.tvPendienteJustificar);
        tvDate=(TextView)view.findViewById(R.id.tvDate);
        ImgIncidencia=(ImageView) view.findViewById(R.id.imgIncidencia);
        fabDescarga=(FabButton) view.findViewById(R.id.fabDescarga);
        tvComentario=(TextView)view.findViewById(R.id.tvComentario);
        layoutImgIncidencia=(RelativeLayout)view.findViewById(R.id.layoutImgIncidencia);
        layoutAutorizar=(RelativeLayout)view.findViewById(R.id.layoutAutorizar);
        layoutComentarios=(RelativeLayout)view.findViewById(R.id.layoutComentarios);
        txtComentarioIncidencia=(EditText)view.findViewById(R.id.txtComentarioIncidencia);
        btnAutorizar=(Button)view.findViewById(R.id.btnAutorizar);
        btnRechazar=(Button)view.findViewById(R.id.btnRechazar);
        btnAutorizarDirecto=(Button)view.findViewById(R.id.btnAutorizarDirecto);


    }
}
