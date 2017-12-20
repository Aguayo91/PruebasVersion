package com.sociomas.aventones.UI.Activities.Carros;

import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Request.Alta.AltaAutoRequest;
import com.sociomas.core.WebService.Model.Request.Alta.EditarAutoRequest;
import com.sociomas.core.WebService.Model.Request.Alta.ServerResponseAventones;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarInteractor {


    public interface CallBackEditarAlta extends CallBackBase{
        void onSuccess(ServerResponse result);
    }

    public void altaAutoMovilAsync(Vehiculo selectedVehiculo, final CallBackEditarAlta callBackEditarAlta){
        final AltaAutoRequest request = new AltaAutoRequest();
        request.setCodigoColor(selectedVehiculo.getCodigoColor());
        request.setNumeroAsientos(selectedVehiculo.getNumeroAsientos());
        request.setModelo(selectedVehiculo.getModelo());
        request.setPlacas(selectedVehiculo.getPlacas());
        ApplicationAventon.getIntance().getControllerAventon().generarAltaAuto(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                //el webservice responde
                if (response.isSuccessful()) {
                    callBackEditarAlta.onSuccess(response.body());
                }
                else{
                    callBackEditarAlta.OnError(new Throwable(ApplicationAventon.getAppContext().getString(R.string.Error_Conexion)));
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                callBackEditarAlta.OnError(new Throwable(ApplicationAventon.getAppContext().getString(R.string.Error_Conexion)));
            }
        });
    }

    public void editarMovilAsync(Vehiculo selectedVehiculo,final CallBackEditarAlta callBackEditarAlta){
        EditarAutoRequest request = new EditarAutoRequest();
        request.setIdAutomovil(Integer.valueOf(selectedVehiculo.getIdAutomovil()));
        request.setCodigoColor(selectedVehiculo.getCodigoColor());
        request.setNumeroAsientos(selectedVehiculo.getNumeroAsientos());
        request.setModelo(selectedVehiculo.getModelo());
        request.setPlacas(selectedVehiculo.getPlacas());
        ApplicationAventon.getIntance().getControllerAventon().editarAutomovil(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    callBackEditarAlta.onSuccess(response.body());
                }
                else{
                    callBackEditarAlta.OnError(new Throwable(ApplicationAventon.getAppContext().getString(R.string.Error_Conexion)));

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                callBackEditarAlta.OnError(new Throwable(ApplicationAventon.getAppContext().getString(R.string.Error_Conexion)));
            }
        });
    }
}
