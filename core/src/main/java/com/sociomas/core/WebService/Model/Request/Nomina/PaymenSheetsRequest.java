package com.sociomas.core.WebService.Model.Request.Nomina;



import java.io.Serializable;
import java.util.List;

/**
 * Created by gtoledo on 19/09/2017.
 */

public class PaymenSheetsRequest implements Serializable{
    private String accion;
    private String id_num_empleado;
    private String token;
    private Object jsonString;
//    private String nip;
//    private String firmaToken;
//    private boolean isNip;
//    private boolean isFirmaToken;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getId_num_empleado() {
        return id_num_empleado;
    }

    public void setId_num_empleado(String id_num_empleado) {
        this.id_num_empleado = id_num_empleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getJsonString() {
        return jsonString;
    }

    public void setJsonString(Object jsonString) {
        this.jsonString = jsonString;
    }
//
//    public String getNip() {
//        return nip;
//    }
//
//    public void setNip(String nip) {
//        this.nip = nip;
//    }
//
//    public String getFirmaToken() {
//        return firmaToken;
//    }
//
//    public void setFirmaToken(String firmaToken) {
//        this.firmaToken = firmaToken;
//    }
//
//    public boolean isNip() {
//        return isNip;
//    }
//
//    public void setNip(boolean nip) {
//        isNip = nip;
//    }
//
//    public boolean isFirmaToken() {
//        return isFirmaToken;
//    }
//
//    public void setFirmaToken(boolean firmaToken) {
//        isFirmaToken = firmaToken;
//    }

    @Override
    public String toString() {
        return "PaymenSheetsRequest{" +
                "accion='" + accion + '\'' +
                ", id_num_empleado='" + id_num_empleado + '\'' +
                ", token='" + token + '\'' +
                ", jsonString=" + jsonString +
//                ", nip='" + nip + '\'' +
//                ", firmaToken='" + firmaToken + '\'' +
//                ", isNip=" + isNip +
//                ", isFirmaToken=" + isFirmaToken +
                '}';
    }
}
