package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Alta.AltaQR;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oemy9 on 23/10/2017.
 */
@DatabaseTable(tableName = "QrPendiente")
public class QrPendientes implements Serializable {
    public static final String ID="ID";
    public static final String INFO_QR="INFO_QR";
    public static final String LAT="LAT";
    public static final String LNG="LNG";
    public static final String FECHA_HORA="FECHA_HORA";
    public static final String PENDIENTE="IS_PENDIENTE";
    public static final String ACCURACY="ACCURACY";

    @DatabaseField(columnName = ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = INFO_QR)
    private String infoQr;
    @DatabaseField(columnName = LAT)
    private double lat;
    @DatabaseField(columnName = LNG)
    private double lng;
    @DatabaseField(columnName =FECHA_HORA)
    private Date fechaHora;
    @DatabaseField(columnName = PENDIENTE)
    private boolean pendiente;
    @DatabaseField(columnName = ACCURACY)
    private double accuracy;


    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfoQr() {
        return infoQr;
    }

    public void setInfoQr(String infoQr) {
        this.infoQr = infoQr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    //TODO FINALIZAR QR
    public AltaQR toAlta(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_AVENTON);
        AltaQR altaQR=new AltaQR();
        altaQR.setDatosQR(this.getInfoQr());
        altaQR.setLatitud(this.getLat());
        altaQR.setLongitud(this.getLng());
        altaQR.setAccuracy(this.getAccuracy());
        altaQR.setFechaDispositivo(dateFormat.format(this.getFechaHora()));
        return altaQR;
    }
}
