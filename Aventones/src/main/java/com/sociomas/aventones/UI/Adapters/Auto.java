package com.sociomas.aventones.UI.Adapters;

/**
 * Created by jromeromar on 20/09/2017.
 */

public class Auto {

    public Auto(String auto, String placas, int color, int capacidad){
        this.auto=auto;
        this.placas=placas;
        this.color=color;
        this.capacidad=capacidad;
    }
    public Auto(){}

    public String auto;
    public String getAuto() {
        return auto;
    }
    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String placas;
    public String getPlacas() {
        return placas;
    }
    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public int color;
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public int capacidad;
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}