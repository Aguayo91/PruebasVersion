package com.gruposalinas.elektra.sociomas.UI.Activities.Horarios;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Catalogos.AdapterCatalogoEmpleado;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Horarios.AdapterHorariosV2;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.UI.Controls.PendientesView;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.SnackBarBuilder;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.UI.Controls.Spinner.SpinnerPlantilla;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Enums.EnumStatusHorario;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Dia;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;
import com.sociomas.core.WebService.Model.Response.Horario.ResponseHorario;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 16/06/2017.
 */


public class BaseHorariosActivity extends BaseAppCompactActivity implements AdapterView.OnItemClickListener, DialogOptions.CallBackDialgoOptions {
    protected ArrayList<Horario> listPropuestas = new ArrayList<>();
    protected ArrayList<Horario> listHorarios = new ArrayList<>();
    protected Horario selectedDiaPropuesta;
    protected ArrayList<SearchBoxItem> listEmpleados = new ArrayList<>();
    protected HashMap<String, String> listKeys = new HashMap<>();
    protected ListView listViewHorarios;
    protected AdapterHorariosV2 adapterHorarios;
    protected SnackBarBuilder snackBarBuilder;
    protected SpinnerPlantilla spinnerPlantilla;
    protected Spinner spinnerEmpleado;
    protected EnumConsulta enumConsulta;
    protected DialogOptions dialogOptions;
    protected PendientesView pendientesView;



    protected ArrayList<Dia> getDias() {
        ArrayList<Dia> listDias = new ArrayList<>();
        listDias.add(new Dia(0, "Domingo"));
        listDias.add(new Dia(1, "Lunes"));
        listDias.add(new Dia(2, "Martes"));
        listDias.add(new Dia(3, "Miércoles"));
        listDias.add(new Dia(4, "Jueves"));
        listDias.add(new Dia(5, "Viernes"));
        listDias.add(new Dia(6, "Sábado"));
        return listDias;
    }

    protected boolean hasPropuesta(int dia) {
        boolean propuesta = false;
        for (Horario item : listPropuestas) {
            if (item.getTiDiaSemana() == dia) {
                propuesta = true;
                break;
            }
        }
        return propuesta;
    }

    protected Horario getDiaHorario(int dia) {
        Horario selectedDia = null;
        for (Horario item : listPropuestas) {
            if (item.getTiDiaSemana() == dia) {
                selectedDia = item;
                break;
            }
        }
        return selectedDia;
    }

