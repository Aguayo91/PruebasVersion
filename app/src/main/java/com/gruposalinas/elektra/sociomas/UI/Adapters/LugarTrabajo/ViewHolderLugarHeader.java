package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.GroupViewHolder;

public class ViewHolderLugarHeader extends GroupViewHolder {

    public ImageView imageArrow;
    public TextView tvDescripcionLugar;

    public ViewHolderLugarHeader(View itemView) {
        super(itemView);
        imageArrow=(ImageView)itemView.findViewById(R.id.imageArrow);
        tvDescripcionLugar=(TextView)itemView.findViewById(R.id.tvDescripcionLugar);
    }
    @Override
    public void expand() {
        imageArrow.setImageResource(R.mipmap.ic_arrow_white_up);
        super.expand();
    }

    @Override
    public void collapse() {
        imageArrow.setImageResource(R.mipmap.ic_arrorw_white);
        super.collapse();
    }
}