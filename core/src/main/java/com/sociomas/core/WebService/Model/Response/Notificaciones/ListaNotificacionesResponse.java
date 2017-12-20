package com.sociomas.core.WebService.Model.Response.Notificaciones;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class ListaNotificacionesResponse extends ServerResponse implements Serializable {

    @SerializedName("Notificacion")
    List<NotificacionResponse> notificaciones;

    public List<NotificacionResponse> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionResponse> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @Override
    public String toString() {
        return "ListaNotificacionesResponse{" +
                "notificaciones=" + notificaciones +
                '}';
    }
}
