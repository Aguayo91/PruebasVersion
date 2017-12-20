package com.gruposalinas.elektra.sociomas.UI.Controls.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gruposalinas.elektra.sociomas.UI.Activities.Cuentanos.VistaWebView;
import com.gruposalinas.elektra.sociomas.UI.Activities.SOS.SosActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Constants;


public class Footer extends LinearLayout implements View.OnClickListener{
    private boolean showPanico;
    private Context context;

    public Footer(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context,attrs);

    }

    public Footer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context,attrs);
    }

    public void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_footer,this);
        this.context=context;
    }

    public void inflateLayouts(Context context,AttributeSet attrs){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_footer,this);
        this.context=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.Footer);
        showPanico=typedArray.getBoolean(R.styleable.Footer_showPanico,true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView imgPaginaWeb=(ImageView)this.findViewById(R.id.imgPaginaWeb);
        LinearLayout imgPanico=(LinearLayout)this.findViewById(R.id.imgPanico);
        imgPanico.setVisibility(showPanico? VISIBLE:GONE);
        imgPaginaWeb.setOnClickListener(this);
        imgPanico.setOnClickListener(this);

        /*SOLUCIONA EL ERROR DE  OCULTAR LA BARRA CUANDO EL TECLADO
        * SE ESTA MOSTRANDO*/

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgPaginaWeb:
                Intent intent=new Intent(context, VistaWebView.class);
                intent.putExtra(Constants.PIE_PAGINA,true);
                this.context.startActivity(intent);
                break;
            case R.id.imgPanico:
                this.context.startActivity(new Intent(context,SosActivity.class));
                break;
        }
    }
}