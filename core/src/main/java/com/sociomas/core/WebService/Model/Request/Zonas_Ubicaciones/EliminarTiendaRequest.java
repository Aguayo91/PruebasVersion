package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 29/06/2017.
 */

public class EliminarTiendaRequest extends ServerRequest {
    private ArrayList<EditarTiendaItem> posiciones;

    public ArrayList<EditarTiendaItem> getPosiciones() { return this.posiciones; }

    public void setPosiciones(ArrayList<EditarTiendaItem> posiciones) { this.posiciones = posiciones; }
}
