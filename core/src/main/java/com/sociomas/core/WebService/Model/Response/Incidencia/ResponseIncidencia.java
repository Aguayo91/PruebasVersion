package com.sociomas.core.WebService.Model.Response.Incidencia;


import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 15/05/2017.
 */

public class ResponseIncidencia extends ServerResponse {

        @SerializedName("listado")
        private ArrayList<ListadoIncidencias> listadoIncidencias;

        public ArrayList<ListadoIncidencias> getListadoIncidencias() { return this.listadoIncidencias; }

        public void setListadoIncidencias(ArrayList<ListadoIncidencias> listadoIncidencias) { this.listadoIncidencias = listadoIncidencias; }

        @SerializedName("listado_plantilla")
        private ArrayList<ListadoIncidencias> listado_Incidencias_plantilla;

        public ArrayList<ListadoIncidencias> getListadoPlantilla() { return this.listado_Incidencias_plantilla; }

        public void setListadoPlantilla(ArrayList<ListadoIncidencias> listado_Incidencias_plantilla) { this.listado_Incidencias_plantilla = listado_Incidencias_plantilla; }

}
