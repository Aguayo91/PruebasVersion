package com.sociomas.core.DataBaseModel;

/**
 * Created by oemy9 on 21/07/2017.
 */

public class ConfiguracionItem {
    private int imagen;
    private String descripcion;
    private boolean isVisible;


    public ConfiguracionItem(int imagen,String descripcion, boolean isVisible){
        this.imagen=imagen;
        this.descripcion=descripcion;
        this.isVisible=isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
