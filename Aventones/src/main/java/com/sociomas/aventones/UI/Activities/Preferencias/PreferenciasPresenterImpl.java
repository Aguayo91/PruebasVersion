package com.sociomas.aventones.UI.Activities.Preferencias;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPreferencias;
import com.sociomas.core.WebService.Model.Request.Perfil.EditarPreferenciasRequest;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 06/10/2017.
 */

public class PreferenciasPresenterImpl extends BasePresenterImpl implements PreferenciasPresenter, CallBackPreferencias, CallBackBaseResponse {

    private PreferenciasView view;
    private PreferenciasInteractor interactor;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PreferenciasView)view;
        this.interactor=new PreferenciasInteractor();
    }
    @Override
    public void obtenerPreferencias() {
        onShowProgress();
        this.interactor.obtenerPreferenciasAsync(this);
    }
    @Override
    public void insertarEditarPreferencias(boolean editar,String observaciones, String tiempoTolerancia,int idRelCaracteristicaUsuario, ArrayList<CatPreferenciasEmpleado> caractEmpleado) {
        onShowProgress();
        EditarPreferenciasRequest request = new EditarPreferenciasRequest();
        if(!editar) {
            request.setCaractEmpleado(new CaractEmpleado(observaciones, tiempoTolerancia));
            request.setCatPreferenciasEmpleado(caractEmpleado);
            interactor.insertarPreferenciasAsync(request, this);
        }
        else {
            request.setCaractEmpleado(new CaractEmpleado(observaciones, tiempoTolerancia, idRelCaracteristicaUsuario));
            request.setCatPreferenciasEmpleado(caractEmpleado);
            interactor.editarPreferenciasAsync(request,this);
        }

    }

    @Override
    public void onSuccess(CaractEmpleado caractEmpleado, ArrayList<CatPreferenciasEmpleado> listPreferencias) {
        view.setPreferencias(caractEmpleado,listPreferencias);
        onHideProgress();
    }
    @Override
    public void OnError(Throwable error) {
        onShowAlert(error.getMessage());
        onHideProgress();
    }

    @Override
    public void onSuccess(ServerResponse response) {
        if(response.getMensaje()!=null){
            onShowAlert(response.getMensaje());
        }
        else if(response.getMensajeError()!=null){
            onShowToast(response.getMensajeError());
        }
        onHideProgress();
        view.navegarPublicar();
    }


    public interface PreferenciasView extends BaseView{
        void setPreferencias(CaractEmpleado caractEmpleado, ArrayList<CatPreferenciasEmpleado> listPreferencias);
        void navegarPublicar();
    }
}
