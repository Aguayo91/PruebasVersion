package com.sociomas.core.WebService.Model.Request.Permisos;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 19/05/2017.
 */

public class RootPermiso extends ServerRequest {
    private int estatus;
    private String busqueda;


    public int getStatus() {
        return estatus;
    }

    public void setStatus(int status) {
        this.estatus = status;
    }


    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

}
