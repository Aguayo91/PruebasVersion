package com.sociomas.core.WebService.Model.Request.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gtoledo on 26/09/2017.
 */

public class NominaJsonStringOption2Request implements Serializable {
    @SerializedName("llave")
    private String llave;

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    @Override
    public String toString() {
        return "NominaJsonStringOption2Request{" +
                "llave='" + llave + '\'' +
                '}';
    }
}
