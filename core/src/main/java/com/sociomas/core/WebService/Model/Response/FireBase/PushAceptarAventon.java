package com.sociomas.core.WebService.Model.Response.FireBase;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 13/10/2017.
 */

public class PushAceptarAventon {
    @SerializedName("id_rel_usuario_asientos_reservados")
    private int idRelUsuarioAsientos;
    @SerializedName("nombre_empleado")
    private String nombreEmpleado;
    @SerializedName("numero_empleado")
    private String numeroEmpleado;
    @SerializedName("origen")
    private String origen;
    @SerializedName("destino")
    private String destino;

    public int getIdRelUsuarioAsientos() {
        return idRelUsuarioAsientos;
    }

    public void setIdRelUsuarioAsientos(int idRelUsuarioAsientos) {
        this.idRelUsuarioAsientos = idRelUsuarioAsientos;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public String toString() {
        return "{" +
                "id_rel_usuario_asientos_reservados=" + idRelUsuarioAsientos +
                ", nombre_empleado='" + nombreEmpleado + '\'' +
                ", numero_empleado='" + numeroEmpleado + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                '}';
    }
}
