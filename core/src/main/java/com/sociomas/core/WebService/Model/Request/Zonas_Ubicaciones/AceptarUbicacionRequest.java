package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 04/07/2017.
 */

@SuppressWarnings("unused")
public class AceptarUbicacionRequest  extends ServerRequest {
    private String va_numero_pos;

    public String getVaNumeroPos() { return this.va_numero_pos; }

    public void setVaNumeroPos(String va_numero_pos) { this.va_numero_pos = va_numero_pos; }

    private String va_nombre_pos;

    public String getVaNombrePos() { return this.va_nombre_pos; }

    public void setVaNombrePos(String va_nombre_pos) { this.va_nombre_pos = va_nombre_pos; }

    private String typeMov;

    public String getTypeMov() { return this.typeMov; }

    public void setTypeMov(String typeMov) { this.typeMov = typeMov; }

    private String motivo;

    public String getMotivo() { return this.motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }
}
