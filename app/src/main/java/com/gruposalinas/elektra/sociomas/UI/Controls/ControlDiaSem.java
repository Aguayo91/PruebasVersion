package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by jromeromar on 14/11/2017.
 */

public class ControlDiaSem extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private ImageView imgCheck, imgNoCheck;
    public int countImagen = 1;
    private TextView tvDia;
    private boolean isChecked = true;

    public ControlDiaSem(Context context) {

        super(context);
        inflateLayouts(context, null);
    }

    public ControlDiaSem(Context context, AttributeSet attrs) {

        super(context, attrs);
        inflateLayouts(context, attrs);
    }

    public ControlDiaSem(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        inflateLayouts(context, attrs);
    }

    private void inflateLayouts(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_dia_sem, this);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {

        imgCheck = (ImageView) findViewById(R.id.imgCheck);
        imgNoCheck = (ImageView) findViewById(R.id.imgNOCheck);
        tvDia = (TextView) findViewById(R.id.tvDia);
        tvDia.setOnClickListener(this);
        super.onFinishInflate();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (countImagen % 2 == 0) {
            check();
        } else {
            noCheck();
        }
        countImagen++;
    }

    public void check() {
        isChecked = true;
        imgCheck.setVisibility(VISIBLE);
        imgNoCheck.setVisibility(INVISIBLE);
    }

    public void noCheck() {
        isChecked = false;
        imgCheck.setVisibility(INVISIBLE);
        imgNoCheck.setVisibility(VISIBLE);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setListener(OnClickListener listener) {
        tvDia.setOnClickListener(listener);
    }

    public void setDia(String dia) {
        tvDia.setText(dia);
    }

    public void desactivarClick() {
        tvDia.setClickable(false);
    }

    public void activarClick() {
        tvDia.setClickable(true);
    }

}