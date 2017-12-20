package com.gruposalinas.elektra.sociomas.UI.Activities.Permisos;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos.AdapterPermisosPlantilla;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchBoxDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.CallBacks.CallBackPermiso;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Permisos.RootPermiso;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;

public class PermisosPlantillaActivity extends BaseAppCompactActivity implements CallBackPermiso, SearchBoxDialog.SearchBoxDialogCallBack,
        SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener, View.OnClickListener {

    private ArrayList<ListExclusiones>responseListPermisos;
    private  ControllerAPI controllerAPI;
    private SwipeRefreshLayout refreshLayout;
    private ListView listViewPermisos;
    private AdapterPermisosPlantilla adapterPermisosPlantilla;
    private SearchBoxDialog searchBoxDialog;
    private CustomProgressBar customProgressBar;
    private FloatingActionButton fabAgregarPermiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos_plantilla);
        this.listViewPermisos=(ListView)findViewById(R.id.listViewPermisosP);
        this.listViewPermisos.setItemsCanFocus(true);
        this.fabAgregarPermiso=(FloatingActionButton)findViewById(R.id.fabAgregarPermiso);
        this.fabAgregarPermiso.setOnClickListener(this);

        this.controllerAPI=new ControllerAPI(this);
        this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        this.refreshLayout.setOnRefreshListener(this);
        this.responseListPermisos=new ArrayList<>();
        this.controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(), EnumConsulta.LineaDirecta);
        this.controllerAPI.setCallBackPermiso(this);
        this.setToolBar("Permisos de mi plantilla");
        this.searchBoxDialog=new SearchBoxDialog(this);
        this.searchBoxDialog.setCallBack(this);
        this.searchBoxDialog.setTitle("Filtrar por empleado");
        this.customProgressBar=new CustomProgressBar(this);
        this.customProgressBar.show(this);
    }



    @Override
    public void OnError(Throwable mensajeError) {
        Alertas alerta=new Alertas(this);
        alerta.displayMensaje(mensajeError.getMessage(),this);
        this.customProgressBar.dismiss();
    }
    @Override
    public void OnSuccessIOS(ArrayList<ListExclusiones> responsePermisos) {
        if(!responsePermisos.isEmpty()){
            this.responseListPermisos.clear();
            this.responseListPermisos=responsePermisos;
            setAdapterPermisosPlantilla(this.responseListPermisos);
            searchBoxDialog.setListEmpleados(this.responseListPermisos);
        }
        else{
            finish();
            Toast.makeText(getApplicationContext(),getString(R.string.no_plantilla_permisos),Toast.LENGTH_LONG).show();
        }

        this.customProgressBar.dismiss();
    }

    private void searchByNumeroEmpleado(String numeroEmpleado){
        ArrayList<ListExclusiones>responseSearch=new ArrayList<>();
        for(ListExclusiones item:this.responseListPermisos){
            if(item.getIdNumEmpleado().equals(numeroEmpleado)){
                responseSearch.add(item);
            }
        }
       setAdapterPermisosPlantilla(responseSearch);
    }
    private void setAdapterPermisosPlantilla(ArrayList<ListExclusiones> responsePermisos){
        this.adapterPermisosPlantilla=new AdapterPermisosPlantilla(this,responsePermisos);
        AlphaInAnimationAdapter  alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterPermisosPlantilla);
        alphaInAnimationAdapter.setAbsListView(listViewPermisos);
        this.listViewPermisos.setAdapter(alphaInAnimationAdapter);
        this.listViewPermisos.setOnScrollListener(this);
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
                searchBoxDialog.showAsync();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh() {
        controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(),EnumConsulta.LineaDirecta);
        this.refreshLayout.setRefreshing(false);
    }
    @Override
    public void onResult(SearchBoxItem resultItem) {
        if(!resultItem.getValue().equals(searchBoxDialog.TODOS)) {
            searchByNumeroEmpleado(resultItem.getId());
        }
        else{
            setAdapterPermisosPlantilla(this.responseListPermisos);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,AgregarPermisoV2.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.LIST_EMPLEADOS,searchBoxDialog.getItemArrayList());
        intent.putExtras(bundle);
        intent.putExtra(Constants.IS_PLANTILLA,true);
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
                fabAgregarPermiso.setVisibility(View.VISIBLE);
        }
        else{
                fabAgregarPermiso.setVisibility(View.GONE);
        }
    }
}
