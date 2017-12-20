package com.sociomas.core.WebService.Model.Response.Ubicaciones;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RootUbicacion  {
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

    private ArrayList<Ubicaciones> ubicaciones;

    public ArrayList<Ubicaciones> getUbicaciones() { return this.ubicaciones; }

    public void setUbicaciones(ArrayList<Ubicaciones> ubicaciones) { this.ubicaciones = ubicaciones; }
}
