package com.sociomas.core.WebService.Model.Response.Incidencia;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 05/06/2017.
 */

public class CatalogoResponse extends ServerResponse {
    private ArrayList<Catalogo> lista;
    public ArrayList<Catalogo> getLista() { return this.lista; }
    public void setLista(ArrayList<Catalogo> lista) { this.lista = lista; }
}
