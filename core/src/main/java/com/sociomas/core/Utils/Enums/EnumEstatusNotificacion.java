package com.sociomas.core.Utils.Enums;

/**
 * Created by GiioToledo on 27/11/17.
 */

public enum EnumEstatusNotificacion {
    ENVIADA(1),
    NO_ENVIADA(2),
    LEIDA(3),
    BORRADA(4);

    private int value;

    EnumEstatusNotificacion(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public static EnumEstatusNotificacion fromValue(int value) {
        for (EnumEstatusNotificacion type : EnumEstatusNotificacion.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
