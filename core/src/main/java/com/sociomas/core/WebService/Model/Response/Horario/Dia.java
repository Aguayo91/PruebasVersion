package com.sociomas.core.WebService.Model.Response.Horario;

/**
 * Created by oemy9 on 16/06/2017.
 */

@SuppressWarnings("unused")
public class Dia {
    private int id;
    private String nombre;
    private boolean asignado;
    private boolean pendiente;


    public Dia(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

}
