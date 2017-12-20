package com.sociomas.core.WebService.Model.Request.Codigo;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 13/07/2017.
 */

public class CrearCodigoRequest  extends ServerRequest {

    public CrearCodigoRequest(){

    }

    public CrearCodigoRequest(String numeroTelefono){
        this.telefono=numeroTelefono;
        this.va_numero_telefono=numeroTelefono;
    }

    private  String va_numero_telefono;
    private  String telefono;
    public String getVa_numero_telefono() {
        return va_numero_telefono;
    }
    public void setVa_numero_telefono(String va_numero_telefono) {
        this.va_numero_telefono = va_numero_telefono;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
