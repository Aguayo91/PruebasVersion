package com.sociomas.aventones.UI.Controls.Pickers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.sociomas.aventones.R;

/**
 * Created by jromeromar on 12/09/2017.
 */

public class SeekBarPicker extends RelativeLayout {

    private Context context;
    private Drawable icon;
    public SeekBarPicker(Context context) {
        super(context);
        inflateSeekbar(context,null);
    }

    private void  inflateSeekbar(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.dialog_picker_dia,this);
        this.context=context;
    }
}