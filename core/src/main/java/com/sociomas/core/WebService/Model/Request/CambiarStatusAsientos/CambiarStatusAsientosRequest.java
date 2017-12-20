package com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 16/10/2017.
 */

public class CambiarStatusAsientosRequest extends ServerRequest {
    @SerializedName("estatus_asientos")
    private StatusAsientos statusAsientos;

    public StatusAsientos getStatusAsientos() {
        return statusAsientos;
    }

    public void setStatusAsientos(StatusAsientos statusAsientos) {
        this.statusAsientos = statusAsientos;
    }
}
