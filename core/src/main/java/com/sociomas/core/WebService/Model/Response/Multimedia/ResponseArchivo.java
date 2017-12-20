package com.sociomas.core.WebService.Model.Response.Multimedia;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 18/05/2017.
 */

public class ResponseArchivo extends ServerResponse {
    private ArrayList<ArchivoListado> listado;

    public ArrayList<ArchivoListado> getListado() { return this.listado; }

    public void setListado(ArrayList<ArchivoListado> listado) { this.listado = listado; }
}
