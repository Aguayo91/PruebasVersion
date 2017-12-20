package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.Expandible;

import android.support.annotation.StringRes;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacks.CallBackIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ExpandableGroupIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by oemy9 on 20/10/2017.
 */

public class IncidenciaPresenterImpl extends BasePresenterImpl implements IncidenciaPresenter, CallBackIncidencia {

    private IncidenciaView view;
    private IncidenciaInteractor interactor;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(IncidenciaView)view;
        this.interactor=new IncidenciaInteractor();
    }

    @Override
    public void obtenerIncidencias() {
        onShowProgress();
        this.interactor.ListadoIncidenciasAsync(this);
    }

    @Override
    public void rechazarAprobar(ListadoIncidencias selectedItem, final boolean autorizar) {

        if(selectedItem.getComentarioRechazo().isEmpty()) {
            view.onComentarioVacio(R.string.comentario_vacio);
            return;
        }
        onShowProgress();
        interactor.rechazarAprobarAsync(selectedItem, autorizar, new CallBackAprobarRechazar() {
            @Override
            public void OnSuccess(ServerResponse response) {
                int mensaje=autorizar ? R.string.justificacion_aprobada : R.string.justificacion_rechazada;
                view.onSucessAprobadaRechazada(mensaje);
                obtenerIncidencias();
                onHideProgress();
            }

            @Override
            public void OnError(Throwable error) {
                onShowAlert(error.getMessage());
                onHideProgress();
            }
        });
    }

    @Override
    public void validarJustificacion(ListadoIncidencias selectedItem) {
        onShowProgress();
        interactor.validarJustificacion(selectedItem, new CallBackBaseResponse() {
            @Override
            public void onSuccess(ServerResponse response) {
                view.onSucessAprobadaRechazada(R.string.justificacion_aprobada);
                obtenerIncidencias();
                onHideProgress();
            }
            @Override
            public void OnError(Throwable error) {
                onShowAlert(error.getMessage());
                onHideProgress();
            }
        });
    }

    @Override
    public void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados) {
            if(incidenciaResponse!=null){
                ArrayList<ExpandableGroupIncidencias>listInicidencias=new ArrayList<>();
                listInicidencias.add(new ExpandableGroupIncidencias(String.format("Mis justificaciones (%s)",incidenciaResponse.getListadoIncidencias().size()),
                        incidenciaResponse.getListadoIncidencias()));
                listInicidencias.add(new ExpandableGroupIncidencias(String.format("Justificaciones de mi equipo (%s)",incidenciaResponse.getListadoPlantilla().size()),
                        incidenciaResponse.getListadoPlantilla()));
                view.setListIncidencias(listInicidencias);
            }
            onHideProgress();
    }

    @Override
    public void OnError(Throwable error) {
            onShowAlert(error.getMessage());
    }



    public interface IncidenciaView extends BaseView{
            void onSucessAprobadaRechazada(@StringRes  int mensaje);
            void onComentarioVacio(@StringRes int mensaje);
            void openChild(int child);
            void setListIncidencias(ArrayList<ExpandableGroupIncidencias>listIncidencias);
    }
}
