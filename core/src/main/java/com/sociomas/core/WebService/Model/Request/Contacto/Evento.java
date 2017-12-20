package com.sociomas.core.WebService.Model.Request.Contacto;


import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 21/04/2017.
 */

@SuppressWarnings("unused")
public class Evento extends ServerRequest {

    @SerializedName("evento")
    private String evento;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("comentario")
    private String comentario;
    @SerializedName("idEvento")
    private String idEvento;
    @SerializedName("Success")
    private boolean Success;

    public boolean isSetSuccess() {
        return Success;
    }

    public void setSuccess(boolean setSuccess) {
        this.Success = setSuccess;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    /*
    jsonObject.put("token",securityItems.getTokenEncrypt());
    jsonObject.put("id_num_empleado",securityItems.getIdEmployEncrypt());
    jsonObject.put("evento",obtenerEstatusAlerta.getEvento());
    jsonObject.put("fecha",obtenerEstatusAlerta.getFecha());
    jsonObject.put("comentario",obtenerEstatusAlerta.getBateria()+"");*/

}
