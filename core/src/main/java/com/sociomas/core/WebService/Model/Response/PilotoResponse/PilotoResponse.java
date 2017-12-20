package com.sociomas.core.WebService.Model.Response.PilotoResponse;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 04/10/2017.
 */

public class PilotoResponse extends ServerResponse {
    private boolean registrado;

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }
}
