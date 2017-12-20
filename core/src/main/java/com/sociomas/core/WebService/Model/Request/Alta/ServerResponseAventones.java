package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jromeromar on 26/09/2017.
 */

public class ServerResponseAventones {
    @SerializedName("dispositivo_activo")
    @Expose
    private Integer dispositivoActivo;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("serverTime")
    @Expose
    private String serverTime;
    @SerializedName("serverUTCTime")
    @Expose
    private Object serverUTCTime;
    @SerializedName("VehiculoEmp")
    @Expose
    private Object vehiculoEmp;

    public Integer getDispositivoActivo() {
        return dispositivoActivo;
    }

    public void setDispositivoActivo(Integer dispositivoActivo) {
        this.dispositivoActivo = dispositivoActivo;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public Object getServerUTCTime() {
        return serverUTCTime;
    }

    public void setServerUTCTime(Object serverUTCTime) {
        this.serverUTCTime = serverUTCTime;
    }

    public Object getVehiculoEmp() {
        return vehiculoEmp;
    }

    public void setVehiculoEmp(Object vehiculoEmp) {
        this.vehiculoEmp = vehiculoEmp;
    }
}
