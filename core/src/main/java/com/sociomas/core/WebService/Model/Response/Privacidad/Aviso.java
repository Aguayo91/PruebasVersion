package com.sociomas.core.WebService.Model.Response.Privacidad;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by oemy9 on 16/11/2017.
 */
public class Aviso {

    @SerializedName("id_tipo_aviso")
    @Expose
    private Integer idTipoAviso;
    @SerializedName("leyenda")
    @Expose
    private String leyenda;
    @SerializedName("subtitulo")
    @Expose
    private String subtitulo;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("version")
    @Expose
    private Double version;

    public Integer getIdTipoAviso() {
        return idTipoAviso;
    }

    public void setIdTipoAviso(Integer idTipoAviso) {
        this.idTipoAviso = idTipoAviso;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

}
