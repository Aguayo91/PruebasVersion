package com.gruposalinas.elektra.sociomas.UI.Controls.Navigation;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.gruposalinas.elektra.sociomas.Utils.Date.TimeUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.RangoMonitoreo;
import com.sociomas.core.DataBaseModel.RegistroGPS;


import java.util.ArrayList;

/**
 * Created by oemy9 on 07/06/2017.
 */

@SuppressWarnings("unused")
public class SemaroView extends LinearLayout implements View.OnClickListener {
    private TextView tvBadge;
    private RelativeLayout rootLayout;
    private ImageButton badgeButton;
    private Context context;
    private boolean isVerde,isNarajana,isRojo;
    private Animation showAnimation=AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
    private Animation hideAnimation=AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);

    public SemaroView(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public SemaroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public SemaroView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }

    private void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.semaforo_layout,this);
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout);
        tvBadge=(TextView)findViewById(R.id.tvBadge);
        badgeButton=(ImageButton) findViewById(R.id.badge_icon_button);
        badgeButton.setOnClickListener(this);
        tvBadge.setText(context.getString(R.string.semaforo_admiracion));
        tvBadge.setVisibility(GONE);

        /*PERMITE QUE SE CARGUE LA VISTA PRINCIPAL*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIfPendientes();
            }
        },500);

    }

    public void changeColor(int color){
        badgeButton.setColorFilter(ContextCompat.getColor(getContext(),color), PorterDuff.Mode.SRC_IN);
    }

    private void checkIfPendientes(){
        GPSBDHelper gpsbdHelper=new GPSBDHelper(this.context);
        ArrayList<RegistroGPS> sinSincronizar= gpsbdHelper.getNoSincronizados();
        try {


            RangoMonitoreo rangoMonitoreo;
            rangoMonitoreo = Utils.getHorarioByNumeroDia(this.context);
            if (rangoMonitoreo != null) {
                TimeUtils timeUtils=new TimeUtils(this.context);
                String entrada = rangoMonitoreo.getHoraInicial();
                String salida = rangoMonitoreo.getHoraFinal();
                long milisegundosEntrada = timeUtils.getDiferenceInMillis(entrada);
                long milisegundosSalida = timeUtils.getDiferenceInMillis(salida);

                if (milisegundosEntrada > 0 || milisegundosSalida>0) {

                    if(sinSincronizar!=null){
                        int tamanio=sinSincronizar.size();
                        if(tamanio>=30){
                            tvBadge.setBackground(ContextCompat.getDrawable(this.context,R.drawable.badge_circle_red));
                            tvBadge.setText(String.valueOf(tamanio));
                            isRojo=true;
                        }
                        else if(tamanio>5 && tamanio<30){
                            tvBadge.setBackground(ContextCompat.getDrawable(this.context,R.drawable.badge_circle_orange));
                            tvBadge.setText(String.valueOf(tamanio));
                            isNarajana=true;
                        }
                        else if(tamanio<5){
                            tvBadge.setBackground(ContextCompat.getDrawable(this.context,R.drawable.badge_circle_green));
                            tvBadge.setText(String.valueOf(tamanio));
                            isVerde=true;
                        }
                        this.rootLayout.setVisibility(VISIBLE);
                        this.tvBadge.setVisibility(VISIBLE);
                    }

                }
                else{
                    this.rootLayout.setVisibility(GONE);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        SnackBarBuilder snackBarBuilder=new SnackBarBuilder(Utils.scanForActivity(getContext()));


            if(isVerde){
               // boolean gone=bubbleVerde.getVisibility()==GONE;
                //bubbleVerde.setVisibility(gone?VISIBLE: GONE);
                //bubbleVerde.setAnimation(gone? showAnimation: hideAnimation);
                snackBarBuilder.showSuccess(context.getString(R.string.verde_text));
            }
            else if(isNarajana){
              //  boolean gone=bubbleNaranja.getVisibility()==GONE;
              //  bubbleNaranja.setVisibility(gone? VISIBLE: GONE);
                //bubbleNaranja.setAnimation(gone? showAnimation: hideAnimation);
                snackBarBuilder.showWarning(context.getString(R.string.naranja_text));
            }
            else if(isRojo){
                snackBarBuilder.showError(context.getString(R.string.rojo_text));
                // boolean gone=bubbleRojo.getVisibility()==GONE;
              //  bubbleRojo.setVisibility(gone? VISIBLE: GONE);
                //bubbleRojo.setAnimation(gone? showAnimation: hideAnimation);
            }
    }
}
