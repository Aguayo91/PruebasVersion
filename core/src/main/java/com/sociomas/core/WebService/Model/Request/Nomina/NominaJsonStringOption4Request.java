package com.sociomas.core.WebService.Model.Request.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gtoledo on 26/09/2017.
 */

public class NominaJsonStringOption4Request implements Serializable {

    @SerializedName("llave")
    private String llave;

    @SerializedName("Num_empleado")
    private String Num_empleado;

    @SerializedName("Num_cuenta_empleado")
    private String Num_cuenta_empleado;

    @SerializedName("Dispositivo_empleado")
    private String Dispositivo_empleado;

    @SerializedName("ip_cliente")
    private String ip_cliente;

    @SerializedName("sistema")
    private String sistema;

    @SerializedName("Via_liberacion")
    private String Via_liberacion;

    @SerializedName("Id_usuario")
    private String Id_usuario;

    @SerializedName("pais")
    private String pais;

    @SerializedName("validacion")
    private String validacion;

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNum_empleado() {
        return Num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        Num_empleado = num_empleado;
    }

    public String getNum_cuenta_empleado() {
        return Num_cuenta_empleado;
    }

    public void setNum_cuenta_empleado(String num_cuenta_empleado) {
        Num_cuenta_empleado = num_cuenta_empleado;
    }

    public String getDispositivo_empleado() {
        return Dispositivo_empleado;
    }

    public void setDispositivo_empleado(String dispositivo_empleado) {
        Dispositivo_empleado = dispositivo_empleado;
    }

    public String getIp_cliente() {
        return ip_cliente;
    }

    public void setIp_cliente(String ip_cliente) {
        this.ip_cliente = ip_cliente;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getVia_liberacion() {
        return Via_liberacion;
    }

    public void setVia_liberacion(String via_liberacion) {
        Via_liberacion = via_liberacion;
    }

    public String getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        Id_usuario = id_usuario;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    @Override
    public String toString() {
        return "NominaJsonStringOption4Request{" +
                "llave='" + llave + '\'' +
                ", Num_empleado='" + Num_empleado + '\'' +
                ", Num_cuenta_empleado='" + Num_cuenta_empleado + '\'' +
                ", Dispositivo_empleado='" + Dispositivo_empleado + '\'' +
                ", ip_cliente='" + ip_cliente + '\'' +
                ", sistema='" + sistema + '\'' +
                ", Via_liberacion='" + Via_liberacion + '\'' +
                ", Id_usuario='" + Id_usuario + '\'' +
                ", pais='" + pais + '\'' +
                ", validacion='" + validacion + '\'' +
                '}';
    }
}
