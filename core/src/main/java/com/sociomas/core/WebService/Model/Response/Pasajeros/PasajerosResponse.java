package com.sociomas.core.WebService.Model.Response.Pasajeros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class PasajerosResponse extends ServerResponse {
    @SerializedName("lstSolicitudes")
    @Expose
    private ArrayList<PasajerosList> lstSolicitudes;

    public ArrayList<PasajerosList> getLstSolicitudes() {
        return lstSolicitudes;
    }

    public void setLstSolicitudes(ArrayList<PasajerosList> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }
}
