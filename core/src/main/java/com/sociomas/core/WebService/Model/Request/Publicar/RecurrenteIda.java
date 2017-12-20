
package com.sociomas.core.WebService.Model.Request.Publicar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecurrenteIda {
    @SerializedName("id_dia_ida")
    @Expose
    private Integer idDiaIda;
    public Integer getIdDiaIda() {
        return idDiaIda;
    }
    public void setIdDiaIda(Integer idDiaIda) {
        this.idDiaIda = idDiaIda;
    }
    public RecurrenteIda(Integer idDiaIda) {
        this.idDiaIda = idDiaIda;
    }
}
