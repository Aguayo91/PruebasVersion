package com.sociomas.core.WebService.Model.Request.Registro;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 12/07/2017.
 */

@SuppressWarnings("unused")
public class CheckEmpleadoRequest extends ServerRequest {

    public CheckEmpleadoRequest(){

    }
    public CheckEmpleadoRequest(String numeroEmpleado){
        this.setIdNumEmpleado(numeroEmpleado);
    }

    private String id_empresa;

    public String getId_empresa() {
        return id_empresa;
    }

    public void setIdEmpresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }
}
