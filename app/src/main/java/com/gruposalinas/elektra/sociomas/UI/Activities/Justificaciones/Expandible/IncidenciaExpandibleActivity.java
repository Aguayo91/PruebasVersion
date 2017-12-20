package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.Expandible;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioPresenter;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Incidencias.AdapterIncidenciasExpandible;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Enums.EnumTipo;
import com.sociomas.core.WebService.Model.Response.Incidencia.ExpandableGroupIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

import java.util.ArrayList;

public class IncidenciaExpandibleActivity extends BaseCoreCompactActivity implements IncidenciaPresenterImpl.IncidenciaView, AdapterIncidenciasExpandible.ListenerActions {

    private IncidenciaPresenter presenter;
    private RecyclerView recyclerIncidencias;
    private AdapterIncidenciasExpandible adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia_expandible);
        setToolBar(getString(R.string.mis_justificaciones));
        setPresenter();
    }
    @Override
    public void initView() {
        this.recyclerIncidencias=(RecyclerView)findViewById(R.id.recyclerIncidencia);
    }
    @Override
    public void setPresenter() {
        this.presenter=new IncidenciaPresenterImpl();
        this.presenter.register(this);
        this.presenter.obtenerIncidencias();
    }

    @Override
    public void onSucessAprobadaRechazada(@StringRes int mensaje) {
        SnackBarBuilder snackBarBuilder=new SnackBarBuilder(this);
        snackBarBuilder.showSuccess(getString(mensaje));
    }

    @Override
    public void onComentarioVacio(@StringRes int mensaje) {
        SnackBarBuilder snackBarBuilder = new SnackBarBuilder(this);
        snackBarBuilder.showError(getString(mensaje));
    }

    @Override
    public void openChild(int child) {
        if(adapter!=null){
            adapter.toggleGroup(child);
        }
    }

    @Override
    public void setListIncidencias(ArrayList<ExpandableGroupIncidencias> listIncidencias) {
        if(listIncidencias!=null){
            adapter=new AdapterIncidenciasExpandible(this,listIncidencias);
            adapter.setListenerActions(this);
            adapter.toggleGroup(0);
            this.recyclerIncidencias.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerIncidencias.setAdapter(adapter);
        }
    }
    @Override
    public void rechazarAprobar(ListadoIncidencias selectedItem, boolean autorizar) {
        this.presenter.rechazarAprobar(selectedItem,autorizar);
    }
    @Override
    public void validarJustificacion(ListadoIncidencias selectedItem) {
        this.presenter.validarJustificacion(selectedItem);
    }

    @Override
    public void onSelectedItemdMios(ListadoIncidencias selectedItem) {
        AdapterUtils.navegarIncidencias(this,selectedItem, EnumTipo.mio);
    }

}

