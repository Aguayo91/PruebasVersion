package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

/**
 * Created by oemy9 on 23/10/2017.
 */

public class ViewHolderIncidenciaChild  extends ChildViewHolder {
    public TextView tvTipoJustificacion;
    public TextView tvFechaJustificacion;
    public ImageView ImgJustificacion;
    public LinearLayout linearContenedor;

    public ViewHolderIncidenciaChild(View itemView) {
        super(itemView);
        tvTipoJustificacion=(TextView)itemView.findViewById(R.id.tvTipoJustificacion);
        tvFechaJustificacion=(TextView)itemView.findViewById(R.id.tvFechaJustificacion);
        ImgJustificacion=(ImageView) itemView.findViewById(R.id.img_justificacion);
        linearContenedor=(LinearLayout)itemView.findViewById(R.id.contenedor);
    }
}
