package com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.viewholders.ChildViewHolder;

/**
 * Created by oemy9 on 18/09/2017.
 */

public class ViewHolderLugarChild extends ChildViewHolder {
   public TextView tvLugarTrabajo;
   public ImageView imageViewCircleNombre;

   public ViewHolderLugarChild(View itemView) {
      super(itemView);
      tvLugarTrabajo=(TextView)itemView.findViewById(R.id.tvLugarTrabajo);
      imageViewCircleNombre=(ImageView)itemView.findViewById(R.id.imageCircleNombre);
   }
}
