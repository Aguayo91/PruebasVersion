package com.gruposalinas.elektra.sociomas.UI.Presenters.impl;

import android.content.Context;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Presenters.BuzonPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Registro.ModificarNotificacionRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Notificaciones.ListaNotificacionesResponse;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class BuzonPresenterImpl extends BasePresenterImpl implements BuzonPresenter {

    public static final String TAG = BuzonPresenterImpl.class.getSimpleName();

    @Override
    public void consultarNotificacionesUsuario(final Context context) {
        onShowProgress();
        getControllerAPI().consultarNotificacionesGeneral(new ServerRequest()).enqueue(new Callback<ListaNotificacionesResponse>() {
            @Override
            public void onResponse(Call<ListaNotificacionesResponse> call, Response<ListaNotificacionesResponse> response) {
                try {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            ListaNotificacionesResponse list = response.body();
                            if (list.getNotificaciones() != null) {
                                if(!list.getNotificaciones().isEmpty()) {
                                    Collections.reverse(list.getNotificaciones());
                                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
                                    event.getModel().put(ViewEvent.ENTRIES_LIST, list.getNotificaciones());
                                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.rvBuzon);
                                    notifyData(event);
                                }
                                else{
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_LAYOUT_ELEMENT);
                                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                    notifyData(eventErr);
                                }
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_LAYOUT_ELEMENT);
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                notifyData(eventErr);
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        notifyData(eventErr);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ListaNotificacionesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                try {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                    notifyData(eventErr);

                    ViewEvent eventL = new ViewEvent(ViewEventType.SHOW_LAYOUT_ELEMENT);
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    notifyData(eventL);
                }

                catch (Exception e) {
                    Log.e(TAG, e.toString());

                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);

                    ViewEvent eventL = new ViewEvent(ViewEventType.SHOW_LAYOUT_ELEMENT);
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    notifyData(eventL);

                }
                onHideProgress();
            }
        });
    }

    @Override
    public void borrarNotificacion(final Context context, NotificacionResponse notificacionResponse) {
        onShowProgress();
        ModificarNotificacionRequest request = new ModificarNotificacionRequest();
        request.setId_estatus_notificacion(EnumEstatusNotificacion.BORRADA.getValue());
        request.setId_estatus_usuario_notificacion(notificacionResponse.getId_estatus_usuario_notificacion());
        getControllerAPI().modificarEstatusNotificacion(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            if (!response.body().getError()) {
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.rvBuzon);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
                                notifyData(event);
                            } else {
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.rvBuzon);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
                                event.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                                notifyData(event);
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        notifyData(eventErr);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                try {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                    notifyData(eventErr);
                }
                catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                }
                onHideProgress();
            }
        });
    }

    @Override
    public void modificarEstatusNotificacion(final Context context, final NotificacionResponse nResponse) {
        onShowProgress();
        ModificarNotificacionRequest request = new ModificarNotificacionRequest();
        request.setId_estatus_notificacion(EnumEstatusNotificacion.LEIDA.getValue());
        request.setId_estatus_usuario_notificacion(nResponse.getId_estatus_usuario_notificacion());
        Utils.updateNotificacionEstatus(context,true);
        getControllerAPI().modificarEstatusNotificacion(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    if (response.body() != null) {
                        if (!response.body().getError()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            if (!response.body().getError()) {
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvTitle);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
                                event.getModel().put(ViewEvent.ENTRY, nResponse);
                                notifyData(event);
                            } else {
                                //No se pudo actualizar a Leida.
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvTitle);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
                                event.getModel().put(ViewEvent.ENTRY, null);
                                event.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                                notifyData(event);
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                        notifyData(eventErr);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                try {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, context.getString(R.string.server_error));
                    notifyData(eventErr);
                }
                catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                    notifyData(eventErr);
                }
                onHideProgress();
            }
        });
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
