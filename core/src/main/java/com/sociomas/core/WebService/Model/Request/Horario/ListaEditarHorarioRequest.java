package com.sociomas.core.WebService.Model.Request.Horario;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 21/11/17.
 */

public class ListaEditarHorarioRequest extends ServerRequest implements Serializable {

    @SerializedName("listaHorarioEmpleado")
    private List<EditarHorarioRequest> listaHorarioEmpleado;

    @SerializedName("id_num_empleado_logeado")
    private String id_num_empleado_logeado;

    public List<EditarHorarioRequest> getListaHorarioEmpleado() {
        return listaHorarioEmpleado;
    }

    public void setListaHorarioEmpleado(List<EditarHorarioRequest> listaHorarioEmpleado) {
        this.listaHorarioEmpleado = listaHorarioEmpleado;
    }

    public String getId_num_empleado_logeado() {
        return id_num_empleado_logeado;
    }

    public void setId_num_empleado_logeado(String id_num_empleado_logeado) {
        this.id_num_empleado_logeado = id_num_empleado_logeado;
    }

    @Override
    public String toString() {
        return "ListaEditarHorarioRequest{" +
                "listaHorarioEmpleado=" + listaHorarioEmpleado +
                ", id_num_empleado_logeado='" + id_num_empleado_logeado + '\'' +
                '}';
    }
}
