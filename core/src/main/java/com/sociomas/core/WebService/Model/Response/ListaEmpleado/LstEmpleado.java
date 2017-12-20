package com.sociomas.core.WebService.Model.Response.ListaEmpleado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 28/11/2017.
 */
public class LstEmpleado {

    @SerializedName("id_num_empleado")
    @Expose
    private String idNumEmpleado;
    @SerializedName("nombre_completo")
    @Expose
    private String nombreCompleto;

    public String getIdNumEmpleado() {
        return idNumEmpleado;
    }

    public void setIdNumEmpleado(String idNumEmpleado) {
        this.idNumEmpleado = idNumEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
