package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 10/10/2017.
 */

public class CoordenadasRutaModelResponse implements Serializable {

    @SerializedName("id_num_empleado")
    private String idNumEmpleado;

    @SerializedName("token")
    private String token;

    @SerializedName("id_trayecto_aventon")
    private int idTrayectoAventon;

    public String getIdNumEmpleado() {
        return idNumEmpleado;
    }

    public void setIdNumEmpleado(String idNumEmpleado) {
        this.idNumEmpleado = idNumEmpleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdTrayectoAventon() {
        return idTrayectoAventon;
    }

    public void setIdTrayectoAventon(int idTrayectoAventon) {
        this.idTrayectoAventon = idTrayectoAventon;
    }
}