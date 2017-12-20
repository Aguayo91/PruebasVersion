package com.sociomas.aventones.UI.Activities.Preferencias;

import com.google.gson.Gson;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPreferencias;
import com.sociomas.core.WebService.Model.Request.Perfil.EditarPreferenciasRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.PreferenciaResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 06/10/2017.
 */

public class PreferenciasInteractor {

    public void editarPreferenciasAsync(EditarPreferenciasRequest request, final CallBackBaseResponse callBackBaseResponse){
        ApplicationAventon.getIntance().getControllerAventon().modificarPreferenciasPerfil(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    ServerResponse serverResponse= null;
                    try {
                        serverResponse = new Gson().fromJson(response.body().string(),ServerResponse.class);
                        callBackBaseResponse.onSuccess(serverResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    callBackBaseResponse.OnError(new Throwable("Error"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackBaseResponse.OnError(new Throwable("Error"));

            }
        });
    }

    public void insertarPreferenciasAsync(EditarPreferenciasRequest request, final CallBackBaseResponse callBack){
        ApplicationAventon.getIntance().getControllerAventon().insertarPreferenciasPerfil(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        callBack.onSuccess(new Gson().fromJson(response.body().string(),ServerResponse.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public  void obtenerPreferenciasAsync(final CallBackPreferencias callBackPreferencias){
        ApplicationAventon.getIntance().getControllerAventon().obtenerPreferenciasPerfil(new ServerRequest()).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        PreferenciaResponse preferenciaResponse = new Gson().fromJson(response.body().string(), PreferenciaResponse.class);
                        callBackPreferencias.onSuccess(preferenciaResponse.getCaractEmpleado(), preferenciaResponse.getCatPreferenciasEmpleado());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    callBackPreferencias.OnError(new Throwable("Error en petición"));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }


        });
    }



}
