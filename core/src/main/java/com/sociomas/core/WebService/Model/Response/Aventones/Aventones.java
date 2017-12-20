package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 04/10/2017.
 */

public class Aventones implements Serializable {


    private boolean mostrasInfoAdicional=false;


    public boolean isMostrasInfoAdicional() {
        return mostrasInfoAdicional;
    }

    public void setMostrasInfoAdicional(boolean mostrasInfoAdicional) {
        this.mostrasInfoAdicional = mostrasInfoAdicional;
    }

    @SerializedName("alimentos")
    private boolean alimentos;

    @SerializedName("asientos")
    private int asientos;

    @SerializedName("desc_destino")
    private String desc_destino;

    @SerializedName("desc_origen")
    private String desc_origen;

    @SerializedName("equipaje")
    private boolean equipaje;

    @SerializedName("fumar")
    private boolean fumar;

    @SerializedName("hora_llegada")
    private String hora_llegada;

    @SerializedName("hora_salida")
    private String hora_salida;

    @SerializedName("id_automovil")
    private int id_automovil;

    @SerializedName("id_aventon")
    private int id_aventon;

    @SerializedName("id_num_empleado")
    private String id_num_empleado;

    @SerializedName("id_trayecto_aventon")
    private int id_trayecto_aventon;

    @SerializedName("mascotas")
    private boolean mascotas;

    @SerializedName("modelo")
    private String modelo;

    @SerializedName("niños")
    private boolean niños;

    @SerializedName("nombre_completo")
    private String nombre_completo;

    @SerializedName("observaciones")
    private String observaciones;

    @SerializedName("placas")
    private String placas;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("va_numero_pos")
    private int va_numero_pos;

    @SerializedName("latitud_destino")
    private double latitud_destino;

    @SerializedName("latitud_origen")
    private double latitud_origen;

    @SerializedName("longitud_destino")
    private double longitud_destino;

    @SerializedName("longitud_origen")
    private double longitud_origen;

    @SerializedName("tiempo_tolerancia")
    private String tiempo_tolerancia;

    @SerializedName("DiasAventon")
    private List<DiasAventon> diasAventonList;

    @SerializedName("id_tipo_aventon")
    private int idTipoAventon;

    @SerializedName("viaje_redondo")
    private boolean viajeRedondo;

    @SerializedName("estatus_descripcion")
    private String estatusDescripcion;

    @SerializedName("id_estatus_solicitud_aventon")
    private int idaventon;

    public boolean isAlimentos() {
        return alimentos;
    }

