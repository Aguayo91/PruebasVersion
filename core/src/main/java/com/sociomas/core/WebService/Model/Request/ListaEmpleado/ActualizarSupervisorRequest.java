package com.sociomas.core.WebService.Model.Request.ListaEmpleado;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 28/11/2017.
 */

public class ActualizarSupervisorRequest extends ServerRequest {
    @SerializedName("id_supervisor")
    private String idSupervisor;

    public String getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public ActualizarSupervisorRequest(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }
}
