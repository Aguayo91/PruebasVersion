package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GiioToledo(giio.toledo@gmail.com) on 17/11/17.
 */

public class Dias implements Serializable {
    @SerializedName("dia")
    private String dia;
    @SerializedName("activo")
    private int activo;

    public Dias(String dia, int activo) {
        this.dia = dia;
        this.activo = activo;
    }
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getActivo() {
        return activo;
    }

    public boolean isActivo() {
        return activo == 1;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Dias{" +
                "dia='" + dia + '\'' +
                ", activo=" + activo +
                '}';
    }
}
