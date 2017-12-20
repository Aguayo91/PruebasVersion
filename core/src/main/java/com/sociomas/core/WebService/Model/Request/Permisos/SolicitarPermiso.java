package com.sociomas.core.WebService.Model.Request.Permisos;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 09/06/2017.
 */

@SuppressWarnings("unused")
public class SolicitarPermiso extends ServerRequest {
    private String dt_fecha_hora_inicial;
    private String dt_fecha_hora_final;
    private String tipo_exclusion;
    private String motivo;


    public String getDt_fecha_hora_inicial() {
        return dt_fecha_hora_inicial;
    }

    public void setDt_fecha_hora_inicial(String dt_fecha_hora_inicial) {
        this.dt_fecha_hora_inicial = dt_fecha_hora_inicial;
    }

    public String getDt_fecha_hora_final() {
        return dt_fecha_hora_final;
    }

    public void setDt_fecha_hora_final(String dt_fecha_hora_final) {
        this.dt_fecha_hora_final = dt_fecha_hora_final;
    }

    public String getTipo_exclusion() {
        return tipo_exclusion;
    }

    public void setTipo_exclusion(String tipo_exclusion) {
        this.tipo_exclusion = tipo_exclusion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
