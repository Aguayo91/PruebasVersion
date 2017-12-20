package com.sociomas.core.WebService.Model.Request.Registro;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by GiioToledo on 28/11/17.
 */

public class ModificarNotificacionRequest extends ServerRequest implements Serializable{

    @SerializedName("id_estatus_usuario_notificacion")
    private int id_estatus_usuario_notificacion;

    @SerializedName("id_estatus_notificacion")
    private int id_estatus_notificacion;

    public int getId_estatus_usuario_notificacion() {
        return id_estatus_usuario_notificacion;
    }

    public void setId_estatus_usuario_notificacion(int id_estatus_usuario_notificacion) {
        this.id_estatus_usuario_notificacion = id_estatus_usuario_notificacion;
    }

    public int getId_estatus_notificacion() {
        return id_estatus_notificacion;
    }

    public void setId_estatus_notificacion(int id_estatus_notificacion) {
        this.id_estatus_notificacion = id_estatus_notificacion;
    }

    @Override
    public String toString() {
        return "ModificarNotificacionRequest{" +
                "id_estatus_usuario_notificacion=" + id_estatus_usuario_notificacion +
                ", id_estatus_notificacion=" + id_estatus_notificacion +
                '}';
    }
}
