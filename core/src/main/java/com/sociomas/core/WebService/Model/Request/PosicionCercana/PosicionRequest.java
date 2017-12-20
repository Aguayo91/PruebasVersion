package com.sociomas.core.WebService.Model.Request.PosicionCercana;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 09/10/2017.
 */

public class PosicionRequest extends ServerRequest {

    @SerializedName("latitud")
    private Double latitud;
    @SerializedName("longitud")
    private Double longitud;
    @SerializedName("rango")
    private int rango;


    public PosicionRequest(Double latitud, Double longitud, int rango) {
        this.latitud = latitud;
        this.longitud=longitud;
        this.rango=rango;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public int getRango() {
        return rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }
}
