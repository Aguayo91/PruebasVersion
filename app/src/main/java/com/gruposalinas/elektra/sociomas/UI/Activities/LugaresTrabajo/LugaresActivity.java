package com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.CatalogoTiendasActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.MapaZonaActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Adapters.LugarTrabajo.AdapterLugarTrabajo;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;

import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.ExpandableRecyclerView.models.ExpandableGroup;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Zonas.ExpandableGroupPosicionValida;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import java.util.Collections;

public class LugaresActivity extends BaseAppCompactActivity implements LugaresPresenterImpl.LugaresView,RecyclerItemClickListener,AdapterLugarTrabajo.ListenerActions,
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    private RecyclerView recyclerListTrabajo;
    private LugaresPresenter presenter;
    private int childIndex,selectedGroup;
    private AdapterLugarTrabajo adapterLugarTrabajo;
    private Spinner spinnerEmpleado;
    private EditText txtBusqueda;
    private ArrayList<SearchBoxItem> listEmpleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        this.setToolBar(getString(R.string.mi_lugar_trabajo));
        setPresenter();
    }
    @Override
    public void initView() {
        recyclerListTrabajo=(RecyclerView)findViewById(R.id.recyclerListTrabajo);
        spinnerEmpleado = (Spinner) findViewById(R.id.spinnerCatalogoEmpleado);
        txtBusqueda=(EditText)findViewById(R.id.txtBusqueda);
        txtBusqueda.setFocusable(false);
        txtBusqueda.setCursorVisible(false);
        txtBusqueda.setOnClickListener(this);

    }
    @Override
    public void setListeners() {
        spinnerEmpleado.setOnItemSelectedListener(this);
    }
    @Override
    public void setPresenter() {
        this.presenter=new LugaresPresenterImpl();
        this.presenter.register(this);
        this.presenter.requestLugaresTrabajo();
        this.presenter.requestEmpleadosPlantilla();
    }
    @Override
    public Activity getActivityInstance() {
        return this;
    }


    @Override
    public void setListLugaresTrabajo(ArrayList<ExpandableGroupPosicionValida> listLugares) {
        try {
            adapterLugarTrabajo = new AdapterLugarTrabajo(this, listLugares);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            adapterLugarTrabajo.toggleGroup(0);
            recyclerListTrabajo.setLayoutManager(layoutManager);
            recyclerListTrabajo.setAdapter(adapterLugarTrabajo);
            adapterLugarTrabajo.setItemClickListener(this);
            adapterLugarTrabajo.setListenerActions(this);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void setListEmpleadoPlantilla(ArrayList<SearchBoxItem> listEmpleadoPlantilla) {
        if(!listEmpleadoPlantilla.isEmpty()){
            this.listEmpleados=listEmpleadoPlantilla;
            this.listEmpleados.add(new SearchBoxItem(manager.getString(Constants.SP_ID),manager.getString(Constants.SP_NAME)));
        }
        else{
            this.listEmpleados=new ArrayList<>();
            this.listEmpleados.add(new SearchBoxItem(manager.getString(Constants.SP_ID),manager.getString(Constants.SP_NAME)));
        }
        if(listEmpleados!=null) {
            Collections.reverse(listEmpleados);
            AdapterCatalogoEmpleado adapterCatalogoEmpleado = new AdapterCatalogoEmpleado(this, R.layout.spinner_item, this.listEmpleados);
            adapterCatalogoEmpleado.setDelimitador(getString(R.string.lugares_trabajo_title));
            adapterCatalogoEmpleado.setTituloDialogo(getString(R.string.lugares_trabajo_title));
            this.spinnerEmpleado.setAdapter(adapterCatalogoEmpleado);
        }
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        LugarTrabajo item=(LugarTrabajo)selectedItem;
        if(!item.getIdEstatusPosic().equalsIgnoreCase("P")) {
            Intent intent = new Intent(this, MapaZonaActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(Constants.SELECTED_ZONA_TIENDA, item);
            mBundle.putBoolean(Constants.ELIMINAR_UBICACION,true);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
        else{
            showCustomToastDuration(getString(R.string.lugares_pendientes),500);
        }
    }

    @Override
    public void onAutorizarRechazar(int group, int child, LugarTrabajo p, boolean autorizar) {
        this.selectedGroup=group;
        this.childIndex=child;
        presenter.onAutorizarRechazar(p,autorizar);
    }
    @Override
    public void onAutorizarRechazarSuccess(boolean autorizar,String nombreEmpledo) {
        try {
            SnackBarBuilder snackBarBuilder = new SnackBarBuilder(this);
            String mensaje = getString(autorizar ? R.string.autorizado_propuesta : R.string.rechazado_propuesta);
            snackBarBuilder.showSuccess(mensaje);
            adapterLugarTrabajo.getGroups().get(selectedGroup).getItems().remove(childIndex);
            adapterLugarTrabajo.notifyDataSetChanged();
        }
        catch (Exception ex){
            presenter.requestLugaresTrabajo();
        }
    }
    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SearchBoxItem selectedEmpleado=(SearchBoxItem)spinnerEmpleado.getSelectedItem();
        presenter.requestLugaresTrabajo(selectedEmpleado.getId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
            if(v.getId()==txtBusqueda.getId()){
                Intent intent=new Intent(this,CatalogoTiendasActivity.class);
                intent.putExtra(Constants.IS_PLANTILLA,false);
                intent.putExtra(Constants.SELECTED_ID_EMPLEADO, Utils.getNumeroEmpleado(this));
                startActivity(intent);
            }
    }
}
