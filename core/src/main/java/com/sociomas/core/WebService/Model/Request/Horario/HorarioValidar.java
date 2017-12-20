package com.sociomas.core.WebService.Model.Request.Horario;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 20/06/2017.
 */

public class HorarioValidar extends ServerRequest {

    private String id_num_empleado_logeado;

    public String getIdNumEmpleadoLogeado() { return this.id_num_empleado_logeado; }

    public void setIdNumEmpleadoLogeado(String id_num_empleado_logeado) { this.id_num_empleado_logeado = id_num_empleado_logeado; }

    private int diasArray;

    public int getDiasArray() { return this.diasArray; }

    public void setDiasArray(int diasArray) { this.diasArray = diasArray; }

    private String horaEntradaArray;

    public String getHoraEntradaArray() { return this.horaEntradaArray; }

    public void setHoraEntradaArray(String horaEntradaArray) { this.horaEntradaArray = horaEntradaArray; }

    private boolean validar;

    public boolean getValidar() { return this.validar; }

    public void setValidar(boolean validar) { this.validar = validar; }

    private String horaSalidaArray;

    public String getHoraSalidaArray() { return this.horaSalidaArray; }

    public void setHoraSalidaArray(String horaSalidaArray) { this.horaSalidaArray = horaSalidaArray; }

    private String comentario;

    public String getComentario() { return this.comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }
}
