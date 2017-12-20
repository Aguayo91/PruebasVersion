package com.sociomas.aventones.UI.Activities.MisPasajeros;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.Pasajeros.AdapterPasajeros;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Pasajeros.PasajerosList;

import java.util.ArrayList;

public class MisPasajerosActivity extends BaseCoreCompactActivity implements PasajerosPresenterImpl.PasajerosView, AdapterPasajeros.onPasajerosListener {

    private PasajerosPresenter presenter;
    private RecyclerView recyclerPasajeros;
    private View layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pasajeros);
        setToolBar(R.string.mis_pasajeros);
        setPresenter();
    }

    @Override
    public void initView() {
        recyclerPasajeros=(RecyclerView)findViewById(R.id.recyclerPasajeros);
        layoutEmpty=findViewById(R.id.layoutEmpty);
    }

    @Override
    public void setPresenter() {
        presenter=new PasajerosPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
        if(Utils.hasInternet(this)) {
            presenter.obtenerPasajeros();
        }
        else{
            recyclerPasajeros.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setListPasajeros(ArrayList<PasajerosList> listPasajeros) {
        if(listPasajeros!=null){
            if(!listPasajeros.isEmpty()){
                AdapterPasajeros adapter=new AdapterPasajeros(this,listPasajeros);
                adapter.setListenerPasajeros(this);
                recyclerPasajeros.setLayoutManager(new LinearLayoutManager(this));
                recyclerPasajeros.setAdapter(adapter);
            }
            else{
                recyclerPasajeros.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);

            }
        }
        else{
            recyclerPasajeros.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }

    }

    public void actualizar(View v){
        presenter.obtenerPasajeros();
    }

    @Override
    public void onAceptarRechazarAventon(boolean aceptar, int idRelAsientosReservados) {
        presenter.aceptarRechazarAventon(aceptar,idRelAsientosReservados);
    }

    @Override
    public void obtenerPasajeros() {
        presenter.obtenerPasajeros();
    }
}
