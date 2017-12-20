
package com.sociomas.core.WebService.Model.Response.PrefenciaAventon;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreferenciaResponse {

    @SerializedName("dispositivo_activo")
    @Expose
    private Integer dispositivoActivo;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("mensaje")
    @Expose
    private Object mensaje;
    @SerializedName("serverTime")
    @Expose
    private String serverTime;
    @SerializedName("serverUTCTime")
    @Expose
    private Object serverUTCTime;
    @SerializedName("CaractEmpleado")
    @Expose
    private CaractEmpleado caractEmpleado;
    @SerializedName("CatPreferenciasEmpleado")
    @Expose
    private ArrayList<CatPreferenciasEmpleado> catPreferenciasEmpleado = null;
    @SerializedName("sexo")
    @Expose
    private Object sexo;

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

    public Object getMensaje() {
        return mensaje;
    }

    public void setMensaje(Object mensaje) {
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

    public CaractEmpleado getCaractEmpleado() {
        return caractEmpleado;
    }

    public void setCaractEmpleado(CaractEmpleado caractEmpleado) {
        this.caractEmpleado = caractEmpleado;
    }

    public ArrayList<CatPreferenciasEmpleado> getCatPreferenciasEmpleado() {
        return catPreferenciasEmpleado;
    }

    public void setCatPreferenciasEmpleado(ArrayList<CatPreferenciasEmpleado> catPreferenciasEmpleado) {
        this.catPreferenciasEmpleado = catPreferenciasEmpleado;
    }

    public Object getSexo() {
        return sexo;
    }

    public void setSexo(Object sexo) {
        this.sexo = sexo;
    }

}
