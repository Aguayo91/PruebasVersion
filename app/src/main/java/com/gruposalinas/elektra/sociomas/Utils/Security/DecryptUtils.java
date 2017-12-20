package com.gruposalinas.elektra.sociomas.Utils.Security;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by oemy9 on 08/03/2017.
 */

public class DecryptUtils {

    public static final String TAG = "UTILS";
    public static  int minutos=0;
    public static  int intentos=0;
    /*
        Regresa Fecha en formato listo para encriptar
    * */
    public static String getFormatDate(int minutos) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MINUTE,minutos);
        dateFormat.format(cal.getTime());
        return dateFormat.format(cal.getTime());
    }



    /*

        Regresa la llave de encription de cada uno de los métodos
    */
    public static String getKeyEncription(int minutos) {
        return getFormatDate(minutos);
    }

    /*
    *   Regresa  IV  obligatorio para generar el encriptamiento
    * */
    public static String getIV() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(cal.getTime());
    }

    private static String baseDecrypt(String textoEncriptado, int minutos) throws  Exception{
        String decrypt="";
        CryptLib cryptLib = new CryptLib();
        String key = getKeyEncription(minutos);
        String iv = getIV();
        decrypt = cryptLib.decrypt(textoEncriptado.trim(), key, iv);
        return decrypt.trim();
    }

    /*DESENCRIPTA EL TEXTO QUE PROVIENE DEL WEBSERVICE ENCRIPTADO*/
    public static String decryptAES(String textoEncriptado) {
        String decrypt="";
        try {
            //¿CUÁNTAS VECES HEMOS TRATADO DE DESENCRIPTAR EL TEXTO?
            switch (intentos){
                case 0: // NO SE AGREGAN MINUTOS
                    decrypt=baseDecrypt(textoEncriptado,0);
                    break;
                case 1: //SE LE RESTA UN MINUTO A LA HORA ACTUAL
                   decrypt=baseDecrypt(textoEncriptado,-1);
                break;
                case 2: // SE LE RESTA DOS MINUTOS A LA HORA ACTUAL
                    decrypt=baseDecrypt(textoEncriptado,-2);
                    break;
                case 3:
                    decrypt=baseDecrypt(textoEncriptado,-3);
                    break;
                case 4: // SE LE SUMA UN MINUTO A LA HORA ACTUAL
                    decrypt=baseDecrypt(textoEncriptado,1);
                break;
                case 5: // SE LE SUMAN DOS MINUTOS A LA HORA ACTUAL
                    decrypt=baseDecrypt(textoEncriptado,2);
                    break;
                case 6: // SE LE SUMAN DOS MINUTOS A LA HORA ACTUAL
                    decrypt=baseDecrypt(textoEncriptado,3);
                    break;
            }


        }
        catch (Exception ex){
            intentos++;
            Log.i(TAG,"EX INTENTOS"+String.valueOf(intentos));
            decrypt=decryptAES(textoEncriptado);
        }
        if(!decrypt.isEmpty()){
            intentos=0;
        }
        return  decrypt;

    }
    /*
    public static  boolean tokenHasError(String errorToken){
        return  errorToken.equals(Constants.ERROR_LLAVE);
    }*/

}
