package com.sociomas.core.Utils.Enums;

/**
 * Created by GiioToledo on 27/11/17.
 */

public enum EnumNotificacion {
    LISTA_CATALOGO_NOTIFICACIONES(1);

    private int value;

    EnumNotificacion(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public static EnumNotificacion fromValue(int value) {
        for (EnumNotificacion type : EnumNotificacion.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