    protected void getHorariosAsync(final EnumConsulta consulta) {
        this.snackBarBuilder = new SnackBarBuilder(this);
        this.dialogOptions=new DialogOptions(this);
        this.customProgressBar.show(this);
        this.enumConsulta = consulta;
        ControllerAPI controllerAPI = new ControllerAPI(this);
        controllerAPI.getListadoHorario(new ServerRequest()).enqueue(new Callback<ResponseHorario>() {
            @Override
            public void onResponse(Call<ResponseHorario> call, Response<ResponseHorario> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        listHorarios = filtrarLista(consulta, consulta == EnumConsulta.Mias ? response.body().getHorario() :
                                response.body().getHorarioPlantilla());
                        setAdapterListView(listHorarios, true,false);
                    } else {
                        alertaAsync.displayMensaje(response.body().getMensajeError(), BaseHorariosActivity.this);
                    }
                }
                customProgressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseHorario> call, Throwable t) {
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion), BaseHorariosActivity.this);
                customProgressBar.dismiss();
            }
        });

    }

    protected void setAdapterListView(ArrayList<Horario> listHorarios, boolean updateAdapter, boolean pendientes) {
        adapterHorarios = new AdapterHorariosV2(BaseHorariosActivity.this, listHorarios);
        adapterHorarios.setPantallPendientes(pendientes);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapterHorarios);
        alphaInAnimationAdapter.setAbsListView(listViewHorarios);
        listViewHorarios.setAdapter(alphaInAnimationAdapter);

           /*CASO DE QUE TIENE PLANTILLA SE LLENA CON EL ADAPTER*/
        if (spinnerPlantilla != null && updateAdapter) {
            spinnerPlantilla.initSpinnerPlantilla("Horarios",null,false);
        }

        //HAY PROPUESTAS ENTONCES TE MUESTRA EL  MENSAJE DE PROPUESTA
        if (listPropuestas != null && (!listPropuestas.isEmpty()) && updateAdapter && enumConsulta == EnumConsulta.Mias) {
            snackBarBuilder.showAlertNotificacion(getString(R.string.solicitudes_pendientes_de_autorizaci_n), Constants.SEGUNDO*3);
        }
    }

    protected ArrayList<Horario> searchByNumeroEmpleado(String numeroEmpleado) {
        ArrayList<Horario> listHorarioEmpleado = new ArrayList<>();
        ArrayList<Dia> listDias = getDias();
        for (Horario item : this.listHorarios) {
            if (item.getIdNumEmpleado() != null && (item.getIdNumEmpleado().equals(numeroEmpleado))) {
                Dia selectedDia = listDias.get(item.getTiDiaSemana());
                selectedDia.setAsignado(true);
                listHorarioEmpleado.add(item);
            }
        }
         /*DÍAS NO ASIGNADOS*/
        Collections.reverse(listDias); //RECORDARNDO QUE EL DÍA DOMINGO ES LA POSICIÓN 0
        for (Dia itemDia : listDias) {
            if (!itemDia.isAsignado()) {
                Horario horario = new Horario(itemDia.getId(), itemDia.getNombre(), "NO_ASGINADO");
                horario.setIdNumEmpleado(numeroEmpleado);
                listHorarioEmpleado.add(horario);
            }
        }
        boolean hasPropuesta = false;
        /*VERIFICAMOS SI EXISTE EN LA LISTA DE PROPUESTAS*/
        for (Horario item : this.listPropuestas) {
            if (item.getIdNumEmpleado().equals(numeroEmpleado)) {
                hasPropuesta = true;
                break;
            }
        }

        if (hasPropuesta) {
            snackBarBuilder.showAlertNotificacion(getString(R.string.solicitudes_pendientes_de_autorizaci_n),Constants.SEGUNDO*5, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(Constants.LIST_PROPUESTAS, listPropuestas);
                    mBundle.putSerializable(Constants.LIST_EMPLEADOS, listEmpleados);
                    mBundle.putInt(Constants.SELECTED_POSITION_EMPLEADO, spinnerPlantilla.getSelectedItemAtPosition());
                    Intent intent = new Intent(BaseHorariosActivity.this, HorariosAutorizarActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                }
            });
        }

        return listHorarioEmpleado;
    }

    protected ArrayList<Horario> filtrarLista(EnumConsulta enumConsulta, ArrayList<Horario> listHorario) {
        ArrayList<Horario> listValidos = new ArrayList<>();
        ArrayList<Dia> listDias = getDias();
        if (listHorario != null) {
                /*OBTIENE LOS VALIDOS*/
            for (Horario item : listHorario) {
                if (!listKeys.containsKey(item.getVaNombreCompleto())) {
                    item.setIdNumEmpleado(DecryptUtils.decryptAES(item.getIdNumEmpleado()));
                    listKeys.put(item.getVaNombreCompleto(), item.getIdNumEmpleado());
                } else {
                    item.setIdNumEmpleado(listKeys.get(item.getVaNombreCompleto()));
                }
                   /*ES VALIDO*/
                if (item.getBitValido() || EnumStatusHorario.fromString(item.getEstatus()) == EnumStatusHorario.DIA_LIBRE) {
                    Dia selectedDia = listDias.get(item.getTiDiaSemana());
                    selectedDia.setAsignado(true);
                    item.setNombreDia(selectedDia.getNombre());
                    listValidos.add(item);
                }
                    /*LISTA DE PROPUESTAS*/
                else {
                    listPropuestas.add(item);
                }
            }
        }
        /*DÍAS NO ASIGNADOS*/
        for (Dia itemDia : listDias) {
            if (!itemDia.isAsignado()) {
                listValidos.add(new Horario(itemDia.getId(), itemDia.getNombre(), "NO_ASGINADO"));
            }
        }

        /*OBTENEMOS LA LISTA DE EMPLEADOS*/
        for (Map.Entry<String, String> item : listKeys.entrySet()) {
            listEmpleados.add(new SearchBoxItem(item.getValue(), item.getKey()));
        }
        /*CONSULTANDO DESDE PLANTILLA*/
        if(enumConsulta==EnumConsulta.LineaDirecta && listEmpleados.isEmpty()){
            Toast.makeText(this,getString(R.string.no_plantilla_horarios),Toast.LENGTH_LONG).show();
            finish();
        }
        return listValidos;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Horario item = adapterHorarios.getItem(position);
        if (hasPropuesta(item.getTiDiaSemana()) && this.enumConsulta == EnumConsulta.Mias) {
            this.selectedDiaPropuesta = getDiaHorario(item.getTiDiaSemana());
            if (selectedDiaPropuesta != null) {
                Spanned texto;
                if(selectedDiaPropuesta.getTmHoraEntrada().equals(selectedDiaPropuesta.getTmHoraSalida())) {
                    texto=Html.fromHtml(getString(R.string.dialogo_horario_cancelar_dialibre));
                }
                else {
                    texto = Html.fromHtml(getString(R.string.dialogo_horario_cancelar, selectedDiaPropuesta.getTmHoraEntrada(), selectedDiaPropuesta.getTmHoraSalida()));
                }
                dialogOptions.show(this.getString(R.string.horario_titulo),texto);
                dialogOptions.setCallBackDialgoOptions(this);

            }
        }
        else {
            Intent intent = new Intent(this, HorarioPendienteActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(Constants.SELECTED_HORARIO_ITEM, item);
            mBundle.putString(Constants.SELECTED_CONSULTA, this.enumConsulta.toString());
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }



    @Override
    public void OnDismiss(boolean accepted) {
        //EL USUARIO ACEPTÓ CANCELAR SOLICITUD DE HORARIO
        if(accepted){
            cancelarPeticionHorarioAsync(selectedDiaPropuesta);
        }
    }
    private void cancelarPeticionHorarioAsync(final Horario selectedHorario){
        customProgressBar.show(this);
        final EditarHorarioRequest request=new EditarHorarioRequest();
        request.setIdNumEmpleado(selectedHorario.getIdNumEmpleado()!=null? selectedHorario.getIdNumEmpleado(): Utils.getNumeroEmpleado(this));
        request.setNvHoraEntrada(selectedHorario.getTmHoraEntrada());
        request.setNvHoraSalida(selectedHorario.getTmHoraSalida());
        request.setDiaSemana(selectedHorario.getTiDiaSemana());
        request.setTipo(this.enumConsulta.toString());
        request.setLibre(0);
        request.setCancelar(1);
        request.setRechazar(0);
        request.setComentario("");
        controllerAPI.editarEliminarHorario(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        dialogOptions.showExito("Se ha cancelado su solicitud de horario correctamente");
                        getHorariosAsync(EnumConsulta.Mias);
                    }
                    else{
                        alertaAsync.displayMensaje(response.body().getMensajeError(),BaseHorariosActivity.this);
                    }
                }
                customProgressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                customProgressBar.dismiss();
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion),BaseHorariosActivity.this);
            }
        });
    }

}
