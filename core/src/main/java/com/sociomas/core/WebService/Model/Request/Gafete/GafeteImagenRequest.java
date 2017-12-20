package com.sociomas.core.WebService.Model.Request.Gafete;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 22/11/2017.
 */

public class GafeteImagenRequest extends ServerRequest {
    @SerializedName("img_size")
    private String imgSize;
    @SerializedName("latitud")
    private double latitud;
    @SerializedName("longitud")
    private double longitud;

    public GafeteImagenRequest(double latitud, double longitud, String imgSize) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.imgSize=imgSize;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
