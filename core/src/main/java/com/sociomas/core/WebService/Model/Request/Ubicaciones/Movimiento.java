package com.sociomas.core.WebService.Model.Request.Ubicaciones;

import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 30/03/2017.
 *
 * POJO DEL WEBSERVICE MOVIMIENTO
 *
 *
 */

@SuppressWarnings("unused")
public class Movimiento extends ServerRequest {
    private String dec_bateria;

    public String getDecBateria() { return this.dec_bateria; }

    public void setDecBateria(String dec_bateria) { this.dec_bateria = dec_bateria; }

    private String dec_longitud;

    public String getDecLongitud() { return this.dec_longitud; }

    public void setDecLongitud(String dec_longitud) { this.dec_longitud = dec_longitud; }

    private String dec_latitud;

    public String getDecLatitud() { return this.dec_latitud; }

    public void setDecLatitud(String dec_latitud) { this.dec_latitud = dec_latitud; }

    private int provider;

    public int getProvider() { return this.provider; }

    public void setProvider(int provider) { this.provider = provider; }

    private String dt_fecha_hora;

    public String getDtFechaHora() { return this.dt_fecha_hora; }

    public void setDtFechaHora(String dt_fecha_hora) { this.dt_fecha_hora = dt_fecha_hora; }

    private String velocidad;

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    private String actividad;

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    private String zonahorario;

    public String getZonahorario() {
        return zonahorario;
    }

    public void setZonahorario(String zonahorario) {
        this.zonahorario = zonahorario;
    }

    private String accuracy;

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

}
