package com.sociomas.core.WebService.Model.Response.Notificaciones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 27/11/17.
 */

public class CatalogoNotificaciones implements Serializable {

    @SerializedName("contador")
    private int contador;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("id_catalogo_notificacion")
    private int id_catalogo_notificacion;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_catalogo_notificacion() {
        return id_catalogo_notificacion;
    }

    public void setId_catalogo_notificacion(int id_catalogo_notificacion) {
        this.id_catalogo_notificacion = id_catalogo_notificacion;
    }

    @Override
    public String toString() {
        return "CatalogoNotificaciones{" +
                "contador=" + contador +
                ", descripcion='" + descripcion + '\'' +
                ", id_catalogo_notificacion=" + id_catalogo_notificacion +
                '}';
    }
}
