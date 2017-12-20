package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciasActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Justificar.JustificarActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterJustificacionV2;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class JustificacionSelectionActivity extends BaseCoreCompactActivity
        implements JustificacionSelectionPresenterImpl.JustificacionView, RecyclerItemClickListener, AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerJustificaciones;
    private JustificacionSelectionPresenter presenter;
    private SpinnerPlantilla spinnerPlantilla;
    private int currentPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificacion_selection);
        setToolBar(R.string.justificaciones);
        setPresenter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(JustificacionSelectionActivity.this);
                }
            });
        }
    }

    @Override
    public void setPresenter() {
        presenter=new JustificacionSelectionPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());
    }

    @Override
    public void initView() {
        recyclerJustificaciones=(RecyclerView)findViewById(R.id.recyvlerJustificaciones);
        spinnerPlantilla=(SpinnerPlantilla)findViewById(R.id.spinnerPlantilla);
        spinnerPlantilla.initSpinnerPlantilla("Justificaciones","Justificaciones de",false);
    }


    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void setListIncidencias(List<ListadoIncidencias> listIncidencias) {
        AdapterJustificacionV2 adpt=new AdapterJustificacionV2(listIncidencias,this);
        AlphaInAnimationAdapter alphaAdpt=new AlphaInAnimationAdapter(adpt);
        alphaAdpt.setDuration(1000);
        adpt.setItemClickListener(this);
        recyclerJustificaciones.setLayoutManager(new LinearLayoutManager(this));
        recyclerJustificaciones.setAdapter(alphaAdpt);
    }

    @Override
    public void setVisibilitySpinnerPlantilla(boolean visible) {
        spinnerPlantilla.setVisibility(visible? View.VISIBLE: View.GONE);
        //Tiene plantila
        if(visible){
            spinnerPlantilla.setOnItemSelectedListener(this);
        }
        //No tiene plantilla
        else{
            presenter.obtenerJustificaciones(Utils.getNumeroEmpleado(this));
        }
    }

    @Override
    public void setSelectedItem(Plantilla item) {
        spinnerPlantilla.setSelectedItem(item);
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        Intent i=new Intent(this, JustificarActivityV2.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.SELECTED_INCIDENCIA,(ListadoIncidencias)selectedItem);
        bundle.putBoolean(Constants.IS_PLANTILLA,presenter.isPlantilla());
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //FILTRO DEL ADAPTER POR EL ID DEL SPINNER SELECCIONADDO
        presenter.obtenerJustificaciones(spinnerPlantilla.getSelectedItem().getIdEmpleado());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
