package com.sociomas.core.WebService.Model.Request.SolicitarAventon;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 12/10/2017.
 */

public class AsientosReservados {
    @SerializedName("id_trayecto_aventon")
    private int idTrayectoAventon;
    @SerializedName("numero_asientos_reservados")
    private int numeroAsientosReservados;
    @SerializedName("fecha_hora_reservacion_pasajero")
    private String fechaHoraReservacion;

    public AsientosReservados(){

    }

    public AsientosReservados(int idTrayectoAventon, int numeroAsientosReservados, String fechaHoraReservacion) {
        this.idTrayectoAventon = idTrayectoAventon;
        this.numeroAsientosReservados=numeroAsientosReservados;
        this.fechaHoraReservacion=fechaHoraReservacion;
    }

    public int getIdTrayectoAventon() {
        return idTrayectoAventon;
    }

    public void setIdTrayectoAventon(int idTrayectoAventon) {
        this.idTrayectoAventon = idTrayectoAventon;
    }

    public int getNumeroAsientosReservados() {
        return numeroAsientosReservados;
    }

    public void setNumeroAsientosReservados(int numeroAsientosReservados) {
        this.numeroAsientosReservados = numeroAsientosReservados;
    }

    public String getFechaHoraReservacion() {
        return fechaHoraReservacion;
    }

    public void setFechaHoraReservacion(String fechaHoraReservacion) {
        this.fechaHoraReservacion = fechaHoraReservacion;
    }
}
