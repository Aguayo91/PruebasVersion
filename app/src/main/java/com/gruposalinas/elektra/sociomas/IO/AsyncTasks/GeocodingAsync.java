package com.gruposalinas.elektra.sociomas.IO.AsyncTasks;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 05/05/2017.
 */

public class GeocodingAsync extends AsyncTask<Double,String,String> {

    private Context context;
    private GeocodingInterface geocodingInterface;

    public interface GeocodingInterface
    {
         void OnGeocodingFinish(String result);
    }

    public void setGeocodingInterface(GeocodingInterface geocodingInterface) {
        if(geocodingInterface!=null) {
            this.geocodingInterface = geocodingInterface;
        }
    }

    public  GeocodingAsync(Context context){
        if(context!=null){
            this.context=context;
        }
    }

    @Override
    protected String doInBackground(Double... params) {

        String addressText=null;

        List<Address> addresses = null;
        Geocoder geocoder=new Geocoder(context,Locale.getDefault());
        double laltitud=params[0].doubleValue();
        double longitud=params[1].doubleValue();

        try {
            addresses = geocoder.getFromLocation(laltitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder resultado=new StringBuilder();
        if(addresses!=null) {
            if (addresses.size() > 0) {
                 String calleNumero=addresses.get(0).getThoroughfare()+" "+ addresses.get(0).getSubThoroughfare();
                 String colonia=addresses.get(0).getSubLocality();
                 resultado.append(calleNumero);
                 resultado.append(" ");
                 resultado.append(colonia);
                /*
                addressText = addresses.get(0).getAddressLine(0);
                if(addresses.get(0).getSubLocality()!=null && (!addresses.get(0).getSubLocality().isEmpty())){
                    addressText+=" "+addresses.get(0).getSubLocality();
                }*/
            }
        }

        return  resultado.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(this.geocodingInterface!=null){
            this.geocodingInterface.OnGeocodingFinish(s);
        }
    }

}
