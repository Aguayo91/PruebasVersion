package com.sociomas.aventones.UI.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sociomas.aventones.R;

import java.util.zip.Inflater;

/**
 * Created by jmarquezu on 10/10/2017.
 */

public class ControlQrId extends RelativeLayout {

    TextView tvIdAventon;
    ImageView imgQr;
    public ControlQrId(Context context) {
        super(context);
    }

    public ControlQrId(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInflate();
    }
    private void setInflate (){
        ((LayoutInflater)getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                        R.layout.item_qr_id,this,true);
        tvIdAventon = (TextView)findViewById(R.id.tvIdViaje);
        imgQr = (ImageView)findViewById(R.id.imageQr);
    }
}
