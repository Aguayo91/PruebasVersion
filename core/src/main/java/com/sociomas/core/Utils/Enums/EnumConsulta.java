package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 08/06/2017.
 */

public enum  EnumConsulta {
    Mias{
        @Override
        public String toString() {
            return "Mias";
        }
    },LineaDirecta {
        @Override
        public String toString() {
            return "Línea directa";
        }
    };


    public static EnumConsulta fromString(String consulta){
        EnumConsulta enumConsulta=Mias;
        switch (consulta){
            case "Mias":
                enumConsulta=Mias;
                break;
            case "Línea directa":
                enumConsulta=LineaDirecta;
                break;
        }
        return enumConsulta;
    }



}
