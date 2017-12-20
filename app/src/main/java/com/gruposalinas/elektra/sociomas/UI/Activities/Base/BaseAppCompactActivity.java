package com.gruposalinas.elektra.sociomas.UI.Activities.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.IO.Services.SOSAudioService;
import com.gruposalinas.elektra.sociomas.IO.Services.SOService;
import com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar.VerificarSmsActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Contactos.ContactosActivityV2;
import com.gruposalinas.elektra.sociomas.UI.Activities.DesactivarSOS.DesactivarActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.SOS.SosActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.DrawerListAdapter;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.ActionMenuDialog;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.sociomas.core.DataBaseModel.DatosContacto;
import com.sociomas.core.UI.Controls.Model.EnumNavegacion;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Response.Registro.Empleado;

/**
 * Created by oemy9 on 24/04/2017.
 */

public class BaseAppCompactActivity extends BaseCoreCompactActivity implements
        DrawerLayout.DrawerListener{

    protected Alertas alertaAsync;
    protected ControllerAPI controllerAPI;
    protected CustomProgressBar customProgressBar;
    protected ActionMenuDialog menuDialog;
    protected String currentTitle;
    private  DialogOptions dialogOptions;
    private DrawerLayout drawer;
    private EnumNavegacion selectedItem,lastSelectedItem;
    private ListView listDrawer;
    private TextView tvHeader;
    private ImageView imageHeader;
    private DrawerListAdapter draweAdapter;
    private TextView tvVersion;
    private View contentView;
    private static final float END_SCALE = 0.7f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.alertaAsync=new Alertas(this);
        this.controllerAPI=new ControllerAPI(this);
        this.customProgressBar=new CustomProgressBar(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected  void NavegarActivityAsync( Class<? extends BaseCoreCompactActivity> activity){


        if(!Utils.telefonoValidado(this)){
            dialogOptions.showWarning(getString(R.string.verificar_dialogo_titulo),getString(R.string.verificar_dialogo_description));
            dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                @Override
                public void OnDismiss(boolean accepted) {
                    if(accepted){
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(BaseAppCompactActivity.this,VerificarSmsActivity.class));
                    }
                }
            });
            return;
        };

        if(this.getClass()!=activity){
            //panico se valida que ya tenga contactos registrados
            if(activity==SosActivity.class){
                DatosContacto datosContacto= new DatosContacto();
                startActivity(new Intent(getApplicationContext(),
                        !datosContacto.isguardar(this)? ContactosActivityV2.class: SosActivity.class));
            }
            //navega al inicio
            else if(activity==InicioActivity.class)
            {
                Intent intent=new Intent(this,activity);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);            }
            else {
                startActivity(new Intent(this, activity));
            }
        }
    }

    @Override
    public void OnOptionSelected(EnumNavegacion navegacion) {
        if(navegacion==EnumNavegacion.SOS) {
            DatosContacto datosContacto = new DatosContacto();
            if (Utils.servicioEjecutando(this, SOService.class) || Utils.servicioEjecutando(this, SOSAudioService.class)) {
                            startActivity(new Intent(this, DesactivarActivity.class));
            } else {
                startActivity(new Intent(this, !datosContacto.isguardar(this) ? ContactosActivityV2.class : SosActivity.class));
            }
        }
        else {
            super.OnOptionSelected(navegacion);
        }
    }

    protected void saveInDatabase(String numeroEmpleado, Empleado empleado){
        empleado.setIdNumEmpleado(numeroEmpleado);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(Constants.SP_NAME, Utils.toTitleCase(empleado.getVaNombreCompleto()));
        edit.putString(Constants.SP_ENTERPRISE, empleado.getIdEmpresa());
        edit.putInt(Constants.SP_POSITION, empleado.getIdPuesto());
        edit.putString(Constants.SP_HASLOGGED, Constants.SP_LOGGED);
        edit.putString(Constants.SP_ID, empleado.getIdNumEmpleado());
        edit.putString(Constants.MY_PHONE,empleado.getVaNumeroTelefono());
        edit.putString(Constants.MY_CODIGO,String.valueOf(2));
        //QUITAR EL COMENTARIO DE ESTA LINEA SI QUEREMOS VALIDAR EL TELÉFONO
        edit.putBoolean(Constants.TELEFONO_VALIDADO,true);
        edit.putString(Constants.MY_CLAVE,"52");
        if(empleado.getFoto()!=null){
            edit.putString(Constants.IMAGEN_CREDENCIAL,empleado.getFoto());
        }
        edit.commit();
    }


}
