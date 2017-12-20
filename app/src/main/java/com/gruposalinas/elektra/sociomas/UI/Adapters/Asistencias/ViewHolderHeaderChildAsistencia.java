package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

/**
 * Created by oemy9 on 18/09/2017.
 */

class ViewHolderHeaderChildAsistencia extends ChildViewHolder {
    public TextView tvFechaAsistencia;
    public TextView tvEntradaAsistencia;
    public TextView tvSalidaAsistencia;
    public ImageView imageStatusAsistencia;
    public ViewHolderHeaderChildAsistencia(View itemView) {
        super(itemView);
        tvFechaAsistencia = (TextView) itemView.findViewById(R.id.tvFechaAsistencia);
        tvEntradaAsistencia = (TextView) itemView.findViewById(R.id.tvEntradaAsistencia);
        tvSalidaAsistencia = (TextView) itemView.findViewById(R.id.tvSalidaAsistencia);
    }
}
