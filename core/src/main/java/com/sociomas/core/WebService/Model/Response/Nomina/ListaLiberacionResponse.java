package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 29/11/17.
 */

public class ListaLiberacionResponse implements Serializable {

    @SerializedName("recibos")
    List<RecibosLiberacion> recibosLiberacionResponses;

    public List<RecibosLiberacion> getRecibosLiberacionResponses() {
        return recibosLiberacionResponses;
    }

    public void setRecibosLiberacionResponses(List<RecibosLiberacion> recibosLiberacionResponses) {
        this.recibosLiberacionResponses = recibosLiberacionResponses;
    }

    @Override
    public String toString() {
        return "ListaLiberacionResponse{" +
                "recibosLiberacionResponses=" + recibosLiberacionResponses +
                '}';
    }
}
