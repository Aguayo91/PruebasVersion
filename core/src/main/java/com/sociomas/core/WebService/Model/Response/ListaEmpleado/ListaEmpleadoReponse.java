package com.sociomas.core.WebService.Model.Response.ListaEmpleado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 28/11/2017.
 */

public class ListaEmpleadoReponse extends ServerResponse {
    @SerializedName("lstEmpleados")
    @Expose
    private ArrayList<LstEmpleado> lstEmpleados;

    public ArrayList<LstEmpleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(ArrayList<LstEmpleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }
}
