package com.sociomas.core.WebService.Model.Response.Registro;

/**
 * Created by oemy9 on 12/07/2017.
 */

public class Empleado
{
    private String foto;

    public String getFoto() { return this.foto; }

    public void setFoto(String foto) { this.foto = foto; }

    private String id_dispositivo;

    public String getIdDispositivo() { return this.id_dispositivo; }

    public void setIdDispositivo(String id_dispositivo) { this.id_dispositivo = id_dispositivo; }

    private String id_empresa;

    public String getIdEmpresa() { return this.id_empresa; }

    public void setIdEmpresa(String id_empresa) { this.id_empresa = id_empresa; }

    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private int id_puesto;

    public int getIdPuesto() { return this.id_puesto; }

    public void setIdPuesto(int id_puesto) { this.id_puesto = id_puesto; }

    private String va_nombre_completo;

    public String getVaNombreCompleto() { return this.va_nombre_completo; }

    public void setVaNombreCompleto(String va_nombre_completo) { this.va_nombre_completo = va_nombre_completo; }

    private String va_numero_telefono;

    public String getVaNumeroTelefono() { return this.va_numero_telefono; }

    public void setVaNumeroTelefono(String va_numero_telefono) { this.va_numero_telefono = va_numero_telefono; }

    private int telefono_valido;

    public int getTelefono_valido() {
        return telefono_valido;
    }

    public void setTelefono_valido(int telefono_valido) {
        this.telefono_valido = telefono_valido;
    }
}
