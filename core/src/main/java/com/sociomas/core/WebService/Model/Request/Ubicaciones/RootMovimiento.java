package com.sociomas.core.WebService.Model.Request.Ubicaciones;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 30/03/2017.
 */

public class RootMovimiento extends ServerRequest {
    private ArrayList<Movimiento> movimientos;
    public ArrayList<Movimiento> getMovimientos() {
        return this.movimientos;
    }
    public void setMovimientos(ArrayList<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
