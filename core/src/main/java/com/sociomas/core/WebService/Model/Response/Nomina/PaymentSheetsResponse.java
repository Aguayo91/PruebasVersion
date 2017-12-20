package com.sociomas.core.WebService.Model.Response.Nomina;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;

/**
 * Created by gtoledo on 27/09/2017.
 */

public class PaymentSheetsResponse extends ServerResponse implements Serializable {

    @SerializedName("strJson")
    private String strJson;

    public String getStrJson() {
        return strJson;
    }

    public void setStrJson(String strJson) {
        this.strJson = strJson;
    }

    @Override
    public String toString() {
        return "PaymentSheetsResponse{" +
                "strJson='" + strJson + '\'' +
                '}';
    }
}
