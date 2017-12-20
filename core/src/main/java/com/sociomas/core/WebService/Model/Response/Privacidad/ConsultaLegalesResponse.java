package com.sociomas.core.WebService.Model.Response.Privacidad;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 08/12/17.
 */

public class ConsultaLegalesResponse extends ServerResponse implements Serializable{
    @SerializedName("DatoLegal")
    private List<DatoLegalRespose> DatoLegal;

    public List<DatoLegalRespose> getDatoLegal() {
        return DatoLegal;
    }

    public void setDatoLegal(List<DatoLegalRespose> datoLegal) {
        DatoLegal = datoLegal;
    }

    @Override
    public String toString() {
        return "ConsultaLegalesResponse{" +
                "DatoLegal=" + DatoLegal +
                '}';
    }
}
