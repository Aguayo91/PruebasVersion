package com.sociomas.core.WebService.Model.Request.Gafete;

/**
 * Created by oemy9 on 22/08/2017.
 */

public class ArchivoAdjunto{
    private String nombre_archivo;
    private String archivo;
    private String extension;
    private String tipo;

    public ArchivoAdjunto(){

    }

    public ArchivoAdjunto(String nombre_archivo,String archivo, String extension) {
        this.nombre_archivo = nombre_archivo;
        this.archivo=archivo;
        this.extension=extension;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
