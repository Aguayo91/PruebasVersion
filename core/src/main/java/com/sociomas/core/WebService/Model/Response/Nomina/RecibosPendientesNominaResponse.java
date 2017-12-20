package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gtoledo on 27/09/2017.
 */

public class RecibosPendientesNominaResponse implements Serializable {

    @SerializedName("element")
    private List<ReciboNomina> recibosPendientes;

    @SerializedName("errorCode")
    private String errorCode;

    @SerializedName("errorDescription")
    private String errorDescription;

    public List<ReciboNomina> getRecibosPendientes() {
        return recibosPendientes;
    }

    public void setRecibosPendientes(List<ReciboNomina> recibosPendientes) {
        this.recibosPendientes = recibosPendientes;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return "RecibosPendientesNominaResponse{" +
                "recibosPendientes=" + recibosPendientes +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
