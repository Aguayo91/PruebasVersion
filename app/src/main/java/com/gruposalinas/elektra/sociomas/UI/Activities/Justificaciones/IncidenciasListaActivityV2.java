package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.Utils.Enums.EnumTipo;

public class IncidenciasListaActivityV2 extends BaseIncidenciaActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_incidencias_v2);
        setToolBar(getString(R.string.mis_justificaciones));
        gridViewIncidencias =(GridView) findViewById(R.id.listadoIncidencias);
        gridViewIncidencias.setOnItemClickListener(this);
        callWebService(EnumTipo.mio);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        AdapterUtils.navegarIncidencias(this,adapterIncidencia.getItem(position),EnumTipo.mio);
    }
}
