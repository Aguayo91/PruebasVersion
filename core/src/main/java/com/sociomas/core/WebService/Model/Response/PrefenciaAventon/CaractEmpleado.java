
package com.sociomas.core.WebService.Model.Response.PrefenciaAventon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaractEmpleado {

    @SerializedName("id_rel_caracteristicas_usuario")
    @Expose
    private Integer idRelCaracteristicasUsuario;
    @SerializedName("observaciones")
    @Expose
    private String observaciones;
    @SerializedName("tiempo_tolerancia")
    @Expose
    private String tiempoTolerancia;

    public CaractEmpleado(String observaciones, String tiempoTolerancia) {
        this.observaciones = observaciones;
        this.tiempoTolerancia=tiempoTolerancia;
    }

    public CaractEmpleado(String observaciones, String tiempoTolerancia,Integer idRelCaracteristicasUsuario) {
        this.observaciones = observaciones;
        this.tiempoTolerancia=tiempoTolerancia;
        this.idRelCaracteristicasUsuario = idRelCaracteristicasUsuario;
    }

    public Integer getIdRelCaracteristicasUsuario() {
        return idRelCaracteristicasUsuario;
    }

    public void setIdRelCaracteristicasUsuario(Integer idRelCaracteristicasUsuario) {
        this.idRelCaracteristicasUsuario = idRelCaracteristicasUsuario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTiempoTolerancia() {
        return tiempoTolerancia;
    }

    public void setTiempoTolerancia(String tiempoTolerancia) {
        this.tiempoTolerancia = tiempoTolerancia;
    }

}
