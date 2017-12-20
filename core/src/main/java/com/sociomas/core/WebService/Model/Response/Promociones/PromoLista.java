package com.sociomas.core.WebService.Model.Response.Promociones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class PromoLista implements Serializable {
    @SerializedName("CATE_TOTAL")
    @Expose
    private String total;
    @SerializedName("SCAT_DESC")
    @Expose
    private String categoria;
    @SerializedName("SCAT_LLAV_PR")
    @Expose
    private Integer idCategoria;

    @SerializedName("CATE_DESC")
    @Expose
    private String cATEDESC;
    @SerializedName("CATE_LLAV_PR")
    @Expose
    private String cATELLAVPR;

    @SerializedName("DPUBL_ARCH")
    @Expose
    private String dPUBLARCH;
    @SerializedName("INTE_DESC")
    @Expose
    private String iNTEDESC;
    @SerializedName("INTE_LLAV_PR")
    @Expose
    private String iNTELLAVPR;
    @SerializedName("INTE_NOMBRE")
    @Expose
    private String nombreEmpresa;
    @SerializedName("NOMBRE_ANEXO")
    @Expose
    private String nOMBREANEXO;
    @SerializedName("PUBL_COND")
    @Expose
    private String condiciones;
    @SerializedName("PUBL_DES")
    @Expose
    private String descuento;
    @SerializedName("PUBL_LIGA")
    @Expose
    private String urlDescuento;
    @SerializedName("telefono")
    @Expose
    private String telefono;



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getcATEDESC() {
        return cATEDESC;
    }

    public void setcATEDESC(String cATEDESC) {
        this.cATEDESC = cATEDESC;
    }

    public String getcATELLAVPR() {
        return cATELLAVPR;
    }

    public void setcATELLAVPR(String cATELLAVPR) {
        this.cATELLAVPR = cATELLAVPR;
    }

    public String getdPUBLARCH() {
        return dPUBLARCH;
    }

    public void setdPUBLARCH(String dPUBLARCH) {
        this.dPUBLARCH = dPUBLARCH;
    }

    public String getiNTEDESC() {
        return iNTEDESC;
    }

    public void setiNTEDESC(String iNTEDESC) {
        this.iNTEDESC = iNTEDESC;
    }

    public String getiNTELLAVPR() {
        return iNTELLAVPR;
    }

    public void setiNTELLAVPR(String iNTELLAVPR) {
        this.iNTELLAVPR = iNTELLAVPR;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getnOMBREANEXO() {
        return nOMBREANEXO;
    }

    public void setnOMBREANEXO(String nOMBREANEXO) {
        this.nOMBREANEXO = nOMBREANEXO;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getUrlDescuento() {
        return urlDescuento;
    }

    public void setUrlDescuento(String urlDescuento) {
        this.urlDescuento = urlDescuento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
