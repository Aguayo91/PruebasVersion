package com.gruposalinas.elektra.sociomas.IO.Recievers;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;


import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
/*
*  RECIEVER CQUE SE EJECUTA CUANDO EL USUARIO TIENE LA APLICACIÓN EN PRIMER PLANO
*  O ENCIENDE EL DISPOSITIVO POR PRIMERA VEZ.
*  IMPORTANTE VEA QUE SE ENVÍA UNA VARIABLE BOOLEANA PARA SABER SI LA APP ESTA EN PRIMER PLANO
*  O SE EJECUTA DESDE EL BOOT
* */
public class LocationBootReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = "LocationBootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
         boolean fromForeground=intent.getAction().equals(Constants.INCIAR_SERVICIOS_BROADCAST);
         Utils.runAlarm(context, !fromForeground);

         /*
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                   Utils.runScheduleJob(context, !fromForeground);
        }
        else {

        }*/
    }
}
