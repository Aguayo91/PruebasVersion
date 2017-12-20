package com.sociomas.core.WebService.Model.Response.NumeroTelefono;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by jmarquezu on 07/12/2017.
 */

public class TelefonoResponse extends ServerResponse{

    @SerializedName("numero_telefono")
    private String numeroTelefono;

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}
