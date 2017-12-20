package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import android.content.Context;
import android.database.Observable;
import android.util.Log;
import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.HorariosTrabajoPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.UI.Controls.Model.DrawerItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;
import com.sociomas.core.WebService.Model.Request.Horario.ListaEditarHorarioRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;
import com.sociomas.core.WebService.Model.Response.Horario.ResponseHorario;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.TipoHorarioEmpleadoRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 17/11/17.
 */

public class HorariosTrabajoPresenterImpl extends BasePresenterImpl implements HorariosTrabajoPresenter {

    private static final String TAG = HorariosTrabajoPresenterImpl.class.getSimpleName();

    @Override
    public void notificaHorarioVariable(final Context context, int tipoHorario) {
        sendShowProgress();
        SimpleDateFormat dtFmt=new SimpleDateFormat(Constants.DATE_FORMAT_AVENTON);
        SecurityItems secItm = new SecurityItems(Utils.getNumeroEmpleado(context));
        TipoHorarioEmpleadoRequest request = new TipoHorarioEmpleadoRequest();
        request.setIdNumEmpleado(secItm.getIdEmployEncrypt());
        request.setToken(secItm.getTokenEncrypt());
        request.setId_tipo_horario(tipoHorario);
        request.setFechaDispositivo(dtFmt.format(new Date()).concat(".200")); //IMPORTANTE SI NO CONCATENAS ESTE NÚMERO EL SERVICIO NO FUNCIONA
        getControllerAPI().modificarTipoHorarioEmpleado(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.e(TAG, response.toString());
                try {
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.switchviewIOS);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
                    notifyData(event);
                } catch (Exception e) {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                    notifyData(eventErr);
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.switchviewIOS);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                    notifyData(event);
                }
                sendHideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                notifyData(eventErr);
                sendHideProgress();
            }
        });

    }

    @Override
    public void ModificarListaHorarioEmpleado(final Context context, Map<String, EditarHorarioRequest> mapHorario1, String horaEntrada, String horaSalida) {
        sendShowProgress();
        SecurityItems si = new SecurityItems(Utils.getNumeroEmpleado(context));
        ListaEditarHorarioRequest request = new ListaEditarHorarioRequest();
        request.setIdNumEmpleado(si.getIdEmployEncrypt());
        request.setToken(si.getTokenEncrypt());
        List<EditarHorarioRequest> listaHorarioEmpleado = new ArrayList<>();
        for (String key : mapHorario1.keySet()) {
            listaHorarioEmpleado.add(mapHorario1.get(key).withHorarario(horaEntrada, horaSalida));
        }
        request.setListaHorarioEmpleado(listaHorarioEmpleado);
        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.v(TAG, json);
        getControllerAPI().actualizarHorarioEmpleado(request).enqueue(actualizarHorarioRequest);

    }

    Callback<ServerResponse> actualizarHorarioRequest = new Callback<ServerResponse>() {
        @Override
        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            try {

                if(response.isSuccessful()){
                    Log.v(TAG, response.body().toString());
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, response.body().getError());
                    event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                    notifyData(event);
                }
                else if(!response.isSuccessful()){
                    Log.v(TAG, response.body().toString());
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, response.body().getError());
                    event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                    notifyData(event);
                }
                 else if (response.body() != null) {
                    Log.v(TAG, response.body().toString());
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, response.body().getError());
                    event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                    notifyData(event);
                } else {
                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                    event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                    notifyData(event);
                }
            } catch (Exception e) {
                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                notifyData(event);
            }
            sendHideProgress();
        }

        @Override
        public void onFailure(Call<ServerResponse> call, Throwable t) {
            sendHideProgress();
            Log.e(TAG, t.toString());
            ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
            event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnContinuar);
            event.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
            event.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
        }
    };

    @Override
    public void ModificarListaHorarioEmpleado(Context context, Map<String, EditarHorarioRequest> mapHorario1, String horaentrada, String horaSalida, Map<String, EditarHorarioRequest> mapHorario2, String horaentrada2, String horaSalida2) {
        sendShowProgress();
        SecurityItems si = new SecurityItems(Utils.getNumeroEmpleado(context));
        ListaEditarHorarioRequest request = new ListaEditarHorarioRequest();
        request.setIdNumEmpleado(si.getIdEmployEncrypt());
        request.setToken(si.getTokenEncrypt());
        List<EditarHorarioRequest> listaHorarioEmpleado = new ArrayList<>();
        for (String key : mapHorario1.keySet()) {
            listaHorarioEmpleado.add(mapHorario1.get(key).withHorarario(horaentrada, horaSalida));
        }
        for (String key2 : mapHorario2.keySet()) {
            listaHorarioEmpleado.add(mapHorario2.get(key2).withHorarario(horaentrada2, horaSalida2));
        }
        request.setListaHorarioEmpleado(listaHorarioEmpleado);
        Gson gson = new Gson();
        String json = gson.toJson(request);
        Log.v(TAG, json);
        getControllerAPI().actualizarHorarioEmpleado(request).enqueue(actualizarHorarioRequest);
    }

    @Override
    public void cargarHorariosEmpleado(final Context context) {
        sendShowProgress();
        SecurityItems items = new SecurityItems(Utils.getNumeroEmpleado(context));
        ServerRequest request = new ServerRequest();
        request.setIdNumEmpleado(items.getIdEmployEncrypt());
        request.setToken(items.getTokenEncrypt());
        getControllerAPI().getListadoHorario(request).enqueue(new Callback<ResponseHorario>() {
            @Override
            public void onResponse(Call<ResponseHorario> call, Response<ResponseHorario> response) {
                try {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            Log.v(TAG, response.body().toString());
                            final ResponseHorario responseHorario = response.body();
                            io.reactivex.Observable.fromIterable(responseHorario.getHorario()).filter(new Predicate<Horario>() {
                                @Override
                                public boolean test(Horario horario) throws Exception {
                                    return horario.getBitValido();
                                }
                            }).toList().subscribe(new Consumer<List<Horario>>() {
                                @Override
                                public void accept(List<Horario> horarios) throws Exception {
                                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
                                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.ctrPicker);
                                    event.getModel().put(ViewEvent.ENTRIES_LIST, horarios);
                                    notifyData(event);
                                    sendHideProgress();
                                }
                            });

                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                            eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ctrPicker);
                            notifyData(eventErr);
                            sendHideProgress();
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ctrPicker);
                        notifyData(eventErr);
                        sendHideProgress();
                    }
                } catch (Exception e) {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la petición, intenta mas tarde.");
                    eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ctrPicker);
                    notifyData(eventErr);
                    sendHideProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseHorario> call, Throwable t) {
                Log.v(TAG, t.toString());
                sendHideProgress();
                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                try {
                    eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                } catch (Exception e) {
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ocurrio un error al realizar la peticion, intenta mas tarde.");
                }
                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ctrPicker);
                notifyData(eventErr);
            }
        });
    }


    private void sendShowProgress() {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
    }

    private void sendHideProgress() {
        ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
        notifyData(eventHide);
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}