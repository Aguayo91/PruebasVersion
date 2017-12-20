package com.gruposalinas.elektra.sociomas.IO.Recievers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.gruposalinas.elektra.sociomas.IO.Services.ActivityService;

@SuppressWarnings("unused")
public class ActivityReceiver extends WakefulBroadcastReceiver {
    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        startWakefulService(context,new Intent(context, ActivityService.class));
    }
}
