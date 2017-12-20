package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sociomas.core.Utils.Constants;

import static com.gruposalinas.elektra.sociomas.Utils.Utils.TAG;

/*REGRESA LALATITUD Y LONGITUD CON UN BROADCAST*/
public class LocationUIService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;


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
            googleApiClient = new GoogleApiClient.Builder(this)
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
            onLocationChangedListener(location);
            stopLocationUpdates();
        }
    }
    private void onLocationChangedListener(Location location){
        Intent intentBroadCast = new Intent(Constants.BROAD_CAST_LOCATION);
        intentBroadCast.putExtra(Constants.CURRENT_LATLONG,new double[]{
                    location.getLatitude(),location.getLongitude()
            });
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentBroadCast);

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
            locationRequest.setInterval(1200); // milliseconds
            locationRequest.setFastestInterval(1200);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
        catch (SecurityException ex){
            ex.printStackTrace();;
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
}
