package com.sociomas.core.WebService.Model.Request.Incidencia;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 12/06/2017.
 */

@SuppressWarnings("unused")
public class RAprobarJustificante extends ServerRequest {
    private String empleado_valida;
    private String va_comentarios;
    private int id_csc_incid;

    public int getId_csc_incid() {
        return id_csc_incid;
    }

    public void setId_csc_incid(int id_csc_incid) {
        this.id_csc_incid = id_csc_incid;
    }

    public String getEmpleado_valida() {
        return empleado_valida;
    }
    public void setEmpleado_valida(String empleado_valida) {
        this.empleado_valida = empleado_valida;
    }

    public String getVa_comentarios() {
        return va_comentarios;
    }
    public void setVa_comentarios(String va_comentarios) {
        this.va_comentarios = va_comentarios;
    }

}
