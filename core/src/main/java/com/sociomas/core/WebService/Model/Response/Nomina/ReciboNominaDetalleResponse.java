package com.sociomas.core.WebService.Model.Response.Nomina;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class ReciboNominaDetalleResponse implements Serializable {

    @SerializedName("cabecera")
    private NominaDetalleCabecera cabecera;

    @SerializedName("percepcionDeduccions")
    private List<NominaDetallePercepcionDeducciones> percepcionDeduccions;

    @SerializedName("mensajes")
    private List<NominaDetalleMensajes> mensajes;

    @SerializedName("templates")
    private Object templates;

    public NominaDetalleCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(NominaDetalleCabecera cabecera) {
        this.cabecera = cabecera;
    }

    public List<NominaDetallePercepcionDeducciones> getPercepcionDeduccions() {
        return percepcionDeduccions;
    }

    public void setPercepcionDeduccions(List<NominaDetallePercepcionDeducciones> percepcionDeduccions) {
        this.percepcionDeduccions = percepcionDeduccions;
    }

    public List<NominaDetalleMensajes> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<NominaDetalleMensajes> mensajes) {
        this.mensajes = mensajes;
    }

    public Object getTemplates() {
        return templates;
    }

    public void setTemplates(Object templates) {
        this.templates = templates;
    }

    @Override
    public String toString() {
        return "ReciboNominaDetalleResponse{" +
                "cabecera=" + cabecera +
                ", percepcionDeduccions=" + percepcionDeduccions +
                ", mensajes=" + mensajes +
                ", templates=" + templates +
                '}';
    }
}
