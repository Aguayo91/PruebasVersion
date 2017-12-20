package com.sociomas.core.Utils.Enums;

/**
 * Created by oemy9 on 17/10/2017.
 */

public enum EnumPerfilConductor {

     CONDUCTOR(1),
     PASAJERO(2),
     NINGUNO(3),
     PASAJERO_CONDUCTOR(4);

     private  int value;
     EnumPerfilConductor(int value){
         this.value=value;
     }

     public static EnumPerfilConductor getPerfil(int status){
         EnumPerfilConductor perfil=PASAJERO;
         switch (status){
             case 1:
                 perfil=CONDUCTOR;
                 break;
             case 2:
                 perfil=PASAJERO;
                 break;
             case 3:
                 perfil=NINGUNO;
                 break;
             case 4:
                 perfil=PASAJERO_CONDUCTOR;
                 break;
         }
         return perfil;
     }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
