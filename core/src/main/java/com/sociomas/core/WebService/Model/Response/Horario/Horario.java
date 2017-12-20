package com.sociomas.core.WebService.Model.Response.Horario;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by oemy9 on 15/06/2017.
 */

@SuppressWarnings("unused")
public class Horario implements Serializable {

    public Horario(){

    }
    public Horario(int ti_dia_semana,String nombreDia, String estatus){
        this.ti_dia_semana=ti_dia_semana;
        this.nombreDia=nombreDia;
        this.setEstatus(estatus);
    }
    private String Cambio;

    public String getCambio() {
        return Cambio;
    }

    public void setCambio(String cambio) {
        this.Cambio = cambio;
    }

    private String nombreDia;

    public String getNombreDia() {
        return nombreDia;
    }

    private boolean hasPropuesta;

    public boolean HasPropuesta() {
        return hasPropuesta;
    }

    public void setHasPropuesta(boolean hasPropuesta) {
        this.hasPropuesta = hasPropuesta;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    private int ti_dia_semana;

    public int getTiDiaSemana() { return this.ti_dia_semana; }
    public void setTiDiaSemana(int ti_dia_semana) { this.ti_dia_semana = ti_dia_semana; }

    private String id_num_empleado;
    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private int id_csc_ho_emp;

    public int getIdCscHoEmp() { return this.id_csc_ho_emp; }

    public void setIdCscHoEmp(int id_csc_ho_emp) { this.id_csc_ho_emp = id_csc_ho_emp; }

    private String tm_hora_entrada;

    public String getTmHoraEntrada() { return this.tm_hora_entrada; }

    public void setTmHoraEntrada(String tm_hora_entrada) { this.tm_hora_entrada = tm_hora_entrada; }

    private String tm_hora_salida;

    public String getTmHoraSalida() { return this.tm_hora_salida; }

    public void setTmHoraSalida(String tm_hora_salida) { this.tm_hora_salida = tm_hora_salida; }

    private String Estatus;

    public String getEstatus() { return this.Estatus; }

    public void setEstatus(String Estatus) { this.Estatus = Estatus; }

    private String va_nombre_completo;

    public String getVaNombreCompleto() { return this.va_nombre_completo; }

    public void setVaNombreCompleto(String va_nombre_completo) { this.va_nombre_completo = va_nombre_completo; }

    private boolean bit_valido;

    public boolean getBitValido() { return this.bit_valido; }

    public void setBitValido(boolean bit_valido) { this.bit_valido = bit_valido; }


    public  String toJson(){
        return  new Gson().toJson(this);
    }



}
