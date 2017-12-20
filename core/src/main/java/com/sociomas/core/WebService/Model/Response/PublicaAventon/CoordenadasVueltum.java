
package com.sociomas.core.WebService.Model.Response.PublicaAventon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordenadasVueltum {


    public CoordenadasVueltum(Double latitudVuelta,Double longitudVuelta, String horaEstimadaVuelta) {
        this.latitudVuelta = latitudVuelta;
        this.longitudVuelta=longitudVuelta;
        this.horaEstimadaVuelta=horaEstimadaVuelta;
    }

    @SerializedName("latitud_vuelta")
    @Expose
    private Double latitudVuelta;
    @SerializedName("longitud_vuelta")
    @Expose
    private Double longitudVuelta;
    @SerializedName("hora_estimada_vuelta")
    @Expose
    private Object horaEstimadaVuelta;

    public Double getLatitudVuelta() {
        return latitudVuelta;
    }

    public void setLatitudVuelta(Double latitudVuelta) {
        this.latitudVuelta = latitudVuelta;
    }

    public Double getLongitudVuelta() {
        return longitudVuelta;
    }

    public void setLongitudVuelta(Double longitudVuelta) {
        this.longitudVuelta = longitudVuelta;
    }

    public Object getHoraEstimadaVuelta() {
        return horaEstimadaVuelta;
    }

    public void setHoraEstimadaVuelta(Object horaEstimadaVuelta) {
        this.horaEstimadaVuelta = horaEstimadaVuelta;
    }

}
