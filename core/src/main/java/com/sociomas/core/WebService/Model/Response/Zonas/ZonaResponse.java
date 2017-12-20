package com.sociomas.core.WebService.Model.Response.Zonas;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 23/06/2017.
 */

public class ZonaResponse extends ServerResponse {
    private ArrayList<LugarTrabajo> Posiciones;
    public ArrayList<LugarTrabajo> getPosiciones() { return this.Posiciones; }
    public void setPosiciones(ArrayList<LugarTrabajo> Posiciones) { this.Posiciones = Posiciones; }
    private ArrayList<LugarTrabajo> zonas;
    public ArrayList<LugarTrabajo> getZonas() { return this.zonas; }
    public void setZonas(ArrayList<LugarTrabajo> zonas) { this.zonas = zonas; }
}
