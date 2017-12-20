package com.sociomas.core.WebService.Model.Request.Perfil;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by jromeromar on 27/09/2017.
 */

public class EditarPreferenciasRequest extends ServerRequest {


    private String sexo;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    private com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado CaractEmpleado;

    public com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado getCaractEmpleado() { return this.CaractEmpleado; }

    public void setCaractEmpleado(com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado CaractEmpleado) { this.CaractEmpleado = CaractEmpleado; }

    private ArrayList<com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado> CatPreferenciasEmpleado;

    public ArrayList<com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado> getCatPreferenciasEmpleado() { return this.CatPreferenciasEmpleado; }

    public void setCatPreferenciasEmpleado(ArrayList<com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado> CatPreferenciasEmpleado) { this.CatPreferenciasEmpleado = CatPreferenciasEmpleado; }

}
