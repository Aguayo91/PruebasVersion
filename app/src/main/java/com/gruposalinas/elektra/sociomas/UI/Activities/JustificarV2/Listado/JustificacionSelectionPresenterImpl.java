package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado;

import android.content.Intent;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Incidencia.ExpandableGroupIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 27/11/2017.
 */

public class JustificacionSelectionPresenterImpl extends BasePresenterImpl implements  JustificacionSelectionPresenter {

    private JustificacionSelectionInteractor interactor;
    private JustificacionView view;
    private boolean isPlantilla;
    private Plantilla selectedItem;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.interactor=new JustificacionSelectionInteractor();
        this.view=(JustificacionView)view;
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(Constants.IS_PLANTILLA)){
            isPlantilla=intent.getBooleanExtra(Constants.IS_PLANTILLA,false);
            view.setVisibilitySpinnerPlantilla(isPlantilla);
        }
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedItem=(Plantilla)intent.getSerializableExtra(ViewEvent.ENTRY);
            view.setSelectedItem(selectedItem);
        }
    }

    @Override
    public void obtenerJustificaciones(String numeroEmpleado) {
        onShowProgress();
        this.interactor.obtenerJustificacionesAsync(numeroEmpleado, isPlantilla, new Subscriber<List<ListadoIncidencias>>() {
            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(List<ListadoIncidencias> listadoIncidencias) {
                if(listadoIncidencias!=null){
                    view.setListIncidencias(listadoIncidencias);
                }
            }

            @Override
            public void onError(Throwable t) {
                onShowAlert(R.string.Error_Conexion);
                onHideProgress();
            }

            @Override
            public void onComplete() {
                onHideProgress();
            }
        });
    }

    @Override
    public boolean isPlantilla() {
        return this.isPlantilla;
    }

    public interface  JustificacionView extends BaseView{
        void setListIncidencias(List<ListadoIncidencias> listIncidencias);
        void setVisibilitySpinnerPlantilla(boolean visible);
        void setSelectedItem(Plantilla item);
    }
}
