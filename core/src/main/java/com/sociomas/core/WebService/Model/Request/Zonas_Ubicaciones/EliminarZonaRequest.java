package com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones;

import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 01/07/2017.
 */

@SuppressWarnings("unused")
public class EliminarZonaRequest extends ServerRequest {
    private  int id_csc_zo_pos;

    public int getId_csc_zo_pos() {
        return id_csc_zo_pos;
    }

    public void setId_csc_zo_pos(int id_csc_zo_pos) {
        this.id_csc_zo_pos = id_csc_zo_pos;
    }
}
