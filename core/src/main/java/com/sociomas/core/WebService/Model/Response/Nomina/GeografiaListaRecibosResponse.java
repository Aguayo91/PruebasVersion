package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class GeografiaListaRecibosResponse extends ServerResponse implements Serializable {

    @SerializedName("listaRecibos")
    private List<GeografiaReciboDetalleConcentradoResponse> listaRecibos;

    @SerializedName("errorCode")
    private String errorCode;

    @SerializedName("errorDescription")
    private String errorDescription;

    public List<GeografiaReciboDetalleConcentradoResponse> getListaRecibos() {
        return listaRecibos;
    }

    public void setListaRecibos(List<GeografiaReciboDetalleConcentradoResponse> listaRecibos) {
        this.listaRecibos = listaRecibos;
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
        return "GeografiaListaRecibosResponse{" +
                "listaRecibos=" + listaRecibos +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
