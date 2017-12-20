package com.sociomas.core.WebService.Model.Response.Permisos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by oemy9 on 19/05/2017.
 */

public class ExclusionesEmpleado
{
    @SerializedName("exclusiones")
    private ArrayList<Exclusiones> exclusiones;

    public ArrayList<Exclusiones> getExclusiones() { return this.exclusiones; }

    public void setExclusiones(ArrayList<Exclusiones> exclusiones) { this.exclusiones = exclusiones; }

    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private String va_nombre_completo;

    public String getVaNombreCompleto() { return this.va_nombre_completo; }

    public void setVaNombreCompleto(String va_nombre_completo) { this.va_nombre_completo = va_nombre_completo; }
}