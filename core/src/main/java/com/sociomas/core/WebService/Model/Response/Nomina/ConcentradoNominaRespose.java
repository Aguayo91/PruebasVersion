package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gtoledo on 28/09/2017.
 */

public class ConcentradoNominaRespose implements Serializable{

    @SerializedName("recibos")
    private List<ListaRecibosNominaResponse> recibos;

    public List<ListaRecibosNominaResponse> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<ListaRecibosNominaResponse> recibos) {
        this.recibos = recibos;
    }

    @Override
    public String toString() {
        return "ConcentradoNominaRespose{" +
                "recibos=" + recibos +
                '}';
    }
}
