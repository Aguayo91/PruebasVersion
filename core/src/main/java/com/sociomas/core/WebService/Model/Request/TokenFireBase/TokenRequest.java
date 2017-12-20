package com.sociomas.core.WebService.Model.Request.TokenFireBase;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 03/10/2017.
 */

public class TokenRequest extends ServerRequest {
    @SerializedName("token_dispositivo")
    private String tokenDispositivo;

    public String getTokenDispositivo() {
        return tokenDispositivo;
    }

    public void setTokenDispositivo(String tokenDispositivo) {
        this.tokenDispositivo = tokenDispositivo;
    }
}
