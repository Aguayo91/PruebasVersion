package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 25/11/2017.
 */

public enum EnumAsistencia {
    ASISTENCIA_CORRECTA(1){
        @Override
        public String toString() {
            return  "Asistencia Correcta";
        }
    },
    RETARDO(2){
        @Override
        public String toString() {
            return "Retardo";
        }
    },
    SALIDA_ANTES_HORARIO(3){
        @Override
        public String toString() {
            return "Salida antes de horario";
        }
    },
    ENTRADA_DESPUES_HORA_LIMITE(4){
        @Override
        public String toString() {
            return "Entrada después de hora límite";
        }
    },
    TODAVIA_NO_TERMINA_DIA(5){
        @Override
        public String toString() {
            return "Todavía no termina el día";
        }
    },
    FALTA(6){
        @Override
        public String toString() {
            return "Falta";
        }
    },
    NO_ASIGNADO(7){
        @Override
        public String toString() {
            return "No asignado";
        }
    };

    private int value;
     EnumAsistencia(int value){
            this.value=value;
    }

    public int getValue() {
        return value;
    }


    public static  EnumAsistencia fromString(String value){
        for (EnumAsistencia type : EnumAsistencia.values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }


        return null;
    }


    public static EnumAsistencia fromValue(int value) {
        for (EnumAsistencia type : EnumAsistencia.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
