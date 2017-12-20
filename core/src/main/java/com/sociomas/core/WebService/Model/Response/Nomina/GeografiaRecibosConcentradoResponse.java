package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class GeografiaRecibosConcentradoResponse implements Serializable {
    @SerializedName("recibos")
    private List<GeografiaListaRecibosResponse> recibos;

    public List<GeografiaListaRecibosResponse> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<GeografiaListaRecibosResponse> recibos) {
        this.recibos = recibos;
    }

    @Override
    public String toString() {
        return "GeografiaRecibosConcentradoResponse{" +
                "recibos=" + recibos +
                '}';
    }
}
