package com.gruposalinas.elektra.sociomas.UI.Controls.Notification;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.gruposalinas.elektra.sociomas.R;
import com.tapadoo.alerter.Alerter;

import de.mateware.snacky.Snacky;

/**
 * Created by oemy9 on 13/06/2017.
 */

public class SnackBarBuilder {

    private Activity activity;


    public SnackBarBuilder(Activity activity) {
        this.activity = activity;
    }


    public  void showAlertNotificacion(String title, int segundos){
        Alerter.create(this.activity)
                .setTitle(title)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setTextAppearance(R.style.textAlertStyle)
                .enableSwipeToDismiss().setDuration(segundos)
                .enableVibration(true)
                .show();
    }
    public  void showAlertNotificacion(String title, int segundos, View.OnClickListener listener){
        Alerter.create(this.activity)
                .setTitle(title)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setTextAppearance(R.style.textAlertStyle)
                .enableSwipeToDismiss().setDuration(segundos)
                .enableVibration(true).setOnClickListener(listener)
                .show();
    }

    public void showAlertNotificacionPush(String title, int segundos){
        Alerter.create(this.activity)
                .setTitle(title)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setTextAppearance(R.style.textAlertStyle)
                .enableSwipeToDismiss().setDuration(segundos)
                .enableVibration(true)
                .show();
    }

    public   void showError(String text){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text)
                .setDuration(Snacky.LENGTH_LONG).error();
        snackbar.show();

    }

    public   void showWarning(String text){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text)
                .setDuration(Snacky.LENGTH_LONG).warning();
        snackbar.show();

    }


    public void showInfo(String text){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text)
                .setDuration(Snacky.LENGTH_LONG).info();
        snackbar.show();
    }

    public void showSuccess(String text){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text)
                .setDuration(Snacky.LENGTH_LONG).success();
        snackbar.show();
    }

    public void showSuccessCallBack(String text, BaseTransientBottomBar.BaseCallback<Snackbar> callback){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text)
                .setDuration(Snacky.LENGTH_LONG).success();
        snackbar.show();
        snackbar.addCallback(callback);
    }

    public void showPrimaryColor(final Context context, String text, int duration){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity)
                .setText(text).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setDuration(duration).warning();
        snackbar.show();
    }

    public  void showPrimaryColor(final Context context, String text, int duration, View.OnClickListener listener){
        Snackbar snackbar = Snacky.builder()
                .setActivty(activity).setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setText(text)
                .setActionTextSize(20).setActionText(">").setActionClickListener(listener)
                .setActionTextTypefaceStyle(Typeface.BOLD)
                .setDuration(duration).warning();
        snackbar.show();
    }



}
