package com.sociomas.core.WebService.Model.Response.Permisos;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 09/06/2017.
 */

public class CatalogoPermisoRes extends ServerResponse {
    private ArrayList<CatalogoTipo> tipo;

    public ArrayList<CatalogoTipo> getTipo() { return this.tipo; }

    public void setTipo(ArrayList<CatalogoTipo> tipo) { this.tipo = tipo; }
}
