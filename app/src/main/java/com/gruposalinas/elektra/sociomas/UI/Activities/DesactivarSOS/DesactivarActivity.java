package com.gruposalinas.elektra.sociomas.UI.Activities.DesactivarSOS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.IO.Services.SOSAudioService;
import com.gruposalinas.elektra.sociomas.IO.Services.SOService;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivityV2;
import com.sociomas.core.MVP.ViewEvent;

public class DesactivarActivity extends BaseAppCompactActivity implements DesactivarSOSPresenterImpl.DesactivarView {

    private RelativeLayout rl1,rl2;
    private ImageView imgPanico,imgBanned;
    private RelativeLayout layoutEmpleado;
    private TextView titleNumero,titleKey;
    private EditText txtUsuario;
    private DesactivarSosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desactivar);
        setPresenter();
        setToolBar("S.O.S Activado",R.color.rojo,R.color.blanco);
    }

    @Override
    public void initView() {
        rl1=(RelativeLayout)findViewById(R.id.Relative1);
        layoutEmpleado=(RelativeLayout)findViewById(R.id.layoutEmpleado);
        txtUsuario=(EditText)findViewById(R.id.txtUsuario);
        imgBanned=(ImageView)findViewById(R.id.imgBanned);
        titleNumero=(TextView)findViewById(R.id.title);
    }

    @Override
    public void setListeners() {
        Animation transparent2= AnimationUtils.loadAnimation(this,R.anim.item_transparent);
        imgBanned.startAnimation(transparent2);
        Animation transparent= AnimationUtils.loadAnimation(this,R.anim.transparent2);
        layoutEmpleado.setVisibility(View.VISIBLE);
        titleNumero.startAnimation(transparent);
    }

    @Override
    public void onErrorNumeroEmpleado(String mensaje) {
        txtUsuario.setError(mensaje);
        txtUsuario.requestFocus();
    }

    @Override
    public void onSuccess() {
        stopService(new Intent(this, SOService.class));
        stopService(new Intent(this, SOSAudioService.class));
        Intent intent=new Intent(this, InicioActivityV2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        showToast(getString(R.string.boton_desactivado));
    }
    public void Desactivar(View v){
        this.presenter.validarNumeroEmpleado(txtUsuario.getText().toString());
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }
    @Override
    public void setPresenter() {
            this.presenter=new DesactivarSOSPresenterImpl();
            this.presenter.register(this);
    }
    @Override
    public Activity getActivityInstance() {
        return this;
    }
}
