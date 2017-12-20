package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 04/10/2017.
 */

public class ConsultaAventonesResponse implements Serializable {

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

    @SerializedName("Aventones")
    private List<Aventones> listAventones;

    @SerializedName("DiasAventon")
    private List<DiasAventon>listDiasAventon;

    @SerializedName("id_tipo_aventon")
    private int idTipoaventon;

    @SerializedName("viaje_redondo")
    private boolean viajeRedondo;

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

    public List<Aventones> getListAventones() {
        return listAventones;
    }

    public List<DiasAventon>getListDiasAventon(){return listDiasAventon;}

    public void setListAventones(List<Aventones> listAventones) {
        this.listAventones = listAventones;
    }
    public void setListDiasAventon(List<DiasAventon>listDiasAventon){
        this.listDiasAventon= listDiasAventon;
    }

    public int getIdTipoaventon() {
        return idTipoaventon;
    }

    public void setIdTipoaventon(int idTipoaventon) {
        this.idTipoaventon = idTipoaventon;
    }

    public boolean isViajeRedondo() {
        return viajeRedondo;
    }

    public void setViajeRedondo(boolean viajeRedondo) {
        this.viajeRedondo = viajeRedondo;
    }

    @Override
    public String toString() {
        return "ConsultaAventonesResponse{" +
                "dispositivoActivo=" + dispositivoActivo +
                ", error=" + error +
                ", mensaje='" + mensaje + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", serverUTCTime='" + serverUTCTime + '\'' +
                ", listAventones=" + listAventones +
                ", listDiasAventon=" + listDiasAventon +
                ", idTipoaventon=" + idTipoaventon +
                ", viajeRedondo=" + viajeRedondo +
                '}';
    }
}
