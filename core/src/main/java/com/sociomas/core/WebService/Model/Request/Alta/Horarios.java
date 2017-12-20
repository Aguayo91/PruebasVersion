package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.Aventones.DiasAventon;

import java.util.List;

/**
 * Created by jromeromar on 16/11/2017.
 */

public class Horarios {

    @SerializedName("horaEntrada")
    private String horaEntrada;

    @SerializedName("horaSalida")
    private String horaSalida;

    private List<Dias> diasList;

    public Horarios(String horaEntrada, String horaSalida) {
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public Horarios(String horaEntrada, String horaSalida, List<Dias> diasList) {
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.diasList = diasList;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public List<Dias> getDiasList() {
        return diasList;
    }

    public void setDiasList(List<Dias> diasList) {
        this.diasList = diasList;
    }

    @Override
    public String toString() {
        return "Horarios{" +
                "horaEntrada='" + horaEntrada + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", diasList=" + diasList +
                '}';
    }
}
