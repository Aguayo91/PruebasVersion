package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 12/09/17.
 */

public class NominaDetallePercepcionDeducciones implements Serializable{

    @SerializedName("anio")
    private String anio;

    @SerializedName("concepto")
    private String concepto;

    @SerializedName("conceptoNominal")
    private String conceptoNominal;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("detalleNumero")
    private String detalleNumero;

    @SerializedName("dias")
    private String dias;

    @SerializedName("importe")
    private String importe;

    @SerializedName("llave")
    private String llave;

    @SerializedName("numCompania")
    private String numCompania;

    @SerializedName("numEmpleado")
    private String numEmpleado;

    @SerializedName("numEmpresa")
    private String numEmpresa;

    @SerializedName("periodo")
    private String periodo;

    @SerializedName("tipoCompania")
    private String tipoCompania;

    @SerializedName("tipoPeriodo")
    private String tipoPeriodo;

    @SerializedName("tipoRegsitro")
    private String tipoRegsitro;

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConceptoNominal() {
        return conceptoNominal;
    }

    public void setConceptoNominal(String conceptoNominal) {
        this.conceptoNominal = conceptoNominal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalleNumero() {
        return detalleNumero;
    }

    public void setDetalleNumero(String detalleNumero) {
        this.detalleNumero = detalleNumero;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNumCompania() {
        return numCompania;
    }

    public void setNumCompania(String numCompania) {
        this.numCompania = numCompania;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(String numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipoCompania() {
        return tipoCompania;
    }

    public void setTipoCompania(String tipoCompania) {
        this.tipoCompania = tipoCompania;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public String getTipoRegsitro() {
        return tipoRegsitro;
    }

    public void setTipoRegsitro(String tipoRegsitro) {
        this.tipoRegsitro = tipoRegsitro;
    }

    @Override
    public String toString() {
        return "NominaDetallePercepcionDeducciones{" +
                "anio='" + anio + '\'' +
                ", concepto='" + concepto + '\'' +
                ", conceptoNominal='" + conceptoNominal + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", detalleNumero='" + detalleNumero + '\'' +
                ", dias='" + dias + '\'' +
                ", importe='" + importe + '\'' +
                ", llave='" + llave + '\'' +
                ", numCompania='" + numCompania + '\'' +
                ", numEmpleado='" + numEmpleado + '\'' +
                ", numEmpresa='" + numEmpresa + '\'' +
                ", periodo='" + periodo + '\'' +
                ", tipoCompania='" + tipoCompania + '\'' +
                ", tipoPeriodo='" + tipoPeriodo + '\'' +
                ", tipoRegsitro='" + tipoRegsitro + '\'' +
                '}';
    }
}
