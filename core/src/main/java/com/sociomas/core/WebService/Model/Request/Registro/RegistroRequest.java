package com.sociomas.core.WebService.Model.Request.Registro;


import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 12/07/2017.
 */

public class RegistroRequest extends ServerRequest {
    @SerializedName("va_numero_telefono")
    private String va_numero_telefono;
    private String strPassword;

    public RegistroRequest(){}

    public  RegistroRequest(String numeroEmpleado, String respuesta,String numeroTel){
        this.setIdNumEmpleado(numeroEmpleado);
        this.setRespuestaPass(respuesta);
        this.setNumeroTel(numeroTel);
    }

    public String getNumeroTel() {
        return va_numero_telefono;
    }

    public void setNumeroTel(String va_numero_telefono) {
        this.va_numero_telefono = va_numero_telefono;
    }

    public String getRespuesta() {
        return strPassword;
    }

    public void setRespuestaPass(String respuesta) {
        this.strPassword = respuesta;
    }
}
