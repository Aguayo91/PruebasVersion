
package com.sociomas.core.WebService.Model.Request.Publicar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoAventon {

    @SerializedName("id_automovil")
    @Expose
    private Integer idAutomovil;
    @SerializedName("viaje_redondo")
    @Expose
    private Boolean viajeRedondo;


    public TipoAventon(Integer idAutomovil,boolean viajeRedondo) {
        this.idAutomovil = idAutomovil;
        this.viajeRedondo=viajeRedondo;

    }

    public Integer getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(Integer idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public Boolean getViajeRedondo() {
        return viajeRedondo;
    }

    public void setViajeRedondo(Boolean viajeRedondo) {
        this.viajeRedondo = viajeRedondo;
    }




}
