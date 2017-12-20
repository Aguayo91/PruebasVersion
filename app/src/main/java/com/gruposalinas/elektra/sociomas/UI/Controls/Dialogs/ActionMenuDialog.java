package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

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

import com.gruposalinas.elektra.sociomas.UI.Activities.Horarios.HorariosPlantillaActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Horarios.MisHorariosActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.IncidenciaPlantillaActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.IncidenciasListaActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo.LugaresActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Permisos.MisPermisosActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Permisos.PermisosPlantillaActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Ubicaciones.LastUbicacionActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones.MisZonasUActivity;
import com.gruposalinas.elektra.sociomas.R;
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
        switch (view.getId()){
            case R.id.imgPlantilla:
            case R.id.tvPlantilla:
                intentListener(currentAction,true);
                break;

            case R.id.imgMis:
            case R.id.tvMis:
                intentListener(currentAction,false);
                break;

            case R.id.tvUltimaUbicacion:
            case R.id.imgUltimaUbicacion:
                Intent intent=new Intent(this.context, LastUbicacionActivity.class);
                context.startActivity(intent);
                break;

        }
        alertDialog.dismiss();
    }
    private void intentListener(EnumAction action, boolean plantilla){
        switch (action){
            case HORARIO:
                Intent intent=new Intent(context,!plantilla? MisHorariosActivity.class: HorariosPlantillaActivity.class);
                context.startActivity(intent);
                break;
            case PERMISO:
                Intent intentPermiso=new Intent(context,!plantilla? MisPermisosActivity.class: PermisosPlantillaActivity.class);
                context.startActivity(intentPermiso);
                break;
            case JUSTIFICACION:
                Intent intentJustificacion=new Intent(context,!plantilla? IncidenciasListaActivityV2.class: IncidenciaPlantillaActivity.class);
                context.startActivity(intentJustificacion);
                break;
            case UBICACION:
                Intent intentUbicacion=new Intent(context, LugaresActivity.class);
                intentUbicacion.putExtra(Constants.IS_PLANTILLA,plantilla);
                context.startActivity(intentUbicacion);
                break;

        }
    }


    private void updateIntefaz(EnumAction action){
        switch (action){
            case JUSTIFICACION:
                    tvTitle.setText(context.getString(R.string.justificaciones));
                    tvMis.setText(context.getString(R.string.misjustificaciones));
                    tvPlantilla.setText(context.getString(R.string.justificacionesplantilla));
                    imgMis.setImageResource(R.mipmap.ic_mis_justificaciones);
                    imgPlantilla.setImageResource(R.mipmap.ic_justificaciones_plantilla);
                    tvUltimaUbicacion.setVisibility(View.GONE);
                    imgUltimaUbicacion.setVisibility(View.GONE);
                break;
            case PERMISO:
                    tvTitle.setText(context.getString(R.string.permisos));
                    tvMis.setText(context.getString(R.string.mispermisos));
                    tvPlantilla.setText(context.getString(R.string.permisos_de_mi_plantilla));
                    imgMis.setImageResource(R.mipmap.ic_mis_permisos);
                    imgPlantilla.setImageResource(R.mipmap.ic_permisos_plantilla);
                    tvUltimaUbicacion.setVisibility(View.GONE);
                    imgUltimaUbicacion.setVisibility(View.GONE);
                break;
            case UBICACION:
                 tvTitle.setText(context.getString(R.string.zonas_ubicaciones));
                 tvMis.setText(context.getString(R.string.mis_ubicaciones));
                 tvPlantilla.setText(context.getString(R.string.ubicaciones_plantilla));
                 imgMis.setImageResource(R.mipmap.ic_mis_ubicaciones);
                 imgPlantilla.setImageResource(R.drawable.btn_horarios_plantilla);
                 tvUltimaUbicacion.setVisibility(View.GONE);
                 imgUltimaUbicacion.setVisibility(View.GONE);
                break;
            case HORARIO:
                tvTitle.setText(context.getString(R.string.horarios_dialogo));
                tvMis.setText(context.getString(R.string.mis_horarios));
                tvPlantilla.setText(context.getString(R.string.horarios_de_mi_plantilla));
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
