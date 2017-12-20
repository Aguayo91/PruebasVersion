package com.sociomas.core.Utils.Enums;

import android.content.Intent;

/**
 * Created by oemy9 on 28/03/2017.
 */

    @SuppressWarnings("unused")
    public  enum EnumAlarma
    {
        entrada, diez_minutos, salida;
        private static  final String name=EnumAlarma.class.getName();
        public  void attach(Intent intent){
                intent.putExtra(name,ordinal());
        }
        public static EnumAlarma getFrom(Intent intent){
            if(!intent.hasExtra(name)){
                throw new IllegalStateException();
            }
            return  values()[intent.getIntExtra(name,-1)];
        }
    }

