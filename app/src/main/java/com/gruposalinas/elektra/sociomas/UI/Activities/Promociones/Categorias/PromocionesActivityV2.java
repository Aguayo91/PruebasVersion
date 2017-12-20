package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Categorias;

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
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Promociones.Listado.PromocionesListadoV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterCategoriasV2;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoLista;

import java.util.ArrayList;

public class PromocionesActivityV2 extends BaseCoreCompactActivity implements PromoCatPresenterImpl.PromocionesView,
        MenuItemCompat.OnActionExpandListener,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener{

    private SwipeRefreshLayout refreshLayout;
    private AdapterCategoriasV2 adapterCategorias;
    private GridView gridViewCategorias;

    private PromoCatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
        setPresenter();

    }

    @Override
    public void initView() {
        this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        this.gridViewCategorias=(GridView)findViewById(R.id.gridViewCategorias);
        this.gridViewCategorias.setOnItemClickListener(this);
        this.refreshLayout.setOnRefreshListener(this);
        this.setToolBar(R.string.promo_descuentos);

    }

    @Override
    public void setListeners() {
        super.setListeners();
    }

    @Override
    public void setPresenter() {
        presenter=new PromoCatPresenterImpl();
        presenter.register(this);
        presenter.obtenerCategorias();

    }

    @Override
    public void setListPromociones(ArrayList<PromoLista> listCategorias) {
        if(listCategorias!=null && (!listCategorias.isEmpty())){
            adapterCategorias=new AdapterCategoriasV2(this,listCategorias);
            AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapterCategorias);
            alphaInAnimationAdapter.setAbsListView(gridViewCategorias);
            gridViewCategorias.setAdapter(alphaInAnimationAdapter);
        }
        customProgressBar.dismiss();

    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_promocion_categoria,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem,this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void changeToolbarTitle(String title) {
        setToolBar(title);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent=new Intent(this, PromocionesListadoV2.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,adapterCategorias.getItem(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        presenter.obtenerCategorias();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
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

        if(!query.isEmpty()&&adapterCategorias!=null) {
            adapterCategorias.getFilter().filter(query);
        }
        else if(adapterCategorias!=null){
            adapterCategorias.reset();
       /* }else{
            presenter.onShowAlert(R.string.Error_Conexion);
       */
        }
        return true;
    }
}
