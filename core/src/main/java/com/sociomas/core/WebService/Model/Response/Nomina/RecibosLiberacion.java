package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 29/11/17.
 */

public class RecibosLiberacion implements Serializable{

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorDescription")
    private String errorDescription;

    @SerializedName("observaciones")
    private String observaciones;

    @SerializedName("resultado")
    private String resultado;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "RecibosLiberacion{" +
                "errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
