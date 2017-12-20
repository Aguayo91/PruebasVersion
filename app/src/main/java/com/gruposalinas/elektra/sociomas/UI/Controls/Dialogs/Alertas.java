package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

/**
 * Created by Adrian on 09/08/2016.
 */
@SuppressWarnings("unused")
public class Alertas extends AlertDialog
{

    public Alertas(Context context) {
        super(context);
    }


    public void displayTimerMensaje(final Context context,final int title, final int description, final String url){
            final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
            TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
            Button confirmar=(Button) dialogo.findViewById(R.id.boton_confirmar);

            titulodialo.setGravity(Gravity.CENTER_HORIZONTAL);
            titulodialo.setText(context.getString(title));
            final TextView tvError=(TextView)dialogo.findViewById(R.id.mensajerrror);
            tvError.setText(context.getString(description));
            alert.setContentView(dialogo);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });
            alert.show();

          CountDownTimer countDownTimer=new CountDownTimer(Constants.SEGUNDO*5,Constants.SEGUNDO) {
            @Override
            public void onTick(long millisUntilFinished) {
                long segundos=millisUntilFinished/ Constants.SEGUNDO;
                tvError.setText(context.getString(description,segundos));
            }

            @Override
             public void onFinish() {
                alert.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        }.start();




    }

    // errores
    public void displayMensaje(String error,final Context context)
    {
        final SessionManager sessionManager = new SessionManager(context);
        final Activity activity=(Activity)context;
        final Dialog alert = new Dialog(context, R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        Button confirmar=(Button) dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(sessionManager.get(Constants.IS_ERROR_FECHA)==true? ApplicationBase.getAppContext().getString(R.string.revisa_fecha) :error);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                if(sessionManager.get(Constants.IS_ERROR_FECHA)){
                    activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });
        if(!activity.isFinishing()) {
            alert.show();
        }
        else{
            Log.i("ALERTA","AVOIDING CRASH");
        }
    }


    public void showConfirmar(Context context,String mensaje){
            final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
            Button confirmar=(Button) dialogo.findViewById(R.id.boton_confirmar);
            TextView tvMensaje=(TextView)dialogo.findViewById(R.id.mensajerrror);
            tvMensaje.setText(mensaje);
            alert.setContentView(dialogo);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });
            alert.show();


    }

    //displayMensaje//confirmacion exito//

    public void showExito(final Context context, final Activity activity)
    {

        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);

        alert.setContentView(dialogo);
        alert.setCancelable(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity == null) {
                    alert.dismiss();
                } else {
                    Intent intent = new Intent(context, activity.getClass());
                    context.startActivity(intent);
                    alert.dismiss();

                }

            }
        });

        alert.show();
    }


    public void showError(final Activity activity,String title,String description){
        final Dialog alert = new Dialog(activity,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        titulodialo.setText(title);
        Button confirmar=(Button) dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(Html.fromHtml(description));
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }



}
