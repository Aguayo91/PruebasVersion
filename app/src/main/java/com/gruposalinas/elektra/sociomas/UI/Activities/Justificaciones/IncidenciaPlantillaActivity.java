package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;

import java.util.HashMap;

public class IncidenciaPlantillaActivity extends BaseIncidenciaActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        SearchBoxDialog.SearchBoxDialogCallBack{

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia_plantilla);
        listViewIncidenciasPlantilla = (ListView) findViewById(R.id.listViewPlantilla);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        setToolBar("Justificaciones de mi plantilla");
        callWebService(EnumTipo.plantilla);

    }

    @Override
    public void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados) {
        super.OnSuccess(incidenciaResponse, hashMapEmpleados);
        searchBoxDialog.setCallBack(this);
    }

    @Override
    public void onRefresh() {
        callWebService(EnumTipo.plantilla);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onResult(SearchBoxItem resultItem) {
        if(!resultItem.getValue().equals(searchBoxDialog.TODOS)){
            adapterIncidenciaPlantilla.setListIndidencias(searchByNumeroEmpleado(resultItem.getId()));
        }
        else{
            adapterIncidenciaPlantilla.setListIndidencias(listadoIncidenciasIncidencias);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filtro:
                if(searchBoxDialog!=null) {
                    searchBoxDialog.showAsync();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
