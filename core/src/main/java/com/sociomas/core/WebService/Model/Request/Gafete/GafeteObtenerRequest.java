package com.sociomas.core.WebService.Model.Request.Gafete;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 24/08/2017.
 */

public class GafeteObtenerRequest extends ServerRequest {
    private double dec_latitud;
    private double dec_longitud;

    public double getDec_laltitud() {
        return dec_latitud;
    }

    public void setDec_laltitud(double dec_laltitud) {
        this.dec_latitud = dec_laltitud;
    }

    public double getDec_longitud() {
        return dec_longitud;
    }

    public void setDec_longitud(double dec_longitud) {
        this.dec_longitud = dec_longitud;
    }
}
