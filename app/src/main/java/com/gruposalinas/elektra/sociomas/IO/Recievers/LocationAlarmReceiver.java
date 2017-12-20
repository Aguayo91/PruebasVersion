package com.gruposalinas.elektra.sociomas.IO.Recievers;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.IO.Services.HoraCheckerService;
import com.gruposalinas.elektra.sociomas.IO.Services.HoraService;
import com.gruposalinas.elektra.sociomas.IO.Services.LocationService;
import com.gruposalinas.elektra.sociomas.Utils.SupportUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;

import java.util.Calendar;

import okhttp3.internal.Util;

/*
* BROADCAST QUE SE EJECUTA TANTO EN EL ALARMNMANAGER & JOBSERVICE
* SE ENCARGA DE VERIFICAR SI EL DISPOSITIVO ES  UN MODELO NO SOPORTADO O NOP
* EN CASO DE QUE NO LO SEA DEDICA A LANZAR LOS SERVICIOS DE LOCATION & HORASERVICE
* */
public class LocationAlarmReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = "LocationAlarmReceiver";
    private PowerManager pm;
    private PowerManager.WakeLock wl;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isModeloNoSoportado = SupportUtils.isHuawei() || SupportUtils.isModeloNoSoportado();
        boolean readyTurnOn = Calendar.getInstance().get(Calendar.MINUTE) % 3 == 0;
        if (isModeloNoSoportado && readyTurnOn) {
            pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "SERVICIO ACTUALIZACION GPS");
            wl.acquire();
            Log.d(TAG, "onReceive: iniciando powerManager");
        }
        startWakefulService(context, new Intent(context, LocationService.class));
        if (isModeloNoSoportado && readyTurnOn) {
            wl.release();
            Log.d(TAG, "onReceive: activando powerManager");
        }

    }
}
        /*
        startWakefulService(context,new Intent(context, HoraService.class));
        if(intent.getBooleanExtra(Constants.FROM_REBOOT,false)){
               Intent i=new Intent(context,HoraCheckerService.class);
               i.putExtra(Constants.FROM_REBOOT,true);
               startWakefulService(context,i);
        }*/


