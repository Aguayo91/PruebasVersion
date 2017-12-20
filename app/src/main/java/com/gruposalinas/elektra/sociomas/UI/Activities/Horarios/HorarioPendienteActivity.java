package com.gruposalinas.elektra.sociomas.UI.Activities.Horarios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.TimeText;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAlarma;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioPendienteActivity extends BaseHorariosActivity implements View.OnClickListener, DialogOptions.CallBackDialgoOptions {
    private TimeText txtHoraEntrada;
    private TimeText txtHoraSalida;
    private Button btnGuardar;
    private TextView tvDia;
    private Horario selectedHorario;
    private DialogOptions dialogOptions;
    private CheckBox checkedDiaLibre;
    private CustomProgressBar customProgressBar;
    private boolean isReady =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_pendiente);
        this.customProgressBar=new CustomProgressBar(this);
        this.txtHoraEntrada=(TimeText)findViewById(R.id.txtHoraEntrada);
        this.txtHoraSalida=(TimeText)findViewById(R.id.txtHoraSalida);
        this.btnGuardar=(Button)findViewById(R.id.btnGuardar);
        this.checkedDiaLibre=(CheckBox)findViewById(R.id.checkedDiaLibre);
        this.tvDia=(TextView)findViewById(R.id.tvDia);
        this.dialogOptions=new DialogOptions(this);
        this.txtHoraEntrada.setTipo(EnumAlarma.entrada);
        this.txtHoraSalida.setTipo(EnumAlarma.salida);


        if(getIntent().hasExtra(Constants.SELECTED_HORARIO_ITEM)) {
            this.selectedHorario=(Horario)getIntent().getSerializableExtra(Constants.SELECTED_HORARIO_ITEM);
            this.enumConsulta= EnumConsulta.fromString(getIntent().getStringExtra(Constants.SELECTED_CONSULTA));
            this.tvDia.setText("DÍA SELECCIONADO: "+selectedHorario.getNombreDia());
        }

        this.setToolBar(getString(R.string.solicitud_cambio));
        this.btnGuardar.setOnClickListener(this);
        this.checkedDiaLibre.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnGuardar && isReady) {
            generarPeticionHorarioAsync(checkedDiaLibre.isChecked());
        }
        else if(view.getId()==R.id.checkedDiaLibre) {
            enabledDisabled(checkedDiaLibre.isChecked());
        }
    }

    private void enabledDisabled(boolean enabled){
        this.txtHoraSalida.setEnabled(!enabled);
        this.txtHoraEntrada.setEnabled(!enabled);
    }

    private void generarPeticionHorarioAsync(boolean diaLibre){
        customProgressBar.show(this);
        final EditarHorarioRequest request=new EditarHorarioRequest();
        request.setIdNumEmpleado(selectedHorario.getIdNumEmpleado()!=null? selectedHorario.getIdNumEmpleado(): Utils.getNumeroEmpleado(this));
        request.setNvHoraEntrada(txtHoraEntrada.getText().toString());
        request.setNvHoraSalida(txtHoraSalida.getText().toString());
        request.setDiaSemana(selectedHorario.getTiDiaSemana());
        request.setTipo(this.enumConsulta.toString());
        if(diaLibre) {
            request.setLibre(1);
            request.setComentario(Constants.SOLICITUD_DIA_DESCANSO);
            request.setEdicion(Constants.ELIMINAR_HORARIO_EXTRA);}
        else{
            request.setLibre(0);
            request.setCancelar(0);
            request.setRechazar(0);
            request.setComentario("");
        }
        ControllerAPI controllerAPI=new ControllerAPI(this);
        controllerAPI.editarEliminarHorario(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                   if(!response.body().getError()) {
                       dialogOptions.showExito();
                       dialogOptions.setCallBackDialgoOptions(HorarioPendienteActivity.this);
                   }
                   else{
                       alertaAsync.displayMensaje(response.body().getMensajeError(),HorarioPendienteActivity.this);
                   }
                }
                isReady=true;
                customProgressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                customProgressBar.dismiss();
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion),HorarioPendienteActivity.this);
            }
        });
    }


    @Override
    public void OnDismiss(boolean accepted) {
        startActivity(new Intent(HorarioPendienteActivity.this,
            enumConsulta==EnumConsulta.Mias? MisHorariosActivity.class:HorariosPlantillaActivity.class));
        finish();
    }
}
