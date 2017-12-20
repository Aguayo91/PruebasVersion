package com.sociomas.core.Utils.Enums;

/**
 * Created by gtoledo on 27/09/2017.
 */

public enum EnumNomina {

    RECIBOS_PENDIENTES("1"),
    RECIBOS_DETALLE("2"),
    RECIBOS_CONCENTRADO("3"),
    LIBERAR_NOMINA("4"),
    CONSULTA_CUENTA("5");

    private String value;

    EnumNomina(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String get() {
        return this.value;
    }
}
