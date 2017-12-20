package com.sociomas.core.WebService.Model.Response.Sugerencia;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;

/**
 * Created by oemy9 on 09/10/2017.
 */

public class SugerenciaResponse extends ServerResponse {
    @SerializedName("PosicionesEmpleado")
    private ArrayList<LugarTrabajo>listPosicionesEmpleado;

    public ArrayList<LugarTrabajo> getListPosicionesEmpleado() {
        return listPosicionesEmpleado;
    }

    public void setListPosicionesEmpleado(ArrayList<LugarTrabajo> listPosicionesEmpleado) {
        this.listPosicionesEmpleado = listPosicionesEmpleado;
    }
}
