package com.sociomas.core.WebService.Model.Response.Permisos;

import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 08/06/2017.
 */

public class ResponsePermisoIOS extends ServerResponse {
    private ArrayList<ListExclusiones> exclusionesEmpleado;

    public ArrayList<ListExclusiones> getExclusionesEmpleado() { return this.exclusionesEmpleado; }

    public void setExclusionesEmpleado(ArrayList<ListExclusiones> exclusionesEmpleado) { this.exclusionesEmpleado = exclusionesEmpleado; }

    private ArrayList<ListExclusiones> misExclusiones;

    public ArrayList<ListExclusiones> getMisExclusiones() { return this.misExclusiones; }

    public void setMisExclusiones(ArrayList<ListExclusiones> misExclusiones) { this.misExclusiones = misExclusiones; }

    private ArrayList<ListExclusiones> plantillaExclusiones;

    public ArrayList<ListExclusiones>getPlantillaExclusiones() { return this.plantillaExclusiones; }

    public void setPlantillaExclusiones(ArrayList<ListExclusiones> plantillaExclusiones) { this.plantillaExclusiones = plantillaExclusiones; }
}
