package com.sociomas.core.WebService.Model.Response.Aventones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jromeromar on 16/10/2017.
 */

public class Aventone  implements Serializable{

    @SerializedName("DiasAventon")
    @Expose
    private List<DiasAventon> diasAventon = null;
    @SerializedName("alimentos")
    @Expose
    private Boolean alimentos;
    @SerializedName("asientos")
    @Expose
    private Integer asientos;
    @SerializedName("desc_destino")
    @Expose
    private String descDestino;
    @SerializedName("desc_origen")
    @Expose
    private String descOrigen;
    @SerializedName("equipaje")
    @Expose
    private Boolean equipaje;
    @SerializedName("estatus_descripcion")
    @Expose
    private Object estatusDescripcion;
    @SerializedName("fumar")
    @Expose
    private Boolean fumar;
    @SerializedName("hora_llegada")
    @Expose
    private String horaLlegada;
    @SerializedName("hora_salida")
    @Expose
    private String horaSalida;
    @SerializedName("id_automovil")
    @Expose
    private Integer idAutomovil;
    @SerializedName("id_aventon")
    @Expose
    private Integer idAventon;
    @SerializedName("id_estatus_solicitud_aventon")
    @Expose
    private Integer idEstatusSolicitudAventon;
    @SerializedName("id_num_empleado")
    @Expose
    private Object idNumEmpleado;
    @SerializedName("id_tipo_aventon")
    @Expose
    private Integer idTipoAventon;
    @SerializedName("id_trayecto_aventon")
    @Expose
    private Integer idTrayectoAventon;
    @SerializedName("latitud_destino")
    @Expose
    private Integer latitudDestino;
    @SerializedName("latitud_origen")
    @Expose
    private Integer latitudOrigen;
    @SerializedName("longitud_destino")
    @Expose
    private Integer longitudDestino;
    @SerializedName("longitud_origen")
    @Expose
    private Integer longitudOrigen;
    @SerializedName("mascotas")
    @Expose
    private Boolean mascotas;
    @SerializedName("modelo")
    @Expose
    private Object modelo;
    @SerializedName("ni\u00f1os")
    @Expose
    private Boolean niOs;
    @SerializedName("nombre_completo")
    @Expose
    private Object nombreCompleto;
    @SerializedName("placas")
    @Expose
    private Object placas;
    @SerializedName("telefono")
    @Expose
    private Object telefono;
    @SerializedName("tiempo_tolerancia")
    @Expose
    private Object tiempoTolerancia;
    @SerializedName("va_numero_pos")
    @Expose
    private Integer vaNumeroPos;
    @SerializedName("viaje_redondo")
    @Expose
    private Boolean viajeRedondo;

    public List<DiasAventon> getDiasAventon() {
        return diasAventon;
    }

    public void setDiasAventon(List<DiasAventon> diasAventon) {
        this.diasAventon = diasAventon;
    }

    public Aventone withDiasAventon(List<DiasAventon> diasAventon) {
        this.diasAventon = diasAventon;
        return this;
    }

    public Boolean getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(Boolean alimentos) {
        this.alimentos = alimentos;
    }

