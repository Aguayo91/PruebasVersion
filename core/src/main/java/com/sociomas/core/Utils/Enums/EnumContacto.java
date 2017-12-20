package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 19/08/2017.
 */

public enum EnumContacto {
    Domicilio{
        @Override
        public String toString() {
            return "Domicilio";
        }
    },Telefono {
        @Override
        public String toString() {
            return "Teléfono";
        }
    },Condiciones{
        @Override
        public String toString() {
            return "Términos y condiciones";
        }
    };
}
