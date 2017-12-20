package com.sociomas.core.DataBaseModel;

/**
 * Created by oemy9 on 18/04/2017.
 */

public class Pais {
    private int drawableFile;
    private String codigo;
    private String nombre;


    public Pais(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre=nombre;
    }

    public int getDrawableFile() {
        return drawableFile;
    }

    public void setDrawableFile(int drawableFile) {
        this.drawableFile = drawableFile;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
