package com.sociomas.core.WebService.Model.Request.Pasajeros;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 18/10/2017.
 */

public class PasajerosRequest extends ServerRequest {
    @SerializedName("id_aventon")
    private int idAventon;

    public int getIdAventon() {
        return idAventon;
    }

    public void setIdAventon(int idAventon) {
        this.idAventon = idAventon;
    }
}
