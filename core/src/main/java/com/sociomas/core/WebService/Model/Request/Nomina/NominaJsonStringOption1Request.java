package com.sociomas.core.WebService.Model.Request.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gtoledo on 26/09/2017.
 */

public class NominaJsonStringOption1Request implements Serializable {
    @SerializedName("noCuenta")
    private String noCuenta;

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    @Override
    public String toString() {
        return "NominaJsonStringOption1Request{" +
                "noCuenta='" + noCuenta + '\'' +
                '}';
    }
}
