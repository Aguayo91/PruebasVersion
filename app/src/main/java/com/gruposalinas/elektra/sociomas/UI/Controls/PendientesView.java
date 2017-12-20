package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 24/07/2017.
 */

@SuppressWarnings("unused")
public class PendientesView extends RelativeLayout {
    private RelativeLayout  solicitudesLayout;
    private TextView tvFlecha;

    public PendientesView(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public PendientesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public PendientesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }
    private void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.pendientes_view,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.solicitudesLayout=(RelativeLayout)findViewById(R.id.solicitudesLayout);
        this.tvFlecha=(TextView)findViewById(R.id.tvFlecha);
    }

    public  void show(boolean showFlecha){
        if(solicitudesLayout!=null) {
            solicitudesLayout.setVisibility(View.VISIBLE);
            solicitudesLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_up));
            tvFlecha.setVisibility(showFlecha? VISIBLE: GONE);
        }
    }
}
