package com.sociomas.aventones.UI.Activities.Carros;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacksAventones.CallBackVehiculo;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 05/10/2017.
 */

public class CarsPresenterImpl extends BasePresenterImpl implements CarPresenter, CallBackVehiculo, CarInteractor.CallBackEditarAlta {

    private CarsView view;
    private CarInteractor interactor;
    private boolean navegarPreferencias;
    private List<Vehiculo>listVehiculos=new ArrayList<>();


    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(Constants.SELECTED_AUTOS)){
           listVehiculos=(ArrayList<Vehiculo>)intent.getSerializableExtra(Constants.SELECTED_AUTOS);
            if(view!=null) {
                view.setListVehiculo(listVehiculos);
           }
        }
       else {
            obtenerAutosListadoAsync();
        }
    }



    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(CarsView)view;
        this.interactor=new CarInteractor();
    }


    @Override
    public void agregarEditarAuto(Vehiculo selectedVehiculo, boolean navegarPreferencias) {

        this.navegarPreferencias=navegarPreferencias;

        if(!navegarPreferencias) {
            if(validarAuto(selectedVehiculo)){
                enviarAutoAsync(selectedVehiculo);
            }
        }
        else{
            if(selectedVehiculo.getModelo().isEmpty() && selectedVehiculo.getPlacas().isEmpty() &&
                    selectedVehiculo.getCodigoColor().isEmpty() && !listVehiculos.isEmpty())
            {
                view.navegarPreferencias();
            }
            else{
                if(validarAuto(selectedVehiculo)){
                    enviarAutoAsync(selectedVehiculo);
                }
            }
        }
    }

    boolean validarAuto(Vehiculo selectedVehiculo){
        boolean result=false;
        if (selectedVehiculo.getModelo().isEmpty()) {
            view.onAutoModeloError(R.string.ingresa_modelo_auto);
        } else if (selectedVehiculo.getPlacas().isEmpty()) {
            view.onAutoPlacaError(R.string.ingresa_placa);
        } else if (selectedVehiculo.getCodigoColor().isEmpty()) {
            view.onAutoColorError(R.string.ingresa_color);
        }
        else{
            result=true;
        }

        return result;
    }

    void enviarAutoAsync(Vehiculo selectedVehiculo){
        onShowProgress();
        if(TextUtils.isEmpty(selectedVehiculo.getIdAutomovil())){
            interactor.altaAutoMovilAsync(selectedVehiculo,this);
        }
        else {
            interactor.editarMovilAsync(selectedVehiculo,this);
        }
    }

    @Override
    public void obtenerAutosListadoAsync() {
        ApplicationAventon.getIntance().getControllerAventon().obtenerListadoAutoAsync(new ServerRequest(),this);
    }

    @Override
    public void onSucces(List<Vehiculo> listVehiculo) {
        this.listVehiculos=listVehiculo;
        view.setListVehiculo(listVehiculo);
    }


    @Override
    public void onSuccess(ServerResponse result) {
        if(result.getMensaje()!=null) {
            onShowAlert(result.getMensaje());
            if(navegarPreferencias) {
                view.navegarPreferencias();
            }
            obtenerAutosListadoAsync();
            ViewEvent event = new ViewEvent(ViewEventType.SHOW_DIALOG_EVENT_TYPE);
            event.getModel().put(ViewEvent.BOOLEAN_OBJECT, navegarPreferencias);
            event.getModel().put(ViewEvent.RESOURCE_ID, R.id.RecyclerView);
            event.getModel().put(ViewEvent.MESSAGE, result.getMensaje());
            notifyData(event);
            view.onCleanCampos();
//            view.navegarPreferencias();
        }
        onHideProgress();
    }

    @Override
    public void OnError(Throwable error) {
        onShowAlert(error.getMessage());
    }


    public interface  CarsView extends BaseView {
        void setListVehiculo(List<Vehiculo>list);
        void onAutoModeloError(@StringRes int errorMsg);
        void onAutoPlacaError(@StringRes int errorMsg);
        void onAutoColorError(@StringRes int errorMsg);
        void navegarPreferencias();
        void onCleanCampos();
    }

}
