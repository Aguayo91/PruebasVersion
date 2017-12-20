package com.gruposalinas.elektra.sociomas.UI.Activities.Promociones;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Promociones.AdapterPromocion;
import com.gruposalinas.elektra.sociomas.R;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacks.CallBackPromociones;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerPromociones;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;

import java.util.ArrayList;

public class PromocionListadoActivity extends BaseAppCompactActivity   implements AdapterView.OnItemClickListener, CallBackPromociones, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {
    private AdapterPromocion adapterPromocion;
    private ListView listViewPromocion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocion_listado);
        this.listViewPromocion=(ListView)findViewById(R.id.listViewPromocion);
        this.listViewPromocion.setOnItemClickListener(this);
        String idCategoria=getIntent().hasExtra(Constants.ID_CATEGORIA_PROMOCION)? getIntent().getStringExtra(Constants.ID_CATEGORIA_PROMOCION):"183";
        getPromocionesListadoAsync(idCategoria);
        setToolBar(Utils.toTitleCase(getIntent().getStringExtra(Constants.NOMBRE_CATEGORIA_PROMOCION)));
    }
    private void getPromocionesListadoAsync(String idCategoria){
        customProgressBar.show(this);
        ControllerPromociones controller=new ControllerPromociones(this);
        controller.obtenerPromocionesAsync(idCategoria);
        controller.setCallBackPromociones(this);
    }
    @Override
    public void OnSuccess(ArrayList<PromocionesResponse> listPromociones) {
        if(listPromociones!=null && (!listPromociones.isEmpty())){
            adapterPromocion=new AdapterPromocion(this,listPromociones);
            ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapterPromocion);
            scaleInAnimationAdapter.setAbsListView(listViewPromocion);
            listViewPromocion.setAdapter(scaleInAnimationAdapter);
        }
        customProgressBar.dismiss();
    }
    @Override
    public void OnError(Throwable mensajeError) {
        customProgressBar.dismiss();
        alertaAsync.displayMensaje(mensajeError.getMessage(),this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
         Intent intent=new Intent(this,PromocionesDetalle.class);
         Bundle bundle=new Bundle();
         bundle.putSerializable(Constants.SELECTED_EMPRESA_PROMOCION,adapterPromocion.getItem(position));
         intent.putExtras(bundle);
         startActivity(intent);
    }
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_promocion_categoria,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem,this);
        return super.onCreateOptionsMenu(menu);
    }*/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String query) {
       if(!query.isEmpty()){
           adapterPromocion.getFilter().filter(query);
       }
       else{
           adapterPromocion.reset();
       }
       return true;
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
}
