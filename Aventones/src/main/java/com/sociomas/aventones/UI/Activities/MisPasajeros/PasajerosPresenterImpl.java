package com.sociomas.aventones.UI.Activities.MisPasajeros;

import android.content.Intent;

import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos.CambiarStatusAsientosRequest;
import com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos.StatusAsientos;
import com.sociomas.core.WebService.Model.Request.Pasajeros.PasajerosRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventone;
import com.sociomas.core.WebService.Model.Response.Pasajeros.PasajerosList;
import com.sociomas.core.WebService.Model.Response.Pasajeros.PasajerosResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class PasajerosPresenterImpl extends BasePresenterImpl implements PasajerosPresenter {

    private Aventone selectedAventon;
    private PasajerosView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(PasajerosView)view;
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedAventon=(Aventone)intent.getSerializableExtra(ViewEvent.ENTRY);
        }
    }

    @Override
    public void obtenerPasajeros() {
        PasajerosRequest pasajerosRequest=new PasajerosRequest();
        pasajerosRequest.setIdAventon(selectedAventon.getIdAventon());
        onShowProgress();
        ApplicationAventon.getIntance().getControllerAventon().obtenerPasajerosAventon(pasajerosRequest).enqueue(new Callback<PasajerosResponse>() {
            @Override
            public void onResponse(Call<PasajerosResponse> call, Response<PasajerosResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        view.setListPasajeros(response.body().getLstSolicitudes());
                    }
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<PasajerosResponse> call, Throwable t) {
                onHideProgress();
            }
        });
    }

    @Override
    public void aceptarRechazarAventon(boolean aceptar, int idRelAsientosReservados) {
        onShowProgress();
        CambiarStatusAsientosRequest request=new CambiarStatusAsientosRequest();
        request.setStatusAsientos(new StatusAsientos(idRelAsientosReservados,aceptar? 2: 3));
        ApplicationAventon.getIntance().getControllerAventon().cambiarStatusAsientos(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(!response.body().getError()){
                    onShowToast(response.body().getMensaje());
                    view.obtenerPasajeros();
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

    public interface PasajerosView extends BaseView{
        void setListPasajeros(ArrayList<PasajerosList>listPasajeros);
        void obtenerPasajeros();
    }
}
