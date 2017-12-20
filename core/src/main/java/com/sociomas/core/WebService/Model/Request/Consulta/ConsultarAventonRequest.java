package com.sociomas.core.WebService.Model.Request.Consulta;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultarAventonRequest  {

    @SerializedName("id_num_empleado")
    @Expose
    private String idNumEmpleado;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("orig_latitud")
    @Expose
    private Double origLatitud;
    @SerializedName("orig_longitud")
    @Expose
    private Double origLongitud;
    @SerializedName("placeId_origen")
    @Expose
    private String placeIdOrigen;
    @SerializedName("numero_pos_origen")
    @Expose
    private String numeroPosOrigen;
    @SerializedName("rango_origen")
    @Expose
    private Integer rangoOrigen;
    @SerializedName("dest_latitud")
    @Expose
    private Double destLatitud;
    @SerializedName("dest_longitud")
    @Expose
    private Double destLongitud;
    @SerializedName("placeId_destino")
    @Expose
    private String placeIdDestino;
    @SerializedName("rango_destino")
    @Expose
    private Integer rangoDestino;
    @SerializedName("hora_llegada")
    @Expose
    private String horaLlegada;
    @SerializedName("numero_pos_destino")
    @Expose
    private String numeroPosDestino;

    public String getIdNumEmpleado() {
        return idNumEmpleado;
    }

    public void setIdNumEmpleado(String idNumEmpleado) {
        this.idNumEmpleado = idNumEmpleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getOrigLatitud() {
        return origLatitud;
    }

    public void setOrigLatitud(Double origLatitud) {
        this.origLatitud = origLatitud;
    }

    public Double getOrigLongitud() {
        return origLongitud;
    }

    public void setOrigLongitud(Double origLongitud) {
        this.origLongitud = origLongitud;
    }

    public String getPlaceIdOrigen() {
        return placeIdOrigen;
    }

    public void setPlaceIdOrigen(String placeIdOrigen) {
        this.placeIdOrigen = placeIdOrigen;
    }

    public String getNumeroPosOrigen() {
        return numeroPosOrigen;
    }

    public void setNumeroPosOrigen(String numeroPosOrigen) {
        this.numeroPosOrigen = numeroPosOrigen;
    }

    public Integer getRangoOrigen() {
        return rangoOrigen;
    }

    public void setRangoOrigen(Integer rangoOrigen) {
        this.rangoOrigen = rangoOrigen;
    }

    public Double getDestLatitud() {
        return destLatitud;
    }

    public void setDestLatitud(Double destLatitud) {
        this.destLatitud = destLatitud;
    }

    public Double getDestLongitud() {
        return destLongitud;
    }

    public void setDestLongitud(Double destLongitud) {
        this.destLongitud = destLongitud;
    }

    public String getPlaceIdDestino() {
        return placeIdDestino;
    }

    public void setPlaceIdDestino(String placeIdDestino) {
        this.placeIdDestino = placeIdDestino;
    }

    public Integer getRangoDestino() {
        return rangoDestino;
    }

    public void setRangoDestino(Integer rangoDestino) {
        this.rangoDestino = rangoDestino;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getNumeroPosDestino() {
        return numeroPosDestino;
    }

    public void setNumeroPosDestino(String numeroPosDestino) {
        this.numeroPosDestino = numeroPosDestino;
    }

}