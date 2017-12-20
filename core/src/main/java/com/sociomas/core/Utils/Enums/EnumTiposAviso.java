package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 16/11/2017.
 */

public enum EnumTiposAviso {
    POLITICAS_PRIVACIDAD(1){
        @Override
        public String toString() {
            return "Políticas de privacidad";
        }
    },
    TERMINOS_CONDICIONES(2){
        @Override
        public String toString() {
            return "Términos y condiciones";
        }
    };
    EnumTiposAviso(int value) {
        this.value = value;
    }
    private int value;
    public int getValue() {
        return value;
    }

    public static EnumTiposAviso fromString(String value){
        for (EnumTiposAviso e: EnumTiposAviso.values()){
            if(e.name().equalsIgnoreCase(value))
            {    return  e;
            }
        }
        return null;
    }

}
