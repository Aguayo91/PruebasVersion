package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 29/11/17.
 */

public class RecibosLiberacionResponse implements Serializable{
    @SerializedName("recibos")
    private RecibosLiberacion recibosLiberacion;

    public RecibosLiberacion getRecibosLiberacion() {
        return recibosLiberacion;
    }

    public void setRecibosLiberacion(RecibosLiberacion recibosLiberacion) {
        this.recibosLiberacion = recibosLiberacion;
    }

    @Override
    public String toString() {
        return "RecibosLiberacionResponse{" +
                "recibosLiberacion=" + recibosLiberacion +
                '}';
    }
}
