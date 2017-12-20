package com.sociomas.core.WebService.Model.Response.Configuracion;

/**
 * Created by oemy9 on 14/07/2017.
 */

@SuppressWarnings("unused")
public class RMonitoreoList {
    private int int_numero_dia;

    public int getIntNumeroDia() { return this.int_numero_dia; }

    public void setIntNumeroDia(int int_numero_dia) { this.int_numero_dia = int_numero_dia; }

    private String tm_hora_final;

    public String getTmHoraFinal() { return this.tm_hora_final; }

    public void setTmHoraFinal(String tm_hora_final) { this.tm_hora_final = tm_hora_final; }

    private String tm_hora_final_string;

    public String getTmHoraFinalString() { return this.tm_hora_final_string; }

    public void setTmHoraFinalString(String tm_hora_final_string) { this.tm_hora_final_string = tm_hora_final_string; }

    private String tm_hora_inicial;

    public String getTmHoraInicial() { return this.tm_hora_inicial; }

    public void setTmHoraInicial(String tm_hora_inicial) { this.tm_hora_inicial = tm_hora_inicial; }

    private String tm_hora_inicial_string;

    public String getTmHoraInicialString() { return this.tm_hora_inicial_string; }

    public void setTmHoraInicialString(String tm_hora_inicial_string) { this.tm_hora_inicial_string = tm_hora_inicial_string; }
}
