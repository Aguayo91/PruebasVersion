package com.sociomas.core.Utils.Enums;

import java.util.Locale;

/**
 * Created by oemy9 on 23/05/2017.
 */

@SuppressWarnings("unused")
public enum EnumExclusion {
    visita_medica(1),asunto_personal(2),
    descanso_vacaciones(3), ;

    private int value;

     EnumExclusion(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public static EnumExclusion getFromString(String value){
        EnumExclusion exclusion=asunto_personal;
        switch (value.toLowerCase(Locale.ENGLISH)){
            case "visita médica /incapacidad":
                exclusion=visita_medica;
                break;
            case "descanso / vacaciones":
                exclusion=descanso_vacaciones;
                break;
            case "asunto personal":
                exclusion=asunto_personal;
                break;
        }
        return exclusion;
    }
}
