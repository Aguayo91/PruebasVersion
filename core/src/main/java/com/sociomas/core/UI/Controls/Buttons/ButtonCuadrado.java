package com.sociomas.core.UI.Controls.Buttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sociomas.core.R;

/**
 * Created by jmarquezu on 22/11/2017.
 */

public class ButtonCuadrado extends RelativeLayout {
    Drawable icon;
    String tipo;
    TextView tvTipo;
    ImageView imgLogo;

    public ButtonCuadrado(Context context) {
        super(context);
        inflateLayout(context,null);
    }

    public ButtonCuadrado(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context, attrs);
    }

    public ButtonCuadrado(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context, attrs);
    }

    public ButtonCuadrado(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayout(context,attrs);
    }

    public void  inflateLayout(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.item_boton_cuadrado,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Botones);
            icon=typedArray.getDrawable(R.styleable.Botones_icono);
            tipo=typedArray.getString(R.styleable.Botones_tipo);
        }
    }

    @Override
    protected void onFinishInflate() {
        tvTipo = (TextView)findViewById(R.id.tvTipo);
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