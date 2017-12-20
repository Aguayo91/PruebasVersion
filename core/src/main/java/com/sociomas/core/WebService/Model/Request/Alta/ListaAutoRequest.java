package com.sociomas.core.WebService.Model.Request.Alta;

import java.io.Serializable;

/**
 * Created by jromeromar on 22/09/2017.
 */

public class ListaAutoRequest implements Serializable{

    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private String token;

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }

}
