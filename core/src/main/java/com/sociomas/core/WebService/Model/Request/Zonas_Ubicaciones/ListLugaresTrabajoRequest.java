package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 28/06/2017.
 */

public class ListLugaresTrabajoRequest extends ServerRequest {
    private String textoBusqueda;
    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public ListLugaresTrabajoRequest(){

    }
        public ListLugaresTrabajoRequest(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }
}
