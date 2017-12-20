package com.sociomas.aventones.UI.Controls.Model;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class Dia {
    private int id;
    private String nombre;
    private boolean isChecked;

    public Dia(int id,String nombre,boolean isChecked) {
        this.id=id;
        this.nombre = nombre;
        this.isChecked=isChecked;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        Dia dia=(Dia)obj;
        if(dia.getId()==getId()){
            return true;
        }
        return false;
    }
}
