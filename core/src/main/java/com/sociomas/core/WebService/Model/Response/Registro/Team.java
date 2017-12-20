package com.sociomas.core.WebService.Model.Response.Registro;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 01/11/2017.
 */

public class Team {
    @SerializedName("id_num_empleado")
    private String numeroEmpleado;
    @SerializedName("va_nombre_completo")
    private String nombreCompleto;

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
