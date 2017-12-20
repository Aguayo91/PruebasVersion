package com.gruposalinas.elektra.sociomas.UI.Presenters;

import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Notificaciones.NotificacionResponse;

/**
 * Created by GiioToledo on 27/11/17.
 */

public interface BuzonPresenter extends BasePresenter{

    void consultarNotificacionesUsuario(Context context);

    void borrarNotificacion(Context context, NotificacionResponse notificacionResponse);

    void modificarEstatusNotificacion(Context context, NotificacionResponse notificacionesExtendidas);
}
