package com.sociomas.core.WebService.Model.Request.Gafete;

import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 22/08/2017.
 */

public class GafeteCrearRequest extends ServerRequest {
    private ArrayList<ArchivoAdjunto> Archivos;
    private int Tipo;
    public ArrayList<ArchivoAdjunto> getArchivos() {
        return Archivos;
    }
    public void setArchivos(ArrayList<ArchivoAdjunto> archivos) {
        Archivos = archivos;
    }

    public int getTipoArchivo() {
        return Tipo;
    }

    public void setTipoArchivo(int tipo) {
        Tipo = tipo;
    }

}
