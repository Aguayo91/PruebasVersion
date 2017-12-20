package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaHoy;
import android.content.Intent;

import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasInteractor;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.Calendar;

import okhttp3.internal.Util;
/**
 * Created by oemy9 on 01/12/2017.
 */
public class MisAsistenciasHoyPresenterImpl extends BasePresenterImpl implements MisAsistenciasHoyPresenter, Subscriber<ResultadoAsistencia> {

    private MisAsistenciasInteractor interactor;
    private MisAsistenciasHoyView view;
    private boolean isPlantilla;
    private Plantilla selectedItem;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(MisAsistenciasHoyView)view;
        interactor=new MisAsistenciasInteractor();
    }

    @Override
    public void setArguments(Intent intent) {
        super.setArguments(intent);
        isPlantilla=intent.getBooleanExtra(Constants.IS_PLANTILLA,false);
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedItem=(Plantilla)intent.getSerializableExtra(ViewEvent.ENTRY);
            view.setInfoEmpleado(selectedItem.getNombreEmpleado(),selectedItem.getIdEmpleado());
        }
        else{
            Empleado empleado=Utils.getCurrentEmpleado(ApplicationBase.getIntance().getApplicationContext());
            view.setInfoEmpleado(empleado.getName(),empleado.getIdEmployee());
        }
    }

    @Override
    public void obtenerAsistenciasHoy() {
          onShowProgress();
          String numeroEmpleado=!isPlantilla? Utils.getNumeroEmpleado(ApplicationBase.getIntance().getApplicationContext()):
                  selectedItem.getIdEmpleado();
          interactor.getAsistenciasHoy(numeroEmpleado, Calendar.getInstance(),Calendar.getInstance(),this);
    }

    @Override
    public boolean isPlantilla() {
        return  isPlantilla;
    }

    @Override
    public Plantilla getSelectedEmpleado() {
        return selectedItem;
    }

    @Override
    public void onSubscribe(Subscription s) {
    }

    @Override
    public void onNext(ResultadoAsistencia r) {
        view.setDiaHoyAsistencia(r);
    }


    @Override
    public void onError(Throwable t) {
        onHideProgress();
    }

    @Override
    public void onComplete() {
        onHideProgress();
    }

    public interface  MisAsistenciasHoyView extends BaseView{
        void setDiaHoyAsistencia(ResultadoAsistencia r);
        void setInfoEmpleado(String nombre,String numeroEmpleado);
    }
}
