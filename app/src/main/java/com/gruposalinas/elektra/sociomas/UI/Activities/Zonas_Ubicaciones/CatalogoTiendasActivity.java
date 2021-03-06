package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;



import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.gruposalinas.elektra.sociomas.R;

import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasActivityV2;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ListLugaresTrabajoRequest;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaPosicionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

public class CatalogoTiendasActivity extends BaseZonaActivity implements AdapterView.OnItemClickListener, Callback<ZonaPosicionResponse> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_tiendas);
        setToolBar(getString(R.string.mi_lugar_trabajo));
        listViewCatalogoZonas=(ExpandableStickyListHeadersListView) findViewById(R.id.listViewCatalogoZonas);
        listViewCatalogoZonas.setOnItemClickListener(this);
        customProgressBar.show(this);
        ListLugaresTrabajoRequest listLugaresTrabajoRequest =new ListLugaresTrabajoRequest();
        listLugaresTrabajoRequest.setTextoBusqueda(" ");
        controllerAPI.consultarPosiciones(listLugaresTrabajoRequest).enqueue(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(CatalogoTiendasActivity.this);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_zona_catalogo, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem,this);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        LugarTrabajo selectedItem=adapterTiendasCatalogo.getItem(position);
        Intent intent=new Intent(this,MapaZonaActivity.class);
        Bundle mBundle=new Bundle();
        mBundle.putSerializable(Constants.SELECTED_ZONA_TIENDA,selectedItem);
        mBundle.putBoolean(Constants.ELIMINAR_UBICACION,false);
        mBundle.putBoolean(Constants.IS_PLANTILLA,isPlantilla);
        mBundle.putString(Constants.SELECTED_ID_EMPLEADO,currentNumeroEmpleado);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<ZonaPosicionResponse> call, Response<ZonaPosicionResponse> response) {
        if(response.isSuccessful()){
            if(!response.body().getError()) {
                setAdapterTiendaCatalogo(response.body().getPosiciones());
            }
            else {
                alertaAsync.displayMensaje(response.body().getMensajeError(),CatalogoTiendasActivity.this);
            }
        }
        customProgressBar.dismiss();
    }

    @Override
    public void onFailure(Call<ZonaPosicionResponse> call, Throwable t) {
        alertaAsync.displayMensaje(getString(R.string.Error_Conexion),CatalogoTiendasActivity.this);
    }
}
