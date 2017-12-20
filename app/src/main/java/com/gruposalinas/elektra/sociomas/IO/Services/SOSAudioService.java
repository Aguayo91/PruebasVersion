package com.gruposalinas.elektra.sociomas.IO.Services;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.UI.Activities.DesactivarSOS.DesactivarActivity;
import com.gruposalinas.elektra.sociomas.Utils.MediaUtils.MediaAudioUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackSOSWebService;
import com.sociomas.core.WebService.Model.Request.SOS.Archivo;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;

public class SOSAudioService extends SOSBaseService implements  MediaAudioUtils.MediaListener, CallBackSOSWebService {
    public static final String TAG="SOS_AUDIO_SERVICE";
    private MediaAudioUtils mediaAudioUtils;
    private SessionManager manager;
    /*SERVICIO DE AUDIO*/
    private  int COUNT_TO_WS=0; //CONTADOR QUE LLEGARÁ HASTA EL LIMETE DEL MAX AUDRIO
    private  int MAX_AUDIO_TO_WS=1;//CONTADOR DE MÁXIMO O LIMITE
    private  int CURRENT_COUNT=1; //CONTADOR QUE GENERA EL AUTOINCREMENTO DEL NOMBRE DE ARCHIVO
    private  boolean isRunning; //¿ESTÁ CORRIENDO ACTUALMENTE EL SERVICIO?
    private  int TIEMPO_GRABACION=60; //TIEMPO DE GRABACIÓN

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            this.mediaAudioUtils=new MediaAudioUtils(this,EXTENSION_AUDIO,TIEMPO_GRABACION);
            this.mediaAudioUtils.iniciarRecording();
            this.mediaAudioUtils.setListener(this);

            if(intent.hasExtra(Constants.SOS_CURRENT_REQUEST)) {
                this.manager=new SessionManager(this);
                this.currentRequest = (SosRequest)intent.getSerializableExtra(Constants.SOS_CURRENT_REQUEST);
                this.setCallBackSOSWebService(this);
                this.isRunning=true;
            }
        }
        return START_STICKY;
    }


    @Override
    public void onFinish(String base64File, String audioPath) {
        if(isRunning) {
            Archivo archivo = new Archivo(base64File, mediaAudioUtils.getFileNameWS(CURRENT_COUNT), mediaAudioUtils.getFileExt(), audioPath, 1024);
            this.listArchivos.add(archivo);
            this.mediaAudioUtils = null;
            this.mediaAudioUtils = new MediaAudioUtils(this, EXTENSION_AUDIO, TIEMPO_GRABACION);
            this.mediaAudioUtils.iniciarRecording();
            this.mediaAudioUtils.setListener(this);
            Log.i(TAG, "GRABACIÓN CREADA:" + CURRENT_COUNT + "\n COUNT TO WS:" + COUNT_TO_WS);
            COUNT_TO_WS++;
            CURRENT_COUNT++;
            if (COUNT_TO_WS == MAX_AUDIO_TO_WS) {
                sendToWebService();
                COUNT_TO_WS = 0;
            }
            if(CURRENT_COUNT%10==0){
                Utils.sendNotification(this,"SOS SOCIO MAS","El botón de SOS se encuentra encendido." +
                        "Si deseas desactivar  presiona está notificación. ",new Intent(this, DesactivarActivity.class),false);
            }
        }
    }
    @Override
    public void onError(String mensajeError) {
        this.eliminarArchivos();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onResponse(PanicoLog panicoLog, SosRequest currentRequest) {
        this.eliminarArchivos();
    }

    @Override
    public void onDestroy() {
        isRunning=false;
        super.onDestroy();
    }
}
