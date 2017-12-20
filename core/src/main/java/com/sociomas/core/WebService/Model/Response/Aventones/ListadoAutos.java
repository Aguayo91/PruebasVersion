package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 04/10/2017.
 */

public class ListadoAutos implements Serializable {
    @SerializedName("dispositivo_activo")
    private int dispositivoActivo;

    @SerializedName("error")
    private boolean error;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("serverTime")
    private String serverTime;

    @SerializedName("serverUTCTime")
    private String serverUTCTime;

    @SerializedName("VehiculoEmp")
    private List<Vehiculo> listVehiculoEmp;

    public int getDispositivoActivo() {
        return dispositivoActivo;
    }

    public void setDispositivoActivo(int dispositivoActivo) {
        this.dispositivoActivo = dispositivoActivo;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
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

    public String getServerUTCTime() {
        return serverUTCTime;
    }

    public void setServerUTCTime(String serverUTCTime) {
        this.serverUTCTime = serverUTCTime;
    }

    public List<Vehiculo> getListVehiculoEmp() {
        return listVehiculoEmp;
    }

    public void setListVehiculoEmp(List<Vehiculo> listVehiculoEmp) {
        this.listVehiculoEmp = listVehiculoEmp;
    }

    @Override
    public String toString() {
        return "ListadoAutos{" +
                "dispositivoActivo=" + dispositivoActivo +
                ", error=" + error +
                ", mensaje='" + mensaje + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", serverUTCTime='" + serverUTCTime + '\'' +
                ", listVehiculoEmp=" + listVehiculoEmp +
                '}';
    }
}
