package com.sociomas.core.WebService.Model.Request.Notificaciones;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class ConsultaNotificacionesRequest extends ServerRequest implements Serializable {

    @SerializedName("tipo_vista")
    private int tipo_vista;

    public int getTipo_vista() {
        return tipo_vista;
    }

    public void setTipo_vista(int tipo_vista) {
        this.tipo_vista = tipo_vista;
    }

    @Override
    public String toString() {
        return "ConsultaNotificacionesRequest{" +
                "tipo_vista=" + tipo_vista +
                '}';
    }
}
