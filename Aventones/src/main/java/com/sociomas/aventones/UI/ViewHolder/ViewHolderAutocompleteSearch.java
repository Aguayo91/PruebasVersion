package com.sociomas.aventones.UI.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by oemy9 on 31/08/2017.
 */

/*VIEWHOLDER QUE SE USARÁ EN EL CUADRO DE BÚSQUEDA*/
public class ViewHolderAutocompleteSearch  extends RecyclerView.ViewHolder{
    public  TextView tvAutocompleteTitle;
    public  TextView tvSeccion;
    public  TextView tvAutocompleteDescription;
    public ImageView imageMaker;

    public ViewHolderAutocompleteSearch(View itemView) {
        super(itemView);
        tvSeccion=(TextView)itemView.findViewById(R.id.tvSeccion);
        tvAutocompleteTitle =(TextView)itemView.findViewById(R.id.tvQueryTitle);
        tvAutocompleteDescription=(TextView)itemView.findViewById(R.id.tvQueryDescription);
        imageMaker=(ImageView) itemView.findViewById(R.id.imageMaker);
    }


}