    public void setAlimentos(boolean alimentos) {
        this.alimentos = alimentos;
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public String getDesc_destino() {
        return desc_destino;
    }

    public void setDesc_destino(String desc_destino) {
        this.desc_destino = desc_destino;
    }

    public String getDesc_origen() {
        return desc_origen;
    }

    public void setDesc_origen(String desc_origen) {
        this.desc_origen = desc_origen;
    }

    public boolean isEquipaje() {
        return equipaje;
    }

    public void setEquipaje(boolean equipaje) {
        this.equipaje = equipaje;
    }

    public boolean isFumar() {
        return fumar;
    }

    public void setFumar(boolean fumar) {
        this.fumar = fumar;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getId_automovil() {
        return id_automovil;
    }

    public void setId_automovil(int id_automovil) {
        this.id_automovil = id_automovil;
    }

    public int getId_aventon() {
        return id_aventon;
    }

    public void setId_aventon(int id_aventon) {
        this.id_aventon = id_aventon;
    }

    public String getId_num_empleado() {
        return id_num_empleado;
    }

    public String getTiempo_tolerancia() {return tiempo_tolerancia;}

    public void setTiempo_tolerancia(String tiempo_tolerancia) {this.tiempo_tolerancia = tiempo_tolerancia;}

    public void setId_num_empleado(String id_num_empleado) {
        this.id_num_empleado = id_num_empleado;
    }

    public int getId_trayecto_aventon() {
        return id_trayecto_aventon;
    }

    public void setId_trayecto_aventon(int id_trayecto_aventon) {
        this.id_trayecto_aventon = id_trayecto_aventon;
    }

    public boolean isMascotas() {
        return mascotas;
    }

    public void setMascotas(boolean mascotas) {
        this.mascotas = mascotas;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isNiños() {
        return niños;
    }

    public void setNiños(boolean niños) {
        this.niños = niños;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public int getVa_numero_pos() {
        return va_numero_pos;
    }

    public void setVa_numero_pos(int va_numero_pos) {
        this.va_numero_pos = va_numero_pos;
    }

    public double getLatitud_destino() {
        return latitud_destino;
    }

    public void setLatitud_destino(double latitud_destino) {
        this.latitud_destino = latitud_destino;
    }

    public double getLatitud_origen() {
        return latitud_origen;
    }

    public void setLatitud_origen(double latitud_origen) {
        this.latitud_origen = latitud_origen;
    }

    public double getLongitud_destino() {
        return longitud_destino;
    }

    public void setLongitud_destino(double longitud_destino) {
        this.longitud_destino = longitud_destino;
    }

    public double getLongitud_origen() {
        return longitud_origen;
    }

    public void setLongitud_origen(double longitud_origen) {
        this.longitud_origen = longitud_origen;
    }

    public List<DiasAventon> getDiasAventonList() {
        return diasAventonList;
    }

    public void setDiasAventonList(List<DiasAventon> diasAventonList) {
        this.diasAventonList = diasAventonList;
    }

    public int getIdTipoAventon() {
        return idTipoAventon;
    }

    public void setIdTipoAventon(int idTipoAventon) {
        this.idTipoAventon = idTipoAventon;
    }

    public boolean isViajeRedondo() {
        return viajeRedondo;
    }

    public void setViajeRedondo(boolean viajeRedondo) {
        this.viajeRedondo = viajeRedondo;
    }

    public String getEstatusDescripcion() {
        return estatusDescripcion;
    }

    public void setEstatusDescripcion(String estatusDescripcion) {
        this.estatusDescripcion = estatusDescripcion;
    }

    public int getIdaventon() {
        return idaventon;
    }

    public void setIdaventon(int idaventon) {
        this.idaventon = idaventon;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Aventones{" +
                "mostrasInfoAdicional=" + mostrasInfoAdicional +
                ", alimentos=" + alimentos +
                ", asientos=" + asientos +
                ", desc_destino='" + desc_destino + '\'' +
                ", desc_origen='" + desc_origen + '\'' +
                ", equipaje=" + equipaje +
                ", fumar=" + fumar +
                ", hora_llegada='" + hora_llegada + '\'' +
                ", hora_salida='" + hora_salida + '\'' +
                ", id_automovil=" + id_automovil +
                ", id_aventon=" + id_aventon +
                ", id_num_empleado='" + id_num_empleado + '\'' +
                ", id_trayecto_aventon=" + id_trayecto_aventon +
                ", mascotas=" + mascotas +
                ", modelo='" + modelo + '\'' +
                ", niños=" + niños +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", placas='" + placas + '\'' +
                ", telefono='" + telefono + '\'' +
                ", va_numero_pos=" + va_numero_pos +
                ", latitud_destino=" + latitud_destino +
                ", latitud_origen=" + latitud_origen +
                ", longitud_destino=" + longitud_destino +
                ", longitud_origen=" + longitud_origen +
                ", tiempo_tolerancia='" + tiempo_tolerancia + '\'' +
                ", diasAventonList=" + diasAventonList +
                ", idTipoAventon=" + idTipoAventon +
                ", viajeRedondo=" + viajeRedondo +
                ", estatusDescripcion='" + estatusDescripcion + '\'' +
                ", idaventon=" + idaventon +
                '}';
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
