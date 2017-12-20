package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 22/08/2017.
 */

public enum EnumTiposArchivo {
    GENERAL(0),
    FOTO(1),
    IDENTIFICACION(2),
    PANICO(1);
    private int value;
    EnumTiposArchivo(int value){
        this.value=value;
    }
    public int getValue(){
        return this.value;
    }
}
