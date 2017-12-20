package com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones;

import android.content.Context;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Enums.EnumEstatusNotificacion;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Registro.ModificarNotificacionRequest;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jr441 on 28/11/2017.
 */

public class NotificacionesExtendidasPresenterImplement extends BasePresenterImpl implements NotificacionesExtendidasPresenter {
    private static final String TAG = NotificacionesExtendidasPresenterImplement.class.getSimpleName();

    @Override
    public void modificarEstatusNotificacion(final Context context, NotificacionResponse nResponse) {
        onShowProgress();
        ModificarNotificacionRequest request = new ModificarNotificacionRequest();
        request.setId_estatus_notificacion(EnumEstatusNotificacion.LEIDA.getValue());
        request.setId_estatus_usuario_notificacion(nResponse.getId_estatus_usuario_notificacion());
        getcControllerAPI().modificarEstatusNotificacion(request).enqueue(new Callback<ServerResponse>() {
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
                                notifyData(event);
                            } else {
                                //No se pudo actualizar a Leida.
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvTitle);
                                event.getModel().put(ViewEvent.BOOLEAN_OBJECT, !response.body().getError());
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

    public ControllerAPI getcControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
