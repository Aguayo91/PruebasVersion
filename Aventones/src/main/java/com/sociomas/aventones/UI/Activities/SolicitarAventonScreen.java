package com.sociomas.aventones.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Inicio.AventonInicioActivity;
import com.sociomas.aventones.UI.Adapters.AdapterScreenSolicitarAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.PreferenciasSolicitud;
import java.util.ArrayList;
import java.util.List;

public class SolicitarAventonScreen extends BaseCoreCompactActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lManager;
    private RecyclerView.Adapter adapter;
    private Aventones consultaAventones;
    private Bundle mBundle;
    private TextView tvNameConductor;
    private String nameConductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_aventon_screen);
        mBundle = getIntent().getExtras();


        mBundle = getIntent().getExtras();
        if (mBundle.containsKey(ViewEvent.ENTRY)) {
            consultaAventones = (Aventones) mBundle.getSerializable(ViewEvent.ENTRY);
            nameConductor=consultaAventones.getNombre_completo();

        } else {
            //TODO mostrar dialogo de error
        }

        tvNameConductor=(TextView)findViewById(R.id.tvNameConductor);
        tvNameConductor.setText(nameConductor);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);

        // Usar un administrador para el recycler (Horizontal o Vertical)
        //Vertical
        lManager = new LinearLayoutManager(this);
        //Horizontal
        LinearLayoutManager managerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(managerHorizontal);

        List<PreferenciasSolicitud> items= new ArrayList<>();
        items.add(new PreferenciasSolicitud(R.drawable.ic_cerveza,"",""));
        items.add(new PreferenciasSolicitud(R.drawable.ic_armas,"",""));

        adapter = new AdapterScreenSolicitarAventon(items);
        recyclerView.setAdapter(adapter);
    }

    public void onEntendido(View v){
        Intent i = new Intent(this, AventonInicioActivity.class);
        startActivity(i);
    }
}