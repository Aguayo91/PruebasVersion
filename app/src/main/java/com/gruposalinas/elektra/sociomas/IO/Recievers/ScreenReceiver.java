package com.gruposalinas.elektra.sociomas.IO.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gruposalinas.elektra.sociomas.IO.Services.ScreenOnService;
import com.sociomas.core.Utils.Constants;


public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ScreenOnService.class);
        i.putExtra(Constants.SCREEN_EVENTO, true);
        context.startService(i);
    }
}