package com.sociomas.core.WebService.Model.Request.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;

/**
 * Created by GiioToledo on 18/11/17.
 */

public class ModificarPrefLugarTrabajoRequest extends ServerRequest implements Serializable {

    @SerializedName("id_pref_lugar_trabajo")
    private int id_pref_lugar_trabajo;


    @SerializedName("fecha_dispositivo")
    private String fechaDispositivo;

    public String getFechaDispositivo() {
        return fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo) {
        this.fechaDispositivo = fechaDispositivo;
    }

    public int getId_pref_lugar_trabajo() {
        return id_pref_lugar_trabajo;
    }

    public void setId_pref_lugar_trabajo(int id_pref_lugar_trabajo) {
        this.id_pref_lugar_trabajo = id_pref_lugar_trabajo;
    }

    @Override
    public String toString() {
        return "ModificarPrefLugarTrabajoRequest{" +
                "id_pref_lugar_trabajo=" + id_pref_lugar_trabajo +
                '}';
    }
}
