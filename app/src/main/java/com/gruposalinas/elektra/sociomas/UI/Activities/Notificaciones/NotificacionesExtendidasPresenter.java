package com.gruposalinas.elektra.sociomas.UI.Activities.Notificaciones;

import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;

/**
 * Created by jr441 on 28/11/2017.
 */

public interface NotificacionesExtendidasPresenter extends BasePresenter {
    void modificarEstatusNotificacion(Context context, NotificacionResponse notificacionesExtendidas);
}
