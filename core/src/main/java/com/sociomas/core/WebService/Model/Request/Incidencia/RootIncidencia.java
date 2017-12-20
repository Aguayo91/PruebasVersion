package com.sociomas.core.WebService.Model.Request.Incidencia;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 15/05/2017.
 */

@SuppressWarnings("unused")
public class RootIncidencia extends ServerRequest {
    private String fechaFin;
    private String fechaInicio;

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
