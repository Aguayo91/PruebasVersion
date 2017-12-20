package com.sociomas.aventones.UI.Controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.sociomas.aventones.R;

public class ProhibitedImage extends RelativeLayout implements View.OnClickListener {

    private ImageView imgProhibite,imgIcono;
    private Drawable icon;
    private Context context;
    private String valor;
    private boolean selectedValor;
    public int countImagen=2;
    private Integer idPreferencia;
    private Integer idRelacion;
    private boolean check=true;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ProhibitedImage(Context context) {
        super(context);
        inflateLayouts(context,null);
    }

    public ProhibitedImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);
    }

    public ProhibitedImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }
    private void  inflateLayouts(Context context, AttributeSet attrs)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.prohibited_layout,this);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProhibitedImage);
            icon = typedArray.getDrawable(R.styleable.ProhibitedImage_iconoRef);
        }
        this.context=context;
    }
    @Override
    protected void onFinishInflate() {
        imgProhibite =(ImageView)findViewById(R.id.imgProhibido);
        imgIcono=(ImageView)findViewById(R.id.imgIcono);
        imgIcono.setOnClickListener(this);
        if(icon!=null){
            imgIcono.setImageDrawable(icon);
        }
        super.onFinishInflate();
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (countImagen % 2 == 0) {
                    check();
        } else {
                    Nocheck();
        }
        countImagen++;
        }

    public void check(){
        imgProhibite.setVisibility(INVISIBLE);
        selectedValor=true;

    }

    public void Nocheck(){
        imgProhibite.setVisibility(VISIBLE);
        selectedValor=false;

    }

    public void setIsChecked(boolean isChecked, Integer idPreferencia, Integer idRelacion)
    {
        this.idPreferencia=idPreferencia;
        this.idRelacion=idRelacion;
        if(isChecked){
            check();
            countImagen++;
        }
        else {
            Nocheck();
        }

    }

    public void setIsChecked(boolean isChecked){
        if(isChecked){
            check();
            countImagen++;
        }
        else {
            Nocheck();
        }
    }


    public void setIsChecked(boolean isChecked, int idPreferencia)
    {
        this.idPreferencia=idPreferencia;
        if(isChecked){
            check();
            countImagen++;
        }
        else {
            Nocheck();
        }
    }


    public boolean isSelectedValor() {
        return selectedValor;
    }

    public String selectedValorStr(){
        return  Boolean.toString(isSelectedValor());
    }

    public int getSelectedId() {
        return idPreferencia;
    }

    public void setSelectedId(int selectedId) {
        this.idPreferencia = selectedId;
    }

    public Integer getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(Integer idRelacion) {
        this.idRelacion = idRelacion;
    }

    public void desactivarClick(){
        imgIcono.setClickable(false);
    }

}
