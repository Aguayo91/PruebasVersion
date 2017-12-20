package com.sociomas.core.Async;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.sociomas.core.WebService.Model.Response.Geocoding.GeocodingResult;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 31/08/2017.
 */

public class GeocodingAsync extends AsyncTask<Double,String,String> {

    private Context context;
    private GeocodingInterface geocodingInterface;
    private LatLng currentLatLng;

    public interface GeocodingInterface
    {
         void OnGeocodingFinish(GeocodingResult result);
    }

    public void setGeocodingInterface(GeocodingInterface geocodingInterface) {
        if(geocodingInterface!=null) {
            this.geocodingInterface = geocodingInterface;
        }
    }

    public GeocodingAsync(Context context){
        if(context!=null){
            this.context=context;
        }
    }

    @Override
    protected String doInBackground(Double... params) {
        List<Address> addresses = null;
        Geocoder geocoder=new Geocoder(context,Locale.getDefault());
        double latitud=params[0].doubleValue();
        double longitud=params[1].doubleValue();
        currentLatLng=new LatLng(latitud,longitud);
        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder resultado=new StringBuilder();
        if(addresses!=null) {
            if (addresses.size() > 0) {
                 String calleNumero=addresses.get(0).getThoroughfare()+" "+ addresses.get(0).getSubThoroughfare();
                 String colonia=addresses.get(0).getSubLocality();
                 resultado.append(calleNumero);
                 resultado.append("\n");
                 resultado.append(colonia);
            }
        }

        return  resultado.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(this.geocodingInterface!=null){
            this.geocodingInterface.OnGeocodingFinish(new GeocodingResult(new double[]{
                    currentLatLng.latitude,currentLatLng.longitude
            } ,s));
        }
    }

}
