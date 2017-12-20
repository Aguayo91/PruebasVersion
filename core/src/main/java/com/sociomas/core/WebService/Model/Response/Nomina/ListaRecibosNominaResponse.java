package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class ListaRecibosNominaResponse extends ServerResponse implements Serializable {

    @SerializedName("listaRecibos")
    private List<ReciboNominaDetalleResponse> listaRecibos;

    @SerializedName("reciboParent")
    private ReciboNomina reciboParent;

    @SerializedName("errorCode")
    private String errorCode;

    @SerializedName("errorDescription")
    private String errorDescription;

    public List<ReciboNominaDetalleResponse> getListaRecibos() {
        return listaRecibos;
    }

    public void setListaRecibos(List<ReciboNominaDetalleResponse> listaRecibos) {
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

    public ReciboNomina getReciboParent() {
        return reciboParent;
    }

    public void setReciboParent(ReciboNomina concepto) {
        this.reciboParent = concepto;
    }

    @Override
    public String toString() {
        return "ListaRecibosNominaResponse{" +
                "listaRecibos=" + listaRecibos +
                ", reciboParent='" + reciboParent + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
