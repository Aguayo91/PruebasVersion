package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.DataBaseModel.QrPendientes;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.Date;

/**
 * Created by jmarquezu on 12/10/2017.
 */

public class AltaQR extends ServerRequest {
    private String id_num_empleado_conductor;
    private double accuracy;
    private double latitud;
    private double longitud;
    @SerializedName("datosQR")
    private String datosQR;
    @SerializedName("fecha_dispositivo")
    private String fechaDispositivo;

    public String getDatosQR() {
        return datosQR;
    }

    public void setDatosQR(String datosQR) {
        this.datosQR = datosQR;
    }

    public String getId_num_empleado_conductor() {
        return id_num_empleado_conductor;
    }

    public void setId_num_empleado_conductor(String id_num_empleado_conductor) {
        this.id_num_empleado_conductor = id_num_empleado_conductor;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getFechaDispositivo() {
        return fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo) {
        this.fechaDispositivo = fechaDispositivo;
    }

    public QrPendientes toPendiente(){
        QrPendientes qrPendientes=new QrPendientes();
        qrPendientes.setInfoQr(this.getDatosQR());
        qrPendientes.setLat(this.getLatitud());
        qrPendientes.setLng(this.getLongitud());
        qrPendientes.setFechaHora(new Date());
        qrPendientes.setAccuracy(this.getAccuracy());
        qrPendientes.setPendiente(true);
        return qrPendientes;
    }
}
