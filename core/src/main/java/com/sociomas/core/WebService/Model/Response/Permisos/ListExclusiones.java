package com.sociomas.core.WebService.Model.Response.Permisos;

/**
 * Created by oemy9 on 08/06/2017.
 */

@SuppressWarnings("unused")
public class ListExclusiones {

    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private String busqueda;

    public String getBusqueda() { return this.busqueda; }

    public void setBusqueda(String busqueda) { this.busqueda = busqueda; }

    private String comentario;

    public String getComentario() { return this.comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    private String descripcion_estatus_exclusion;

    public String getDescripcionEstatusExclusion() { return this.descripcion_estatus_exclusion; }

    public void setDescripcionEstatusExclusion(String descripcion_estatus_exclusion) { this.descripcion_estatus_exclusion = descripcion_estatus_exclusion; }

    private String descripcion_exclusion;

    public String getDescripcionExclusion() { return this.descripcion_exclusion; }

    public void setDescripcionExclusion(String descripcion_exclusion) { this.descripcion_exclusion = descripcion_exclusion; }

    private String dt_fecha_hora_final;

    public String getDtFechaHoraFinal() { return this.dt_fecha_hora_final; }

    public void setDtFechaHoraFinal(String dt_fecha_hora_final) { this.dt_fecha_hora_final = dt_fecha_hora_final; }

    private String dt_fecha_hora_inicial;

    public String getDtFechaHoraInicial() { return this.dt_fecha_hora_inicial; }

    public void setDtFechaHoraInicial(String dt_fecha_hora_inicial) { this.dt_fecha_hora_inicial = dt_fecha_hora_inicial; }

    private String fecha_hora_final;

    public String getFechaHoraFinal() { return this.fecha_hora_final; }

    public void setFechaHoraFinal(String fecha_hora_final) { this.fecha_hora_final = fecha_hora_final; }

    private String fecha_hora_inicial;

    public String getFechaHoraInicial() { return this.fecha_hora_inicial; }

    public void setFechaHoraInicial(String fecha_hora_inicial) { this.fecha_hora_inicial = fecha_hora_inicial; }

    private int id_estatus;

    public int getIdEstatus() { return this.id_estatus; }

    public void setIdEstatus(int id_estatus) { this.id_estatus = id_estatus; }

    private int id_exclusion;

    public int getIdExclusion() { return this.id_exclusion; }

    public void setIdExclusion(int id_exclusion) { this.id_exclusion = id_exclusion; }

    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private String motivo;

    public String getMotivo() { return this.motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }

    private int tipo_exclusion;

    public int getTipoExclusion() { return this.tipo_exclusion; }

    public void setTipoExclusion(int tipo_exclusion) { this.tipo_exclusion = tipo_exclusion; }

    private String va_nombre_completo;

    public String getVaNombreCompleto() { return this.va_nombre_completo; }

    public void setVaNombreCompleto(String va_nombre_completo) { this.va_nombre_completo = va_nombre_completo; }

    private int estatus;

    public int getEstatusRequest() {
        return estatus;
    }

    public void setEstatusRequest(int estatus) {
        this.estatus = estatus;
    }

    private int permisos;

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }

    private int retardos;

    public int getRetardos() {
        return retardos;
    }

    public void setRetardos(int retardos) {
        this.retardos = retardos;
    }

    private int salidas_antes;

    public int getSalidas_antes() {
        return salidas_antes;
    }

    public void setSalidas_antes(int salidas_antes) {
        this.salidas_antes = salidas_antes;
    }

    private int faltas;

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
}
