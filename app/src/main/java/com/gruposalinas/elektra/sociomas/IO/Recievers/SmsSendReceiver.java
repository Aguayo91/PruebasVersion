package com.gruposalinas.elektra.sociomas.IO.Recievers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsSendReceiver extends BroadcastReceiver {
    public static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
    public static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK: // Sms sent
                    Toast.makeText(context, "ENVIANDO MENSAJE...", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                    Toast.makeText(context, "NO ENVIADO", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                    Toast.makeText(context, "NO SERVICIO", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                    Toast.makeText(context,"NULL PDU", Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                    Toast.makeText(context,"RADIO OFF", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        //detect la reception d'un sms
        else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "RECIBIDO", Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "NO RECIBIFO", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    /**
     * Test if device can send SMS
     * @param context
     * @return
     */
    public static boolean canSendSMS(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public static void sendSMS(final Context context, String phoneNumber, String message) {

        if (!canSendSMS(context)) {
            Toast.makeText(context, "NO SE PUEDE ENVIAR", Toast.LENGTH_LONG).show();
            return;
        }

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        final SmsSendReceiver smsUtils = new SmsSendReceiver();
        //register for sending and delivery
       try {
           context.unregisterReceiver(smsUtils);
           context.registerReceiver(smsUtils, new IntentFilter(SENT_SMS_ACTION_NAME));
           context.registerReceiver(smsUtils, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

           SmsManager sms = SmsManager.getDefault();
           ArrayList<String> parts = sms.divideMessage(message);

           ArrayList<PendingIntent> sendList = new ArrayList<>();
           sendList.add(sentPI);

           ArrayList<PendingIntent> deliverList = new ArrayList<>();
           deliverList.add(deliveredPI);

           sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList);

           //we unsubscribed in 10 seconds
           new android.os.Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   context.unregisterReceiver(smsUtils);
               }
           }, 10000);
       }
       catch (Exception ex){
           ex.printStackTrace();
       }

    }
}
