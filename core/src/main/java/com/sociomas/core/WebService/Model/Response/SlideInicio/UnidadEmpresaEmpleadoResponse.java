package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;

/**
 * Created by GiioToledo on 15/11/17.
 */

public class UnidadEmpresaEmpleadoResponse extends ServerResponse implements Serializable {
    @SerializedName("logoEmpresa")
    private UnidadNegocioResponse unidadNegocioResponse;

    public UnidadNegocioResponse getUnidadNegocioResponse() {
        return unidadNegocioResponse;
    }

    public void setUnidadNegocioResponse(UnidadNegocioResponse unidadNegocioResponse) {
        this.unidadNegocioResponse = unidadNegocioResponse;
    }

    @Override
    public String toString() {
        return "UnidadEmpresaEmpleadoResponse{" +
                "unidadNegocioResponse=" + unidadNegocioResponse +
                '}';
    }
}
