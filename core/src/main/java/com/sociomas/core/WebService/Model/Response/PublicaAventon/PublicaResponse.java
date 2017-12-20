package com.sociomas.core.WebService.Model.Response.PublicaAventon;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oemy9 on 09/10/2017.
 */

public class PublicaResponse extends ServerResponse implements Serializable {
    @SerializedName("id_aventon")
    private int IdAventon;
    @SerializedName("id_trayecto_aventon_ida")
    private int idTrayectoAventonIda;
    @SerializedName("id_trayecto_aventon_vuelta")
    private int idTrayectoAventonVuelta;
    @SerializedName("lstCoordenadas_Ida")
    private ArrayList<CoordenadasIda>listCoordenadasIda;
    @SerializedName("distancia_trayecto_ida_value")
    private Double distanciaTrayectoIdaValue;
    @SerializedName("distancia_trayecto_vuelta_value")
    private Double distanciaTrayectoVueltaValue;
    @SerializedName("duracion_trayecto_ida_value")
    private Double duracionTrayectoIdaValue;
    @SerializedName("duracion_trayecto_vuelta_value")
    private Double duracionTrayectoVueltaValue;

    @SerializedName("distancia_trayecto_ida_text")
    private String distanciaTrayectoIdaText;
    @SerializedName("distancia_trayecto_vuelta_text")
    private String distanciaTrayectoVueltaText;

    @SerializedName("duracion_trayecto_ida_text")
    private String duracionTrayectoIdaText;
    @SerializedName("duracion_trayecto_vuelta_text")
    private String duracionTrayectoVueltaText;


    public String getDistanciaTrayectoVueltaText() {
        return distanciaTrayectoVueltaText;
    }

    public void setDistanciaTrayectoVueltaText(String distanciaTrayectoVueltaText) {
        this.distanciaTrayectoVueltaText = distanciaTrayectoVueltaText;
    }

    public String getDuracionTrayectoIdaText() {
        return duracionTrayectoIdaText;
    }

    public void setDuracionTrayectoIdaText(String duracionTrayectoIdaText) {
        this.duracionTrayectoIdaText = duracionTrayectoIdaText;
    }

    public String getDuracionTrayectoVueltaText() {
        return duracionTrayectoVueltaText;
    }

    public void setDuracionTrayectoVueltaText(String duracionTrayectoVueltaText) {
        this.duracionTrayectoVueltaText = duracionTrayectoVueltaText;
    }

    public String getDistanciaTrayectoIdaText() {
        return distanciaTrayectoIdaText;
    }

    public void setDistanciaTrayectoIdaText(String distanciaTrayectoIdaText) {
        this.distanciaTrayectoIdaText = distanciaTrayectoIdaText;
    }

    public Double getDistanciaTrayectoIdaValue() {
        return distanciaTrayectoIdaValue;
    }

    public void setDistanciaTrayectoIdaValue(Double distanciaTrayectoIdaValue) {
        this.distanciaTrayectoIdaValue = distanciaTrayectoIdaValue;
    }

    public Double getDistanciaTrayectoVueltaValue() {
        return distanciaTrayectoVueltaValue;
    }

    public void setDistanciaTrayectoVueltaValue(Double distanciaTrayectoVueltaValue) {
        this.distanciaTrayectoVueltaValue = distanciaTrayectoVueltaValue;
    }

    public Double getDuracionTrayectoIdaValue() {
        return duracionTrayectoIdaValue;
    }

    public void setDuracionTrayectoIdaValue(Double duracionTrayectoIdaValue) {
        this.duracionTrayectoIdaValue = duracionTrayectoIdaValue;
    }

    public Double getDuracionTrayectoVueltaValue() {
        return duracionTrayectoVueltaValue;
    }

    public void setDuracionTrayectoVueltaValue(Double duracionTrayectoVueltaValue) {
        this.duracionTrayectoVueltaValue = duracionTrayectoVueltaValue;
    }

    public ArrayList<CoordenadasIda> getListCoordenadasIda() {
        return listCoordenadasIda;
    }

    public void setListCoordenadasIda(ArrayList<CoordenadasIda> listCoordenadasIda) {
        this.listCoordenadasIda = listCoordenadasIda;
    }

    public int getIdAventon() {
        return IdAventon;
    }

    public void setIdAventon(int idAventon) {
        IdAventon = idAventon;
    }

    public int getIdTrayectoAventonIda() {
        return idTrayectoAventonIda;
    }

    public void setIdTrayectoAventonIda(int idTrayectoAventonIda) {
        this.idTrayectoAventonIda = idTrayectoAventonIda;
    }

    public int getIdTrayectoAventonVuelta() {
        return idTrayectoAventonVuelta;
    }

    public void setIdTrayectoAventonVuelta(int idTrayectoAventonVuelta) {
        this.idTrayectoAventonVuelta = idTrayectoAventonVuelta;
    }
}
