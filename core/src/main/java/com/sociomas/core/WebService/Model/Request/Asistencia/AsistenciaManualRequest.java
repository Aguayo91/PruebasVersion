package com.sociomas.core.WebService.Model.Request.Asistencia;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.Movimiento;

/**
 * Created by oemy9 on 18/11/2017.
 */

public class AsistenciaManualRequest extends Movimiento {
    @SerializedName("id_tipo_registro_manual_posicion")
    private int idTipoRegistroManualPosicion;

    public int getIdTipoRegistroManualPosicion() {
        return idTipoRegistroManualPosicion;
    }

    public void setIdTipoRegistroManualPosicion(int idTipoRegistroManualPosicion) {
        this.idTipoRegistroManualPosicion = idTipoRegistroManualPosicion;
    }
}
