package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 18/11/2017.
 */

public enum  EnumJornada {

    ENTRADA(1), SALIDA(2);

    private int value;

    EnumJornada(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
