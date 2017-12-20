package com.sociomas.core.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@SuppressWarnings("unused")
@DatabaseTable(tableName = "empleados")
public class Empleado extends BaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String DB_NAME  = "empleados";
	public static final String ID 		= "id";
    public static final String NOMBRE 	= "nombre";
    public static final String PUESTO 	= "puesto";
    public static final String EMPRESA 	= "empresa";
    public static final String TELEFONO = "telefono";
    public static final String EMAIL 	= "email";
    public static final String IMEI     = "imei";
	public static final String IS_USUARIO_AVENTON="is_usuario_aventon";
	public static final String ID_ROL_AVENTON="id_rol_aventon";


	@DatabaseField(columnName = ID)
    private String idEmployee;
    @DatabaseField(columnName = NOMBRE)
    private String name;
    @DatabaseField(columnName = PUESTO)
    private int position;    
    @DatabaseField(columnName = EMPRESA)
    private String enterprise;
    @DatabaseField(columnName = TELEFONO)
    private String phoneNumber;
    @DatabaseField(columnName = IMEI)
    private String imei;
    @DatabaseField(columnName = EMAIL)
    private String email;
	@DatabaseField(columnName = IS_USUARIO_AVENTON)
	private boolean isUsuarioAventon;
	@DatabaseField(columnName = ID_ROL_AVENTON)
	private int idRolAventon;

	String foto;
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getEmail() {
		return email;
	}
	public boolean isUsuarioAventon() {return isUsuarioAventon;}
	public void setUsuarioAventon(boolean usuarioAventon) {isUsuarioAventon = usuarioAventon;}
	public int getIdRolAventon() {return idRolAventon;}
	public void setIdRolAventon(int idRolAventon) {this.idRolAventon = idRolAventon;}
}
