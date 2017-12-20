package com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.GroupViewHolder;

/**
 * Created by oemy9 on 23/10/2017.
 */

public class ViewHolderIncidenciaHeader extends GroupViewHolder {

    public ImageView imageArrow;
    public TextView tvDescripcion;

    public ViewHolderIncidenciaHeader(View itemView) {
        super(itemView);
        imageArrow = (ImageView) itemView.findViewById(R.id.imageArrow);
        tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcionLugar);
    }

    @Override
    public void expand() {
        imageArrow.setImageResource(R.drawable.ic_arrow_up);
        super.expand();
    }

    @Override
    public void collapse() {
        imageArrow.setImageResource(R.drawable.ic_arrow_down);
        super.collapse();
    }
}
