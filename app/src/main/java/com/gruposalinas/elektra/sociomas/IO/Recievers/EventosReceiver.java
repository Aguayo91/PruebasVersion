package com.gruposalinas.elektra.sociomas.IO.Recievers;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.IO.Services.EventoServiceV2;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;

public class EventosReceiver extends WakefulBroadcastReceiver {

    public static final String SIM_CHANGED="android.intent.action.SIM_STATE_CHANGED";
    public static final String PROVIDER_CHANGED ="android.location.PROVIDERS_CHANGED";
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    Context context;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context=context;

        prefs =context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        edit = prefs.edit();

        if(Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction()))
        {
            if(isAirplaneModeOn(context))
            {
                saveEvento(Constants.MODO_AVION);
            }
            else{
                saveEvento(Constants.APAGARMODOAVION);
            }
        }


        if(intent.getAction().equals(PROVIDER_CHANGED))
        {


            if(geGpstatus(context)== Settings.Secure.LOCATION_MODE_OFF)
            {
                  Toast.makeText(context,"apagado",Toast.LENGTH_LONG).show();

                edit.putBoolean(Constants.PROVIDER, true);// se guarda esto para la base
                edit.apply();

                // se manda a llamar el servicio//
                saveEvento(Constants.APAGAR_GPS);

            } else {

                edit.putBoolean(Constants.PROVIDER, false);
                saveEvento(Constants.PRENDERGPS);
            }
        }

        if(Intent.ACTION_SHUTDOWN.equals(intent.getAction()))
        {
            saveEvento(Constants.APAGAR_TELEFONO);
        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            saveEvento(Constants.PRENDERTElEFONO);
        }

        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()) || Intent.ACTION_BATTERY_LOW.equals(intent.getAction()))
        {
            Utils.sendNotification(context,context.getString(R.string.beteriabaja),context.getString(R.string.bateria),true);
        }

        //intent.getAction().equals(Intent.ACTION_TIME_CHANGED)
        if(intent.getAction().equals(Intent.ACTION_DATE_CHANGED)){

            saveEvento(Constants.CAMBIO_FECHA_HORA);


        }
        if(intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)){
           saveEvento(Constants.CAMBIO_ZONA);

        }

        if(intent.getAction().equals(SIM_CHANGED)){
            saveEvento(Constants.CAMBIO_SIM);
        }


    }
    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    public  int geGpstatus(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        int mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
        return  mode;
    }


    private void saveEvento(int evento){
        Intent pushIntent = new Intent(context, EventoServiceV2.class);
        edit.putInt(Constants.EVENTO,evento);
        edit.apply();
        startWakefulService(context,pushIntent);
    }




}
