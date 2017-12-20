package com.sociomas.core.WebService.Model.Request.Horario;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 18/06/2017.
 */

@SuppressWarnings("unused")
public class EditarHorarioRequest extends ServerRequest {

    public EditarHorarioRequest() {

    }

    public EditarHorarioRequest(String HoraEntrada, String HoraSalida, int dia_semana) {
        this.nv_hora_entrada = HoraEntrada;
        this.nv_hora_salida = HoraSalida;
        this.dia_semana = dia_semana;
        this.libre = 0;
        this.cancelar = 0;
        this.rechazar = 0;
        this.comentario = "";
    }

    public EditarHorarioRequest withHorarario(String nv_hora_entrada, String nv_hora_salida) {
        this.nv_hora_entrada = nv_hora_entrada;
        this.nv_hora_salida = nv_hora_salida;
        return this;
    }

    private String id_num_empleado_logeado;

    public String getIdNumEmpleadoLogeado() { return this.id_num_empleado_logeado; }

    public void setIdNumEmpleadoLogeado(String id_num_empleado_logeado) { this.id_num_empleado_logeado = id_num_empleado_logeado; }

    private String nv_hora_salida;

    public String getNvHoraSalida() { return this.nv_hora_salida; }

    public void setNvHoraSalida(String nv_hora_salida) { this.nv_hora_salida = nv_hora_salida; }

    private String nv_hora_entrada;

    public String getNvHoraEntrada() { return this.nv_hora_entrada; }

    public void setNvHoraEntrada(String nv_hora_entrada) { this.nv_hora_entrada = nv_hora_entrada; }

    private String edicion;

    public String getEdicion() { return this.edicion; }

    public void setEdicion(String edicion) { this.edicion = edicion; }

    private int dia_semana;

    public int getDiaSemana() { return this.dia_semana; }

    public void setDiaSemana(int dia_semana) { this.dia_semana = dia_semana; }

    private String comentario;

    public String getComentario() { return this.comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    private  int libre;
    private  int cancelar;
    private  int rechazar;

    public int getLibre() {
        return libre;
    }

    public void setLibre(int libre) {
        this.libre = libre;
    }

    public int getCancelar() {
        return cancelar;
    }

    public void setCancelar(int cancelar) {
        this.cancelar = cancelar;
    }

    public int getRechazar() {
        return rechazar;
    }

    public void setRechazar(int rechazar) {
        this.rechazar = rechazar;
    }
}
