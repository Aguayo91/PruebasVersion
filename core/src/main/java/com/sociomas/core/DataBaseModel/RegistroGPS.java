package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("unused")
@DatabaseTable(tableName = "gps_status")
public class RegistroGPS extends BaseBean {
	public static final String ID = "id";
	public static final String DB_NAME = "gps_status";
	public static final String LATITUD = "latitud";
	public static final String LONGITUD = "longitud";
	public static final String NUM_EMPELADO = "num_empleado";
	public static final String FECHA = "fecha";
	public static final String HORA = "hora";
	public static final String BATERIA = "bateria";
	public static final String SUCCESS = "success";
	public static final String JSONDATE = "jsondate";
	public static final String PROVIDER = "provider";
	public static final String ACCURACY = "accuracy";
	public static final String VELOCIDAD = "velocidad";
	public static final String ID_ACTIVIDAD = "actividad";
	public static final String EXCEPTION_MENSAJE = "exception_mensaje";

	@DatabaseField(id = true, columnName = ID)
	private String id;
	@DatabaseField(columnName = LATITUD)
	private double latitud;
	@DatabaseField(columnName = LONGITUD)
	private double longitud;
	@DatabaseField(columnName = NUM_EMPELADO)
	private String numEmpleado;
	@DatabaseField(columnName = FECHA)
	private String fecha;
	@DatabaseField(columnName = HORA)
	private String hora;
	@DatabaseField(columnName = BATERIA)
	private double bateria;
	@DatabaseField(columnName = SUCCESS)
	private boolean success;
	@DatabaseField(columnName = JSONDATE)
	private String jsonDate;
	@DatabaseField(columnName = PROVIDER)
	private int Provider;
	private String token;
	@DatabaseField(columnName = ACCURACY)
	private float accuracy;
	@DatabaseField(columnName = VELOCIDAD)
	private float velocidad;
	@DatabaseField(columnName = ID_ACTIVIDAD)
	private String id_actividad;
	@DatabaseField(columnName = EXCEPTION_MENSAJE)
	private String exceptionMensaje;


	public String getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(String id_actividad) {
		this.id_actividad = id_actividad;
	}

	//Constructor vacio
	public RegistroGPS() {

	}

	//Para mostrar datos en la lista de registros
	public RegistroGPS(double latitud, double longitud, String fecha, String hora, String id) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.fecha = fecha;
		this.hora = hora;
		this.id = id;
	}

	//Para guardar en webservice
	public RegistroGPS(double latitud, double longitud, String numEmpleado, String fecha, String hora, double bateria, String jsonDate, String id, int provider, float accuracy) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.numEmpleado = numEmpleado;
		this.fecha = fecha;
		this.hora = hora;
		this.bateria = bateria;
		this.jsonDate = jsonDate;
		this.id = id;
		this.Provider = provider;
		this.accuracy = accuracy;

	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
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

	public double getBateria() {
		return bateria;
	}

	public void setBateria(double bateria) {
		this.bateria = bateria;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJsonDate() {
		return jsonDate;
	}

	public void setJsonDate(String jsonDate) {
		this.jsonDate = jsonDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getProvider() {
		return Provider;
	}

	/*public void setProvider(int provider) {
		Provider = provider;
	}
	*/

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}


	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}

	public String getExceptionMensaje() {
		return exceptionMensaje;
	}

	public void setExceptionMensaje(String exceptionMensaje) {
		this.exceptionMensaje = exceptionMensaje;
	}


	/*
	public ContentValues toContentValues(){

		ContentValues contentValues=new ContentValues();
		contentValues.put(this.LATITUD,latitud);
		contentValues.put(this.LONGITUD,longitud);
		contentValues.put(this.LONGITUD,numEmpleado);
		contentValues.put(this.FECHA,fecha);
		contentValues.put(this.HORA,hora);
		contentValues.put(this.BATERIA,bateria);
		contentValues.put(this.JSONDATE,jsonDate);
		contentValues.put(this.ID,this.id);
		contentValues.put(PROVIDER,getProvider());
		contentValues.put(this.ACCURACY,this.accuracy);
		contentValues.put(ACTIVIDAD, this.actividad);
		return contentValues;
	}*/
}