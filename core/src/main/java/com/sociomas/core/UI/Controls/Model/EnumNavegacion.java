package com.sociomas.core.UI.Controls.Model;

/**
 * Created by oemy9 on 27/07/2017.
 */

public enum EnumNavegacion {
        MOVIILIDAD{
            @Override
            public String toString() {
                return "Movilidad";
            }
        },  ADMINISTRACION{
            @Override
            public String toString() {
                return "Administración";
            }
        },SEGURIDAD{
            @Override
            public String toString() {
                return "Seguridad";
            }
        },
        SOS
        {
            @Override
            public String toString() {
                return "SOS";
            }
        };



        public static EnumNavegacion fromString(String option){
            EnumNavegacion retorno=MOVIILIDAD;
            if(option.equalsIgnoreCase("Movilidad")){
                retorno =MOVIILIDAD;
            }
            else if(option.equalsIgnoreCase("Administración"))
            {
                retorno=ADMINISTRACION;
            }
            else if(option.equalsIgnoreCase("Seguridad")){
                retorno=SEGURIDAD;
            }
            return  retorno;
        }

}
