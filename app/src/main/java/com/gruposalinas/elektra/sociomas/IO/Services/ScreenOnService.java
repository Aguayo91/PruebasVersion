package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.UI.Activities.Contactos.ContactosActivityV2;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.DatosContacto;
import com.sociomas.core.Utils.Constants;

public class ScreenOnService extends Service {
    private int contador;
    public static final String TAG="SCREEN SERVICE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null!=intent) {
            boolean screenOn = intent.hasExtra(Constants.SCREEN_EVENTO);
            if (screenOn) {
                if (contador == 4) {
                    Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    Utils.sendNotification(this,"Botón de pánico activado");

                    DatosContacto datosContacto= new DatosContacto();

                    if(!datosContacto.isguardar(this))
                    {
                        Intent intentContactos= new Intent(getApplicationContext(),ContactosActivityV2.class);
                        intentContactos.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentContactos);
                    }
                    else{

                        Intent intentPanico= new Intent(getApplicationContext(),SOService.class);
                        startService(intentPanico);
                    }

                    contador = 0;
                } else {
                    contador++;
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (contador > 0) {
                            contador = 0;
                            Log.i(TAG, "CAMBIANDO CONTADOR");
                        }
                    }
                }, Constants.SEGUNDO * 3);

            }
            return START_STICKY;
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
