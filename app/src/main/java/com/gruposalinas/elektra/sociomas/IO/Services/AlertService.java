package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAlarma;

@SuppressWarnings("unused")
public class AlertService extends Service {
    public static final String TAG="TIPO";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {

            EnumAlarma tipo = EnumAlarma.valueOf(intent.getStringExtra(Constants.TIPO_ALARMA));
            Intent locatationIntent = new Intent(AlertService.this, LocationService.class);
            locatationIntent.putExtra(Constants.TIPO_ALARMA, tipo);
            startService(locatationIntent);



            /*
            if (intent.hasExtra(Constants.TIPO_ALARMA)) {
                EnumAlarma tipo = EnumAlarma.valueOf(intent.getStringExtra(Constants.TIPO_ALARMA));
                switch (tipo) {
                    case entrada:
                        Utils.sendNotification(this, getString(R.string.title_entrada), getString(R.string.description_entrada), true);
                        break;
                    case salida:
                    //    Utils.sendNotification(this, "SALIDA", "YA ES HORA DE QUE SALGAS", true);
                        break;
                    case diez_minutos:
                        Utils.sendNotification(this, getString(R.string.title_tolerancia), getString(R.string.description_tolerancia), true);
                        break;
                }*/




        }

        return  START_REDELIVER_INTENT;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
