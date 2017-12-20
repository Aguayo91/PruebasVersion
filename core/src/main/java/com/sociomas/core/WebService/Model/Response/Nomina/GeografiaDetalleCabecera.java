package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class GeografiaDetalleCabecera implements Serializable {

    @SerializedName("detalleNumero")
    private String detalleNumero;

    @SerializedName("llave")
    private String llave;

    @SerializedName("tipoRegistro")
    private String tipoRegistro;

    @SerializedName("totalFinal")
    private String totalFinal;

    @SerializedName("totalDeducciones")
    private String totalDeducciones;

    @SerializedName("totalPercepciones")
    private String totalPercepciones;

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

    public String getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(String totalFinal) {
        this.totalFinal = totalFinal;
    }

    public String getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(String totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }

    public String getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(String totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    @Override
    public String toString() {
        return "GeografiaDetalleCabecera{" +
                "detalleNumero='" + detalleNumero + '\'' +
                ", llave='" + llave + '\'' +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", totalFinal='" + totalFinal + '\'' +
                ", totalDeducciones='" + totalDeducciones + '\'' +
                ", totalPercepciones='" + totalPercepciones + '\'' +
                '}';
    }
}
