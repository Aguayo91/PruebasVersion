package com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

/**
 * Created by oemy9 on 08/09/2017.
 */

public class ViewHolderAsistenciaHoy extends ChildViewHolder {

   public   TextView tvNombre;
   public   TextView tvEntrada;
   public   TextView tvSalida;
   public   TextView tvRetardo;
   public   ImageView imageStatusAsistencia;
   public   TextView tvNoTermina;

    public ViewHolderAsistenciaHoy(View itemView) {
        super(itemView);
        tvNombre=(TextView)itemView.findViewById(R.id.tvNombre);
        tvEntrada=(TextView)itemView.findViewById(R.id.tvEntrada);
        tvSalida=(TextView)itemView.findViewById(R.id.tvSalida);
        tvRetardo=(TextView)itemView.findViewById(R.id.tvRetardo);
        imageStatusAsistencia=(ImageView)itemView.findViewById(R.id.imageStatusAsistencia);
    }
}
