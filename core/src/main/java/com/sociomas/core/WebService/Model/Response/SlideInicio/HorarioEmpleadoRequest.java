package com.sociomas.core.WebService.Model.Response.SlideInicio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 17/11/17.
 */

public class HorarioEmpleadoRequest implements Serializable {

    @SerializedName("comentario")
    private String comentario;

    @SerializedName("dia_semana")
    private int dia_semana;

    @SerializedName("nv_hora_entrada")
    private String nv_hora_entrada;

    @SerializedName("nv_hora_salida")
    private String nv_hora_salida;

    @SerializedName("libre")
    private int libre;

    @SerializedName("cancelar")
    private int cancelar;

    @SerializedName("rechazar")
    private int rechazar;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(int dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getNv_hora_entrada() {
        return nv_hora_entrada;
    }

    public void setNv_hora_entrada(String nv_hora_entrada) {
        this.nv_hora_entrada = nv_hora_entrada;
    }

    public String getNv_hora_salida() {
        return nv_hora_salida;
    }

    public void setNv_hora_salida(String nv_hora_salida) {
        this.nv_hora_salida = nv_hora_salida;
    }

    public int getLibre() {
        return libre;
    }

    public void setLibre(int libre) {
        this.libre = libre;
    }

    public int getCancelar() {
        return cancelar;
    }

    public void setCancelar(int cancelar) {
        this.cancelar = cancelar;
    }

    public int getRechazar() {
        return rechazar;
    }

    public void setRechazar(int rechazar) {
        this.rechazar = rechazar;
    }

    @Override
    public String toString() {
        return "HorarioEmpleadoRequest{" +
                "comentario='" + comentario + '\'' +
                ", dia_semana=" + dia_semana +
                ", nv_hora_entrada='" + nv_hora_entrada + '\'' +
                ", nv_hora_salida='" + nv_hora_salida + '\'' +
                ", libre=" + libre +
                ", cancelar=" + cancelar +
                ", rechazar=" + rechazar +
                '}';
    }
}
