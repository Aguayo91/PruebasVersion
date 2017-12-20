package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class ReciboNomina extends ServerResponse implements Serializable {

    @SerializedName("banderaRet")
    private String banderaRet;

    @SerializedName("beneficiario")
    private String beneficiario;

    @SerializedName("compania")
    private String compania;

    @SerializedName("concepto")
    private String concepto;

    @SerializedName("conceptoComprobante")
    private String conceptoComprobante;

    @SerializedName("cuentaAbono")
    private String cuentaAbono;

    @SerializedName("deducciones")
    private float deducciones;

    @SerializedName("descPuesto")
    private String descPuesto;

    @SerializedName("fechaFinPeriodo")
    private String fechaFinPeriodo;

    @SerializedName("fechaInicioPeriodo")
    private String fechaInicioPeriodo;

    @SerializedName("folioAlnova")
    private String folioAlnova;

    @SerializedName("folioOperacion")
    private String folioOperacion;

    @SerializedName("importe")
    private String importe;

    @SerializedName("llave")
    private String llave;

    @SerializedName("nssEmpleado")
    private String nssEmpleado;

    @SerializedName("numPeriodo")
    private String numPeriodo;

    @SerializedName("numeroRegistro")
    private String numeroRegistro;


    @SerializedName("percepciones")
    private String percepciones;

    @SerializedName("rfcEmpleado")
    private String rfcEmpleado;

    @SerializedName("statusPago")
    private String statusPago;

    @SerializedName("idEmpresa")
    private String idEmpresa;

    @SerializedName("banderaSumatoria")
    private String banderaSumatoria;

    @SerializedName("contrato")
    private Object contrato;

    @SerializedName("contratos")
    private Object contratos;

    @SerializedName("importeSpecified")
    private boolean importeSpecified;

    @SerializedName("detalleRecibo")
    private ReciboNominaDetalleResponse detalleRecibo;

    public String getBanderaRet() {
        return banderaRet;
    }

    public void setBanderaRet(String banderaRet) {
        this.banderaRet = banderaRet;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConceptoComprobante() {
        return conceptoComprobante;
    }

    public void setConceptoComprobante(String conceptoComprobante) {
        this.conceptoComprobante = conceptoComprobante;
    }

    public String getCuentaAbono() {
        return cuentaAbono;
    }

    public void setCuentaAbono(String cuentaAbono) {
        this.cuentaAbono = cuentaAbono;
    }

    public float getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(float deducciones) {
        this.deducciones = deducciones;
    }

    public String getDescPuesto() {
        return descPuesto;
    }

    public void setDescPuesto(String descPuesto) {
        this.descPuesto = descPuesto;
    }

    public String getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(String fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public String getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(String fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public String getFolioAlnova() {
        return folioAlnova;
    }

    public void setFolioAlnova(String folioAlnova) {
        this.folioAlnova = folioAlnova;
    }

    public String getFolioOperacion() {
        return folioOperacion;
    }

    public void setFolioOperacion(String folioOperacion) {
        this.folioOperacion = folioOperacion;
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

    public String getNssEmpleado() {
        return nssEmpleado;
    }

    public void setNssEmpleado(String nssEmpleado) {
        this.nssEmpleado = nssEmpleado;
    }

    public String getNumPeriodo() {
        return numPeriodo;
    }

    public void setNumPeriodo(String numPeriodo) {
        this.numPeriodo = numPeriodo;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getPercepciones() {
        return percepciones;
    }

    public void setPercepciones(String percepciones) {
        this.percepciones = percepciones;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getStatusPago() {
        return statusPago;
    }

    public void setStatusPago(String statusPago) {
        this.statusPago = statusPago;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getBanderaSumatoria() {
        return banderaSumatoria;
    }

    public void setBanderaSumatoria(String banderaSumatoria) {
        this.banderaSumatoria = banderaSumatoria;
    }

    public Object getContrato() {
        return contrato;
    }

    public void setContrato(Object contrato) {
        this.contrato = contrato;
    }

    public Object getContratos() {
        return contratos;
    }

    public void setContratos(Object contratos) {
        this.contratos = contratos;
    }

    public boolean isImporteSpecified() {
        return importeSpecified;
    }

    public void setImporteSpecified(boolean importeSpecified) {
        this.importeSpecified = importeSpecified;
    }

    public ReciboNominaDetalleResponse getDetalleRecibo() {
        return detalleRecibo;
    }

    public void setDetalleRecibo(ReciboNominaDetalleResponse detalleRecibo) {
        this.detalleRecibo = detalleRecibo;
    }

    @Override
    public String toString() {
        return "ReciboNomina{" +
                "banderaRet='" + banderaRet + '\'' +
                ", beneficiario='" + beneficiario + '\'' +
                ", compania='" + compania + '\'' +
                ", concepto='" + concepto + '\'' +
                ", conceptoComprobante='" + conceptoComprobante + '\'' +
                ", cuentaAbono='" + cuentaAbono + '\'' +
                ", deducciones=" + deducciones +
                ", descPuesto='" + descPuesto + '\'' +
                ", fechaFinPeriodo='" + fechaFinPeriodo + '\'' +
                ", fechaInicioPeriodo='" + fechaInicioPeriodo + '\'' +
                ", folioAlnova='" + folioAlnova + '\'' +
                ", folioOperacion='" + folioOperacion + '\'' +
                ", importe='" + importe + '\'' +
                ", llave='" + llave + '\'' +
                ", nssEmpleado='" + nssEmpleado + '\'' +
                ", numPeriodo='" + numPeriodo + '\'' +
                ", numeroRegistro='" + numeroRegistro + '\'' +
                ", percepciones='" + percepciones + '\'' +
                ", rfcEmpleado='" + rfcEmpleado + '\'' +
                ", statusPago='" + statusPago + '\'' +
                ", idEmpresa='" + idEmpresa + '\'' +
                ", banderaSumatoria='" + banderaSumatoria + '\'' +
                ", contrato=" + contrato +
                ", contratos=" + contratos +
                ", importeSpecified=" + importeSpecified +
                ", detalleRecibo=" + detalleRecibo +
                '}';
    }
}
