package com.gruposalinas.elektra.sociomas.UI.Fragments.Base;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.FragmentGafeteParallaxV;

import java.util.concurrent.TimeUnit;

/**
 * Created by oemy9 on 22/11/2017.
 */

public class FragmentLocation extends Fragment  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    protected LatLng currentLatLng;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private static final String TAG = FragmentLocation.class.getName();

    public static FragmentLocation getInstance(@Nullable  Bundle bundle) {
        FragmentLocation fragment = new FragmentGafeteParallaxV();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (resultCode == ConnectionResult.SUCCESS){
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            this.currentLatLng=new LatLng(location.getLatitude(),location.getLongitude());
            stopLocationUpdates();
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
            locationRequest.setInterval(TimeUnit.MINUTES.toMillis(5));
            locationRequest.setFastestInterval(1200);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
        catch (SecurityException ex){
            ex.printStackTrace();;
        }
    }
}
