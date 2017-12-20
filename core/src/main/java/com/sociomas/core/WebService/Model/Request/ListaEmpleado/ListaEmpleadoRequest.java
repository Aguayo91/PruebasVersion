package com.sociomas.core.WebService.Model.Request.ListaEmpleado;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 28/11/2017.
 */

public class ListaEmpleadoRequest extends ServerRequest {
    @SerializedName("textoBusqueda")
    private String textoBusqueda;

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public ListaEmpleadoRequest(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }
}
