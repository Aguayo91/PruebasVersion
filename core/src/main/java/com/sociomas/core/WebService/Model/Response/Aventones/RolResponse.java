package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jmarquezu on 09/10/2017.
 */

public class RolResponse implements Serializable{

    public class lstAventon{
        private int id_aventon;

        public int getIdAventon() { return this.id_aventon; }

        public void setIdAventon(int id_aventon) { this.id_aventon = id_aventon; }
    }
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
    @SerializedName("desc_rol_aventon")
    @Expose
    private String descRolAventon;
    @SerializedName("id_rol_aventon")
    @Expose
    private Integer idRolAventon;

    public String getIdNumEmpleadoConductor() {
        return idNumEmpleadoConductor;
    }

    public void setIdNumEmpleadoConductor(String idNumEmpleadoConductor) {
        this.idNumEmpleadoConductor = idNumEmpleadoConductor;
    }

    @SerializedName("id_num_empleado_conductor")
    @Expose
    private String idNumEmpleadoConductor;

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

    public String getDescRolAventon() {
        return descRolAventon;
    }

    public void setDescRolAventon(String descRolAventon) {
        this.descRolAventon = descRolAventon;
    }

    public Integer getIdRolAventon() {
        return idRolAventon;
    }

    public void setIdRolAventon(Integer idRolAventon) {
        this.idRolAventon = idRolAventon;
    }


}
