package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 27/06/2017.
 */

@SuppressWarnings("unused")
public class AsignarZonaRequest extends ServerRequest
{

    private int id_csc_zo_pos;
    private String va_comentario;
    public AsignarZonaRequest(){

    }

    public AsignarZonaRequest(int id_csc_zo_pos, String va_comentario) {
        this.id_csc_zo_pos = id_csc_zo_pos;
        this.va_comentario=va_comentario;
    }

    public int getId_csc_zo_pos() {
        return id_csc_zo_pos;
    }

    public void setId_csc_zo_pos(int id_csc_zo_pos) {
        this.id_csc_zo_pos = id_csc_zo_pos;
    }

    public String getVa_comentario() {
        return va_comentario;
    }

    public void setVa_comentario(String va_comentario) {
        this.va_comentario = va_comentario;
    }
}
