package com.gruposalinas.elektra.sociomas.UI.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 07/09/2017.
 */

public class ViewHolderContactos extends RecyclerView.ViewHolder {
    public TextView tvNombreTel;
    public ViewHolderContactos(View itemView) {
        super(itemView);
        tvNombreTel=(TextView)itemView.findViewById(R.id.tvNombreTel);
    }
}
