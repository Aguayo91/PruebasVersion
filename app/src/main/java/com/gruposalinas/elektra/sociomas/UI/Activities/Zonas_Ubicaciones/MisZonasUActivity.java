package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones.AdapterZonasUbicaciones;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacks.CallBackListadoPlantilla;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import java.util.Collections;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MisZonasUActivity extends BaseZonaActivity implements CallBackListadoPlantilla,AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_zonas_u);
        this.listViewZona=(StickyListHeadersListView)findViewById(R.id.listViewZonas);
        this.spinnerEmpleado=(Spinner)findViewById(R.id.spinnerCatalogoEmpleado);
        this.setToolBar(getString(R.string.mi_lugar_trabajo));
        this.adapterZonasUbicaciones=new AdapterZonasUbicaciones(this, R.layout.item_zona_ubicacion,new ArrayList<LugarTrabajo>());
        this.spinnerEmpleado.setVisibility(View.VISIBLE);
        controllerAPI.getListadoEmpleadosPlantilla();
        controllerAPI.setCallBackListadoPlantilla(this);
        this.spinnerEmpleado.setOnItemSelectedListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_zonas_ubicaciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_nuevo_zona_ubicacion:
                Intent intent=new Intent(this,CatalogoTiendasActivity.class);
                intent.putExtra(Constants.IS_PLANTILLA,isPlantilla);
                intent.putExtra(Constants.SELECTED_ID_EMPLEADO,currentNumeroEmpleado);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnError(Throwable mensajeError) {

    }



    @Override
    public void OnSuccess(ArrayList<SearchBoxItem> listadoPlantilla) {
        if(!listadoPlantilla.isEmpty()){
            this.listEmpleados=listadoPlantilla;
            this.listEmpleados.add(new SearchBoxItem(manager.getString(Constants.SP_ID),manager.getString(Constants.SP_NAME)));
        }
        else{
            this.listEmpleados=new ArrayList<>();
            this.listEmpleados.add(new SearchBoxItem(manager.getString(Constants.SP_ID),manager.getString(Constants.SP_NAME)));
        }
        if(listEmpleados!=null) {
            Collections.reverse(listEmpleados);
            this.adapterCatalogoEmpleado = new AdapterCatalogoEmpleado(this, R.layout.spinner_item, this.listEmpleados);
            this.adapterCatalogoEmpleado.setDelimitador(getString(R.string.lugares_trabajo_title));
            this.adapterCatalogoEmpleado.setTituloDialogo(getString(R.string.lugares_trabajo_title));
            this.spinnerEmpleado.setAdapter(adapterCatalogoEmpleado);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        SearchBoxItem selectedEmpleado=(SearchBoxItem)spinnerEmpleado.getSelectedItem();
        this.currentNumeroEmpleado=selectedEmpleado.getId();
        this.isPlantilla=!selectedEmpleado.getId().equals(manager.getString(Constants.SP_ID));
        getUbicacionesAsync(currentNumeroEmpleado,isPlantilla,false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
