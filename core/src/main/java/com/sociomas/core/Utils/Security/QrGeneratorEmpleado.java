/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociomas.core.Utils.Security;

import java.util.Calendar;

/**
 *
 * @author oemy9
 */
public class QrGeneratorEmpleado {
    
    private String numeroEmpleado;
    private String nombreEmpleado;

    public QrGeneratorEmpleado(String numeroEmpleado, String nombreEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombreEmpleado = nombreEmpleado;
    }

    /*
       Nombre|#Emp|Dígito Verificador.
       La cadena sería separada por Pipes (|) los tres datos que describo:
       Nombre, Nombre completo del socio.
       Núm. Emp, Número de empleado.    
    */
    public String getResultado(){
        return getNombreEmpleado()!=null? getNombreEmpleado().concat("|")
                .concat(getNumeroEmpleado())
                .concat("|")
                .concat(getDigitoVerificador(1).toString()): "";
    }
    
    /*
      Dígito Verificador: Un número conformado por la suma del número del mes mas el número del día, 
       más el último dígito del número de empleado.
    */
    private Integer getDigitoVerificador(int n){

             Calendar c=Calendar.getInstance();
             int digitoInt=Integer.parseInt(getUltimoDigito(n));
             int mes=c.get(Calendar.MONTH)+1;
             int diaMes=c.get(Calendar.DAY_OF_MONTH);
             return mes+diaMes+digitoInt;
           
    }
   
    private String getUltimoDigito(int index){
        String ultimoDigito=null;
        for (int i = getNumeroEmpleado().toCharArray().length-1; i >= 0; i--) {
                Character c=getNumeroEmpleado().charAt(i);
                if(Character.isDigit(c)){
                    ultimoDigito=c.toString();
                    break;
               }
        }
        return ultimoDigito;
    }
  
    
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    
   
}
