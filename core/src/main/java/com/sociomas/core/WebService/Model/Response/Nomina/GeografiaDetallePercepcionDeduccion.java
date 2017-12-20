package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class GeografiaDetallePercepcionDeduccion implements Serializable {

    @SerializedName("importe")
    private String importe;
    @SerializedName("detalleNumero")
    private String detalleNumero;
    @SerializedName("llave")
    private String llave;
    @SerializedName("tipoRegistro")
    private String tipoRegistro;
    @SerializedName("concepto")
    private String concepto;
    @SerializedName("descripcion")
    private String descripcion;

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDetalleNumero() {
        return detalleNumero;
    }

    public void setDetalleNumero(String detalleNumero) {
        this.detalleNumero = detalleNumero;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "GeografiaDetallePercepcionDeduccion{" +
                "importe='" + importe + '\'' +
                ", detalleNumero='" + detalleNumero + '\'' +
                ", llave='" + llave + '\'' +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", concepto='" + concepto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
