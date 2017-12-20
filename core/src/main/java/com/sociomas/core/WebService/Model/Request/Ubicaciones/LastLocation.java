package com.sociomas.core.WebService.Model.Request.Ubicaciones;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 28/04/2017.
 */

@SuppressWarnings("unused")
public class LastLocation extends ServerRequest {

    private String empleadoSolicita;
    private String fechaInicio;
    private String fechaFin;
    private String horaInicio;
    private String horaFin;
    private boolean horarioLaboral;
    private boolean ultimaUbicacion;
    private boolean buscaTodos;
    private String numeroEmpleado;
    private int nivel;

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public boolean isBuscaTodos() {
        return buscaTodos;
    }

    public void setBuscaTodos(boolean buscaTodos) {
        this.buscaTodos = buscaTodos;
    }

    public String getEmpleadoSolicita() {
        return empleadoSolicita;
    }

    public void setEmpleadoSolicita(String empleadoSolicita) {
        SecurityItems securityItems=new SecurityItems(empleadoSolicita);
        this.empleadoSolicita = securityItems.getIdEmployEncrypt();
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public boolean isHorarioLaboral() {
        return horarioLaboral;
    }

    public void setHorarioLaboral(boolean horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isUltimaUbicacion() {
        return ultimaUbicacion;
    }

    public void setUltimaUbicacion(boolean ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    /*
    @Query("empleadoSolicita") String  numeroEmpleado,
    @Query("fechaInicio") String  fechaInicio,
    @Query("fechaFin") String  fechaFin,
    @Query("horaInicio") String horaInicio,
    @Query("horaFin") String horaFin,
    @Query("horarioLaboral") boolean horarioLaboral,
    @Query("ultimaUbicacion") boolean ultimaUbicacion*/
}