    public Aventone withAlimentos(Boolean alimentos) {
        this.alimentos = alimentos;
        return this;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    public Aventone withAsientos(Integer asientos) {
        this.asientos = asientos;
        return this;
    }

    public String getDescDestino() {
        return descDestino;
    }

    public void setDescDestino(String descDestino) {
        this.descDestino = descDestino;
    }

    public Aventone withDescDestino(String descDestino) {
        this.descDestino = descDestino;
        return this;
    }

    public String getDescOrigen() {
        return descOrigen;
    }

    public void setDescOrigen(String descOrigen) {
        this.descOrigen = descOrigen;
    }

    public Aventone withDescOrigen(String descOrigen) {
        this.descOrigen = descOrigen;
        return this;
    }

    public Boolean getEquipaje() {
        return equipaje;
    }

    public void setEquipaje(Boolean equipaje) {
        this.equipaje = equipaje;
    }

    public Aventone withEquipaje(Boolean equipaje) {
        this.equipaje = equipaje;
        return this;
    }

    public Object getEstatusDescripcion() {
        return estatusDescripcion;
    }

    public void setEstatusDescripcion(Object estatusDescripcion) {
        this.estatusDescripcion = estatusDescripcion;
    }

    public Aventone withEstatusDescripcion(Object estatusDescripcion) {
        this.estatusDescripcion = estatusDescripcion;
        return this;
    }

    public Boolean getFumar() {
        return fumar;
    }

    public void setFumar(Boolean fumar) {
        this.fumar = fumar;
    }

    public Aventone withFumar(Boolean fumar) {
        this.fumar = fumar;
        return this;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Aventone withHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
        return this;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Aventone withHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
        return this;
    }

    public Integer getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(Integer idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public Aventone withIdAutomovil(Integer idAutomovil) {
        this.idAutomovil = idAutomovil;
        return this;
    }

    public Integer getIdAventon() {
        return idAventon;
    }

    public void setIdAventon(Integer idAventon) {
        this.idAventon = idAventon;
    }

    public Aventone withIdAventon(Integer idAventon) {
        this.idAventon = idAventon;
        return this;
    }

    public Integer getIdEstatusSolicitudAventon() {
        return idEstatusSolicitudAventon;
    }

    public void setIdEstatusSolicitudAventon(Integer idEstatusSolicitudAventon) {
        this.idEstatusSolicitudAventon = idEstatusSolicitudAventon;
    }

    public Aventone withIdEstatusSolicitudAventon(Integer idEstatusSolicitudAventon) {
        this.idEstatusSolicitudAventon = idEstatusSolicitudAventon;
        return this;
    }

    public Object getIdNumEmpleado() {
        return idNumEmpleado;
    }

    public void setIdNumEmpleado(Object idNumEmpleado) {
        this.idNumEmpleado = idNumEmpleado;
    }

    public Aventone withIdNumEmpleado(Object idNumEmpleado) {
        this.idNumEmpleado = idNumEmpleado;
        return this;
    }

    public Integer getIdTipoAventon() {
        return idTipoAventon;
    }

    public void setIdTipoAventon(Integer idTipoAventon) {
        this.idTipoAventon = idTipoAventon;
    }

    public Aventone withIdTipoAventon(Integer idTipoAventon) {
        this.idTipoAventon = idTipoAventon;
        return this;
    }

    public Integer getIdTrayectoAventon() {
        return idTrayectoAventon;
    }

    public void setIdTrayectoAventon(Integer idTrayectoAventon) {
        this.idTrayectoAventon = idTrayectoAventon;
    }

    public Aventone withIdTrayectoAventon(Integer idTrayectoAventon) {
        this.idTrayectoAventon = idTrayectoAventon;
        return this;
    }

    public Integer getLatitudDestino() {
        return latitudDestino;
    }

    public void setLatitudDestino(Integer latitudDestino) {
        this.latitudDestino = latitudDestino;
    }

    public Aventone withLatitudDestino(Integer latitudDestino) {
        this.latitudDestino = latitudDestino;
        return this;
    }

    public Integer getLatitudOrigen() {
        return latitudOrigen;
    }

    public void setLatitudOrigen(Integer latitudOrigen) {
        this.latitudOrigen = latitudOrigen;
    }

    public Aventone withLatitudOrigen(Integer latitudOrigen) {
        this.latitudOrigen = latitudOrigen;
        return this;
    }

    public Integer getLongitudDestino() {
        return longitudDestino;
    }

    public void setLongitudDestino(Integer longitudDestino) {
        this.longitudDestino = longitudDestino;
    }

    public Aventone withLongitudDestino(Integer longitudDestino) {
        this.longitudDestino = longitudDestino;
        return this;
    }

    public Integer getLongitudOrigen() {
        return longitudOrigen;
    }

    public void setLongitudOrigen(Integer longitudOrigen) {
        this.longitudOrigen = longitudOrigen;
    }

    public Aventone withLongitudOrigen(Integer longitudOrigen) {
        this.longitudOrigen = longitudOrigen;
        return this;
    }

    public Boolean getMascotas() {
        return mascotas;
    }

    public void setMascotas(Boolean mascotas) {
        this.mascotas = mascotas;
    }

    public Aventone withMascotas(Boolean mascotas) {
        this.mascotas = mascotas;
        return this;
    }

    public Object getModelo() {
        return modelo;
    }

    public void setModelo(Object modelo) {
        this.modelo = modelo;
    }

    public Aventone withModelo(Object modelo) {
        this.modelo = modelo;
        return this;
    }

    public Boolean getNiOs() {
        return niOs;
    }

    public void setNiOs(Boolean niOs) {
        this.niOs = niOs;
    }

    public Aventone withNiOs(Boolean niOs) {
        this.niOs = niOs;
        return this;
    }

    public Object getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(Object nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Aventone withNombreCompleto(Object nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public Object getPlacas() {
        return placas;
    }

    public void setPlacas(Object placas) {
        this.placas = placas;
    }

    public Aventone withPlacas(Object placas) {
        this.placas = placas;
        return this;
    }

    public Object getTelefono() {
        return telefono;
    }

    public void setTelefono(Object telefono) {
        this.telefono = telefono;
    }

    public Aventone withTelefono(Object telefono) {
        this.telefono = telefono;
        return this;
    }

    public Object getTiempoTolerancia() {
        return tiempoTolerancia;
    }

    public void setTiempoTolerancia(Object tiempoTolerancia) {
        this.tiempoTolerancia = tiempoTolerancia;
    }

    public Aventone withTiempoTolerancia(Object tiempoTolerancia) {
        this.tiempoTolerancia = tiempoTolerancia;
        return this;
    }

    public Integer getVaNumeroPos() {
        return vaNumeroPos;
    }

    public void setVaNumeroPos(Integer vaNumeroPos) {
        this.vaNumeroPos = vaNumeroPos;
    }

    public Aventone withVaNumeroPos(Integer vaNumeroPos) {
        this.vaNumeroPos = vaNumeroPos;
        return this;
    }

    public Boolean getViajeRedondo() {
        return viajeRedondo;
    }

    public void setViajeRedondo(Boolean viajeRedondo) {
        this.viajeRedondo = viajeRedondo;
    }

    public Aventone withViajeRedondo(Boolean viajeRedondo) {
        this.viajeRedondo = viajeRedondo;
        return this;
    }

}
