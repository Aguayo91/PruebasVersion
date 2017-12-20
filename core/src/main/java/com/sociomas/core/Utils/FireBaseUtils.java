package com.sociomas.core.Utils;

import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by oemy9 on 04/12/2017.
 */

public class FireBaseUtils {

    private Intent intent;

    public FireBaseUtils(Intent intent) {
        this.intent = intent;
    }

    public RemoteMessage getRemoteMsgFromInteng(){
        if(intent!=null){
            if(intent.hasExtra(Constants.DATA_SEND)){
            }
        }
        return null;
    }
}
