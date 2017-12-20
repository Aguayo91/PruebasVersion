
package com.sociomas.core.WebService.Model.Response.AutoComplete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Prediction implements Serializable {

    public Prediction(String placeId) {
        this.placeId = placeId;
    }
    public Prediction(){

    }

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("matched_substrings")
    @Expose
    private List<MatchedSubstring> matchedSubstrings = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("reference")
    @Expose
    private String reference;

    private Double latitude = 0.0;
    private Double longitude= 0.0;
    private Boolean isPlace=true;

    @SerializedName("structured_formatting")
    @Expose
    private StructuredFormatting structuredFormatting;
    @SerializedName("terms")
    @Expose
    private List<Term> terms = null;
    @SerializedName("types")
    @Expose
    private List<String> types = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MatchedSubstring> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    public void setMatchedSubstrings(List<MatchedSubstring> matchedSubstrings) {
        this.matchedSubstrings = matchedSubstrings;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(StructuredFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isPlace() {
        return isPlace;
    }

    public void setIsPlace(Boolean place) {
        isPlace = place;
    }

    private String idPosicionValida="";

    public String getIdPosicionValida() {
        return idPosicionValida;
    }

    public boolean isPosicionValida(){
        return idPosicionValida!=null && (!idPosicionValida.isEmpty());
    }

    public void setIdPosicionValida(String idPosicionValida) {
        this.idPosicionValida = idPosicionValida;
    }

    private String codigoPostal;
    private String colonia;
    private String estadoRepublica;
    private String pais;
    private String horaSalidaLlegada;

    public String getHoraSalidaLlegada() {
        return horaSalidaLlegada;
    }

    public void setHoraSalidaLlegada(String horaSalidaLlegada) {
        this.horaSalidaLlegada = horaSalidaLlegada;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstadoRepublica() {
        return estadoRepublica;
    }

    public void setEstadoRepublica(String estadoRepublica) {
        this.estadoRepublica = estadoRepublica;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Calendar calendar;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
