package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.GroupViewHolder;

/**
 * Created by oemy9 on 08/09/2017.
 */

public class ViewHolderAsistenciaExpandable extends GroupViewHolder {
    public TextView tvAsistenciaCorrecta;
    public TextView tvFalta;
    public TextView tvSalidaAntes;
    public TextView tvEntradaDespues;
    public TextView tvMes;
    public ImageView imageArrow;
    public   TextView tvRetardo;
    public   TextView tvNoTermina;

    public ViewHolderAsistenciaExpandable(View itemView) {
        super(itemView);
        tvAsistenciaCorrecta=(TextView)itemView.findViewById(R.id.tvAsistenciaCorrecta);
        tvFalta=(TextView)itemView.findViewById(R.id.tvFalta);
        tvSalidaAntes=(TextView)itemView.findViewById(R.id.tvSalidaAntes);
        tvEntradaDespues=(TextView)itemView.findViewById(R.id.tvEntradaDespues);
        tvMes=(TextView)itemView.findViewById(R.id.tvMes);
        tvRetardo=(TextView)itemView.findViewById(R.id.tvRetardo);
        tvNoTermina=(TextView)itemView.findViewById(R.id.tvNoTermina);
        imageArrow=(ImageView)itemView.findViewById(R.id.imageArrow);
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
