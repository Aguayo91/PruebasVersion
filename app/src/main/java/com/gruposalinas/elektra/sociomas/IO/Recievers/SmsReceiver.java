package com.gruposalinas.elektra.sociomas.IO.Recievers;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar.VerificarSmsActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;


public class SmsReceiver extends BroadcastReceiver {

    public  static  final String CODIGO_MENSAJE="Tu código de verificación para SocioMAS es: ";
    public static final String CODIGO_MENSAJE_ESPACIO="T͏u c odigo d͏e verificaci on  par͏a͏ Socio͏MA͏S͏ es: ";
    public static final String CODIGO_MENSAJE_SIN_ACENTO="Tu codigo de verificacion para SocioMAS es: ";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String cuerpoMensaje=smsMessage.getMessageBody().replaceAll("\\p{C}","");
                    if (cuerpoMensaje.contains(CODIGO_MENSAJE)){
                        getCodigoVerificacionAsync(context,cuerpoMensaje,CODIGO_MENSAJE);
                    }
                    else if(cuerpoMensaje.contains(CODIGO_MENSAJE_ESPACIO)) {
                        getCodigoVerificacionAsync(context,cuerpoMensaje,CODIGO_MENSAJE_ESPACIO);
                    }
                    else if(cuerpoMensaje.contains(CODIGO_MENSAJE_SIN_ACENTO)) {
                        getCodigoVerificacionAsync(context,cuerpoMensaje,CODIGO_MENSAJE_SIN_ACENTO);
                    }


                        /*
                        //AP EN BACKGROUND
                        if (appBackground(context)) {
                            Intent intentSmsActivity = new Intent(context, VerificarSmsActivity.class);
                            intentSmsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentSmsActivity.putExtras(bundle);
                            context.startActivity(intentSmsActivity);
                        }
                        //APP EN FOREGROUND LLAMA AL BROADCAST
                        else {
                            Intent intentBroadCast = new Intent(Constants.BROAD_CODIGO_VERIFICACION);
                            intentBroadCast.putExtras(bundle);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intentBroadCast);
                        }*/

                    }
                }
            }
    }

    private void getCodigoVerificacionAsync(Context context,String cuerpoMensaje,String codigo_mensaje){

        SessionManager manager=new SessionManager(context);
        if(!manager.get(Constants.TELEFONO_VALIDADO,true)) {
            String codigoVerificacion = cuerpoMensaje.replace(codigo_mensaje, "");
            String[] digitosCodigo = codigoVerificacion.split("-");
            Bundle bundle = new Bundle();
            bundle.putStringArray(Constants.CODIGO_VERIFICACION, digitosCodigo);
            Intent intentSmsActivity = new Intent(context, VerificarSmsActivity.class);
            intentSmsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentSmsActivity.putExtras(bundle);
            context.startActivity(intentSmsActivity);
        }
    }
    static boolean appBackground(Context context) {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        if (myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
            return true;
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }
}
