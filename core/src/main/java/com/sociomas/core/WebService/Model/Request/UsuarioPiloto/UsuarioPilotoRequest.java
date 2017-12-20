package com.sociomas.core.WebService.Model.Request.UsuarioPiloto;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 20/10/2017.
 */

public class UsuarioPilotoRequest extends ServerRequest {
    @SerializedName("id_emp")
    private String id_emp;

    public String getId_emp() {
        return id_emp;
    }

    public void setId_emp(String id_emp) {
        this.id_emp = id_emp;
    }

    public UsuarioPilotoRequest(String id_emp) {
        this.id_emp = id_emp;
    }
}
