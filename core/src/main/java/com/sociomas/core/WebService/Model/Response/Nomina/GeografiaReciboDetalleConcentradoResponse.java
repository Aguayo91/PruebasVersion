package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class GeografiaReciboDetalleConcentradoResponse implements Serializable {

    @SerializedName("reciboCabeceraConcentrado")
    private GeografiaDetalleCabecera geografiaDetalleCabecera;

    @SerializedName("detalles")
    private List<GeografiaDetallePercepcionDeduccion> detalles;

    public GeografiaDetalleCabecera getGeografiaDetalleCabecera() {
        return geografiaDetalleCabecera;
    }

    public void setGeografiaDetalleCabecera(GeografiaDetalleCabecera geografiaDetalleCabecera) {
        this.geografiaDetalleCabecera = geografiaDetalleCabecera;
    }

    public List<GeografiaDetallePercepcionDeduccion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<GeografiaDetallePercepcionDeduccion> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "GeografiaReciboDetalleConcentradoResponse{" +
                "geografiaDetalleCabecera=" + geografiaDetalleCabecera +
                ", detalles=" + detalles +
                '}';
    }
}
