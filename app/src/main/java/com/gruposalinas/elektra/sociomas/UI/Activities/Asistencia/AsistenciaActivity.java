package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias.AdapterAsistenciaHoy;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Asistencias.AdapterAsistenciasExpandable;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogAsistenciaDetalle;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.ExpandableListenerCustom;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.util.ArrayList;
public class AsistenciaActivity extends BaseAppCompactActivity implements AsistenciaPresenterImpl.AsistenciaView,
        SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemSelectedListener, RecyclerItemClickListener {

    private ListView listViewAsistencias;
    private RecyclerView recyclerViewAsistencias, recyclerViewAsistenciasHoy;
    private ExpandableRelativeLayout expandableDia;
    private ImageView imageArrow;
    private SwipeRefreshLayout refreshLayout;
    private SpinnerPlantilla spPlantilla;
    private AsistenciaPresenter presenter;
    private int currentPosition=0;
    private boolean loadingFirstTime=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);
        setToolBar(getString(R.string.mis_asistencias));
        setPresenter();
        presenter.getAsistenciasAsync(Utils.getNumeroEmpleado(this));
    }

    @Override
    public void initView() {
        this.recyclerViewAsistencias=(RecyclerView) findViewById(R.id.recyclerViewAsistencias);
        this.refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        this.spPlantilla=(SpinnerPlantilla) findViewById(R.id.spinnerPlantilla);
        this.expandableDia=(ExpandableRelativeLayout)findViewById(R.id.expandableDia);
        this.recyclerViewAsistenciasHoy=(RecyclerView)findViewById(R.id.recyclerViewAsistenciasHoy);
        this.imageArrow=(ImageView)findViewById(R.id.imageArrow);
    }

    @Override
    public void setListeners() {
        refreshLayout.setOnRefreshListener(this);
        expandableDia.setListener(new ExpandableListenerCustom(imageArrow));
        expandableDia.expand();
        spPlantilla.initSpinnerPlantilla(getString(R.string.asistencias_de),getString(R.string.asistencias_de),true);
        spPlantilla.setOnItemSelectedListener(this);
    }

    @Override
    public void setPresenter() {
        presenter=new AsistenciaPresenterImpl();
        presenter.register(this);
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }


    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void setListAsistencia(ArrayList<ExpandableGroupAsistencia> listAsistencia) {
        AdapterAsistenciasExpandable adapterAsistenciasExpandable=
                new AdapterAsistenciasExpandable(listAsistencia);
        adapterAsistenciasExpandable.setContext(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        adapterAsistenciasExpandable.setItemClickListener(this);
        recyclerViewAsistencias.setAdapter(adapterAsistenciasExpandable);
        recyclerViewAsistencias.setLayoutManager(layoutManager);
    }

    @Override
    public void setListAsistenciaHoy(ArrayList<ResultadoAsistencia> listAsistenciaHoy) {
        AdapterAsistenciaHoy adapterAsistenciaHoy=new AdapterAsistenciaHoy(this,listAsistenciaHoy);
        adapterAsistenciaHoy.setItemClickListener(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerViewAsistenciasHoy.setAdapter(adapterAsistenciaHoy);
        recyclerViewAsistenciasHoy.setLayoutManager(layoutManager);
        expandableDia.expand();
    }

    @Override
    public void setListEmpleados(ArrayList<SearchBoxItem> listEmpleados) {
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        //FILTRO DEL ADAPTER POR EL ID DEL SPINNER SELECCIONADDO
        if(currentPosition==position) {
            return;
        }
        else{
            Plantilla selectedItem =spPlantilla.getSelectedItem();
            presenter.getAsistenciasAsync(selectedItem.getIdEmpleado());
        }
        currentPosition=position;

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        ResultadoAsistencia item=(ResultadoAsistencia)selectedItem;
        showCustomToastDuration(item.getComentario(),800);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asistencia,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_info_asistencia:
                DialogAsistenciaDetalle dialogo=new DialogAsistenciaDetalle(this);
                dialogo.showAsync();
                break;
        }
        return true;
    }

    public void onAsistenciaDia(View v){

        expandableDia.toggle();
    }
}