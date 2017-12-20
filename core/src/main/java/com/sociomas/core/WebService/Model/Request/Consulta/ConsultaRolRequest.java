package com.sociomas.core.WebService.Model.Request.Consulta;

/**
 * Created by jmarquezu on 09/10/2017.
 */

public class ConsultaRolRequest {
    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private String token;

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }
}
