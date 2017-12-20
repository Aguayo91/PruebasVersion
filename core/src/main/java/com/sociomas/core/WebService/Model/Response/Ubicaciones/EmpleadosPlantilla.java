package com.sociomas.core.WebService.Model.Response.Ubicaciones;

/**
 * Created by oemy9 on 01/07/2017.
 */

@SuppressWarnings("unused")
public class EmpleadosPlantilla
{
    private String nombre_empleado;

    public String getNombreEmpleado() { return this.nombre_empleado; }

    public void setNombreEmpleado(String nombre_empleado) { this.nombre_empleado = nombre_empleado; }

    private String mostrar_empleado;

    public String getMostrarEmpleado() { return this.mostrar_empleado; }

    public void setMostrarEmpleado(String mostrar_empleado) { this.mostrar_empleado = mostrar_empleado; }

    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }
}
