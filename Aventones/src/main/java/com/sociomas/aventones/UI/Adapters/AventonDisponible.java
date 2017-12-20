package com.sociomas.aventones.UI.Adapters;

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
 * Created by jmarquezu on 18/09/2017.
 */

public class AventonDisponible extends RelativeLayout {



    private TextView tvOrigen,tvDestino, tvEmpleado,tvNumero;
    private ImageView imageView;

    public AventonDisponible(Context context) {super(context);}

    public AventonDisponible(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutInflater();

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.itemAventones);

        String origen =typedArray.getString(R.styleable.itemAventones_origen);
        String destino =typedArray.getString(R.styleable.itemAventones_destino);
        String empleado = typedArray.getString(R.styleable.itemAventones_empleado);
        String numero = typedArray.getString(R.styleable.itemAventones_numero);
        Drawable icon = typedArray.getDrawable(R.styleable.itemAventones_icon);
        tvOrigen.setText(origen);
        tvDestino.setText(destino);
        tvEmpleado.setText(empleado);
        tvNumero.setText(numero);

        imageView.setImageDrawable(icon);

        typedArray.recycle();
    }

    public AventonDisponible(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }



    private void setLayoutInflater() {

        ((LayoutInflater)getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                        R.layout.item_aventones,this,true);

        tvOrigen =(TextView)findViewById(R.id.tvorigen);
        tvDestino = (TextView)findViewById(R.id.tvdestino);
        tvEmpleado =(TextView)findViewById(R.id.tvPerfil);
        tvNumero=(TextView)findViewById(R.id.tvasientos);
        imageView=(ImageView)findViewById(R.id.relative1);
    }
}