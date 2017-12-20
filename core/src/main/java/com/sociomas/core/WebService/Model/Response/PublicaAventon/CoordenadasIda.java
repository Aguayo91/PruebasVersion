
package com.sociomas.core.WebService.Model.Response.PublicaAventon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoordenadasIda  implements Serializable{

    public CoordenadasIda(){

    }

    public CoordenadasIda(Double latitudIda, Double  longitudIda, String horaEstimadaIda) {
        this.latitudIda = latitudIda;
        this.longitudIda=longitudIda;
        this.horaEstimadaIda=horaEstimadaIda;
    }

    @SerializedName("latitud_ida")
    @Expose
    private Double latitudIda;
    @SerializedName("longitud_ida")
    @Expose
    private Double longitudIda;
    @SerializedName("hora_estimada_ida")
    @Expose
    private Object horaEstimadaIda;

    public Double getLatitudIda() {
        return latitudIda;
    }

    public void setLatitudIda(Double latitudIda) {
        this.latitudIda = latitudIda;
    }

    public Double getLongitudIda() {
        return longitudIda;
    }

    public void setLongitudIda(Double longitudIda) {
        this.longitudIda = longitudIda;
    }

    public Object getHoraEstimadaIda() {
        return horaEstimadaIda;
    }

    public void setHoraEstimadaIda(Object horaEstimadaIda) {
        this.horaEstimadaIda = horaEstimadaIda;
    }

}
