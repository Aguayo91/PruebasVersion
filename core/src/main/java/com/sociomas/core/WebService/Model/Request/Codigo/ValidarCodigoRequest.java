package com.sociomas.core.WebService.Model.Request.Codigo;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 12/07/2017.
 */

public class ValidarCodigoRequest extends ServerRequest {
    private  String codigo;
    private  String texto;

    public ValidarCodigoRequest(String codigo,String texto){
        this.codigo=codigo;
        this.texto=texto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
