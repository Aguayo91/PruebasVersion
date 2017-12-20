package com.sociomas.core.WebService.Model.Response.Aventones;

/**
 * Created by jromeromar on 09/10/2017.
 */

public class PreferenciasSolicitud {

    private int imagenPos;
    private String nombre;
    private String status;

    public PreferenciasSolicitud(int imagenPos, String nombre, String status){
        this.imagenPos =imagenPos;
        this.nombre=nombre;
        this.status=status;
    }

    public int getImagenPos() {
        return imagenPos;
    }

    public void setImagenPos(int imagenPos) {
        this.imagenPos = imagenPos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
