package com.sociomas.core.UI.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.maps.model.LatLng;
import com.sociomas.core.Listeners.CallBackLocationGenerated;
import com.sociomas.core.Services.LocationUIService;
import com.sociomas.core.Utils.Constants;

/**
 * Created by oemy9 on 20/10/2017.
 */

public class BaseLocationActivity extends BaseCoreCompactActivity {

    protected    CallBackLocationGenerated callBackLocationGenerated;
    protected LatLng currentLatLng;
    public void setCallBackLocationGenerated(CallBackLocationGenerated callBackLocationGenerated) {
        this.callBackLocationGenerated = callBackLocationGenerated;
    }

    /*MÉTODO QUE INICIA SERVICIO DE GEOLOCALIZACIÓN Y OBTIENE EL REVERSE GEOCODING*/
    private void requestGeocodingAsync() {
        BroadcastReceiver bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {if (intent.getAction().equals(Constants.BROAD_CAST_LOCATION)) {
                double[] latLngObtenido = intent.getDoubleArrayExtra(Constants.CURRENT_LATLONG);
                if(callBackLocationGenerated!=null){
                    LatLng latLng=new LatLng(latLngObtenido[0],latLngObtenido[1]);
                    currentLatLng=latLng;
                    callBackLocationGenerated.onLocationGenerated(latLng);
                }

            }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROAD_CAST_LOCATION);
        bManager.registerReceiver(bReceiver, intentFilter);
        startService(new Intent(this, LocationUIService.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestGeocodingAsync();
    }
}
