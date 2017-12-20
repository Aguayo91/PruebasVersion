package com.sociomas.core.WebService.Model.Response.Ubicaciones;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 01/07/2017.
 */

public class PlantillaResponse extends ServerResponse {

    private ArrayList<EmpleadosPlantilla> empleados_plantilla;

    public ArrayList<EmpleadosPlantilla> getEmpleadosPlantilla() { return this.empleados_plantilla; }

    public void setEmpleadosPlantilla(ArrayList<EmpleadosPlantilla> empleados_plantilla) { this.empleados_plantilla = empleados_plantilla; }
}
