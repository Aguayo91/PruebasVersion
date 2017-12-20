package com.sociomas.aventones.UI.Activities.Rol;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumPerfilConductor;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Request.Consulta.ConsultaRolRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.RolResponse;
import com.sociomas.core.WebService.Model.Response.PilotoResponse.PilotoResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 12/10/2017.
 */

public class AventonRolPresenterImpl extends BasePresenterImpl implements AventonRolPresenter {

    private ControllerAventon controllerAventon;
    private AventonesInicioView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(AventonesInicioView)view;
    }

    @Override
    public void obtenerRolAventon() {
        onShowProgress();
        final ConsultaRolRequest rolRequest = new ConsultaRolRequest();
        if(Utils.hasInternet(getView().getActivityInstance())) {
            ApplicationAventon.getIntance().getControllerAventon().obtenerRolAventon(rolRequest).enqueue(new Callback<RolResponse>() {
                @Override
                public void onResponse(Call<RolResponse> call, Response<RolResponse> response) {
                    if (response.isSuccessful()) {
                        RolResponse rolResponse = response.body();
                        ApplicationAventon.getIntance().getManager().add(Constants.ID_ROL_EMPLEADO_AVENTON, rolResponse.getIdRolAventon());
                        view.navigateAventonRol(rolResponse);
                    } else {
                        onShowToast(R.string.Error_Conexion);
                        view.navegacionFragmento(getCurrentRol(),null);
                    }
                    onHideProgress();
                }

                @Override
                public void onFailure(Call<RolResponse> call, Throwable t) {
                    view.navegacionFragmento(getCurrentRol(), null);
                    onShowToast(R.string.Error_Conexion);
                    onHideProgress();
                }

            });
        }
        else{
            view.navegacionFragmento(getCurrentRol(),null);
            onHideProgress();
        }
    }

    @Override
    public void revisarAgregarPiloto() {
        ApplicationAventon.getIntance().getControllerAventon().administrarUsuarioPiloto(new ServerRequest()).enqueue(new Callback<PilotoResponse>() {
            @Override
            public void onResponse(Call<PilotoResponse> call, Response<PilotoResponse> response) {
                if(response.isSuccessful()){
                        if(!response.body().getError())
                            view.mostrarOpcionAgregarPiloto(response.body().isRegistrado());
                }
            }

            @Override
            public void onFailure(Call<PilotoResponse> call, Throwable t) {
                onShowToast(R.string.server_error);
            }
        });
    }

    private int getCurrentRol(){
        return ApplicationAventon.getIntance().getManager().getInt(Constants.ID_ROL_EMPLEADO_AVENTON, EnumPerfilConductor.NINGUNO.getValue());
    }

    public interface AventonesInicioView extends BaseView {
        void navigateAventonRol(RolResponse rolResponse);
        void navegacionFragmento(int option, Bundle mBundle);
        void mostrarOpcionAgregarPiloto(boolean mostrar);
    }
}
