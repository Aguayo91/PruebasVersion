
package com.sociomas.core.WebService.Model.Request.Publicar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trayecto {


    @SerializedName("placeId_origen_ida")
    @Expose
    private String placeIdOrigenIda;

    @SerializedName("placeId_destino_ida")
    @Expose
    private String placeIdDestinoIda;

    @SerializedName("placeId_origen_vuelta")
    @Expose
    private String placeIdOrigenVuelta;

    @SerializedName("placeId_destino_vuelta")
    @Expose
    private String placeIdDestinoVuelta;


    public String getPlaceIdDestinoVuelta() {
        return placeIdDestinoVuelta;
    }

    public void setPlaceIdDestinoVuelta(String placeIdDestinoVuelta) {
        this.placeIdDestinoVuelta = placeIdDestinoVuelta;
    }

    public String getPlaceIdOrigenVuelta() {
        return placeIdOrigenVuelta;
    }

    public void setPlaceIdOrigenVuelta(String placeIdOrigenVuelta) {
        this.placeIdOrigenVuelta = placeIdOrigenVuelta;
    }

    public String getPlaceIdDestinoIda() {
        return placeIdDestinoIda;
    }

    public void setPlaceIdDestinoIda(String placeIdDestinoIda) {
        this.placeIdDestinoIda = placeIdDestinoIda;
    }

    public String getPlaceIdOrigenIda() {
        return placeIdOrigenIda;
    }

    public void setPlaceIdOrigenIda(String placeIdOrigenIda) {
        this.placeIdOrigenIda = placeIdOrigenIda;
    }

    @SerializedName("latitud_origen_ida")
    @Expose
    private Double latitudOrigenIda;
    @SerializedName("longitud_origen_ida")
    @Expose
    private Double longitudOrigenIda;
    @SerializedName("numero_pos_origen_ida")
    @Expose
    private Object numeroPosOrigenIda;
    @SerializedName("hora_salida_ida")
    @Expose
    private String horaSalidaIda;
    @SerializedName("calle_numero_origen_ida")
    @Expose
    private String calleNumeroOrigenIda;
    @SerializedName("colonia_origen_ida")
    @Expose
    private String coloniaOrigenIda;
    @SerializedName("delegacion_origen_ida")
    @Expose
    private String delegacionOrigenIda;
    @SerializedName("estado_origen_ida")
    @Expose
    private String estadoOrigenIda;
    @SerializedName("pais_origen_ida")
    @Expose
    private String paisOrigenIda;
    @SerializedName("cp_origen_ida")
    @Expose
    private String cpOrigenIda;
    @SerializedName("latitud_destino_ida")
    @Expose
    private Double latitudDestinoIda;
    @SerializedName("longitud_destino_ida")
    @Expose
    private Double longitudDestinoIda;
    @SerializedName("numero_pos_destino_ida")
    @Expose
    private String numeroPosDestinoIda;
    @SerializedName("hora_llegada_ida")
    @Expose
    private String horaLlegadaIda;
    @SerializedName("asientos_disponibles_ida")
    @Expose
    private Integer asientosDisponiblesIda;
    @SerializedName("calle_numero_destino_ida")
    @Expose
    private String calleNumeroDestinoIda;
    @SerializedName("colonia_destino_ida")
    @Expose
    private String coloniaDestinoIda;
    @SerializedName("delegacion_destino_ida")
    @Expose
    private String delegacionDestinoIda;
    @SerializedName("estado_destino_ida")
    @Expose
    private String estadoDestinoIda;
    @SerializedName("pais_destino_ida")
    @Expose
    private String paisDestinoIda;
    @SerializedName("cp_destino_ida")
    @Expose
    private String cpDestinoIda;
    @SerializedName("latitud_origen_vuelta")
    @Expose
    private Double latitudOrigenVuelta;
    @SerializedName("longitud_origen_vuelta")
    @Expose
    private Double longitudOrigenVuelta;
    @SerializedName("numero_pos_origen_vuelta")
    @Expose
    private String numeroPosOrigenVuelta;
    @SerializedName("hora_salida_vuelta")
    @Expose
    private String horaSalidaVuelta;
    @SerializedName("calle_numero_origen_vuelta")
    @Expose
    private String calleNumeroOrigenVuelta;
    @SerializedName("colonia_origen_vuelta")
    @Expose
    private String coloniaOrigenVuelta;
    @SerializedName("delegacion_origen_vuelta")
    @Expose
    private String delegacionOrigenVuelta;
    @SerializedName("estado_origen_vuelta")
    @Expose
    private String estadoOrigenVuelta;
    @SerializedName("pais_origen_vuelta")
    @Expose
    private String paisOrigenVuelta;
    @SerializedName("cp_origen_vuelta")
    @Expose
    private String cpOrigenVuelta;
    @SerializedName("latitud_destino_vuelta")
    @Expose
    private Double latitudDestinoVuelta;
    @SerializedName("longitud_destino_vuelta")
    @Expose
    private Double longitudDestinoVuelta;
    @SerializedName("numero_pos_destino_vuelta")
    @Expose
    private Object numeroPosDestinoVuelta;
    @SerializedName("hora_llegada_vuelta")
    @Expose
    private String horaLlegadaVuelta;
    @SerializedName("asientos_disponibles_vuelta")
    @Expose
    private Integer asientosDisponiblesVuelta;
    @SerializedName("calle_numero_destino_vuelta")
    @Expose
    private String calleNumeroDestinoVuelta;
    @SerializedName("colonia_destino_vuelta")
    @Expose
    private String coloniaDestinoVuelta;
    @SerializedName("delegacion_destino_vuelta")
    @Expose
    private String delegacionDestinoVuelta;
    @SerializedName("estado_destino_vuelta")
    @Expose
    private String estadoDestinoVuelta;
    @SerializedName("pais_destino_vuelta")
    @Expose
    private String paisDestinoVuelta;
    @SerializedName("cp_destino_vuelta")
    @Expose
    private String cpDestinoVuelta;

    public Double getLatitudOrigenIda() {
        return latitudOrigenIda;
    }

    public void setLatitudOrigenIda(Double latitudOrigenIda) {
        this.latitudOrigenIda = latitudOrigenIda;
    }

    public Double getLongitudOrigenIda() {
        return longitudOrigenIda;
    }

    public void setLongitudOrigenIda(Double longitudOrigenIda) {
        this.longitudOrigenIda = longitudOrigenIda;
    }

    public Object getNumeroPosOrigenIda() {
        return numeroPosOrigenIda;
    }

    public void setNumeroPosOrigenIda(Object numeroPosOrigenIda) {
        this.numeroPosOrigenIda = numeroPosOrigenIda;
    }

    public String getHoraSalidaIda() {
        return horaSalidaIda;
    }

    public void setHoraSalidaIda(String horaSalidaIda) {
        this.horaSalidaIda = horaSalidaIda;
    }

    public String getCalleNumeroOrigenIda() {
        return calleNumeroOrigenIda;
    }

    public void setCalleNumeroOrigenIda(String calleNumeroOrigenIda) {
        this.calleNumeroOrigenIda = calleNumeroOrigenIda;
    }

    public String getColoniaOrigenIda() {
        return coloniaOrigenIda;
    }

    public void setColoniaOrigenIda(String coloniaOrigenIda) {
        this.coloniaOrigenIda = coloniaOrigenIda;
    }

    public String getDelegacionOrigenIda() {
        return delegacionOrigenIda;
    }

    public void setDelegacionOrigenIda(String delegacionOrigenIda) {
        this.delegacionOrigenIda = delegacionOrigenIda;
    }

    public String getEstadoOrigenIda() {
        return estadoOrigenIda;
    }

    public void setEstadoOrigenIda(String estadoOrigenIda) {
        this.estadoOrigenIda = estadoOrigenIda;
    }

    public String getPaisOrigenIda() {
        return paisOrigenIda;
    }

    public void setPaisOrigenIda(String paisOrigenIda) {
        this.paisOrigenIda = paisOrigenIda;
    }

    public String getCpOrigenIda() {
        return cpOrigenIda;
    }

    public void setCpOrigenIda(String cpOrigenIda) {
        this.cpOrigenIda = cpOrigenIda;
    }

    public Double getLatitudDestinoIda() {
        return latitudDestinoIda;
    }

    public void setLatitudDestinoIda(Double latitudDestinoIda) {
        this.latitudDestinoIda = latitudDestinoIda;
    }

    public Double getLongitudDestinoIda() {
        return longitudDestinoIda;
    }

    public void setLongitudDestinoIda(Double longitudDestinoIda) {
        this.longitudDestinoIda = longitudDestinoIda;
    }

    public String getNumeroPosDestinoIda() {
        return numeroPosDestinoIda;
    }

    public void setNumeroPosDestinoIda(String numeroPosDestinoIda) {
        this.numeroPosDestinoIda = numeroPosDestinoIda;
    }

    public String getHoraLlegadaIda() {
        return horaLlegadaIda;
    }

    public void setHoraLlegadaIda(String horaLlegadaIda) {
        this.horaLlegadaIda = horaLlegadaIda;
    }

    public Integer getAsientosDisponiblesIda() {
        return asientosDisponiblesIda;
    }

    public void setAsientosDisponiblesIda(Integer asientosDisponiblesIda) {
        this.asientosDisponiblesIda = asientosDisponiblesIda;
    }

    public String getCalleNumeroDestinoIda() {
        return calleNumeroDestinoIda;
    }

    public void setCalleNumeroDestinoIda(String calleNumeroDestinoIda) {
        this.calleNumeroDestinoIda = calleNumeroDestinoIda;
    }

    public String getColoniaDestinoIda() {
        return coloniaDestinoIda;
    }

    public void setColoniaDestinoIda(String coloniaDestinoIda) {
        this.coloniaDestinoIda = coloniaDestinoIda;
    }

    public String getDelegacionDestinoIda() {
        return delegacionDestinoIda;
    }

    public void setDelegacionDestinoIda(String delegacionDestinoIda) {
        this.delegacionDestinoIda = delegacionDestinoIda;
    }

    public String getEstadoDestinoIda() {
        return estadoDestinoIda;
    }

    public void setEstadoDestinoIda(String estadoDestinoIda) {
        this.estadoDestinoIda = estadoDestinoIda;
    }

    public String getPaisDestinoIda() {
        return paisDestinoIda;
    }

    public void setPaisDestinoIda(String paisDestinoIda) {
        this.paisDestinoIda = paisDestinoIda;
    }

    public String getCpDestinoIda() {
        return cpDestinoIda;
    }

    public void setCpDestinoIda(String cpDestinoIda) {
        this.cpDestinoIda = cpDestinoIda;
    }

    public Double getLatitudOrigenVuelta() {
        return latitudOrigenVuelta;
    }

    public void setLatitudOrigenVuelta(Double latitudOrigenVuelta) {
        this.latitudOrigenVuelta = latitudOrigenVuelta;
    }

    public Double getLongitudOrigenVuelta() {
        return longitudOrigenVuelta;
    }

    public void setLongitudOrigenVuelta(Double longitudOrigenVuelta) {
        this.longitudOrigenVuelta = longitudOrigenVuelta;
    }

    public String getNumeroPosOrigenVuelta() {
        return numeroPosOrigenVuelta;
    }

    public void setNumeroPosOrigenVuelta(String numeroPosOrigenVuelta) {
        this.numeroPosOrigenVuelta = numeroPosOrigenVuelta;
    }

    public String getHoraSalidaVuelta() {
        return horaSalidaVuelta;
    }

    public void setHoraSalidaVuelta(String horaSalidaVuelta) {
        this.horaSalidaVuelta = horaSalidaVuelta;
    }

    public String getCalleNumeroOrigenVuelta() {
        return calleNumeroOrigenVuelta;
    }

    public void setCalleNumeroOrigenVuelta(String calleNumeroOrigenVuelta) {
        this.calleNumeroOrigenVuelta = calleNumeroOrigenVuelta;
    }

    public String getColoniaOrigenVuelta() {
        return coloniaOrigenVuelta;
    }

    public void setColoniaOrigenVuelta(String coloniaOrigenVuelta) {
        this.coloniaOrigenVuelta = coloniaOrigenVuelta;
    }

    public String getDelegacionOrigenVuelta() {
        return delegacionOrigenVuelta;
    }

    public void setDelegacionOrigenVuelta(String delegacionOrigenVuelta) {
        this.delegacionOrigenVuelta = delegacionOrigenVuelta;
    }

    public String getEstadoOrigenVuelta() {
        return estadoOrigenVuelta;
    }

    public void setEstadoOrigenVuelta(String estadoOrigenVuelta) {
        this.estadoOrigenVuelta = estadoOrigenVuelta;
    }

    public String getPaisOrigenVuelta() {
        return paisOrigenVuelta;
    }

    public void setPaisOrigenVuelta(String paisOrigenVuelta) {
        this.paisOrigenVuelta = paisOrigenVuelta;
    }

    public String getCpOrigenVuelta() {
        return cpOrigenVuelta;
    }

    public void setCpOrigenVuelta(String cpOrigenVuelta) {
        this.cpOrigenVuelta = cpOrigenVuelta;
    }

    public Double getLatitudDestinoVuelta() {
        return latitudDestinoVuelta;
    }

    public void setLatitudDestinoVuelta(Double latitudDestinoVuelta) {
        this.latitudDestinoVuelta = latitudDestinoVuelta;
    }

    public Double getLongitudDestinoVuelta() {
        return longitudDestinoVuelta;
    }

    public void setLongitudDestinoVuelta(Double longitudDestinoVuelta) {
        this.longitudDestinoVuelta = longitudDestinoVuelta;
    }

    public Object getNumeroPosDestinoVuelta() {
        return numeroPosDestinoVuelta;
    }

    public void setNumeroPosDestinoVuelta(Object numeroPosDestinoVuelta) {
        this.numeroPosDestinoVuelta = numeroPosDestinoVuelta;
    }

    public String getHoraLlegadaVuelta() {
        return horaLlegadaVuelta;
    }

    public void setHoraLlegadaVuelta(String horaLlegadaVuelta) {
        this.horaLlegadaVuelta = horaLlegadaVuelta;
    }

    public Integer getAsientosDisponiblesVuelta() {
        return asientosDisponiblesVuelta;
    }

    public void setAsientosDisponiblesVuelta(Integer asientosDisponiblesVuelta) {
        this.asientosDisponiblesVuelta = asientosDisponiblesVuelta;
    }

    public String getCalleNumeroDestinoVuelta() {
        return calleNumeroDestinoVuelta;
    }

    public void setCalleNumeroDestinoVuelta(String calleNumeroDestinoVuelta) {
        this.calleNumeroDestinoVuelta = calleNumeroDestinoVuelta;
    }

    public String getColoniaDestinoVuelta() {
        return coloniaDestinoVuelta;
    }

    public void setColoniaDestinoVuelta(String coloniaDestinoVuelta) {
        this.coloniaDestinoVuelta = coloniaDestinoVuelta;
    }

    public String getDelegacionDestinoVuelta() {
        return delegacionDestinoVuelta;
    }

    public void setDelegacionDestinoVuelta(String delegacionDestinoVuelta) {
        this.delegacionDestinoVuelta = delegacionDestinoVuelta;
    }

    public String getEstadoDestinoVuelta() {
        return estadoDestinoVuelta;
    }

    public void setEstadoDestinoVuelta(String estadoDestinoVuelta) {
        this.estadoDestinoVuelta = estadoDestinoVuelta;
    }

    public String getPaisDestinoVuelta() {
        return paisDestinoVuelta;
    }

    public void setPaisDestinoVuelta(String paisDestinoVuelta) {
        this.paisDestinoVuelta = paisDestinoVuelta;
    }

    public String getCpDestinoVuelta() {
        return cpDestinoVuelta;
    }

    public void setCpDestinoVuelta(String cpDestinoVuelta) {
        this.cpDestinoVuelta = cpDestinoVuelta;
    }

}
