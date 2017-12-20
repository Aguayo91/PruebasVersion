package com.sociomas.core.WebService.Model.Request.Alta;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jromeromar on 22/09/2017.
 */


public class Vehiculo implements Serializable {

    @SerializedName("foto")
    private String foto;

    @SerializedName("id_dispositivo")
    private String id_dispositivo;

    @SerializedName("id_empresa")
    private String id_empresa;

    @SerializedName("id_num_empleado")
    private String id_num_empleado;

    @SerializedName("id_puesto")
    private int id_puesto;

    @SerializedName("id_rol")
    private int id_rol;

    @SerializedName("pais")
    private String pais;

    @SerializedName("telefono_valido")
    private String telefono_valido;

    @SerializedName("token")
    private String token;

    @SerializedName("va_nombre_completo")
    private String va_nombre_completo;

    @SerializedName("va_numero_telefono")
    private String va_numero_telefono;

    @SerializedName("codigo_color")
    private String codigoColor;

    @SerializedName("fechahora_creacion")
    private String fechahora_creacion;

    @SerializedName("id_automovil")
    private String idAutomovil;

    @SerializedName("modelo")
    private String modelo;

    @SerializedName("numero_asientos")
    private int numeroAsientos;

    private int asientosDisponiblesIda;

    private int asientosDisponiblesVuelta;

    @SerializedName("placas")
    private String placas;


    public Vehiculo() {

    }

    public int getAsientosDisponiblesIda() {
        return asientosDisponiblesIda;
    }

    public void setAsientosDisponiblesIda(int asientosDisponiblesIda) {
        this.asientosDisponiblesIda = asientosDisponiblesIda;
    }

    public int getAsientosDisponiblesVuelta() {
        return asientosDisponiblesVuelta;
    }

    public void setAsientosDisponiblesVuelta(int asientosDisponiblesVuelta) {
        this.asientosDisponiblesVuelta = asientosDisponiblesVuelta;
    }

    public Vehiculo(String codigoColor, String modelo, int numeroAsientos, String placas) {
        this.codigoColor = codigoColor;
        this.modelo = modelo;
        this.numeroAsientos = numeroAsientos;
        this.placas = placas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_num_empleado() {
        return id_num_empleado;
    }

    public void setId_num_empleado(String id_num_empleado) {
        this.id_num_empleado = id_num_empleado;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono_valido() {
        return telefono_valido;
    }

    public void setTelefono_valido(String telefono_valido) {
        this.telefono_valido = telefono_valido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVa_nombre_completo() {
        return va_nombre_completo;
    }

    public void setVa_nombre_completo(String va_nombre_completo) {
        this.va_nombre_completo = va_nombre_completo;
    }

    public String getVa_numero_telefono() {
        return va_numero_telefono;
    }

    public void setVa_numero_telefono(String va_numero_telefono) {
        this.va_numero_telefono = va_numero_telefono;
    }

    public String getCodigoColor() {
        if(codigoColor==null){
            return "";
        }
        else if(codigoColor.contains("#")){
            return  codigoColor.replace("#","");
        }
        else{
            return codigoColor;
        }
    }

    public void setCodigoColor(String codigoColor) {
        this.codigoColor = codigoColor;
    }

    public String getFechahora_creacion() {
        return fechahora_creacion;
    }

    public void setFechahora_creacion(String fechahora_creacion) {
        this.fechahora_creacion = fechahora_creacion;
    }

    public String getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(String idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumeroAsientos() {
        return numeroAsientos;
    }


    public int getAsientosDisponibles(){
        return  numeroAsientos-1;
    }

    public void setNumeroAsientos(int numeroAsientos) {
        this.numeroAsientos = numeroAsientos;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "foto='" + foto + '\'' +
                ", id_dispositivo='" + id_dispositivo + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", id_num_empleado='" + id_num_empleado + '\'' +
                ", id_puesto=" + id_puesto +
                ", id_rol=" + id_rol +
                ", pais='" + pais + '\'' +
                ", telefono_valido='" + telefono_valido + '\'' +
                ", token='" + token + '\'' +
                ", va_nombre_completo='" + va_nombre_completo + '\'' +
                ", va_numero_telefono='" + va_numero_telefono + '\'' +
                ", codigoColor='" + codigoColor + '\'' +
                ", fechahora_creacion='" + fechahora_creacion + '\'' +
                ", idAutomovil='" + idAutomovil + '\'' +
                ", modelo='" + modelo + '\'' +
                ", numeroAsientos=" + numeroAsientos +
                ", placas='" + placas + '\'' +
                '}';
    }
}
