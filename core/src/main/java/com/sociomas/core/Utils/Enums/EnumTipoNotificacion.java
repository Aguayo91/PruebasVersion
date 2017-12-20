package com.sociomas.core.Utils.Enums;

/**
 * Created by GiioToledo on 27/11/17.
 */

public enum EnumTipoNotificacion{
    Generales{
        @Override
        public String toString() {
            return "Generales";
        }
    },Asistencias {
        @Override
        public String toString() {
            return "Asistencias";
        }
    },Gafete{
        @Override
        public String toString() {
            return "Gafete";
        }
    },LiberacionDeNomina {
        @Override
        public String toString() {
            return "Liberacion de Nómina";
        }
    },Aventones {
        @Override
        public String toString() {
            return "Aventones";
        }
    };
}
