package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackSOSWebService;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerSOS;
import com.sociomas.core.WebService.Model.Request.SOS.Archivo;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 11/08/2017.
 */

public abstract class SOSBaseService extends Service {
    protected SessionManager manager;
    //LISTA DE ARCHIVOS
    protected ArrayList<Archivo> listArchivos = new ArrayList<>();
    //REQUEST ACTUAL
    protected SosRequest currentRequest;
    //DATEFORMATS PARA GUARDAR EN BD
    protected SimpleDateFormat dayFormat=new SimpleDateFormat(Constants.DAY_FORMAT);
    protected SimpleDateFormat hourFormat=new SimpleDateFormat(Constants.HOUR_FORMAT);
    //CALLLBACKS DE WS
    protected CallBackSOSWebService callBackSOSWebService;
    //EXTENCIONES DE LOS ARCHIVOS
    protected   final String EXTENSION_AUDIO="wav";
    protected   final String EXTENSION_VIDEO="mp4";
    protected   final String EXTENSION_IMAGEN="jpg";

    public void setCallBackSOSWebService(CallBackSOSWebService callBackSOSWebService) {
        this.callBackSOSWebService = callBackSOSWebService;
    }

    protected void sendToWebService() {

        /*INFORMACIÓN PARA EL WEBSERVICE*/
        currentRequest.setArchivos(listArchivos);
        final PanicoLog panicoLog = new PanicoLog();
        ControllerSOS controllerSOS = new ControllerSOS(this);
        controllerSOS.SOSEnvio(currentRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    panicoLog.setSuccess(!response.body().getError());
                    panicoLog.setMensajeError(response.body().getMensajeError() != null ? response.body().getMensajeError() : "");
                    if(callBackSOSWebService!=null)
                         callBackSOSWebService.onResponse(panicoLog,currentRequest);
                } else {
                    panicoLog.setMensajeError(getString(R.string.Error_Conexion));
                    panicoLog.setSuccess(false);
                    if(callBackSOSWebService!=null)
                        callBackSOSWebService.onResponse(panicoLog,currentRequest);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                panicoLog.setMensajeError(t.getMessage()!=null && (!t.getMessage().isEmpty())?t.getMessage(): getString(R.string.Error_Conexion));
                panicoLog.setSuccess(false);
                if(callBackSOSWebService!=null)
                    callBackSOSWebService.onResponse(panicoLog,currentRequest);
            }
        });
    }

    protected void eliminarArchivos(){
        for(Archivo archivo: listArchivos){
            File file=new File(archivo.getSdUbicacion());
            boolean eliminadoArchivo=file.delete();
        }
        listArchivos.clear();
    }


    private void onResponseWebService(PanicoLog panicoLog,SosRequest currentRequest){
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSelf();
                if(!dbUtils.panicoCompleted(dayFormat.format(currentDate))) {
                 //   startService(new Intent(SOService.this, SOService.class));
                }
            }
        },20*1000);*/
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
