package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 05/10/2017.
 */

public class DiasAventon implements Serializable {
    @SerializedName("dia")
    private String dia;
    @SerializedName("activo")
    private int activo;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getActivo() {
        return activo;
    }

    public boolean isActivo(){
        return activo==1;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
