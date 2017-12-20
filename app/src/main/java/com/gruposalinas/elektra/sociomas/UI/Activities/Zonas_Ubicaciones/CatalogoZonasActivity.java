package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;

import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Zonas.ZonaListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

public class CatalogoZonasActivity extends BaseZonaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_zonas);
        listViewCatalogoZonas=(ExpandableStickyListHeadersListView) findViewById(R.id.listViewCatalogoZonas);
        customProgressBar.show(this);
        setToolBar(getString(R.string.catalogo_zonas));
        getCatalogoZonasAsync(currentNumeroEmpleado);
    }

    private void getCatalogoZonasAsync(final String numeroEmpleado){
        controllerAPI.getCatalogoZonas(new ServerRequest()).enqueue(new Callback<ZonaListResponse>() {
            @Override
            public void onResponse(Call<ZonaListResponse> call, Response<ZonaListResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        setAdapterZonasCatalogo(response.body().getZonas(),numeroEmpleado,isPlantilla);
                    }
                    else{
                        alertaAsync.displayMensaje(response.body().getMensajeError(),CatalogoZonasActivity.this);
                    }
                }
                customProgressBar.dismiss();
            }
            @Override
            public void onFailure(Call<ZonaListResponse> call, Throwable t) {
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion),CatalogoZonasActivity.this);
                customProgressBar.dismiss();
            }
        });
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

}
