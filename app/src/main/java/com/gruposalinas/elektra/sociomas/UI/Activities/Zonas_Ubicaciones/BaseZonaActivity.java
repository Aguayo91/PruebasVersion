package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones.AdapterTiendasCatalogo;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones.AdapterZonasCatalogo;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones.AdapterZonasUbicaciones;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Zonas_Ubicaciones.AdapterZonasUbicacionesAutorizar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.PendientesView;

import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaList;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by oemy9 on 28/06/2017.
 */

public class BaseZonaActivity extends BaseAppCompactActivity implements
        SearchView.OnQueryTextListener,MenuItemCompat.OnActionExpandListener,View.OnClickListener,
        AdapterZonasUbicaciones.CallBackPlantillaPendientes{

    protected String currentNumeroEmpleado;
    protected ExpandableStickyListHeadersListView listViewCatalogoZonas;
    protected StickyListHeadersListView listViewZona;
    protected Spinner spinnerEmpleado;

    protected  boolean isPlantilla;
    protected AdapterZonasCatalogo adapterZonasCatalogo;
    protected AdapterTiendasCatalogo adapterTiendasCatalogo;
    protected AdapterZonasUbicaciones adapterZonasUbicaciones;
    protected AdapterCatalogoEmpleado adapterCatalogoEmpleado;
    protected AdapterZonasUbicacionesAutorizar adapterZonasUbicacionesAutorizar;

    protected ArrayList<ZonaList> listZonas;
    protected ArrayList<LugarTrabajo>listTienda;
    protected ArrayList<SearchBoxItem> listEmpleados;

    protected SnackBarBuilder snackBarBuilder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controllerAPI=new ControllerAPI(this);
        this.snackBarBuilder=new SnackBarBuilder(this);
        if(getIntent().hasExtra(Constants.SELECTED_ID_EMPLEADO)){
            currentNumeroEmpleado=getIntent().getStringExtra(Constants.SELECTED_ID_EMPLEADO);
        }
    }

    protected void getUbicacionesAsync(final String numeroEmpleado, final boolean isPlantilla, final boolean isAutorizar){
        this.customProgressBar.show(this);
        ServerRequest request=new ServerRequest();
        request.setIdNumEmpleado(numeroEmpleado);
        controllerAPI.getPosicionesEmpleado(request).enqueue(new Callback<ZonaResponse>() {
            @Override
            public void onResponse(Call<ZonaResponse> call, Response<ZonaResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                      if(!isAutorizar) { //SE CARGA UBICAICONES & ZONAS PLANTILLA O NORMAL
                          adapterZonasUbicaciones.clearItems();
                          adapterZonasUbicaciones.setActivity(BaseZonaActivity.this);
                          adapterZonasUbicaciones.setOnPlantillaPendienteListener(BaseZonaActivity.this);
                          adapterZonasUbicaciones.setPlantilla(isPlantilla);
                          adapterZonasUbicaciones.setCurrentNumeroEmpleado(numeroEmpleado);
                          adapterZonasUbicaciones.setPosicionesZonas(response.body().getPosiciones(), false);
                         // adapterZonasUbicaciones.setPosicionesZonas(response.body().getZonas(), true);
                       }
                       else{
                          adapterZonasUbicacionesAutorizar = new AdapterZonasUbicacionesAutorizar(BaseZonaActivity.this, R.layout.item_zona_ubicacion, new ArrayList<LugarTrabajo>());
                          adapterZonasUbicacionesAutorizar.clearItems();
                          adapterZonasUbicacionesAutorizar.setPosicionesZonas(response.body().getPosiciones(), false);
                        //  adapterZonasUbicacionesAutorizar.setPosicionesZonas(response.body().getZonas(), true);
                       }

                        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(!isAutorizar?adapterZonasUbicaciones: adapterZonasUbicacionesAutorizar);
                        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
                        stickyListHeadersAdapterDecorator.setStickyListHeadersListView(listViewZona);
                        listViewZona.setAdapter(stickyListHeadersAdapterDecorator);
                    }
                    customProgressBar.dismiss();
                }
                else{
                    alertaAsync.displayMensaje(response.body().getMensajeError(), BaseZonaActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ZonaResponse> call, Throwable t) {
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion), BaseZonaActivity.this);
            }
        });
    }

    protected void setAdapterTiendaCatalogo(ArrayList<LugarTrabajo>listTienda){
        this.listTienda=listTienda;
        adapterTiendasCatalogo=new AdapterTiendasCatalogo(this,R.layout.item_tienda_catalogo,listTienda);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapterTiendasCatalogo);
        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setStickyListHeadersListView(listViewCatalogoZonas);
        listViewCatalogoZonas.setAdapter(adapterTiendasCatalogo);
    }

    protected  void setAdapterZonasCatalogo(ArrayList<ZonaList>listZonas, String numeroEmpleado, boolean isPlantilla){
        this.listZonas=listZonas;
        adapterZonasCatalogo=new AdapterZonasCatalogo(this, R.layout.item_zona_catalogo,listZonas);
        adapterZonasCatalogo.setPlantilla(isPlantilla);
        adapterZonasCatalogo.setCurrentNumeroEmpleado(numeroEmpleado);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapterZonasCatalogo);
        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setStickyListHeadersListView(listViewCatalogoZonas);
        listViewCatalogoZonas.setAdapter(adapterZonasCatalogo);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onTextChangedSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        onTextChangedSearch(newText);
        return true;
    }

    private void onTextChangedSearch(String newText){

        if(!newText.isEmpty()) {
            if (adapterZonasCatalogo != null) {
                adapterZonasCatalogo.getFilter().filter(newText);
            } else if (adapterTiendasCatalogo != null) {
                adapterTiendasCatalogo.getFilter().filter(newText);
            }

        }
        else {
            if (adapterZonasCatalogo != null) {
                adapterZonasCatalogo.setListCatalogoZona(listZonas);
            } else if (adapterTiendasCatalogo != null) {
                adapterTiendasCatalogo.setListCatalogoTienda(listTienda);
            }
        }

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        if(getSupportActionBar()!=null){
            int colorPrimary= ContextCompat.getColor(BaseZonaActivity.this,R.color.colorPrimary);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(colorPrimary));
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        Bundle mBundle = new Bundle();
        if(!listEmpleados.isEmpty() && listEmpleados.size()>1) {
            int selectedPosicion= spinnerEmpleado.getSelectedItemPosition();
            mBundle.putSerializable(Constants.LIST_EMPLEADOS, listEmpleados);
            mBundle.putInt(Constants.SELECTED_POSITION_EMPLEADO,selectedPosicion);
            Intent intent = new Intent(this, ZonasAutorizarActivity.class);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onPendientesAutorizar(boolean setClickListener) {
        PendientesView pendientesView=(PendientesView)findViewById(R.id.pendientesView);
        pendientesView.setVisibility(View.VISIBLE);
        pendientesView.show(false);
        pendientesView.setOnClickListener(this);
    }
}
