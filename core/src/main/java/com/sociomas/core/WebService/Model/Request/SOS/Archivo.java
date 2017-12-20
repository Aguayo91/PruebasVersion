package com.sociomas.core.WebService.Model.Request.SOS;

import java.io.Serializable;

/**
 * Created by oemy9 on 03/08/2017.
 */

public class Archivo implements Serializable {

    public Archivo(){}
    public Archivo(String archivoAdjunto,String nombreArchivo,String extension,String sdUbicacion, int tamanoArchivo) {
        this.archivoAdjunto=archivoAdjunto;
        this.nombreArchivo = nombreArchivo;
        this.extension=extension;
        this.sdUbicacion=sdUbicacion;
        this.tamanoArchivo=tamanoArchivo;
    }

    private String sdUbicacion;

    public String getSdUbicacion() {
        return sdUbicacion;
    }
    public void setSdUbicacion(String sdUbicacion) {
        this.sdUbicacion = sdUbicacion;
    }

    private String nombreArchivo;
    public String getNombreArchivo() { return this.nombreArchivo; }

    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    private String archivoAdjunto;

    public String getArchivoAdjunto() { return this.archivoAdjunto; }

    public void setArchivoAdjunto(String archivoAdjunto) { this.archivoAdjunto = archivoAdjunto; }

    private int tamanoArchivo;

    public int getTamanoArchivo() { return this.tamanoArchivo; }

    public void setTamanoArchivo(int tamanoArchivo) { this.tamanoArchivo = tamanoArchivo; }

    private String extension;

    public String getExtension() { return this.extension; }

    public void setExtension(String extension) { this.extension = extension; }
}
