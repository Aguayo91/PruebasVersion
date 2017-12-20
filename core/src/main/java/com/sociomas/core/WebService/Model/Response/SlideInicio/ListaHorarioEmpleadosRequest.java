package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 17/11/17.
 */

public class ListaHorarioEmpleadosRequest extends ServerRequest implements Serializable {

    @SerializedName("listaHorarioEmpleado")
    private List<HorarioEmpleadoRequest> listaHorario;

    public List<HorarioEmpleadoRequest> getListaHorario() {
        return listaHorario;
    }

    public void setListaHorario(List<HorarioEmpleadoRequest> listaHorario) {
        this.listaHorario = listaHorario;
    }

    @Override
    public String toString() {
        return "ListaHorarioEmpleadosRequest{" +
                "listaHorario=" + listaHorario +
                '}';
    }
}
