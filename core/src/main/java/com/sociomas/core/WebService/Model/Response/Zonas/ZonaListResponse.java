package com.sociomas.core.WebService.Model.Response.Zonas;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 26/06/2017.
 */

public class ZonaListResponse extends ServerResponse {
    private ArrayList<ZonaList> zonas;

    public ArrayList<ZonaList> getZonas() {
        return zonas;
    }

    public void setZonas(ArrayList<ZonaList> zonas) {
        this.zonas = zonas;
    }

    public ArrayList<ZonaList>Posiciones;

    public ArrayList<ZonaList> getPosiciones() {
        return Posiciones;
    }

    public void setPosiciones(ArrayList<ZonaList> posiciones) {
        Posiciones = posiciones;
    }
}
