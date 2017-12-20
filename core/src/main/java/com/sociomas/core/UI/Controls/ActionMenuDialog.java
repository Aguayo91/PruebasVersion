package com.sociomas.core.UI.Controls;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.sociomas.core.R;
import com.sociomas.core.Utils.Constants;


/**
 * Created by oemy9 on 14/07/2017.
 */

public class ActionMenuDialog implements View.OnClickListener {



    public   enum  EnumAction{
        HORARIO, UBICACION, JUSTIFICACION, PERMISO, ASEGURARORA, PANICO
    }

    private Dialog alertDialog;
    private Context context;
    private View rootView;
    private LayoutInflater layoutInflater;
    private TextView tvTitle;
    private TextView tvMis;
    private TextView tvPlantilla;
    private TextView tvUltimaUbicacion;
    private ImageView imgUltimaUbicacion;
    private ImageView imgMis;
    private ImageView imgPlantilla;
    private EnumAction currentAction;


    public ActionMenuDialog(Context context) {
        this.context = context;
        layoutInflater= LayoutInflater.from(this.context);
        this.rootView=layoutInflater.inflate(R.layout.submenu_justificaciones, null);

    }
    public void show(EnumAction action ){

        currentAction=action;

        if(alertDialog==null) {
            alertDialog = new Dialog(context);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(rootView);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.windowAnimations = R.style.DialogAnimation;
            lp.gravity = Gravity.CENTER;
            alertDialog.getWindow().setAttributes(lp);
            tvMis=(TextView)rootView.findViewById(R.id.tvMis);
            tvPlantilla=(TextView)rootView.findViewById(R.id.tvPlantilla);
            tvTitle=(TextView)rootView.findViewById(R.id.tvTitle);
            imgMis=(ImageView)rootView.findViewById(R.id.imgMis);
            imgPlantilla=(ImageView)rootView.findViewById(R.id.imgPlantilla);
            imgUltimaUbicacion=(ImageView)rootView.findViewById(R.id.imgUltimaUbicacion);
            tvUltimaUbicacion=(TextView)rootView.findViewById(R.id.tvUltimaUbicacion);

            imgUltimaUbicacion.setOnClickListener(this);
            tvUltimaUbicacion.setOnClickListener(this);

            tvPlantilla.setOnClickListener(this);
            imgPlantilla.setOnClickListener(this);

            tvMis.setOnClickListener(this);
            imgMis.setOnClickListener(this);

            updateIntefaz(action);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.show();
                }
            },100);

        }
        else{
            updateIntefaz(action);

            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   alertDialog.show();
               }
           },100);

        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.imgPlantilla || i == R.id.tvPlantilla) {
            intentListener(currentAction, true);

        } else if (i == R.id.imgMis || i == R.id.tvMis) {
            intentListener(currentAction, false);

        }
        alertDialog.dismiss();
    }
    private void intentListener(EnumAction action, boolean plantilla){
        switch (action){
            case HORARIO:
                Intent intent=new Intent();
                String actionIntent=!plantilla? context.getString(R.string.action_horario): context.getString(R.string.action_horario_plantilla);
                intent.setAction(actionIntent);
                context.startActivity(intent);
                break;
        }
    }


    private void updateIntefaz(EnumAction action){
        switch (action){
            case HORARIO:
                tvTitle.setText("Horarios");
                tvMis.setText("Mis horarios");
                tvPlantilla.setText("Horarios de mi plantilla");
                imgMis.setImageResource(R.drawable.btn_mis_horarios);
                imgPlantilla.setImageResource(R.drawable.btn_horarios_plantilla);
                tvUltimaUbicacion.setVisibility(View.GONE);
                imgUltimaUbicacion.setVisibility(View.GONE);
                break;
            default:
                break;

        }
    }
}
