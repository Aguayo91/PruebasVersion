package com.gruposalinas.elektra.sociomas.Utils.Security;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

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
    static final String KEYGEN = "TH3B37511022SB@6025e7TS3RV1C763Z";
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

    public String getRequestNominaEncrypt(String requestNomina) {
        return encryptAES(requestNomina);
    }

    public String getTextDecrypt(String textEncrypt){
        return decryptAES(textEncrypt);
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
    public String getToken(String idEmployEncrypt) {
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

    public static String  encryptText(String text) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        if(text.trim().length()==0)
            return  "";

        Key key = getKey();
        Cipher cipher = getCipherInstance();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String data = text;

        byte[] stringBytes = data.getBytes("UTF8");
        byte[] raw = cipher.doFinal(stringBytes);
        String base64 = Base64.encodeToString(raw, Base64.DEFAULT);
        //Codificamos
        //base64 = URLEncoder.encode(base64,"UTF-8");
        return base64;

    }

    public static String decryptText(String text) throws InvalidKeyException,
            IOException, IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException {
        if(text.trim().length()==0)
            return  "";

        Key key = getKey();
        Cipher cipher = getCipherInstance();

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] raw = Base64.decode(text, Base64.DEFAULT);
        byte[] stringBytes = cipher.doFinal(raw);
        String result = new String(stringBytes, "UTF8");
        return result;
    }

    private static Key getKey() throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec(KEYGEN.getBytes());
        SecretKey ks = skf.generateSecret(kspec);
        return ks;
    }

    private static Cipher getCipherInstance() throws NoSuchAlgorithmException,
            NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        return cipher;
    }


}
