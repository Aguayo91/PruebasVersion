package com.sociomas.aventones.UI.Activities.SolicitudAceptarAventon;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.j256.ormlite.stmt.query.In;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Inicio.AventonInicioActivity;
import com.sociomas.aventones.UI.Activities.Rol.AventonRolUsuarioActivity;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.FireBase.PushAceptarAventon;

import java.io.Serializable;

public class SolicitudConductorActivity extends BaseCoreCompactActivity  implements
        SolicitudConductorPresenterImpl.SolicitarConductorView, View.OnClickListener {
    private TextView tvName,tvId,tvOrigen,tvDestino;
    private SolicitudConductorPresenter presenter;
    private FloatingActionButton btnAceptar;
    private FloatingActionButton btnCancelar;
    private Button btnDecicidr;
    private PushAceptarAventon pushAceptarAventon = new PushAceptarAventon();
    private Intent intent;

    public SolicitudConductorActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_conductor);
        setToolBar(getString(R.string.solicitud_exitosa));
        ApplicationAventon.onCreate(getApplicationContext());
        initView();
        setPresenter();
        presenter.mostarInformacion();
    }

    public void setPush (Intent intent){
        this.pushAceptarAventon=new Gson().fromJson(intent.getStringExtra(Constants.DATA_SEND),PushAceptarAventon.class);
    }

    @Override
    public void setPresenter() {
        this.presenter=new SolicitudConductorPresenterImpl();
        presenter.register(this);
        if(getIntent().hasExtra(Constants.DATA_SEND)) {
            try {
                pushAceptarAventon = new Gson().fromJson(getIntent().getStringExtra(Constants.DATA_SEND), PushAceptarAventon.class);
                presenter.setArguments(getIntent().putExtra(Constants.DATA_SEND, pushAceptarAventon.toString()));
            }catch (JsonSyntaxException ex){
                showMsgDialog(this,"Json format exception", getIntent().getStringExtra(Constants.DATA_SEND));
            }
        }
    }

    @Override
    public void initView() {
        tvName=(TextView)findViewById(R.id.tvName);
        tvId=(TextView)findViewById(R.id.tvId);
        tvOrigen=(TextView)findViewById(R.id.tvOrigen);
        tvDestino=(TextView)findViewById(R.id.tvDestino);
        btnAceptar=(FloatingActionButton)findViewById(R.id.btnAceptar);
        btnCancelar=(FloatingActionButton)findViewById(R.id.btnCancelar);
        btnDecicidr = (Button)findViewById(R.id.btnDecidir);
        btnDecicidr.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnAceptar.setOnClickListener(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();
    }

    @Override
    public void mostrarInformacion(PushAceptarAventon solicitudPush) {
        tvName.setText(solicitudPush.getNombreEmpleado());
        tvOrigen.setText(solicitudPush.getOrigen());
        tvDestino.setText(solicitudPush.getDestino());
        tvId.setText(solicitudPush.getNumeroEmpleado());
        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
            if(v.getId()==btnAceptar.getId()){
                presenter.aceptarRechazarAventon(true);
            }
            else if(v.getId()==btnCancelar.getId()){
                presenter.aceptarRechazarAventon(false);
            }
            else if(v.getId()==btnDecicidr.getId()){
                navegarInicio();
                finish();
            }
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void navegarInicio() {
        Intent intent=new Intent(this, AventonRolUsuarioActivity.class);
        startActivity(intent);
    }
}
