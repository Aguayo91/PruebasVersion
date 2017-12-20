package com.gruposalinas.elektra.sociomas.UI.Controls;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.Utils.Enums.EnumAsistencia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jmarquezu on 30/11/2017.
 */

public class AsistenciaHoyControl extends RelativeLayout {

    public static final String ENTRADA="Entrada";
    public static final String SIN_REGISTRO="Pendiente";

    private TextView tvDia,tvMes,tvLlegada,tvTipo,tvStatus;
    private ImageView imgTipo,imgStatus;
    private ImageView imgAsistencia;

    public AsistenciaHoyControl(Context context) {
        super(context);
        inflateView(context);
    }

    public AsistenciaHoyControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateView(context);
    }

    public AsistenciaHoyControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
    }

    public AsistenciaHoyControl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView(context);
    }

    private void inflateView(Context context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.item_asistencias_hoy,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init(){
        Calendar calendar = Calendar.getInstance();
        tvDia = (TextView)findViewById(R.id.tvDia);
        tvMes = (TextView)findViewById(R.id.tvMes);
        tvLlegada = (TextView)findViewById(R.id.tvLlegada);
        tvTipo = (TextView)findViewById(R.id.tvTipoAsistencia);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        imgTipo = (ImageView)findViewById(R.id.imgAsistencia);
        imgStatus = (ImageView)findViewById(R.id.imgStatus);
        setTvDia();

    }

    private void setTvDia (){
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd");
        DateFormat df1 = new SimpleDateFormat("MMM/yyyy",new Locale("es"));
        tvDia.setText(df.format(calendar.getTime()));
        tvMes.setText(df1.format(calendar.getTime()).toUpperCase().replace(".",""));
    }

    public void setAsistencia (Calendar hora, String tipo){
        //hora = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        tvLlegada.setText(ENTRADA+" "+df.format(hora.getTime()));
        tvTipo.setText(tipo);
    }

    public void setStatus(String status){
        tvStatus.setText(status);
    }

    public void setAsistencia (String hora, EnumAsistencia tipo){
        tvLlegada.setText(ENTRADA+" "+hora);
        tvTipo.setText(tipo.toString());
        imgTipo.setImageResource(AdapterUtils.getHashIconosAsistencias().get(tipo));
        if(tipo==EnumAsistencia.TODAVIA_NO_TERMINA_DIA){
            imgStatus.setImageResource(R.drawable.badge_circle_grey_claro);
        }
    }
    public void setAsistencia(EnumAsistencia tipo){
        tvLlegada.setText(SIN_REGISTRO);
        if(tipo==EnumAsistencia.TODAVIA_NO_TERMINA_DIA){
            imgStatus.setImageResource(R.drawable.badge_circle_grey_claro);
            imgTipo.setImageResource(R.drawable.badge_circle_grey_claro);
        }
    }

}


