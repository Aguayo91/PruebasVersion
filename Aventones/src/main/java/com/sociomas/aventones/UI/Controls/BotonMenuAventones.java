package com.sociomas.aventones.UI.Controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by jr441 on 19/11/2017.
 */

public class BotonMenuAventones extends RelativeLayout {
    public ImageView imgLogo;
    public TextView tvTipo;
    public Drawable icon;
    public String tipo;

    public BotonMenuAventones(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public BotonMenuAventones(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public BotonMenuAventones(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    public BotonMenuAventones(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayouts(context,attrs);
    }


    public void  inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.item_boton_aventones,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BotonAventones);
            icon=typedArray.getDrawable(R.styleable.BotonAventones_iconos);
            tipo=typedArray.getString(R.styleable.BotonAventones_tipoAventon);
        }
    }

    @Override
    protected void onFinishInflate() {
        tvTipo = (TextView)findViewById(R.id.tvAccion);
        imgLogo = (ImageView)findViewById(R.id.imgIcono);
        if(tipo!=null){
            tvTipo.setText(tipo);
        }
        if(icon!=null) {
            imgLogo.setImageDrawable(icon);
        }
        super.onFinishInflate();
    }
}
