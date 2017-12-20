package com.gruposalinas.elektra.sociomas.UI.Activities.Horarios;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts.InputBox;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;

import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Dia;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

import de.mateware.snacky.Snacky;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorariosAutorizarActivity extends HorarioPendienteActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener, InputBox.inputBoxCallBack {
    private  Horario selectedHorario;
    private  ArrayList<Horario>listPropuestas;
    private  ArrayList<Horario>listadoFiltro;
    private  InputBox inputBox;
    private  Alertas alerta;
    private  ControllerAPI controllerAPI;
    private  CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios_pendientes);
        this.listViewHorarios=(ListView)findViewById(R.id.listvieHorarios);
        this.listViewHorarios.setOnItemClickListener(this);
        this.spinnerPlantilla=(SpinnerPlantilla) findViewById(R.id.spinnerPlantilla);
        this.snackBarBuilder=new SnackBarBuilder(this);
        this.inputBox=new InputBox(this);
        this.inputBox.setTitle(getString(R.string.autorizar_rechazar_horario));
        this.inputBox.setHint(getString(R.string.escribe_comentario));
        this.inputBox.setPositiveButtonText(getString(R.string.autorizar));
        this.inputBox.setNegativeButtonText(getString(R.string.rechazar));
        this.inputBox.setErrorText(getString(R.string.comentario_vacio));
        this.inputBox.setShowNeutral(true);
        this.inputBox.setCallBack(this);
        this.alerta=new Alertas(this);
        this.controllerAPI=new ControllerAPI(this);
        this.customProgressBar=new CustomProgressBar(this);
        this.setToolBar(getString(R.string.solicitudes_pendientes_de_autorizaci_n));
        if(getIntent().hasExtra(Constants.LIST_PROPUESTAS) && getIntent().hasExtra(Constants.LIST_EMPLEADOS))
        {
            listPropuestas=(ArrayList<Horario>)getIntent().getSerializableExtra(Constants.LIST_PROPUESTAS);
            spinnerPlantilla.initSpinnerPlantilla(getString(R.string.horarios),null,false);
            spinnerPlantilla.setSelection(getIntent().getIntExtra(Constants.SELECTED_POSITION_EMPLEADO,1));
            spinnerPlantilla.setOnItemSelectedListener(this);
        }
    }

    private ArrayList<Horario> filtrarListaPropuesta(String numeroEmpleado){
        ArrayList<Dia>listDias=this.getDias();
        ArrayList<Horario>listHorario=new ArrayList<>();
        for(Horario item:listPropuestas){
            if(item.getIdNumEmpleado().equals(numeroEmpleado)){
                Dia selectedDia = listDias.get(item.getTiDiaSemana());
                selectedDia.setAsignado(true);
                item.setNombreDia(selectedDia.getNombre());
                listHorario.add(item);
            }
        }
        return listHorario;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Plantilla selectedItem=spinnerPlantilla.getSelectedItem();
        if(selectedItem!=null) {
            this.listadoFiltro=filtrarListaPropuesta(selectedItem.getIdEmpleado());
            if(listadoFiltro==null ||  (listadoFiltro.isEmpty())) {
                snackBarBuilder.showPrimaryColor(HorariosAutorizarActivity.this,
                        getString(R.string.no_horarios_pendientes), Snacky.LENGTH_SHORT);
            }
            //SE LLENA EL LISTVIEW CON LA LISTA
            else {
                setAdapterListView(listadoFiltro, false, true);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.selectedHorario=adapterHorarios.getItem(position);
        this.inputBox.showAsync();
    }

    @Override
    public void OnResult(String result) {
        if(!result.isEmpty()){
            autorizarRechazarAsync(selectedHorario,true);
        }
    }
    @Override
    public void OnCancel() {
        autorizarRechazarAsync(selectedHorario,false);

    }



    /*TODO OPTIMIZAR TODA ESTÁ PARTE*/
    private void autorizarRechazarAsync(final Horario selectedHorario, final boolean autorizar){
        this.customProgressBar.show(this);
        boolean diaLibre=selectedHorario.getCambio().equals("Día libre");
        final EditarHorarioRequest request=new EditarHorarioRequest();
        request.setIdNumEmpleado(selectedHorario.getIdNumEmpleado());
        request.setComentario(autorizar?Constants.APROBAR_HORARIO:Constants.RECHAZAR_HORARIO);
        request.setEdicion(autorizar?Constants.APROBAR_HORARIO:Constants.RECHAZAR_HORARIO);
        request.setNvHoraEntrada(selectedHorario.getTmHoraEntrada());
        request.setNvHoraSalida(selectedHorario.getTmHoraSalida());
        request.setDiaSemana(selectedHorario.getTiDiaSemana());
        request.setTipo(EnumConsulta.LineaDirecta.toString());
        request.setRechazar(autorizar?0:1);
        request.setLibre(diaLibre?1:0);
        request.setCancelar(0);
        controllerAPI.editarEliminarHorario(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        adapterHorarios.deleteIndex(listadoFiltro.indexOf(selectedHorario));
                        listPropuestas.remove(listPropuestas.indexOf(selectedHorario));
                        snackBarBuilder.showSuccess(getString(autorizar? R.string.solicitud_autorizada_correctamente: R.string.solicitud_rechazada_correctamente));
                    }
                    else{
                        alerta.displayMensaje(response.body().getMensajeError(),HorariosAutorizarActivity.this);
                    }
                }
                customProgressBar.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                alerta.displayMensaje(getString(R.string.Error_Conexion),HorariosAutorizarActivity.this);
                customProgressBar.dismiss();
            }
        });

    }



}
