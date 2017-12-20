package com.sociomas.core.WebService.Model.Response.Privacidad;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 16/11/2017.
 */

public class PrivacidadResponse extends ServerResponse {
    @SerializedName("Aviso")
    private ArrayList<Aviso>listAviso;

    public ArrayList<Aviso> getListAviso() {
        return listAviso;
    }

    public void setListAviso(ArrayList<Aviso> listAviso) {
        this.listAviso = listAviso;
    }
}
