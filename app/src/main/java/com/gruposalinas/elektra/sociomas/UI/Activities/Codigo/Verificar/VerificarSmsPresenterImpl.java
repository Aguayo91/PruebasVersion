package com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.Model.Request.Codigo.CrearCodigoRequest;
import com.sociomas.core.WebService.Model.Request.Codigo.ValidarCodigoRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by oemy9 on 07/09/2017.
 */
public class VerificarSmsPresenterImpl extends BasePresenterImpl implements VerificarSmsPresenter {

    private VerificarSmsView view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getView()!=null) {

        }
    }

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view = (VerificarSmsView)view;
    }

    @Override
    public void validarCodigo(String primerSegmento,String segundoSegmento,String telefono) {
        String codigo=generarCodigo(primerSegmento,segundoSegmento);

        if(!codigoVerificado(codigo)){
            view.onCodigoIncorrecto("Ingrese los 8 digitos del código de verificación");
            return;
        }
        onShowProgress();
        ApplicationBase.getIntance().getControllerAPI().verificarCodigo(new ValidarCodigoRequest(codigo,telefono)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String respuesta=response.body().string();
                    ServerResponse serverResponse=new Gson().fromJson(respuesta,ServerResponse.class);
                    if(!serverResponse.getError()){
                        view.numeroTelefonicoValidado();
                        onHideProgress();
                    }
                    else {
                        onHideProgress();
                        onShowAlert(serverResponse.getMensajeError());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onHideProgress();
                onShowAlert(R.string.Error_Conexion);
            }
        });

    }

    @Override
    public void reenviarCodigo(String  numeroTelefono) {
        onShowProgress();
        ApplicationBase.getIntance().getControllerAPI().reenviarCodigo(new CrearCodigoRequest(numeroTelefono)).enqueue(new Callback<ResponseBody>() {
              @Override
              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  try {
                      String    respuesta = response.body().string();
                      ServerResponse serverResponse=new Gson().fromJson(respuesta,ServerResponse.class);
                      if(serverResponse.isExito()){
                          view.reenvioExito();
                          onHideProgress();
                      }
                      else{
                            onHideProgress();
                            onShowAlert(serverResponse.getMensajeError());
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  }

              }

              @Override
              public void onFailure(Call<ResponseBody> call, Throwable t) {
                  onHideProgress();
                  onShowAlert(R.string.Error_Conexion);
              }
          });
    }

    @Override
    public void requestLlamadaTelefonica(String  numeroTelefono) {
        ApplicationBase.getIntance().getControllerAPI().codigoPorLlamada(new CrearCodigoRequest(numeroTelefono)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String    respuesta = response.body().string();
                        ServerResponse serverResponse=new Gson().fromJson(respuesta,ServerResponse.class);
                        if(serverResponse.isExito()){
                            view.onLlamadaExito();
                            onHideProgress();
                        }
                        else{
                           onHideProgress();
                           onShowAlert(serverResponse.getMensajeError());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onHideProgress();
                    onShowAlert(R.string.Error_Conexion);
                }
            });
    }

    private boolean codigoVerificado(String codigo){
        return codigo.length()==9;
    }
    public String generarCodigo(String primerSegmento,String segundoSegmento){
        return primerSegmento.concat("-").concat(segundoSegmento);
    }
    public interface  VerificarSmsView extends BaseView {
        void onCodigoIncorrecto(String mensaje);
        void onLlamadaExito();
        void numeroTelefonicoValidado();
        void reenvioExito();
    }
}
