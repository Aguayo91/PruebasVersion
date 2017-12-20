package com.sociomas.aventones.UI.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by jmarquezu on 22/09/2017.
 */

public class ControlColors extends RelativeLayout {

    private TextView tvColor;

    public ControlColors(Context context){super (context);}

    public ControlColors(Context context, AttributeSet att){
        super (context, att);
        setLayoutInflater();
    }

    private void setLayoutInflater() {

        ((LayoutInflater)getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                        R.layout.info_placas,this,true);
        tvColor = (TextView)findViewById(R.id.tvTextDialog);
    }

}
