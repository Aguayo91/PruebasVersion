package com.sociomas.core.WebService.Model.Response.Gafete;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 22/08/2017.
 */

public class ResponseGafete extends ServerResponse {
    private String foto;
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
