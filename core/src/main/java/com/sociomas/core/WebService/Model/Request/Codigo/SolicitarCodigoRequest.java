package com.sociomas.core.WebService.Model.Request.Codigo;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 17/07/2017.
 */

@SuppressWarnings("unused")
public class SolicitarCodigoRequest extends ServerRequest {
    private String va_numero_telefono;
    private String nuevo_telefono;

    public String getNelefono() {
        return va_numero_telefono;
    }

    public void seNumerotelefono(String va_numero_telefono) {
        this.va_numero_telefono = va_numero_telefono;
    }

    public String getNuevoTelefono() {
        return nuevo_telefono;
    }

    public void setNuevoTelefono(String nuevo_telefono) {
        this.nuevo_telefono = nuevo_telefono;
    }
}
