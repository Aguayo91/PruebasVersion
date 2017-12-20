package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterCategorias;
import com.gruposalinas.elektra.sociomas.R;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacks.CallBackPromociones;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerPromociones;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;

import java.util.ArrayList;

public class PromocionesActivity extends BaseAppCompactActivity implements CallBackPromociones,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, MenuItemCompat.OnActionExpandListener,
        SearchView.OnQueryTextListener {

    private SwipeRefreshLayout refreshLayout;
    private AdapterCategorias adapterCategorias;
    private GridView gridViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
        this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        this.gridViewCategorias=(GridView)findViewById(R.id.gridViewCategorias);
        this.gridViewCategorias.setOnItemClickListener(this);
        this.refreshLayout.setOnRefreshListener(this);
        setToolBar("Promociones y descuentos");
        getCategoriasAsync();
    }

    private void getCategoriasAsync(){
        customProgressBar.show(this);
        ControllerPromociones controller=new ControllerPromociones(this);
        controller.obtenerCategoriasAsync();
        controller.setCallBackPromociones(this);
    }

    @Override
    public void OnSuccess(ArrayList<PromocionesResponse> listCategorias) {
            if(listCategorias!=null && (!listCategorias.isEmpty())){
                    adapterCategorias=new AdapterCategorias(this,listCategorias);
                    AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapterCategorias);
                    alphaInAnimationAdapter.setAbsListView(gridViewCategorias);
                    gridViewCategorias.setAdapter(alphaInAnimationAdapter);
            }
            customProgressBar.dismiss();

    }

    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_promocion_categoria,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem,this);
        return super.onCreateOptionsMenu(menu);
    }*/



    @Override
    public void OnError(Throwable mensajeError) {
        customProgressBar.dismiss();
        alertaAsync.displayMensaje(mensajeError.getMessage(),this);
    }

    @Override
    public void onRefresh() {
        getCategoriasAsync();
        refreshLayout.setRefreshing(false);
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
            int colorPrimary= ContextCompat.getColor(this,R.color.colorPrimary);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(colorPrimary));
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(!query.isEmpty()) {
            adapterCategorias.getFilter().filter(query);
        }
        else{
            adapterCategorias.reset();
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent=new Intent(this, PromocionListadoActivity.class);
        intent.putExtra(Constants.NOMBRE_CATEGORIA_PROMOCION,adapterCategorias.getItem(position).getSCATDESC());
        intent.putExtra(Constants.ID_CATEGORIA_PROMOCION,adapterCategorias.getItem(position).getSCATLLAVPR());
        startActivity(intent);
    }
}
