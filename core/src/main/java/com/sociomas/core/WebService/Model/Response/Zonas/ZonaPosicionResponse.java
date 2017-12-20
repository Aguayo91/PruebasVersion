package com.sociomas.core.WebService.Model.Response.Zonas;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 27/06/2017.
 */

public class ZonaPosicionResponse extends ServerResponse {
    private ArrayList<LugarTrabajo> Posiciones;

    public ArrayList<LugarTrabajo> getPosiciones() { return this.Posiciones; }

    public void setPosiciones(ArrayList<LugarTrabajo> Posiciones) { this.Posiciones = Posiciones; }
}
