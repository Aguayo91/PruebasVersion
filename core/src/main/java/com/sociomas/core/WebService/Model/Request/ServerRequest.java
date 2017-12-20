package com.sociomas.core.WebService.Model.Request;
/**
 * Created by oemy9 on 30/03/2017.
 */
@SuppressWarnings("unused")
public class ServerRequest {
    private String token;
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    private String id_num_empleado;
    public String getIdNumEmpleado() {
        return this.id_num_empleado;
    }
    public void setIdNumEmpleado(String id_num_empleado) {
        this.id_num_empleado = id_num_empleado;
    }
    private String id_dispositivo;
    public String getId_dispositivo() {
        return id_dispositivo;
    }
    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }
    private String tipo;
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    private int id_csc_justificacion;
    public int getIdCscJustificacion() {
        return id_csc_justificacion;
    }
    public void setIdCscJustificacion(int id_csc_justificacion) {
        this.id_csc_justificacion = id_csc_justificacion;
    }
}
