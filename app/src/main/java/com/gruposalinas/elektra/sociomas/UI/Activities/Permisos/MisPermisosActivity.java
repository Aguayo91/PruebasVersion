package com.gruposalinas.elektra.sociomas.UI.Activities.Permisos;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos.AdapterPermisoV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Permisos.AdapterPermisoV3;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.R;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.aventones.UI.Adapters.AutosRecyclerAdapter;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.CallBacks.CallBackPermiso;
import com.sociomas.core.WebService.Model.Request.Permisos.RootPermiso;
import com.sociomas.core.WebService.Model.Response.Permisos.ListExclusiones;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MisPermisosActivity extends BaseAppCompactActivity
implements CallBackPermiso,SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<ListExclusiones> responseListPermisos;
    //private GridView gridviewPermisos;
    private RecyclerView rvMisPermisos;
    private AdapterPermisoV2 adapterPermisos;
    private CustomProgressBar customProgressBar;
    private AdapterPermisoV3 mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_permisos_v2);
  //      this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
  //      this.gridviewPermisos=(GridView)findViewById(R.id.gridViewPermisos);
//        this.refreshLayout.setOnRefreshListener(this);
        this.responseListPermisos=new ArrayList<>();
        this.controllerAPI.ListadoPermisosIOSAsync(new RootPermiso(), EnumConsulta.Mias);
        this.controllerAPI.setCallBackPermiso(this);
        this.setToolBar("Mis permisos");
        this.customProgressBar=new CustomProgressBar(this);
        this.customProgressBar.show(this);
        rvMisPermisos=(RecyclerView)findViewById(R.id.rvMisPermisos);
        rvMisPermisos.setHasFixedSize(true);
        rvMisPermisos.setLayoutManager(new LinearLayoutManager(this));
        rvMisPermisos.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_permisos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_nuevo_permiso:
                Intent i = new Intent(this,AgregarPermisoV2.class);
                i.putExtra(Constants.IS_PLANTILLA,false);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void OnSuccessIOS(ArrayList<ListExclusiones> responsePermisos) {
        if(!responsePermisos.isEmpty()){
            this.responseListPermisos.clear();
            this.responseListPermisos=responsePermisos;
            setAdapterPermisosPlantilla(this.responseListPermisos);
        }
        this.customProgressBar.dismiss();
    }

    @Override
    public void OnError(Throwable mensajeError) {
        this.customProgressBar.dismiss();
    }

    private void setAdapterPermisosPlantilla(ArrayList<ListExclusiones> responsePermisos){
        this.adapterPermisos=new AdapterPermisoV2(this,responsePermisos);
        AlphaInAnimationAdapter alphaInAnimationAdapter=new AlphaInAnimationAdapter(this.adapterPermisos);
    //    alphaInAnimationAdapter.setAbsListView(gridviewPermisos);
    //    this.gridviewPermisos.setAdapter(alphaInAnimationAdapter);
    }
}
