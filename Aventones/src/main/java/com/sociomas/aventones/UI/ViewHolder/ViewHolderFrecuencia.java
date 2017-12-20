package com.sociomas.aventones.UI.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class ViewHolderFrecuencia extends RecyclerView.ViewHolder {
    public TextView tvDia;
    public CheckBox checkDia;

    public ViewHolderFrecuencia(View itemView) {
        super(itemView);
        tvDia=(TextView)itemView.findViewById(R.id.tvDia);
        checkDia=(CheckBox)itemView.findViewById(R.id.checkDia);
    }
}
