package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by oemy9 on 05/04/2017.
 */

public class Actividad extends BaseBean {
    public static final String TIPO="tipo";
    public static final String ID="_id";
    public static final String JSONDATE     = "jsondate";
    public static final String FECHA 		= "fecha";
    public static final String HORA 		= "hora";
    public static final String NUM_EMPELADO = "num_empleado";
    public static final String ID_GPS="id_gps";
    @DatabaseField(columnName = ID, unique = true, generatedId = true)
    private int id;
    @DatabaseField(columnName = NUM_EMPELADO)
    private String numEmpleado;
    @DatabaseField(columnName = FECHA)
    private String fecha;
    @DatabaseField(columnName = HORA)
    private String hora;
    @DatabaseField(columnName = JSONDATE)
    private String jsonDate;
    @DatabaseField(columnName = TIPO)
    private String tipo;
    @DatabaseField(columnName =ID_GPS)
    private String idGps;
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumEmpleado() {
        return numEmpleado;
    }
    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public String getJsonDate() {
        return jsonDate;
    }
    public void setJsonDate(String jsonDate) {
        this.jsonDate = jsonDate;
    }
    public String getIdGps() {
        return idGps;
    }
    public void setIdGps(String idGps) {
        this.idGps = idGps;
    }
}
