package com.sociomas.core.WebService.Model.Request.Privacidad;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 16/11/2017.
 */

public class AvisoRequest extends ServerRequest {
    @SerializedName("aceptado")
    private int aceptado;
    @SerializedName("id_tipo_aviso")
    private int idTipoAviso;
    @SerializedName("version")
    private  double version;

    public AvisoRequest(int aceptado, int idTipoAviso, double version) {
        this.aceptado = aceptado;
        this.idTipoAviso = idTipoAviso;
        this.version = version;
    }



    public int getAceptado() {
        return aceptado;
    }

    public void setAceptado(int aceptado) {
        this.aceptado = aceptado;
    }

    public int getIdTipoAviso() {
        return idTipoAviso;
    }

    public void setIdTipoAviso(int idTipoAviso) {
        this.idTipoAviso = idTipoAviso;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }


}
