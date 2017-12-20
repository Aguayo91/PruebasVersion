package com.sociomas.core.WebService.Model.Request.Perfil;

/**
 * Created by jromeromar on 27/09/2017.
 */

public class CatPreferenciasEmpleado {


    public CatPreferenciasEmpleado(int id_preferencia,boolean valor,int id_relacion_preferencia_usuario) {
        this.id_preferencia = id_preferencia;
        this.valor=valor;
        this.id_relacion_preferencia_usuario = id_relacion_preferencia_usuario;
    }

    private int id_relacion_preferencia_usuario;

    public CatPreferenciasEmpleado(int i, boolean selectedValor) {
    }

    public int getIdRelacionPreferenciaUsuario() { return this.id_relacion_preferencia_usuario; }

    public void setIdRelacionPreferenciaUsuario(int id_relacion_preferencia_usuario) { this.id_relacion_preferencia_usuario = id_relacion_preferencia_usuario; }


    private int id_preferencia;

    public int getIdPreferencia() { return this.id_preferencia; }

    public void setIdPreferencia(int id_preferencia) { this.id_preferencia = id_preferencia; }

    private boolean valor;

    public boolean getValor() { return this.valor; }

    public void setValor(boolean valor) { this.valor = valor; }
}

