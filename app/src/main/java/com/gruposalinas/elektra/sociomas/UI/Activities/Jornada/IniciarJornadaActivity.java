package com.gruposalinas.elektra.sociomas.UI.Activities.Jornada;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogsAtencion;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.core.Listeners.CallBackLocationGenerated;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Activities.BaseLocationActivity;
import com.sociomas.core.Utils.Enums.EnumJornada;
import java.util.concurrent.TimeUnit;

public class IniciarJornadaActivity extends BaseLocationActivity implements JornadaPresenterImpl.JornadaView,View.OnClickListener, CallBackLocationGenerated {

    private JornadaPresenter presenter;
    private  int animationDuration = 1000;
    private CircleProgressbar circleInicio,circleFin;
    private ImageView btniniciarJornada, btnTerminarJornada;
    private TextView tvIniciarJornada, tvTerminarJornada;
    private RelativeLayout rlBotones,rlInicioJornada,rlTerminoJornada;
    private boolean firedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_jornada);
        setToolBar(getString(R.string.asistencias));
        setPresenter();

    }

    @Override
    public void initView() {
        btniniciarJornada = (ImageView) findViewById(R.id.imgIniciaBoton);
        btnTerminarJornada = (ImageView) findViewById(R.id.imgTerminaBoton);
        tvIniciarJornada = (TextView) findViewById(R.id.tvInicia);
        tvTerminarJornada = (TextView) findViewById(R.id.tvTermina);
        rlBotones = (RelativeLayout)findViewById(R.id.rlBotones);
        rlInicioJornada = (RelativeLayout)findViewById(R.id.rlComienzoJornada);
        rlTerminoJornada =  (RelativeLayout)findViewById(R.id.rlTerminoJornada);
        circleInicio=(CircleProgressbar)findViewById(R.id.circle_loading);
        circleFin=(CircleProgressbar)findViewById(R.id.circle_loading2);
    }

    @Override
    public void setPresenter() {
        presenter=new JornadaPresenterImpl();
        presenter.register(this);
        checkIfData();
        setCallBackLocationGenerated(this);

    }

    @Override
    public void setListeners() {
        btniniciarJornada.setOnClickListener(this);
        btnTerminarJornada.setOnClickListener(this);
    }

    private void checkIfData(){
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            if(extras.getBoolean(ViewEvent.BOOLEAN_OBJECT,false)){
                mostrarDialogo();
            }
        }
    }

    public void mostrarDialogo(){
        DialogsAtencion dialogo = new DialogsAtencion();
        dialogo.show(getSupportFragmentManager().beginTransaction(),DialogsAtencion.TAG);
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.imgIniciaBoton){
            circleInicio.setVisibility(View.VISIBLE);
            circleInicio.setClockwise(false);
            circleInicio.setProgressWithAnimation(100, animationDuration);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.ingresarAsistenciaManual(EnumJornada.ENTRADA,currentLatLng);
                }

            },1000);

        }else if(v.getId()==R.id.imgTerminaBoton){
            circleFin.setVisibility(View.VISIBLE);
            circleFin.setClockwise(false);
            circleFin.setProgressWithAnimation(100, animationDuration);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            presenter.ingresarAsistenciaManual(EnumJornada.SALIDA, currentLatLng);
                }

            },1000);
         }
    }

    public void cambiarActivity (){
        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IniciarJornadaActivity.this, InicioActivityV2.class));
            }
        }, TimeUnit.SECONDS.toMillis(5));
    }

    @Override
    public void onSuccessRegistro(EnumJornada jornada) {
        rlBotones.setVisibility(View.GONE);
        switch (jornada){
            case ENTRADA:
                rlInicioJornada.setVisibility(View.VISIBLE);
                break;
            case SALIDA:
                rlTerminoJornada.setVisibility(View.VISIBLE);
                break;
        }
        cambiarActivity();
    }

    @Override
    public void onLocationGenerated(LatLng latLng) {
        this.firedLocation=true;
    }
}
