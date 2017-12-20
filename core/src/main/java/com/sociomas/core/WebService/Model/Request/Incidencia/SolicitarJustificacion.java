package com.sociomas.core.WebService.Model.Request.Incidencia;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

/**
 * Created by oemy9 on 06/06/2017.
 */

@SuppressWarnings("unused")
public class SolicitarJustificacion extends ServerRequest {

    private String tamanoArchivo;

    public String getTamanoArchivo() { return this.tamanoArchivo; }

    public void setTamanoArchivo(String tamanoArchivo) { this.tamanoArchivo = tamanoArchivo; }

    private String extension;

    public String getExtension() { return this.extension; }

    public void setExtension(String extension) { this.extension = extension; }

    private String nombreArchivo;

    public String getNombreArchivo() { return this.nombreArchivo; }

    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }


    private String archivoAdjunto;

    public String getArchivoAdjunto() { return this.archivoAdjunto; }

    public void setArchivoAdjunto(String archivoAdjunto) { this.archivoAdjunto = archivoAdjunto; }


    private String va_comentarios;

    public String getVaComentarios() { return this.va_comentarios; }

    public void setVaComentarios(String va_comentarios) { this.va_comentarios = va_comentarios; }

    private String id_num_empleado_justifica;

    public String getIdNumEmpleadoJustifica() { return this.id_num_empleado_justifica; }

    public void setIdNumEmpleadoJustifica(String id_num_empleado_justifica) { this.id_num_empleado_justifica = id_num_empleado_justifica; }

    private int id_csc_incid;

    public int getIdCscIncid() { return this.id_csc_incid; }

    public void setIdCscIncid(int id_csc_incid) { this.id_csc_incid = id_csc_incid; }

    private boolean bit_temp_fija;

    public boolean getBitTempFija() { return this.bit_temp_fija; }

    public void setBitTempFija(boolean bit_temp_fija) { this.bit_temp_fija = bit_temp_fija; }

    private int id_justificacion;

    public int getIdJustificacion() { return this.id_justificacion; }

    public void setIdJustificacion(int id_justificacion) { this.id_justificacion = id_justificacion; }

}
