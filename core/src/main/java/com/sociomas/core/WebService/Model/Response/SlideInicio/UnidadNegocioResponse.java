package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 15/11/17.
 */

public class UnidadNegocioResponse implements Serializable {

    @SerializedName("clave")
    String Clave;

    @SerializedName("descripcion")
    String Descripcion;

    @SerializedName("id_empresa")
    private int Id_Empresa;

    @SerializedName("logo")
    private String LogoBase64;

    @SerializedName("orden")
    private int Orden;

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getId_Empresa() {
        return Id_Empresa;
    }

    public void setId_Empresa(int id_Empresa) {
        Id_Empresa = id_Empresa;
    }

    public String getLogoBase64() {
        return LogoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        LogoBase64 = logoBase64;
    }

    public int getOrden() {
        return Orden;
    }

    public void setOrden(int orden) {
        Orden = orden;
    }

    @Override
    public String toString() {
        return "UnidadNegocioResponse{" +
                "Clave='" + Clave + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Id_Empresa=" + Id_Empresa +
                ", LogoBase64='" + LogoBase64 + '\'' +
                ", Orden=" + Orden +
                '}';
    }
}
