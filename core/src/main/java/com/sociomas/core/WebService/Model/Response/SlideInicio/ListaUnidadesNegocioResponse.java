package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 15/11/17.
 */

public class ListaUnidadesNegocioResponse extends ServerResponse implements Serializable {

    @SerializedName("lstImgEmpresas")
    private List<UnidadNegocioResponse> listEmpresas;

    public List<UnidadNegocioResponse> getListEmpresas() {
        return listEmpresas;
    }

    public void setListEmpresas(List<UnidadNegocioResponse> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    @Override
    public String toString() {
        return "ListaUnidadesNegocioResponse{" +
                "listEmpresas=" + listEmpresas +
                '}';
    }
}
