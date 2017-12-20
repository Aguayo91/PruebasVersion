package com.sociomas.core.WebService.Model.Response.Privacidad;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 08/12/17.
 */

public class DatoLegalRespose implements Serializable{

    @SerializedName("id_rel_leyenda_aviso")
    private int id_rel_leyenda_aviso;

    @SerializedName("subtitulo")
    private String subtitulo;

    @SerializedName("id_tipo_aviso")
    private int id_tipo_aviso;

    @SerializedName("dt_fecha_hora")
    private String dt_fecha_hora;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("version")
    private int version;

    @SerializedName("leyenda")
    private String leyenda;

    @SerializedName("aceptado")
    private boolean aceptado;

    public int getId_rel_leyenda_aviso() {
        return id_rel_leyenda_aviso;
    }

    public void setId_rel_leyenda_aviso(int id_rel_leyenda_aviso) {
        this.id_rel_leyenda_aviso = id_rel_leyenda_aviso;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public int getId_tipo_aviso() {
        return id_tipo_aviso;
    }

    public void setId_tipo_aviso(int id_tipo_aviso) {
        this.id_tipo_aviso = id_tipo_aviso;
    }

    public String getDt_fecha_hora() {
        return dt_fecha_hora;
    }

    public void setDt_fecha_hora(String dt_fecha_hora) {
        this.dt_fecha_hora = dt_fecha_hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    @Override
    public String toString() {
        return "DatoLegalRespose{" +
                "id_rel_leyenda_aviso=" + id_rel_leyenda_aviso +
                ", subtitulo='" + subtitulo + '\'' +
                ", id_tipo_aviso=" + id_tipo_aviso +
                ", dt_fecha_hora='" + dt_fecha_hora + '\'' +
                ", titulo='" + titulo + '\'' +
                ", version=" + version +
                ", leyenda='" + leyenda + '\'' +
                ", aceptado=" + aceptado +
                '}';
    }
}
