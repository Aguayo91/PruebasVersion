package com.sociomas.core.WebService.Model.Request.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by GiioToledo on 16/11/17.
 */

public class ModificaUnidadNegocioRequest extends ServerRequest implements Serializable {
    @SerializedName("clave")
    String Clave;

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    @Override
    public String toString() {
        return "UpdateUnidadNegocio{" +
                "Clave='" + Clave + '\'' +
                '}';
    }
}
