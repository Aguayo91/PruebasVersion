package com.gruposalinas.elektra.sociomas.UI.Activities.Permisos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoPermiso;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.CalendarEditText;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.TimeText;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAlarma;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackCatalogoPermisos;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Permisos.SolicitarPermiso;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Permisos.CatalogoPermisoRes;
import com.sociomas.core.WebService.Model.Response.Permisos.CatalogoTipo;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarPermisoV2 extends BaseAppCompactActivity implements CallBackCatalogoPermisos {
    public static final String TAG="RESPONSE_PERMISOS";

    private CustomProgressBar customProgressBar;
    private ControllerAPI controllerAPI;
    private AdapterCatalogoPermiso adapterCatalogoPermiso;
    private AdapterCatalogoEmpleado adapterCatalogoEmpleado;
    private Spinner spinnerTipoPermiso,spinnerCatalogoEmpleado;
    private TimeText txtHoraInicio,txtHoraFin;
    private CalendarEditText txtCalendarInicio,txtCalendarFin;
    private EditText txtComentario;
    private TextView tvEmpleado;
    private DialogOptions dialogOptions;
    private Alertas alerta;
    private boolean isPlantilla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_permiso_v2);
        this.controllerAPI=new ControllerAPI(this);
        this.controllerAPI.setCallBackCatalogoPermisos(this);
        this.controllerAPI.getCatalogoPermisosAsync(new ServerRequest());
        this.spinnerTipoPermiso=(Spinner)findViewById(R.id.spinnerTipoPermiso);
        this.txtHoraInicio=(TimeText)findViewById(R.id.txtHoraInicio);
        this.txtHoraFin=(TimeText)findViewById(R.id.txtHoraFin);
        this.txtHoraInicio.setTipo(EnumAlarma.entrada);
        this.txtCalendarInicio=(CalendarEditText)findViewById(R.id.CalendarInicio);
        this.txtCalendarFin=(CalendarEditText)findViewById(R.id.CalendarFin);
        this.txtComentario=(EditText)findViewById(R.id.txtComentario);
        this.txtHoraFin.setTipo(EnumAlarma.salida);
        this.customProgressBar=new CustomProgressBar(this);
        this.customProgressBar.show(this);
        this.dialogOptions=new DialogOptions(this);
        this.alerta=new Alertas(this);
        this.spinnerCatalogoEmpleado=(Spinner)findViewById(R.id.spinnerCatalogoEmpleado);
        this.tvEmpleado=(TextView)findViewById(R.id.tvEmpleado);
        this.isPlantilla=getIntent().hasExtra(Constants.IS_PLANTILLA) && getIntent().getBooleanExtra(Constants.IS_PLANTILLA,false);
        if(this.isPlantilla) {
            ArrayList<SearchBoxItem> listEmpleados = (ArrayList<SearchBoxItem>) getIntent().getExtras().getSerializable(Constants.LIST_EMPLEADOS);
            adapterCatalogoEmpleado=new AdapterCatalogoEmpleado(this,R.layout.item_permiso_v2,listEmpleados);
            tvEmpleado.setVisibility(View.VISIBLE);
            spinnerCatalogoEmpleado.setAdapter(adapterCatalogoEmpleado);
            spinnerCatalogoEmpleado.setVisibility(View.VISIBLE);
        }
        setToolBar( getString(isPlantilla? R.string.permisos_de_mi_plantilla: R.string.mis_permisos));
    }

    @Override
    public void OnError(Throwable error) {
        this.customProgressBar.dismiss();
    }

    @Override
    public void OnSuccess(CatalogoPermisoRes response) {
        if(response.getTipo()!=null && (!response.getTipo().isEmpty())) {
            this.adapterCatalogoPermiso = new AdapterCatalogoPermiso(this,R.layout.item_permiso_v2,response.getTipo());
            this.spinnerTipoPermiso.setAdapter(adapterCatalogoPermiso);
        }
        this.customProgressBar.dismiss();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_justificar_incidencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_guardar:
                validarPeticion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validarPeticion(){



        if(txtCalendarInicio.isEmpty()){
            alerta.displayMensaje(getString(R.string.selecciona_fecha_inicio),this);
        }
        else if(txtCalendarFin.isEmpty())
        {
            alerta.displayMensaje(getString(R.string.selecciona_fecha_fin),this);
        }
        else{

            Calendar calendarInicio= Utils.combinarCalendars(this.txtCalendarInicio.getCalendar(),this.txtHoraInicio.getCalendar());
            Calendar calendarFin=Utils.combinarCalendars(this.txtCalendarFin.getCalendar(),this.txtHoraFin.getCalendar());

            if(txtCalendarFin.getCalendar().before(txtCalendarInicio.getCalendar())){
                alerta.displayMensaje(getString(R.string.fecha_fin_posterior),this);
            }
            else if(calendarFin.before(calendarInicio)){
                alerta.displayMensaje(getString(R.string.hora_fin_posterior),this);
            }

            else if(txtComentario.getText().toString().isEmpty()) {
                alerta.displayMensaje(this.getString(R.string.comentario_vacio),this);
            }


            else{
                solicitarPermisoAsync(calendarInicio,calendarFin);
            }


          /*
            else if(txtComentario.getText().toString().length()<30){
                alerta.displayMensaje(getString(R.string.minimo_caracters),this);
            }*/
        }

    }

   private void solicitarPermisoAsync(Calendar calendarInicio,Calendar calendarFin){
       CatalogoTipo selectedItem=(CatalogoTipo) spinnerTipoPermiso.getSelectedItem();
       SearchBoxItem selectedEmpleadoItem=isPlantilla? (SearchBoxItem)spinnerCatalogoEmpleado.getSelectedItem():null;
       SolicitarPermiso solicitarPermiso=new SolicitarPermiso();
       solicitarPermiso.setIdNumEmpleado(selectedEmpleadoItem!=null? selectedEmpleadoItem.getId(): Utils.getNumeroEmpleado(this));
       solicitarPermiso.setDt_fecha_hora_inicial(Utils.getJsonDate(calendarInicio.getTime()));
       solicitarPermiso.setDt_fecha_hora_final(Utils.getJsonDate(calendarFin.getTime()));
       solicitarPermiso.setTipo_exclusion(selectedItem.getValue());
       solicitarPermiso.setMotivo(txtComentario.getText().toString());
       solicitarPermiso.setTipo(isPlantilla? EnumConsulta.LineaDirecta.toString():EnumConsulta.Mias.toString());

       controllerAPI.solicitarPermiso(solicitarPermiso).enqueue(new Callback<ServerResponse>() {
           @Override
           public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
               if (response.isSuccessful()) {
                   if (!response.body().getError()) {
                       dialogOptions.showExito(isPlantilla);
                       dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                           @Override
                           public void OnDismiss(boolean accepted) {
                               if (accepted) {

                                   Intent intent=new Intent(AgregarPermisoV2.this,
                                    PermisosTabActivity.class);
                                   finish();
                                   startActivity(intent);
                               }
                           }
                       });

                   }
                   else{
                        alerta.displayMensaje(response.body().getMensajeError()!=null?
                        response.body().getMensajeError():getString(R.string.Error_Conexion),AgregarPermisoV2.this);
                   }

               }
           }
           @Override
           public void onFailure(Call<ServerResponse> call, Throwable t) {
               alerta.displayMensaje(getString(R.string.Error_Conexion),AgregarPermisoV2.this);

           }
       });
   }

}
