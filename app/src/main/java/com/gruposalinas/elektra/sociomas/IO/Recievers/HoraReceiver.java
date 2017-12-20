package com.gruposalinas.elektra.sociomas.IO.Recievers;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.gruposalinas.elektra.sociomas.IO.Services.AlertService;
import com.sociomas.core.Utils.Constants;

/*RECIBE ALERTAS
  DESDE LA HORA DE ENTRADA
* HORA DE SALIDA
* DIEZ MINUTOS DESPUÉS*/
@SuppressWarnings("unused")
public class HoraReceiver extends WakefulBroadcastReceiver {
    public static final String TAG="HORA_RECEIVER";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i =new  Intent(context, AlertService.class);
        if(intent.hasExtra(Constants.TIPO_ALARMA)){
            String tipo= intent.getStringExtra(Constants.TIPO_ALARMA);
            i.putExtra(Constants.TIPO_ALARMA,tipo);
        }
        context.startService(i);
    }
}
