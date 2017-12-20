package com.sociomas.core.WebService.Model.Response.Permisos;

import java.util.ArrayList;

/**
 * Created by oemy9 on 19/05/2017.
 */

public class ResponsePermisos
{
    private boolean error;

    public boolean getError() { return this.error; }

    public void setError(boolean error) { this.error = error; }

    private String mensajeError;

    public String getMensajeError() { return this.mensajeError; }

    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }

    private String serverTime;

    public String getServerTime() { return this.serverTime; }

    public void setServerTime(String serverTime) { this.serverTime = serverTime; }

    private String serverUTCTime;

    public String getServerUTCTime() { return this.serverUTCTime; }

    public void setServerUTCTime(String serverUTCTime) { this.serverUTCTime = serverUTCTime; }

    private ArrayList<ExclusionesEmpleado> exclusionesEmpleado;

    public ArrayList<ExclusionesEmpleado> getExclusionesEmpleado() { return this.exclusionesEmpleado; }

    public void setExclusionesEmpleado(ArrayList<ExclusionesEmpleado> exclusionesEmpleado) { this.exclusionesEmpleado = exclusionesEmpleado; }

    private String misExclusiones;

    public String getMisExclusiones() { return this.misExclusiones; }

    public void setMisExclusiones(String misExclusiones) { this.misExclusiones = misExclusiones; }

    private String plantillaExclusiones;

    public String getPlantillaExclusiones() { return this.plantillaExclusiones; }

    public void setPlantillaExclusiones(String plantillaExclusiones) { this.plantillaExclusiones = plantillaExclusiones; }
}
