package com.sociomas.core.WebService.Model.Response.Notificaciones;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class ListaCatalogoNotificacionesResponse extends ServerResponse implements Serializable {

    @SerializedName("CatalogoNotificaciones")
    private List<CatalogoNotificaciones> catalogoNotificaciones;

    public List<CatalogoNotificaciones> getCatalogoNotificaciones() {
        return catalogoNotificaciones;
    }

    public void setCatalogoNotificaciones(List<CatalogoNotificaciones> catalogoNotificaciones) {
        this.catalogoNotificaciones = catalogoNotificaciones;
    }

    @Override
    public String toString() {
        return "ListaCatalogoNotificacionesResponse{" +
                "catalogoNotificaciones=" + catalogoNotificaciones +
                '}';
    }
}
