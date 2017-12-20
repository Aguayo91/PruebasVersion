package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.CatalogoTiendasActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.CatalogoZonasActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;

/**
 * Created by oemy9 on 06/06/2017.
 */

public class DialogOptions {


    private Context context;

    public interface CallBackDialgoOptions{
        void OnDismiss(boolean accepted);
    }

    public DialogOptions(Context context) {
        this.context = context;
    }

    private CallBackDialgoOptions callBackDialgoOptions;
    public void setCallBackDialgoOptions(CallBackDialgoOptions callBackDialgoOptions) {
        this.callBackDialgoOptions = callBackDialgoOptions;
    }

    /*IMPLEMENTACIÓN DE ANTIGUO DESARROLLADOR PERO UN POCO MÁS LIMPIA EN CIERTOS ASPECTOS*/
    public void showExito(boolean isPlantilla){
        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.alerta_exito, null);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        final TextView tvDescription=(TextView)dialogo.findViewById(R.id.tvDescription);
        tvDescription.setVisibility(isPlantilla? View.GONE: View.VISIBLE);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        alert.show();
    }

    public void showExito(String title){
        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.alerta_exito, null);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        final TextView tvDescription=(TextView)dialogo.findViewById(R.id.tvDescription);
        final TextView tvTitle=(TextView)dialogo.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        tvDescription.setVisibility(View.GONE);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                alert.dismiss();
            }
        });
        alert.show();
    }

    public void showExito(){
        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.alerta_exito, null);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        alert.show();
    }
    public void  showWarning(String title,String description){
        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.alerta_warning, null);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alert.getWindow().getAttributes());
       // lp.windowAnimations = R.style.DialogAnimation;
        lp.gravity = Gravity.CENTER;
        alert.getWindow().setAttributes(lp);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final TextView tvTitle=(TextView)dialogo.findViewById(R.id.tvTitle);
        final TextView tvDescription=(TextView)dialogo.findViewById(R.id.tvDescription);
        tvTitle.setText(title);
        tvDescription.setText(description);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        alert.show();
    }

    public void  showExito(String title,String description){
        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View dialogo=inflater.inflate(R.layout.alerta_exito, null);
        final TextView tvTitle=(TextView)dialogo.findViewById(R.id.tvTitle);
        final TextView tvDescription=(TextView)dialogo.findViewById(R.id.tvDescription);
        tvTitle.setText(title);
        tvDescription.setText(description);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        alert.show();
    }

    public  void showZonaDialogo(final Context context, final boolean isPlantilla, final String numeroEmpleado){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View rootView = layoutInflater.inflate(R.layout.dialogo_zonas, null);
        Button btnZona=(Button)rootView.findViewById(R.id.btnZona);
        Button btnUbicacion=(Button)rootView.findViewById(R.id.btnUbicacion);
        dialogBuilder.setView(rootView);
        final AlertDialog  alertDialog=dialogBuilder.create();
        alertDialog.show();
        btnZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,CatalogoZonasActivity.class);
                intent.putExtra(Constants.IS_PLANTILLA,isPlantilla);
                intent.putExtra(Constants.SELECTED_ID_EMPLEADO,numeroEmpleado!=null? numeroEmpleado: Utils.getNumeroEmpleado(context));
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CatalogoTiendasActivity.class);
                intent.putExtra(Constants.IS_PLANTILLA,isPlantilla);
                intent.putExtra(Constants.SELECTED_ID_EMPLEADO,numeroEmpleado!=null? numeroEmpleado: Utils.getNumeroEmpleado(context));
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
    }
    public void showDialogoBase(final  Context context){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View rootView = layoutInflater.inflate(R.layout.dialogo_base_aventones, null);
        Button btnConfirmar=(Button)rootView.findViewById(R.id.btnConfirmar);
        Button btnCancelar=(Button)rootView.findViewById(R.id.btnCancelar);
        TextView tvTitle=(TextView) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("Solicitar agregar destino");
        dialogBuilder.setView(rootView);
        final AlertDialog  alertDialog=dialogBuilder.create();
        alertDialog.show();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alertDialog.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(false);
                }
                alertDialog.dismiss();
            }
        });

    }

    public void show(String title, Spanned description){
        final Dialog alert = new Dialog(context, R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=LayoutInflater.from(context);
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView tituloDialogo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        TextView tvDescription=(TextView)dialogo.findViewById(R.id.mensajemostrar);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        tituloDialogo.setText(title);
        tvDescription.setText(description);
        alert.setContentView(dialogo);
        alert.show();
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackDialgoOptions!=null) {
                    callBackDialgoOptions.OnDismiss(false);
                }
                alert.dismiss();
            }
        });
    }

    public void show(String title){
        final Dialog alert = new Dialog(context, R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=LayoutInflater.from(context);
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView tituloDialogo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        tituloDialogo.setText(title);
        alert.setContentView(dialogo);
        alert.show();
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackDialgoOptions!=null){
                    callBackDialgoOptions.OnDismiss(true);
                }
                alert.dismiss();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackDialgoOptions!=null) {
                    callBackDialgoOptions.OnDismiss(false);
                }
                alert.dismiss();
            }
        });
    }

}
