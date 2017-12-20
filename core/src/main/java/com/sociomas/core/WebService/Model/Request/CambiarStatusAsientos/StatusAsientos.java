package com.sociomas.core.WebService.Model.Request.CambiarStatusAsientos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oemy9 on 16/10/2017.
 */

public class StatusAsientos {
    @SerializedName("id_rel_usuario_asientos_reservados")
    private int idRelAsientosReservados;
    @SerializedName("id_estatus_solicitud_aventon")
    private  int idStatusAventon;

    public StatusAsientos(int idRelAsientosReservados, int idStatusAventon) {
        this.idRelAsientosReservados = idRelAsientosReservados;
        this.idStatusAventon=idStatusAventon;
    }

    public int getIdRelAsientosReservados() {
        return idRelAsientosReservados;
    }

    public void setIdRelAsientosReservados(int idRelAsientosReservados) {
        this.idRelAsientosReservados = idRelAsientosReservados;
    }

    public int getIdStatusAventon() {
        return idStatusAventon;
    }

    public void setIdStatusAventon(int idStatusAventon) {
        this.idStatusAventon = idStatusAventon;
    }
}
