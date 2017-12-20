package com.sociomas.core.WebService.Model.Request.Perfil;

public class CaractEmpleado {

    public CaractEmpleado(String observaciones,String tiempo_tolerancia,int id_rel_caracteristicas_usuario) {
        this.observaciones = observaciones;
        this.tiempo_tolerancia=tiempo_tolerancia;
        this.id_rel_caracteristicas_usuario = id_rel_caracteristicas_usuario;
    }
    private int id_rel_caracteristicas_usuario;


    public CaractEmpleado(String observaciones, String minutosFormato) {
    }

    public CaractEmpleado() {

    }


    public int getIdRelCaracteristicasUsuario() { return this.id_rel_caracteristicas_usuario; }

    public void setIdRelCaracteristicasUsuario(int id_rel_caracteristicas_usuario) { this.id_rel_caracteristicas_usuario = id_rel_caracteristicas_usuario; }

    private String observaciones;

    public String getObservaciones() { return this.observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    private String tiempo_tolerancia;

    public String getTiempoTolerancia() { return this.tiempo_tolerancia; }

    public void setTiempoTolerancia(String tiempo_tolerancia) { this.tiempo_tolerancia = tiempo_tolerancia; }

}