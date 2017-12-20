package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by GiioToledo on 17/11/17.
 */

public class TipoHorarioEmpleadoRequest extends ServerRequest implements Serializable {

    @SerializedName("id_tipo_horario")
    private int id_tipo_horario;

    public int getId_tipo_horario() {
        return id_tipo_horario;
    }

    public void setId_tipo_horario(int id_tipo_horario) {
        this.id_tipo_horario = id_tipo_horario;
    }


    @SerializedName("fecha_dispositivo")
    private String fechaDispositivo;

    public String getFechaDispositivo() {
        return fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo) {
        this.fechaDispositivo = fechaDispositivo;
    }


    @Override
    public String toString() {
        return "TipoHorarioEmpleadoRequest{" +
                "id_tipo_horario=" + id_tipo_horario +
                '}';
    }
}
