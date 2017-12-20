package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo on 12/09/17.
 */

public class NominaDetalleMensajes implements Serializable{
    @SerializedName("detalleNumero")
    private String detalleNumero;

    @SerializedName("llave")
    private String llave;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("tipoRegsitro")
    private String tipoRegsitro;

    public String getDetalleNumero() {
        return detalleNumero;
    }

    public void setDetalleNumero(String detalleNumero) {
        this.detalleNumero = detalleNumero;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoRegsitro() {
        return tipoRegsitro;
    }

    public void setTipoRegsitro(String tipoRegsitro) {
        this.tipoRegsitro = tipoRegsitro;
    }

    @Override
    public String toString() {
        return "NominaDetalleMensajes{" +
                "detalleNumero='" + detalleNumero + '\'' +
                ", llave='" + llave + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", tipoRegsitro='" + tipoRegsitro + '\'' +
                '}';
    }
}
