package com.sociomas.core.WebService.Model.Request.Incidencia;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 23/05/2017.
 */

public class IncidenciaHistorial extends ServerRequest {
    private String id_csc_incid;

    public String getId_csc_incid() {
        return id_csc_incid;
    }

    public void setId_csc_incid(String id_csc_incid) {
        this.id_csc_incid = id_csc_incid;
    }
}
