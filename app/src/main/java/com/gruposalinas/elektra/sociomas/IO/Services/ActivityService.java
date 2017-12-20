package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.gruposalinas.elektra.sociomas.Utils.Utils;

import java.security.NoSuchAlgorithmException;


/**
 * Created by oemy9 on 03/03/2017.
 */

public class ActivityService extends Service implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,ResultCallback<Status> {
    private String TAG="ACTIVITY_SERVICE";
    private boolean currentlyProcessingActivity= false;
    private GoogleApiClient googleApiClient;
    public static PendingIntent pendingIntent;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!Utils.userLogeado(this)) {
            return START_REDELIVER_INTENT;
        }
        else {
            this.onStartService("ON START COMMAND");
        }
        return START_STICKY;
    }

    private void onStartService(String from){
        if(!currentlyProcessingActivity) {
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API).
                                addApi(ActivityRecognition.API).addOnConnectionFailedListener(this)
                        .addConnectionCallbacks(this)
                        .build();
                Log.i(TAG, "INICIANDO SERVICIO ACTIVITY FROM:" + from);

                if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                    googleApiClient.connect();
                }
            } else {
                Log.e(TAG, "NO HAY CONEXIÓN A GOOGLE PLAY SERVICES");
            }
            currentlyProcessingActivity=true;
        }
        else{
            Log.i(TAG,"ACTUALMENTE CORRIENDO SERVICIO ACTIVIDAD");
            googleApiClient.reconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG,"ON CONNECTED");
        requestActividadUpdate();
    }
    private  void requestActividadUpdate(){
        try {
            if(googleApiClient.isConnected()) {
                Intent intent = new Intent(this, ActivityIntentService.class);
                try {
                    java.security.SecureRandom secureRandomGenerator=java.security.SecureRandom.getInstance("SHA1PRNG");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getService(this, (int)Math.random(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 1000, pendingIntent)
                            .setResultCallback(this);
             }
        }
        catch (IllegalStateException ex){
            Log.e(TAG,ex.getMessage());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onResult(@NonNull Status status) {
        if(status.isSuccess()) {
            Log.i(TAG, "STATUS CODE OK");
        }
        else if(status.isInterrupted()){
            Log.i(TAG,"STATUS INTERRUPIDO");
        }
        else if(status.isCanceled()){
            Log.i(TAG,"STATUS CANCELADO");
        }
        stopSelf();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.i(TAG,"CONECTION FAILED");
    }
}
