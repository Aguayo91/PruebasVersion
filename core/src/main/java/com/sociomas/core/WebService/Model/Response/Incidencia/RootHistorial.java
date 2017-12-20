package com.sociomas.core.WebService.Model.Response.Incidencia;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 23/05/2017.
 */

public class RootHistorial extends ServerResponse {
    private ArrayList<ListadoHistorial> listado;

    public ArrayList<ListadoHistorial> getListado() { return this.listado; }

    public void setListado(ArrayList<ListadoHistorial> listado) { this.listado = listado; }

    private ArrayList<ListadoHistorial> listado_pendientes;

    public ArrayList<ListadoHistorial> getListadoPendientes() { return this.listado_pendientes; }

    public void setListadoPendientes(ArrayList<ListadoHistorial> listado_pendientes) { this.listado_pendientes = listado_pendientes; }
}
