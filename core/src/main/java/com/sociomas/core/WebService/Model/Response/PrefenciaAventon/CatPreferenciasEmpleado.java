
package com.sociomas.core.WebService.Model.Response.PrefenciaAventon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatPreferenciasEmpleado {

    public CatPreferenciasEmpleado(){

    }

    public CatPreferenciasEmpleado(Integer idPreferencia,Boolean valor) {
        this.idPreferencia = idPreferencia;
        this.valor=valor;
    }

    public CatPreferenciasEmpleado(Integer idPreferencia,Boolean valor,Integer idRelacionPreferenciaUsuario) {
        this.idPreferencia = idPreferencia;
        this.valor=valor;
        this.idRelacionPreferenciaUsuario = idRelacionPreferenciaUsuario;
    }

    @SerializedName("desc_clasificacion_preferencia")
    @Expose
    private String descClasificacionPreferencia;
    @SerializedName("desc_preferencia")
    @Expose
    private String descPreferencia;
    @SerializedName("desc_tipo_preferencia")
    @Expose
    private String descTipoPreferencia;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion;
    @SerializedName("fechaRegistro")
    @Expose
    private String fechaRegistro;
    @SerializedName("id_clasificacion_preferencia")
    @Expose
    private Integer idClasificacionPreferencia;
    @SerializedName("id_preferencia")
    @Expose
    private Integer idPreferencia;
    @SerializedName("id_relacion_preferencia_usuario")
    @Expose
    private Integer idRelacionPreferenciaUsuario;
    @SerializedName("id_tipo_preferencia")
    @Expose
    private Integer idTipoPreferencia;
    @SerializedName("titulo_grupo")
    @Expose
    private String tituloGrupo;
    @SerializedName("valor")
    @Expose
    private Boolean valor;

    public String getDescClasificacionPreferencia() {
        return descClasificacionPreferencia;
    }

    public void setDescClasificacionPreferencia(String descClasificacionPreferencia) {
        this.descClasificacionPreferencia = descClasificacionPreferencia;
    }

    public String getDescPreferencia() {
        return descPreferencia;
    }

    public void setDescPreferencia(String descPreferencia) {
        this.descPreferencia = descPreferencia;
    }

    public String getDescTipoPreferencia() {
        return descTipoPreferencia;
    }

    public void setDescTipoPreferencia(String descTipoPreferencia) {
        this.descTipoPreferencia = descTipoPreferencia;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdClasificacionPreferencia() {
        return idClasificacionPreferencia;
    }

    public void setIdClasificacionPreferencia(Integer idClasificacionPreferencia) {
        this.idClasificacionPreferencia = idClasificacionPreferencia;
    }

    public Integer getIdPreferencia() {
        return idPreferencia;
    }

    public void setIdPreferencia(Integer idPreferencia) {
        this.idPreferencia = idPreferencia;
    }

    public Integer getIdRelacionPreferenciaUsuario() {
        return idRelacionPreferenciaUsuario;
    }

    public void setIdRelacionPreferenciaUsuario(Integer idRelacionPreferenciaUsuario) {
        this.idRelacionPreferenciaUsuario = idRelacionPreferenciaUsuario;
    }

    public Integer getIdTipoPreferencia() {
        return idTipoPreferencia;
    }

    public void setIdTipoPreferencia(Integer idTipoPreferencia) {
        this.idTipoPreferencia = idTipoPreferencia;
    }

    public String getTituloGrupo() {
        return tituloGrupo;
    }

    public void setTituloGrupo(String tituloGrupo) {
        this.tituloGrupo = tituloGrupo;
    }

    public Boolean getValor() {
        return valor;
    }

    public void setValor(Boolean valor) {
        this.valor = valor;
    }

}
