package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.IO.Recievers.ScreenReceiver;
import com.sociomas.core.BuildConfig;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Enums.EnumLocationProvider;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.DbUtils.QrDbHelper;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GPSBDHelper.LocationHelperListener, QrDbHelper.QrDbListener {

    private static final String TAG = "LocationService";
    private GPSBDHelper gpsBDHelper;
    private QrDbHelper qrDbHelper;
    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private boolean isAlarma=false; //PROVIENE DE ALARMA
    private boolean locationChangedFired;
    private Location lastLocation;
    private BroadcastReceiver mReceiver;
    private IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);


    @Override
    public void onCreate() {
           /*      try {

            if (mReceiver == null) {
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                mReceiver = new ScreenReceiver();
                registerReceiver(mReceiver, filter);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }*/
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            if (null!=intent) {


                this.gpsBDHelper = new GPSBDHelper(this);
                this.qrDbHelper=new QrDbHelper(this);
                this.gpsBDHelper.setListener(this);
                //HAY PRIORIDAD EN LA LLAMADA DE ESTE SERVICIO YA QUE PRESENTA UNA HORA DE
                //ENTRADA SALIDA O 10 MINUTOS DESPUÉS
                this.isAlarma = intent != null && intent.hasExtra(Constants.TIPO_ALARMA);

                //EL USUARIO NO ESTÁ LOGEADO
                if (!Utils.userLogeado(this)) {
                    Log.w(TAG,"USUARIO NO ESTÁ LOGEADO");
                    return START_REDELIVER_INTENT;
                }


                //EL GPS NO SE ENCUENTRA ENCENDIDO
               if (!Utils.IsGpsEncendido(this)) {
                   saveEventoVirtual(Constants.APAGAR_GPS);
                   onFailConnection(EnumLocationProvider.NO_ENCENDIDO);
                   stopSelf();
               }

                if (!isAlarma) {
                    this.gpsBDHelper.checkIfEnviarPendientes(new Date());
                }
                else{
                    this.gpsBDHelper.checkIfEnviarPendientes(new Date());
                    startService(new Intent(this,HoraCheckerService.class));
                    stopSelf();
                }

                //ENVIO DE DATOS QR ASYNC
                /*
                this.qrDbHelper.checkIfPendientesQr();
                this.qrDbHelper.setListener(this);*/

                if (!currentlyProcessingLocation) {
                    currentlyProcessingLocation = true;
                    startLocation();
                }

                else {
                    Log.i(TAG, "ACTUALMENTE ESTA CORRIENDO SERVICIO UBICACIÓN");
                    googleApiClient.reconnect();
                }

            }
        }
        catch (NullPointerException ex){
            if(ex.getMessage()!=null) {
                Log.e(TAG, ex.getMessage());
            }
            else{
                ex.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return START_STICKY;
    }

    private void startLocation() {
        Log.d(TAG, "INICIANDO CONEXION CON GOOGLE PLAY SERVICES");
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS){
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();

            }
        } else {
            Log.e(TAG, "NO FUE POSIBLE CONECTARSE A GOOGLE PLAY SERVICES");
            onFailConnection(EnumLocationProvider.OLD_PLAYSERVICES);
        }
    }



    @Override
    public void onDestroy() {
        gpsBDHelper.onDestroy();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        locationChangedFired=true;
        if (location != null) {
            if(!location.hasSpeed()) {
                if(lastLocation!=null) {
                    double time =((location.getTime() - lastLocation.getTime()) / 1000)%60;
                    Float speed = lastLocation.distanceTo(location) /(float)time;
                    if(!speed.isInfinite() && !speed.isNaN()) {
                        location.setSpeed(speed);
                    }
                }
            }
            saveLocationBd(location,EnumLocationProvider.GPS);
        }
        else{
            if(lastLocation!=null){
                saveLocationBd(lastLocation,EnumLocationProvider.GPS);
            }
            else{
                onFailConnection(EnumLocationProvider.GPS);
            }
        }
        stopLocationUpdates();
    }
    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
            googleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1200);
        locationRequest.setFastestInterval(600);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        boolean disponible=LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient).isLocationAvailable();
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(disponible) {
            locationChangedFired=false;
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            io.reactivex.Observable.timer(8,TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    //NO SE EJECUTÓ EL EVENTO ON LOCATION CHANGED
                    if(!locationChangedFired){
                        //¿LA ÚLTIMA UBICACIÓN NO ESTA NULLA?
                        if(lastLocation!=null) {
                            saveLocationBd(lastLocation,EnumLocationProvider.GPS);
                        }
                        else{
                            onFailConnection(EnumLocationProvider.GPS);
                        }
                    }
                    stopSelf();
                }
            });
        }
        else {
            if(lastLocation!=null) {
                saveLocationBd(lastLocation,EnumLocationProvider.GPS);
            }else {
                onFailConnection(EnumLocationProvider.GPS);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        onFailConnection(EnumLocationProvider.GPS);
        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        onFailConnection(EnumLocationProvider.GPS);
    }
    private void onFailConnection(EnumLocationProvider provider)
    {
        Location unkownLocation=new Location(provider.toString());
        unkownLocation.setAltitude(0f);
        unkownLocation.setLongitude(0f);
        saveLocationBd(unkownLocation,provider);
    }

    private void saveLocationBd(Location location, EnumLocationProvider provider){
        if(Utils.isMockSettingOn(this,location)){
            Utils.sendNotification(this,getString(R.string.gps_virtual_activado),getString(R.string.gps__virtual_inhabilitar),true);
            saveEventoVirtual(Constants.GPS_VIRTUAL);
            return;
        }
        else {
            location.setProvider(provider.toString());
            this.gpsBDHelper.generarPosicionActual(location);
        }

    }

    private void saveEventoVirtual(int evento){
        SessionManager manager=new SessionManager(this);
        manager.add(Constants.EVENTO,evento);
        startService(new Intent(this,EventoServiceV2.class));
    }

    @Override
    public void onFinishSavedLocationListener() {
        try {
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);
                mReceiver = null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onSuccessEnviadoQR(String mensajeSuccess) {
        if(mensajeSuccess!=null){
            Utils.sendNotification(this,mensajeSuccess);
        }
    }

    @Override
    public void onCancelEnviadoQR() {

    }
}
