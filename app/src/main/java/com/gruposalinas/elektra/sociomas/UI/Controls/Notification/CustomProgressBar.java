package com.gruposalinas.elektra.sociomas.UI.Controls.Notification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 28/04/2017.
 */

public class CustomProgressBar {
    private Dialog dialog;
    public CustomProgressBar(Context context){
        this.dialog = new Dialog(context, R.style.Theme_Dialog_Translucent);
    }
    public void show(Activity activity){
        try {
            if (!activity.isFinishing()) {
                dialog.setContentView(R.layout.custom_progressbar);
                dialog.setCancelable(true);
                dialog.show();
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
            ex.printStackTrace();
        }
    }
}
