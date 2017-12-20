package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.server.http.HttpStatus;
import com.google.gson.annotations.SerializedName;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.UnidadNegocioSeleccionPresenter;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.UnidadNegocio;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.SlideInicio.ModificaUnidadNegocioRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.ListaUnidadesNegocioResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;

import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 14/11/17.
 */

public class UnidadNegocioSeleccionPresenterImpl extends BasePresenterImpl implements UnidadNegocioSeleccionPresenter {

    public static final String TAG = UnidadNegocioPresenterImpl.class.getSimpleName();

    @Override
    public void loadUnidadesNegocio(final Context context) {
        sendShowProgressEvent();
        ServerRequest request = new ServerRequest();
        SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        getControllerAPI().obtenerUnidadesNegocio(request).enqueue(new Callback<ListaUnidadesNegocioResponse>() {
            @Override
            public void onResponse(Call<ListaUnidadesNegocioResponse> call, Response<ListaUnidadesNegocioResponse> response) {

                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body().toString());
                        if (!response.body().getError()) {
                            List<UnidadNegocioResponse> list = response.body().getListEmpresas();
                            if (list != null) {
                                ViewEvent eventList = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                eventList.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                                eventList.getModel().put(ViewEvent.ENTRIES_LIST, list);
                                notifyData(eventList);
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivReload);
                                eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                                notifyData(eventErr);
                            }
                        } else {
                            Log.e(TAG, response.body().getMensajeError());
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivReload);
                            eventErr.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivReload);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        notifyData(eventErr);
                    }
                    sendHideProgressEvent();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivReload);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                    sendHideProgressEvent();
                }
            }

            @Override
            public void onFailure(Call<ListaUnidadesNegocioResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                sendHideProgressEvent();
                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivReload);
            }
        });
    }

    @Override
    public void guardarUnidadNegocioEmpleado(final Context context, final UnidadNegocioResponse unr) {
        sendShowProgressEvent();
        ModificaUnidadNegocioRequest request = new ModificaUnidadNegocioRequest();
        SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setClave(unr.getClave());
        getControllerAPI().actualizaEmpresaEmpleado(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.v(TAG, response.body().toString());
                try {
                    if (response.code() == HttpStatus.HTTP_OK) {
                        if (response.body() != null) {
                            //TODO implement success update
                            if (!response.body().getError()) {
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvTitleMensaje);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                event.getModel().put(ViewEvent.ENTRY, unr);
                                notifyData(event);

                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                                eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                                notifyData(eventErr);
                                sendHideProgressEvent();
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                            eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                            notifyData(eventErr);
                            sendHideProgressEvent();
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        notifyData(eventErr);
                        sendHideProgressEvent();
                    }
                } catch (Exception e){
                    Log.v(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                    sendHideProgressEvent();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v(TAG, t.toString());
                sendHideProgressEvent();
                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.gridUnidadesNegocio);
                eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                notifyData(eventErr);
            }
        });
    }

    /**
     * Manda un evento para mostrar un progress dialog
     */
    private void sendShowProgressEvent() {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
    }

    /**
     * Manda un evento para ocultar el progress dialog
     */
    @Override
    public void sendHideProgressEvent() {
        ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
        notifyData(eventHide);
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
