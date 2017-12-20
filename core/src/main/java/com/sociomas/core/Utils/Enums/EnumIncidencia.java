package com.sociomas.core.Utils.Enums;

import android.util.Log;

import java.util.Locale;

/**
 * Created by oemy9 on 15/05/2017.
 */

public enum EnumIncidencia {
    sin_justificar, autorizado, pendiente,rechazado;

    public static EnumIncidencia getFromSting(String tipo) {
        EnumIncidencia enumIncidencia=sin_justificar;
        switch (tipo.toUpperCase(Locale.ENGLISH)) {
            case "SIN JUSTIFICAR":
                enumIncidencia = sin_justificar;
                break;
            case "AUTORIZADO":
                enumIncidencia = autorizado;
                break;
            case "PENDIENTE DE AUTORIZACION":
            case "PENDIENTE POR AUTORIZAR":
            case "PENDIENTE DE AUTORIZACIÓN":
                enumIncidencia = pendiente;
                break;
            case "RECHAZADO":
            case "RECHAZADA":
                enumIncidencia=rechazado;
                break;
            default:
                Log.i("ENUM INCIDENCIA","No reconocido:"+tipo);
                break;
        }
        return enumIncidencia;
    }
}