package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 23/05/2017.
 */

@SuppressWarnings("unused")
public enum  EnumStatusExclusion {
    autorizado(2),
    rechazado(3);
    private int value;
    EnumStatusExclusion(int value){
        this.value=value;
    }
    public int getValue() {
        return value;
    }
    public static EnumStatusExclusion getByValue(int value){
       EnumStatusExclusion item=autorizado;
        switch (value){
            case 2:
                item=autorizado;
            break;
            case 3:
                item=rechazado;
            break;
        }
        return  item;
    }
}
