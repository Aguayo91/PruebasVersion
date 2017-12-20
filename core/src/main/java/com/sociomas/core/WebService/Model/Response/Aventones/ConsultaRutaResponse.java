package com.sociomas.core.WebService.Model.Response.Aventones;

import java.util.ArrayList;

/**
 * Created by jromeromar on 11/10/2017.
 */

public class ConsultaRutaResponse {

    private boolean error;

    public boolean getError() { return this.error; }

    public void setError(boolean error) { this.error = error; }

    private Object mensaje;

    public Object getMensaje() { return this.mensaje; }

    public void setMensaje(Object mensaje) { this.mensaje = mensaje; }

    private ArrayList<LstCoordenada> lstCoordenadas;

    public ArrayList<LstCoordenada> getLstCoordenadas() { return this.lstCoordenadas; }

    public void setLstCoordenadas(ArrayList<LstCoordenada> lstCoordenadas) { this.lstCoordenadas = lstCoordenadas; }

}
