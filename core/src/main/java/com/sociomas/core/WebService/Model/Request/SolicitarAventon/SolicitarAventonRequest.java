package com.sociomas.core.WebService.Model.Request.SolicitarAventon;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 12/10/2017.
 */

public class SolicitarAventonRequest extends ServerRequest {
    @SerializedName("asientos_reservados")
    private ArrayList<AsientosReservados>listAsientosReservados;

    public ArrayList<AsientosReservados> getListAsientosReservados() {
        return listAsientosReservados;
    }

    public void setListAsientosReservados(ArrayList<AsientosReservados> listAsientosReservados) {
        this.listAsientosReservados = listAsientosReservados;
    }

    @SerializedName("clickAction")
    private String clickAction;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("cuerpoMensaje")
    private String cuerpoMensaje;

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpoMensaje() {
        return cuerpoMensaje;
    }

    public void setCuerpoMensaje(String cuerpoMensaje) {
        this.cuerpoMensaje = cuerpoMensaje;
    }
}
