package com.gruposalinas.elektra.sociomas.UI.Activities.SOS;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.IO.Services.SOSAudioService;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Contactos.ContactosActivityV2;
import com.gruposalinas.elektra.sociomas.IO.Services.SOService;
import com.gruposalinas.elektra.sociomas.UI.Activities.DesactivarSOS.DesactivarActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Contactos.AdapterContactos;
import com.gruposalinas.elektra.sociomas.UI.Controls.ImageView.ImageProgressPanico;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.BubbleTextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Contacto.Contacto;

import java.util.ArrayList;


public class SosActivity extends BaseAppCompactActivity implements SosPresenterImpl.SosView, ImageProgressPanico.imageProgressListener{
   private RecyclerView recyclerContactos;
   private TextView timer,tvCounter;
   private boolean isLoadingTimer=false;
   private ImageProgressPanico imageProgressPanico;
   private SosPresenterImpl sosPresenter;
   private BubbleTextView bubleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        setPresenter();
        setToolBar("S.O.S Activado",R.color.rojo,R.color.blanco);
    }

    public void onEdit(View v) {
        startActivity(new Intent(this, ContactosActivityV2.class));
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(sosPresenter.getCountDownSOS()!=null){
            sosPresenter.getCountDownSOS().cancel();
        }
        super.onBackPressed();
    }
    @Override
    public void initView() {
        tvCounter=(TextView)findViewById(R.id.tvCounter);
        imageProgressPanico=(ImageProgressPanico)findViewById(R.id.imageProgressPanico);
        recyclerContactos=(RecyclerView)findViewById(R.id.recyclerContactos);
        bubleTextView=(BubbleTextView)findViewById(R.id.bubleTextView);
    }

    @Override
    public void setListeners() {
        imageProgressPanico.setImageProgressListener(this);
        if (Utils.servicioEjecutando(this, SOService.class) || Utils.servicioEjecutando(this, SOSAudioService.class)) {
            startActivity(new Intent(this, DesactivarActivity.class));
            finish();
        }
    }

    @Override
    public void setPresenter() {
        this.sosPresenter=new SosPresenterImpl();
        this.sosPresenter.register(this);
        sosPresenter.checkIfStartTimer();
        sosPresenter.loadContactsList();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void startServiceSOS() {
        startService(new Intent(this,SOService.class));
    }

    @Override
    public void onTickTimer(long segundos) {
        tvCounter.setText(String.valueOf(segundos));
    }

    @Override
    public void onTimerFinish(boolean finished) {
        startService(new Intent(this,SOService.class));
        finish();
    }

    @Override
    public void setListContactos(ArrayList<Contacto> listContactos) {
        if(listContactos!=null && (!listContactos.isEmpty())){
            AdapterContactos adapterContactos=new AdapterContactos(this,listContactos);
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            recyclerContactos.setLayoutManager(layoutManager);
            recyclerContactos.setAdapter(adapterContactos);
        }
    }

    @Override
    public void onCancelSuccess(boolean success) {
        if(sosPresenter.getCountDownSOS()!=null) {
            showToast(getString(R.string.boton_panico_cancelado));
            sosPresenter.getCountDownSOS().cancel();
            finish();
        }
    }

    @Override
    public void onStartTap() {
        bubleTextView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_out));
        bubleTextView.setVisibility(View.GONE);
    }

    @Override
    public void onStopTap() {
        bubleTextView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        bubleTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        if(event!=null){
            switch (event.getEventType()){
                case SHOW_TOAST_MESSAGE:
                    showToast(event.getModel(ViewEvent.MESSAGE).toString());
                    break;
            }
        }
    }


}
