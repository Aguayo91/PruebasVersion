package com.sociomas.core.WebService.Model.Response.Multimedia;

/**
 * Created by oemy9 on 18/05/2017.
 */

public class ArchivoListado
{
    private String archivo;

    public String getArchivo() { return this.archivo; }

    public void setArchivo(String archivo) { this.archivo = archivo; }

    private String extension;

    public String getExtension() { return this.extension; }

    public void setExtension(String extension) { this.extension = extension; }

    private String nombre_archivo;

    public String getNombreArchivo() { return this.nombre_archivo; }

    public void setNombreArchivo(String nombre_archivo) { this.nombre_archivo = nombre_archivo; }

    private String tipo;

    public String getTipo() { return this.tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }
}
