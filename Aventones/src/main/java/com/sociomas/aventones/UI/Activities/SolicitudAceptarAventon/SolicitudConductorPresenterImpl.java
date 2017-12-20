package com.sociomas.aventones.UI.Activities.SolicitudAceptarAventon;

import android.content.Intent;

import com.google.gson.Gson;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos.CambiarStatusAsientosRequest;
import com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos.StatusAsientos;
import com.sociomas.core.WebService.Model.Response.FireBase.PushAceptarAventon;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 13/10/2017.
 */

public class SolicitudConductorPresenterImpl extends BasePresenterImpl implements  SolicitudConductorPresenter {

    private PushAceptarAventon solicitudPush;
    private SolicitarConductorView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(SolicitarConductorView)view;
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(Constants.DATA_SEND)){
            this.solicitudPush=new Gson().fromJson(intent.getStringExtra(Constants.DATA_SEND),PushAceptarAventon.class);
            view.mostrarInformacion(solicitudPush);
        }
    }

    @Override
    public void mostarInformacion() {
          if(solicitudPush!=null){
              view.mostrarInformacion(solicitudPush);
          }
    }

    @Override
    public void aceptarRechazarAventon(boolean aceptar) {
        onShowProgress();
        CambiarStatusAsientosRequest request=new CambiarStatusAsientosRequest();
        request.setStatusAsientos(new StatusAsientos(solicitudPush.getIdRelUsuarioAsientos(),aceptar? 2: 3));
        ApplicationAventon.getIntance().getControllerAventon().cambiarStatusAsientos(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(!response.body().getError()){
                    onShowAlert(response.body().getMensaje());
                    view.navegarInicio();
                }
                else{
                    onShowAlert(R.string.Error_Conexion);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                onShowAlert(R.string.Error_Conexion);
                onHideProgress();
            }
        });

    }

    public interface SolicitarConductorView  extends BaseView{
            void mostrarInformacion(PushAceptarAventon solicitudPush);
            void navegarInicio();

    }
}
