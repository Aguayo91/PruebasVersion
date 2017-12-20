package com.sociomas.aventones.UI.Activities.SolicitarAventon;

import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Request.SolicitarAventon.AsientosReservados;
import com.sociomas.core.WebService.Model.Request.SolicitarAventon.SolicitarAventonRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 12/10/2017.
 */

public class SolicitarAventonInteractor {


    public void solicitarAventonAsync(Aventones aventon, CallBackBaseResponse callBack){
        SolicitarAventonRequest request=new SolicitarAventonRequest();
        AsientosReservados asientosReservados=new AsientosReservados();
        asientosReservados.setIdTrayectoAventon(aventon.getId_trayecto_aventon());
        asientosReservados.setFechaHoraReservacion(new SimpleDateFormat(Constants.DATE_FORMAT_AVENTON).format(new Date()));
        asientosReservados.setNumeroAsientosReservados(1);
        ArrayList<AsientosReservados>list=new ArrayList<>();
        list.add(asientosReservados);
        request.setListAsientosReservados(list);
        ApplicationAventon.getIntance().getControllerAventon().reservarAsientos(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
