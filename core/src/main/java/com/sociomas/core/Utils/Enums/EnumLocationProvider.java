package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 16/11/2017.
 */

public enum  EnumLocationProvider {

    NO_ENCENDIDO(0){
        @Override
        public String toString() {
           return "NO_ENCENDIDO";
        }
    },

    GPS(1){
        @Override
        public String toString() {
            return "GPS";
        }
    },

    RED(2){
        @Override
        public String toString() {
            return "RED";
        }
    },

    UNKNOWN(3){
        @Override
        public String toString() {
            return "UNKNOWN";
        }
    },

    VIRTUAL(4){
        @Override
        public String toString() {
            return "VIRTUAL";
        }
    },

    OLD_PLAYSERVICES(5){
        @Override
        public String toString(){
            return "OLD_PLAYSERVICES";
        }
    };


    private int value;


    EnumLocationProvider(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
