package com.sociomas.core.WebService.Model.Response.Promociones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class PromoResponse extends ServerResponse {
    @SerializedName("ListaDescuentosDetalleCategoria")
    private ArrayList<PromoLista>listDescuentosDetalle;

    @SerializedName("ListaDescuentosCategoria")
    @Expose
    private ArrayList<PromoLista> listaDescuentosCategoria = null;

    public ArrayList<PromoLista> getListaDescuentosCategoria() {
        return listaDescuentosCategoria;
    }

    public void setListaDescuentosCategoria(ArrayList<PromoLista> listaDescuentosCategoria) {
        this.listaDescuentosCategoria = listaDescuentosCategoria;
    }

    public ArrayList<PromoLista> getListDescuentosDetalle() {
        return listDescuentosDetalle;
    }

    public void setListDescuentosDetalle(ArrayList<PromoLista> listDescuentosDetalle) {
        this.listDescuentosDetalle = listDescuentosDetalle;
    }
}

