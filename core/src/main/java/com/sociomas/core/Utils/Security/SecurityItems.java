package com.sociomas.core.Utils.Security;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
* *
 *   ELEMENTOS NECESARIOS PARA LA COMUNICACIÓN ENTRE EL WEBSERVICE Y LA APP
 */
@SuppressWarnings("unused")
public class SecurityItems {

    public static final String TAG = "SEC_ITEMS";
    private String idEmploy;
    private Date date;
    private String password;
    private String key=null, iv=null;
    public SecurityItems(String idEmploy){
        this.idEmploy=idEmploy;
    }

    public SecurityItems(String idEmploy,Date date){
        this.idEmploy=idEmploy;
        this.date=date;
    }


    public SecurityItems(String idEmploy,String password){
        this.idEmploy=idEmploy;
        this.password=password;
    }

   //SETTERS
    public void setPassword(String password){
        this.password=password;
    }

    public void setIdEmploy(String idEmploy){
        this.idEmploy=idEmploy;
    }


    /*
    * TOKEN ENCRYPTADO
    *
    * */
    public String getTokenEncrypt(){
        return encryptAES(getToken(this.getIdEmployEncrypt()));
    }

    /*
    * ID EMPLEADO ENCRYPTADO
    * */

    public String getIdEmployEncrypt(){
       return encryptAES(this.idEmploy);
    }

    public String getPasswordEncrypt(){
        return  encryptAES(this.password);
    }




    /*
        Regresa Fecha en formato listo para encriptar
    * */
    private  String getFormatDate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return f.format(date!=null? date:new Date());
    }



    /*
        Concatena la fecha obtenida con algún oto texto por ejemplo el número de empleado
    * */
    private String getToken(String idEmployEncrypt) {
        return idEmployEncrypt.concat(getFormatDate());
    }

    /*

        Regresa la llave de encription de cada uno de los métodos
    */
    private  String getKeyEncription() {
        return getFormatDate();
    }

    /*
    *   Regresa  IV  obligatorio para generar el encriptamiento
    * */
    private String getIV() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHH");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return f.format(date!=null? date:new Date());

    }

    private void  checkIfHaveChanges(){
        if(key==null || iv==null){
            this.key=getKeyEncription();
            this.iv=getIV();
        }
        else if(!key.equals(getKeyEncription())){
            this.key=getKeyEncription();
        }
        else  if(!iv.equals(getIV())){
            this.iv=getIV();
        }
    }

    private String encryptAES(String textoEncriptar) {
        String encriptado = "";
        try {
          this.checkIfHaveChanges();
            CryptLib cryptLib = new CryptLib();
            String key = this.key;
            String iv = this.iv;
            encriptado = cryptLib.encrypt(textoEncriptar, key, iv);
            return encriptado.trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encriptado.trim();
    }

    public  String decryptAES(String textoEncriptado) {
        String decrypt = "";
        try {
            this.checkIfHaveChanges();
            CryptLib cryptLib = new CryptLib();
            String key = this.key;
            String iv = this.iv;
            decrypt = cryptLib.decrypt(textoEncriptado.trim(), key, iv);
            return decrypt.trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decrypt;
    }

    public String getRequestNominaEncrypt (String requestNomina){
        return encryptAES(requestNomina);
    }

    public String getTextDecrypt (String textDecrypt){
        return decryptAES(textDecrypt);
    }


}
