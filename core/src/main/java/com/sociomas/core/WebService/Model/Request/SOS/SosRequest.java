package com.sociomas.core.WebService.Model.Request.SOS;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oemy9 on 03/08/2017.
 */

public class SosRequest extends ServerRequest implements Serializable {
    private String telefonos;
    private String coordenadas;
    private ArrayList<Archivo>archivos;
    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }
    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }
    public String getTelefonos() {
        return telefonos;
    }
    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }
    public String getCoordenadas() {
        return coordenadas;
    }
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
