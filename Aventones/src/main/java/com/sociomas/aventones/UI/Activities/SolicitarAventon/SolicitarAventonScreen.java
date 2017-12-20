package com.sociomas.aventones.UI.Activities.SolicitarAventon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Inicio.AventonInicioActivity;
import com.sociomas.aventones.UI.Activities.Rol.AventonRolUsuarioActivity;
import com.sociomas.aventones.UI.Adapters.AdapterScreenSolicitarAventon;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Aventones.PreferenciasSolicitud;
import java.util.ArrayList;

public class SolicitarAventonScreen extends BaseCoreCompactActivity  implements SolicitarAventonPresenterImpl.SolicitarAventonView{

    private SolicitarAventonPresenter presenter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView tvNameConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_aventon_screen);
        ApplicationAventon.onCreate(getApplicationContext());
        setPresenter();
    }

    @Override
    public void setPresenter() {
        presenter=new SolicitarAventonPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
    }

    @Override
    public void initView() {
        tvNameConductor=(TextView)findViewById(R.id.tvNameConductor);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
    }
    public void onEntendido(View v){
       Intent i = new Intent(this, AventonRolUsuarioActivity.class);
       startActivity(i);
       finish();
    }
    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()){
            case REFRESH_VIEW:
                tvNameConductor.setText(event.getModel(ViewEvent.MESSAGE).toString());
                break;
        }
    }
    @Override
    public void setListPreferenciasProhibidas(ArrayList<PreferenciasSolicitud> items) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AdapterScreenSolicitarAventon(items);
        recyclerView.setAdapter(adapter);
    }
}