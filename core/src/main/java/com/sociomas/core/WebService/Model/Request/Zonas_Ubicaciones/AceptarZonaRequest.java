package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 04/07/2017.
 */

@SuppressWarnings("unused")
public class AceptarZonaRequest extends ServerRequest {
    private int id_csc_zo_pos;

    public int getIdCscZoPos() { return this.id_csc_zo_pos; }

    public void setIdCscZoPos(int id_csc_zo_pos) { this.id_csc_zo_pos = id_csc_zo_pos; }

    private String status;

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    private String va_comentario;

    public String getVaComentario() { return this.va_comentario; }

    public void setVaComentario(String va_comentario) { this.va_comentario = va_comentario; }
}
