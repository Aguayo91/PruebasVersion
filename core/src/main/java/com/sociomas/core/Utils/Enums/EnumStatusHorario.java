package com.sociomas.core.Utils.Enums;

import android.util.Log;

import java.util.Locale;

/**
 * Created by oemy9 on 16/06/2017.
 */

public enum  EnumStatusHorario {
    PENDIENTE_LIBERAR,VALIDO,PROPUESTA,NO_ASIGNADO,DIA_LIBRE;
    public static  EnumStatusHorario fromString(String status){
        EnumStatusHorario statusHorario=VALIDO;
        switch (status.toUpperCase(Locale.ENGLISH)){
            case "PROPUESTA":
                statusHorario=PROPUESTA;
                break;
            case "PEND. POR LIBERAR":
                statusHorario=PENDIENTE_LIBERAR;
                break;
            case "VALIDO":
                statusHorario=VALIDO;
             break;
            case "DÍA LIBRE":
                statusHorario=DIA_LIBRE;
                break;
            case "NO_ASGINADO":
                statusHorario=NO_ASIGNADO;
                break;
            default:
                Log.i("ENUM","NO SOPORTADO: ".concat(status));
            break;
        }
        return statusHorario;
    }


}

