  package com.sociomas.aventones.UI.Activities.MisAventones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.MisPasajeros.MisPasajerosActivity;
import com.sociomas.aventones.UI.Activities.Publicar.PublicaActivity;
import com.sociomas.aventones.UI.Adapters.AdapterMisPublicados;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventone;
import java.util.List;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class AventonesPublicados extends BaseCoreCompactActivity implements AventonesPublicadosPresenterImpl.AventonPublicadoView, RecyclerItemClickListener {

    private RecyclerView recyclerView;
    private AdapterMisPublicados mAdapter;
    private AventonesPublicadosPresenter presenter;
    private RelativeLayout Relative1,Relative2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aventones_publicados);
        this.setToolBar(getString(R.string.AventonesPublicados));
        setPresenter();
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        Relative1=(RelativeLayout)findViewById(R.id.Relative1);
        Relative2=(RelativeLayout)findViewById(R.id.Relative2);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }
    @Override
    public void setPresenter(){
        presenter=new AventonesPublicadosPresenterImpl();
        presenter.register(this);
        presenter.miListaAventones();
    }

    public void PublicaActivity(View view) {
        Intent i = new Intent(this, PublicaActivity.class);
        startActivity(i);
    }
    @Override
    public void setListAventonesPublicados(List<Aventone> listAventones){

        if(listAventones!=null && (!listAventones.isEmpty())) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(AventonesPublicados.this));
            mAdapter = new AdapterMisPublicados(listAventones);
            mAdapter.setItemClickListener(this);
            AlphaInAnimationAdapter alphaInAnimationAdapter= new AlphaInAnimationAdapter(mAdapter);
            alphaInAnimationAdapter.setDuration(300);
            recyclerView.setAdapter(mAdapter);

        }else{
          setSinAventonesPublicados();
        }

    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
           if(selectedItem!=null){
               Aventone aventon=(Aventone)selectedItem;
               Intent intent=new Intent(this, MisPasajerosActivity.class);
               Bundle bundle=new Bundle();
               bundle.putSerializable(ViewEvent.ENTRY,aventon);
               intent.putExtras(bundle);
               startActivity(intent);
           }
    }
    private void setSinAventonesPublicados() {
        Relative1.setVisibility(View.INVISIBLE);
        Relative2.setVisibility(View.VISIBLE);
    }
    public void back(View view){
        Relative1.setVisibility(View.VISIBLE);
        Relative2.setVisibility(View.INVISIBLE);
    }

}
