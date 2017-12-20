package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import java.io.Serializable;

/**
 * Created by GiioToledo on 14/11/17.
 */

public class UnidadNegocio implements Serializable {
    private String id;
    private String descripcion;
    private int drawable;
    private String imgSrc;

    public UnidadNegocio(String id, String descripcion, int drawable){
        this.id = id;
        this.descripcion = descripcion;
        this.drawable = drawable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "UnidadNegocio{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", drawable=" + drawable +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
