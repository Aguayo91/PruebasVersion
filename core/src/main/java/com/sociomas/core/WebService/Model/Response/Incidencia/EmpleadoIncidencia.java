package com.sociomas.core.WebService.Model.Response.Incidencia;


import com.sociomas.core.Utils.Enums.EnumIncidencia;

/**
 * Created by oemy9 on 16/05/2017.
 */

@SuppressWarnings("unused")
public class EmpleadoIncidencia {
    private String decripcionEmpleado;
    private int autorizados;
    private int sinJustificar;
    private int pendientes;



    public EmpleadoIncidencia(String numeroEmpleado, String status) {
        this.decripcionEmpleado = numeroEmpleado;
        setByStatus(status);
    }
    public void setByStatus(String status){
        if(status!=null) {
            EnumIncidencia incidencia = EnumIncidencia.getFromSting(status);
            switch (incidencia) {
                case autorizado:
                    this.autorizados++;
                    break;
                case sin_justificar:
                    this.sinJustificar++;
                    break;
                case pendiente:
                    this.pendientes++;
                    break;
            }
        }
    }

    public String getDecripcionEmpleado() {
        return decripcionEmpleado;
    }

    public int getAutorizados() {
        return autorizados;
    }

    public int getSinJustificar() {
        return sinJustificar;
    }

    public int getPendientes() {
        return pendientes;
    }
}
