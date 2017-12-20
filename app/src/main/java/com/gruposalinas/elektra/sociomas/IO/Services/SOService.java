package com.gruposalinas.elektra.sociomas.IO.Services;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.gruposalinas.elektra.sociomas.IO.Recievers.SmsSendReceiver;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.gruposalinas.elektra.sociomas.Utils.MediaUtils.BaseMediaUtils;
import com.gruposalinas.elektra.sociomas.Utils.MediaUtils.MediaAudioUtils;
import com.gruposalinas.elektra.sociomas.Utils.MediaUtils.MediaFotoUtils;
import com.gruposalinas.elektra.sociomas.Utils.MediaUtils.MediaVideoUtils;
import com.sociomas.core.DataBaseModel.DatosContacto;
import com.sociomas.core.DataBaseModel.PanicoLog;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackSOSWebService;
import com.sociomas.core.WebService.Model.Request.SOS.Archivo;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;

import java.util.ArrayList;
import java.util.Date;
public class SOService extends SOSBaseService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, CallBackSOSWebService {

    private static final String TAG = "SOService";
    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private int  enviando=0;
    private MediaVideoUtils mediaVideoUtils;
    private MediaAudioUtils mediaAudioUtils;
    private MediaFotoUtils mediaFotoUtils;



    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent != null) {
                if (!currentlyProcessingLocation) {
                    currentlyProcessingLocation = true;
                    startLocation();
                } else {
                    Log.i(TAG, "ACTUALMENTE ESTA CORRIENDO SERVICIO UBICACIÓN");
                    googleApiClient.reconnect();
                }
            }
        }
        catch (NullPointerException ex){
            Log.e(TAG,ex.getMessage());
        }

        return START_REDELIVER_INTENT;
    }

    private void startLocation() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            this.currentRequest=new SosRequest();
            this.manager=new SessionManager(this);
            this.manager.add(Constants.SOS_RUNNING,true);
            this.mediaAudioUtils=new MediaAudioUtils(this,EXTENSION_AUDIO,10);
            this.mediaVideoUtils=new MediaVideoUtils(this,EXTENSION_VIDEO,5);
            this.mediaFotoUtils=new MediaFotoUtils(this,EXTENSION_IMAGEN,3);
            this.setCallBackSOSWebService(this);
            this.googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            generarMediaFiles(location);
            stopLocationUpdates();
        }
    }
    private void generarMediaFiles(Location location){
        if(enviando==0) {
            /*INICIAMOS CON EL AUDIO*/
            mediaVideoUtils.iniciarRecording();
            mediaVideoUtils.setListener(new BaseMediaUtils.MediaListener() {
                @Override
                public void onFinish(String base64File, String audioPath) {
                    Log.i(TAG,"VIDEO TOMADO");
                    Archivo archivo = new Archivo(base64File,mediaVideoUtils.getFileNameWS(),mediaVideoUtils.getFileExt(),audioPath,1024);
                    listArchivos.add(archivo);
                    mediaAudioUtils.iniciarRecording();
                }
                @Override
                public void onError(String mensajeError) {
                    //Toast.makeText(SOService.this,"VIDEO:"+mensajeError,Toast.LENGTH_LONG).show();
                    mediaAudioUtils.iniciarRecording();
                }
            });

            mediaAudioUtils.setListener(new BaseMediaUtils.MediaListener() {
                @Override
                public void onFinish(String base64File, String audioPath) {
                    Log.i(TAG,"AUDIO TOMADO");
                    Archivo archivo = new Archivo(base64File,mediaAudioUtils.getFileNameWS(),mediaAudioUtils.getFileExt(),audioPath,1024);
                    listArchivos.add(archivo);
                    mediaFotoUtils.iniciarRecording();
                }
                @Override
                public void onError(String mensajeError) {
                    mediaFotoUtils.iniciarRecording();
                }
            });
            mediaFotoUtils.setListener(new BaseMediaUtils.MediaListener() {
                @Override
                public void onFinish(String base64File, String audioPath) {
                    Log.i(TAG,"FOTO TOMADA");
                    Archivo archivo = new Archivo(base64File,mediaFotoUtils.getFileNameWS(),mediaFotoUtils.getFileExt(),audioPath,1024);
                    listArchivos.add(archivo);
                    if(MediaFotoUtils.COUNT==MediaFotoUtils.MAX_FOTOS){
                       sendToWebService();
                    }
                }

                @Override
                public void onError(String mensajeError) {
                   sendToWebService();
                }
            });

            //SE GENERAN LOS DATOS NECESARIOS PARA EL BOTÓN DE PANICO
            DatosContacto datosContacto = new DatosContacto();
            SessionManager manager = new SessionManager(this);
            String mensaje = String.format("%s  Necesita ayuda http://maps.google.com/maps?f=q&q=(%s,%s)", manager.getString(Constants.SP_NAME), location.getLatitude(), location.getLongitude());
            //ENVIO DE SMS
            ArrayList<String> listTelefonos = new ArrayList<>();

            if (!datosContacto.gettel1(this).isEmpty()) {
                SmsSendReceiver.sendSMS(this, datosContacto.gettel1(this), mensaje);
                listTelefonos.add(datosContacto.gettel1(this));
            }
            if (!datosContacto.gettel2(this).isEmpty()) {
                SmsSendReceiver.sendSMS(this, datosContacto.gettel2(this), mensaje);
                listTelefonos.add(datosContacto.gettel2(this));

            }
            if (!datosContacto.gettel3(this).isEmpty()) {
                SmsSendReceiver.sendSMS(this, datosContacto.gettel3(this), mensaje);
                listTelefonos.add(datosContacto.gettel3(this));
            }

            //GUARDAMOS  EN UN OBJETO Y PARAMOS LA ACTULIZACIÓN DE REQUEST UBICACIÓN
            currentRequest.setCoordenadas(String.format("%s@%s", location.getLatitude(), location.getLongitude()));
            currentRequest.setTelefonos(TextUtils.join(",", listTelefonos));
            enviando++;
        }
    }
    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
            googleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        try {
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(120*1000);
            locationRequest.setFastestInterval(120*1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
        catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "CONEXION FALLO");
        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "LA CONEXION HA SIDO SUSPENDIDA");
    }


    @Override
    public void onResponse(PanicoLog panicoLog, SosRequest currentRequest) {
        /*NECESARIO PARA INGRESAR AL LOG*/
        final DBUtils dbUtils=new DBUtils(this);
        final Date currentDate=new Date();
        /*INFORMACIÓN PARA EL LOG INTERNO DE LA APP*/
        panicoLog.setFecha(dayFormat.format(currentDate));
        panicoLog.setHora(hourFormat.format(currentDate));
        dbUtils.agregarLogPanico(panicoLog);
        //ELIMINA LOS ARCHIVOS CORRESPONDIENTES
        eliminarArchivos();
        final Intent intentService =new Intent(SOService.this,SOSAudioService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.SOS_CURRENT_REQUEST,currentRequest);
        intentService.putExtras(bundle);
        startService(intentService);
        stopSelf();
    }
}
