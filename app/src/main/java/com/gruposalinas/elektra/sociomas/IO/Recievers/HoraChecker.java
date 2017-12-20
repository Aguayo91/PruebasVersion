package com.gruposalinas.elektra.sociomas.IO.Recievers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.gruposalinas.elektra.sociomas.IO.Services.HoraCheckerService;

public class HoraChecker extends WakefulBroadcastReceiver {
    public static final String TAG="HORA_CHECKER";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(Intent.ACTION_DATE_CHANGED) ||
                intent.getAction().equals(Intent.ACTION_TIME_CHANGED)  ||
                intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED))
        {
            context.startService(new Intent(context,HoraCheckerService.class));
        }
    }
}
