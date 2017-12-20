package com.sociomas.core.WebService.Model.Response.Registro;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 12/07/2017.
 */
public class RegistroResponse extends ServerResponse {
    private Empleado empleado;
    public Empleado getEmpleado() { return this.empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    @SerializedName("Team")
    private ArrayList<Team> plantilla;

    public ArrayList<Team> getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(ArrayList<Team> plantilla) {
        this.plantilla = plantilla;
    }
}
