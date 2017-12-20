package com.sociomas.core.WebService.Model.Response.Menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.List;

/**
 * Created by oemy9 on 03/11/2017.
 */

public class MenuResponse extends ServerResponse {
    @SerializedName("ListaOpcionesMenu")
    @Expose
    private List<Integer> listaOpcionesMenu = null;

    public List<Integer> getListaOpcionesMenu() {
        return listaOpcionesMenu;
    }

    public void setListaOpcionesMenu(List<Integer> listaOpcionesMenu) {
        this.listaOpcionesMenu = listaOpcionesMenu;
    }
}
