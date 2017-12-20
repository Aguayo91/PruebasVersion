package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 30/11/2017.
 */

public enum EnumTipoLugarTrabajo  {

    FIJO(1),
    VARIABLE(2);

    EnumTipoLugarTrabajo(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
