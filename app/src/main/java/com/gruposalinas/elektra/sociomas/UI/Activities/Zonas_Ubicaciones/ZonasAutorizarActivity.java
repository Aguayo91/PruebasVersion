package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.InputBox;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AceptarUbicacionRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.AceptarZonaRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 04/07/2017.
 */

public class ZonasAutorizarActivity extends BaseZonaActivity implements  AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener,InputBox.inputBoxCallBack, Callback<ServerResponse> {
    private Spinner spinnerEmpleado;
    private InputBox inputBox;
    private LugarTrabajo selectedPosicion;
    private DialogOptions dialogOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_zonas_u);
        this.listViewZona = (StickyListHeadersListView) findViewById(R.id.listViewZonas);
        this.listViewZona.setOnItemClickListener(this);
        this.spinnerEmpleado = (Spinner) findViewById(R.id.spinnerCatalogoEmpleado);
        this.setToolBar(getString(R.string.solicitudes_pendientes_de_autorizaci_n));
        if (getIntent().hasExtra(Constants.LIST_EMPLEADOS)) {
            this.listEmpleados = (ArrayList<SearchBoxItem>) getIntent().getSerializableExtra(Constants.LIST_EMPLEADOS);
            AdapterCatalogoEmpleado adapterCatalogoEmpleado = new AdapterCatalogoEmpleado(this, R.layout.spinner_opciones_plantilla, listEmpleados);
            adapterCatalogoEmpleado.setTituloDialogo("Pendientes de autorización");
            spinnerEmpleado.setVisibility(View.VISIBLE);
            spinnerEmpleado.setAdapter(adapterCatalogoEmpleado);
            spinnerEmpleado.setSelection(getIntent().getIntExtra(Constants.SELECTED_POSITION_EMPLEADO, 1));
            spinnerEmpleado.setOnItemSelectedListener(this);
        }
        this.initInputBox();
    }
    private void initInputBox(){
        this.inputBox = new InputBox(this);
        this.inputBox.setTitle(getString(R.string.autorizar_rechazar_horario));
        this.inputBox.setHint(getString(R.string.escribe_comentario));
        this.inputBox.setPositiveButtonText(getString(R.string.autorizar));
        this.inputBox.setNegativeButtonText(getString(R.string.rechazar));
        this.inputBox.setShowNeutral(true);
        this.inputBox.setErrorText(getString(R.string.comentario_vacio));
        this.inputBox.setCallBack(this);
        this.dialogOptions=new DialogOptions(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        SearchBoxItem selectedEmpleado=(SearchBoxItem)spinnerEmpleado.getSelectedItem();
        this.currentNumeroEmpleado=selectedEmpleado.getId();
        getUbicacionesAsync(currentNumeroEmpleado,false,true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.selectedPosicion=adapterZonasUbicacionesAutorizar.getItem(position);
        this.inputBox.showAsync();
    }

    @Override
    public void OnResult(String result) {
        this.autorizarRechazarPosicion(result,true);
    }

    @Override
    public void OnCancel() {
        this.autorizarRechazarPosicion(Constants.RECHAZAR_ZONA_UBICACION,false);
    }

    private void autorizarRechazarPosicion(String comentario,boolean autorizar){
        customProgressBar.show(this);
        if(adapterZonasUbicacionesAutorizar.isUbicacion(selectedPosicion)) {
            AceptarUbicacionRequest request=new AceptarUbicacionRequest();
            request.setIdNumEmpleado(this.currentNumeroEmpleado);
            request.setVaNumeroPos(this.selectedPosicion.getVaNumeroPos());
            request.setVaNombrePos(this.selectedPosicion.getVaNombrePos());
            request.setMotivo(comentario);
            request.setTypeMov(autorizar?"a":"r");
            controllerAPI.aceptarRechazarUbicacion(request).enqueue(this);
        }
        else{
            AceptarZonaRequest request=new AceptarZonaRequest();
            request.setIdNumEmpleado(this.currentNumeroEmpleado);
            request.setIdCscZoPos(this.selectedPosicion.getIdCscZoPos());
            request.setVaComentario(comentario);
            request.setStatus(autorizar?"C":"R");
            controllerAPI.aceptarRechazarZona(request).enqueue(this);

        }
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if(response.isSuccessful()){
            if(!response.body().getError()){
                dialogOptions.showExito(getString(R.string.autorizado_rechazado_propuesta),((SearchBoxItem)spinnerEmpleado.getSelectedItem()).getValue());
                dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                    @Override
                    public void OnDismiss(boolean accepted) {
                        if(accepted){
                            getUbicacionesAsync(currentNumeroEmpleado,false,true);
                        }
                    }
                });
            }
            else{
                alertaAsync.displayMensaje(response.body().getMensajeError(),this);
            }
            customProgressBar.dismiss();
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        alertaAsync.displayMensaje(getString(R.string.Error_Conexion),this);
        customProgressBar.dismiss();
    }
}
