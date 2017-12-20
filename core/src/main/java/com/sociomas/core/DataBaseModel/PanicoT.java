package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 *  PETEICIÓN PÁNICO
 */
@SuppressWarnings("unused")
@DatabaseTable(tableName = "panico")
public class PanicoT extends BaseBean {
    public static final String ID           = "id";
    public static final String FECHA 		= "fecha";
    public static final String IS_OK="is_ok";
    public static final String HTTP_CODE="http_code";
    public static final String MENSAJE_ERROR="mensaje_error";


    @DatabaseField(columnName = ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = FECHA)
    private Date fecha;
    @DatabaseField(columnName = IS_OK)
    private boolean isOk;
    @DatabaseField(columnName = HTTP_CODE)
    private int httpCode;
    @DatabaseField(columnName = MENSAJE_ERROR, canBeNull = true)
    private String mensajeErrorPanico;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;

    }

    public boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        this.isOk = ok;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMensajeErrorPanico() {
        return mensajeErrorPanico;
    }
    public void setMensajeErrorPanico(String mensajeErrorPanico) {
        this.mensajeErrorPanico = mensajeErrorPanico;
    }
}
