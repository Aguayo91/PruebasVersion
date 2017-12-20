package com.sociomas.core.UI.Controls.Notification;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.MessageQueue;
import android.support.test.espresso.IdlingResource;

import com.sociomas.core.R;
import com.sociomas.core.UI.Controls.Progress.ProgressBubble;

/**
 * Created by oemy9 on 28/04/2017.
 */
public class CustomProgressBar implements IdlingResource {
    private Dialog dialog;
    private ProgressBubble progressBubble;
    private boolean isIdle;
    private ResourceCallback resourceCallback;

    public CustomProgressBar(Context context){
        this.dialog = new Dialog(context, R.style.Theme_Dialog_Translucent);
    }
    public void show(Activity activity){
        try {
            if (!activity.isFinishing()) {
                //dialog.setContentView(R.layout.custom_progressbar);
                dialog.setContentView(R.layout.custom_progressbar_bubble);
                dialog.setCancelable(true);
                dialog.show();
                isIdle=true;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void dismiss(){
        try {
            if (this.dialog != null) {
                this.dialog.dismiss();
                this.dialog.hide();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();;
        }
        isIdle=false;
    }

    @Override
    public String getName() {
        return CustomProgressBar.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if(isIdle) {
            return true;
        }
        if(isIdle){
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
