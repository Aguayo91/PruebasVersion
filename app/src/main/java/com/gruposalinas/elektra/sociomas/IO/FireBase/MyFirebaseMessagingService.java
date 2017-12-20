package com.gruposalinas.elektra.sociomas.IO.FireBase;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioInteractor;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.sociomas.core.Utils.Utils;

/**
 * Created by yvegav on 18/10/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        InicioInteractor inicioInteractor=new InicioInteractor();
        Intent intentBroadCast = new Intent(Constants.BROAD_CAST_NOTIFICATION);
        intentBroadCast.putExtra(Constants.DATA_SEND,remoteMessage);
        /*
        if(remoteMessage.getData().containsKey(Constants.ID_NOTIFICACION)) {
            inicioInteractor.modificarEstatusNotificacion(EnumEstatusNotificacion.LEIDA, Integer.valueOf(remoteMessage.getData().get(Constants.ID_NOTIFICACION)));
        }*/
        Utils.updateNotificacionEstatus(this,false);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentBroadCast);
    }
}
