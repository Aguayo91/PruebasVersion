package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 19/10/2017.
 */

public class NominaAccountResponse implements Serializable {
    @SerializedName("noCuenta")
    private String numberAccount;


    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    @Override
    public String toString() {
        return "NominaAccountResponse{" +
                "numberAccount='" + numberAccount + '\'' +
                '}';
    }
}
