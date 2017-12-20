package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 12/09/17.
 */

public class NominaDetalleCabecera implements Serializable {

    @SerializedName("anio")
    private String anio;

    @SerializedName("centroCostos")
    private String centroCostos;

    @SerializedName("claveDepto")
    private String claveDepto;

    @SerializedName("creditoSalario")
    private String creditoSalario;

    @SerializedName("fechaFinPago")
    private String fechaFinPago;

    @SerializedName("fechaIniPago")
    private String fechaIniPago;

    @SerializedName("fechaPago")
    private String fechaPago;

    @SerializedName("llave")
    private String llave;

    @SerializedName("nombreCompania")
    private String nombreCompania;

    @SerializedName("nombreEmpleado")
    private String nombreEmpleado;

    @SerializedName("numCompania")
    private String numCompania;

    @SerializedName("numEmpleado")
    private String numEmpleado;

    @SerializedName("numeroDetalle")
    private String numeroDetalle;

    @SerializedName("numeroEmpresa")
    private String numeroEmpresa;

    @SerializedName("numeroIMSS")
    private String numeroIMSS;

    @SerializedName("periodo")
    private String periodo;

    @SerializedName("regsitroPatronal")
    private String regsitroPatronal;

    @SerializedName("rfcEmpleado")
    private String rfcEmpleado;

    @SerializedName("salarioBaseCotiza")
    private String salarioBaseCotiza;

    @SerializedName("salarioDiario")
    private String salarioDiario;

    @SerializedName("tipoCompania")
    private String tipoCompania;

    @SerializedName("tipoPeriodo")
    private String tipoPeriodo;

    @SerializedName("tipoRegistro")
    private String tipoRegistro;

    @SerializedName("totalCreditos")
    private String totalCreditos;

    @SerializedName("totalDeduccciones")
    private String totalDeduccciones;

    @SerializedName("totalFinal")
    private String totalFinal;

    @SerializedName("totalLetra")
    private String totalLetra;

    @SerializedName("totalPercepciones")
    private String totalPercepciones;

    @SerializedName("valesDespensa")
    private String valesDespensa;

    @SerializedName("banderaContrato")
    private Object banderaContrato;

    @SerializedName("numeroRegistro")
    private Object numeroRegistro;

    @SerializedName("concepto")
    private String concepto;

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }

    public String getClaveDepto() {
        return claveDepto;
    }

    public void setClaveDepto(String claveDepto) {
        this.claveDepto = claveDepto;
    }

    public String getCreditoSalario() {
        return creditoSalario;
    }

    public void setCreditoSalario(String creditoSalario) {
        this.creditoSalario = creditoSalario;
    }

    public String getFechaFinPago() {
        return fechaFinPago;
    }

    public void setFechaFinPago(String fechaFinPago) {
        this.fechaFinPago = fechaFinPago;
    }

    public String getFechaIniPago() {
        return fechaIniPago;
    }

    public void setFechaIniPago(String fechaIniPago) {
        this.fechaIniPago = fechaIniPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
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

    public String getNumeroDetalle() {
        return numeroDetalle;
    }

    public void setNumeroDetalle(String numeroDetalle) {
        this.numeroDetalle = numeroDetalle;
    }

    public String getNumeroEmpresa() {
        return numeroEmpresa;
    }

    public void setNumeroEmpresa(String numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
    }

    public String getNumeroIMSS() {
        return numeroIMSS;
    }

    public void setNumeroIMSS(String numeroIMSS) {
        this.numeroIMSS = numeroIMSS;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getRegsitroPatronal() {
        return regsitroPatronal;
    }

    public void setRegsitroPatronal(String regsitroPatronal) {
        this.regsitroPatronal = regsitroPatronal;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getSalarioBaseCotiza() {
        return salarioBaseCotiza;
    }

    public void setSalarioBaseCotiza(String salarioBaseCotiza) {
        this.salarioBaseCotiza = salarioBaseCotiza;
    }

    public String getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(String salarioDiario) {
        this.salarioDiario = salarioDiario;
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

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(String totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public String getTotalDeduccciones() {
        return totalDeduccciones;
    }

    public void setTotalDeduccciones(String totalDeduccciones) {
        this.totalDeduccciones = totalDeduccciones;
    }

    public String getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(String totalFinal) {
        this.totalFinal = totalFinal;
    }

    public String getTotalLetra() {
        return totalLetra;
    }

    public void setTotalLetra(String totalLetra) {
        this.totalLetra = totalLetra;
    }

    public String getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(String totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    public String getValesDespensa() {
        return valesDespensa;
    }

    public void setValesDespensa(String valesDespensa) {
        this.valesDespensa = valesDespensa;
    }

    public void setBanderaContrato(Object banderaContrato) {
        this.banderaContrato = banderaContrato;
    }

    public Object getBanderaContrato() {
        return banderaContrato;
    }

    public void setNumeroRegistro(Object numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Object getNumeroRegistro() {
        return numeroRegistro;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Override
    public String toString() {
        return "NominaDetalleCabecera{" +
                "anio='" + anio + '\'' +
                ", centroCostos='" + centroCostos + '\'' +
                ", claveDepto='" + claveDepto + '\'' +
                ", creditoSalario='" + creditoSalario + '\'' +
                ", fechaFinPago='" + fechaFinPago + '\'' +
                ", fechaIniPago='" + fechaIniPago + '\'' +
                ", fechaPago='" + fechaPago + '\'' +
                ", llave='" + llave + '\'' +
                ", nombreCompania='" + nombreCompania + '\'' +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", numCompania='" + numCompania + '\'' +
                ", numEmpleado='" + numEmpleado + '\'' +
                ", numeroDetalle='" + numeroDetalle + '\'' +
                ", numeroEmpresa='" + numeroEmpresa + '\'' +
                ", numeroIMSS='" + numeroIMSS + '\'' +
                ", periodo='" + periodo + '\'' +
                ", regsitroPatronal='" + regsitroPatronal + '\'' +
                ", rfcEmpleado='" + rfcEmpleado + '\'' +
                ", salarioBaseCotiza='" + salarioBaseCotiza + '\'' +
                ", salarioDiario='" + salarioDiario + '\'' +
                ", tipoCompania='" + tipoCompania + '\'' +
                ", tipoPeriodo='" + tipoPeriodo + '\'' +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", totalCreditos='" + totalCreditos + '\'' +
                ", totalDeduccciones='" + totalDeduccciones + '\'' +
                ", totalFinal='" + totalFinal + '\'' +
                ", totalLetra='" + totalLetra + '\'' +
                ", totalPercepciones='" + totalPercepciones + '\'' +
                ", valesDespensa='" + valesDespensa + '\'' +
                ", banderaContrato=" + banderaContrato +
                ", numeroRegistro=" + numeroRegistro +
                ", concepto=" + concepto +
                '}';
    }
}
