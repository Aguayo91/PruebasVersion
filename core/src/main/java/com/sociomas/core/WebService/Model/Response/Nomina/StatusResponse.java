package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class StatusResponse implements Serializable {

    @SerializedName("codigoOperacion")
    private String codigoOperacion;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("folio")
    private String folio;

    @SerializedName("tokenCode")
    private int tokenCode;

    public String getCodigoOperacion() {
        return codigoOperacion;
    }

    public void setCodigoOperacion(String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(int tokenCode) {
        this.tokenCode = tokenCode;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "codigoOperacion='" + codigoOperacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", folio='" + folio + '\'' +
                ", tokenCode=" + tokenCode +
                '}';
    }
}
