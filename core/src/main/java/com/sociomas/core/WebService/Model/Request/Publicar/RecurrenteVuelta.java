package com.sociomas.core.WebService.Model.Request.Publicar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 09/10/2017.
 */

public class RecurrenteVuelta  {
    @SerializedName("id_dia_vuelta")
    @Expose
    private Integer idDiaVuelta;
    public Integer getIdDiaVuelta() {
        return idDiaVuelta;
    }
    public void setIdDiaVuelta(Integer idDiaVuelta) {
        this.idDiaVuelta = idDiaVuelta;
    }

    public RecurrenteVuelta(Integer idDiaVuelta) {
        this.idDiaVuelta = idDiaVuelta;
    }
}
