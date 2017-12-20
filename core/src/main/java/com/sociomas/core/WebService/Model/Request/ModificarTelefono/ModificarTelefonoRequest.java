package com.sociomas.core.WebService.Model.Request.ModificarTelefono;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by jmarquezu on 07/12/2017.
 */

public class ModificarTelefonoRequest extends ServerRequest implements Serializable {


    @SerializedName("numero_telefono")
    public String numero_telefono;

    public ModificarTelefonoRequest(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public String getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    @Override
    public String toString() {
        return "ModificarTelefonoRequest{" +
                "numero_telefono='" + numero_telefono + '\'' +
                '}';
    }
}
