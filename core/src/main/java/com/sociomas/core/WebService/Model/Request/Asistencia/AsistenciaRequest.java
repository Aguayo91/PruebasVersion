package com.sociomas.core.WebService.Model.Request.Asistencia;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 11/08/2017.
 */

public class AsistenciaRequest extends ServerRequest {
    private String numeroEmpleado;
    private String empleadoSolicita;
    private String fechaFin;
    private String fechaInicio;
    private int nivel;
    private int todos;

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getEmpleadoSolicita() {
        return empleadoSolicita;
    }

    public void setEmpleadoSolicita(String empleadoSolicita) {
        this.empleadoSolicita = empleadoSolicita;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getTodos() {
        return todos;
    }

    public void setTodos(int todos) {
        this.todos = todos;
    }
}
