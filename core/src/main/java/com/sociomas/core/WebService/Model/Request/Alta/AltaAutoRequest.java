package com.sociomas.core.WebService.Model.Request.Alta;

import java.io.Serializable;

/**
 * Created by jromeromar on 22/09/2017.
 */

public class AltaAutoRequest implements Serializable {
    private String id_num_empleado;

    public String getIdNumEmpleado() { return this.id_num_empleado; }

    public void setIdNumEmpleado(String id_num_empleado) { this.id_num_empleado = id_num_empleado; }

    private String token;

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }

    private String modelo;

    public String getModelo() { return this.modelo; }

    public void setModelo(String modelo) { this.modelo = modelo; }

    private String placas;

    public String getPlacas() { return this.placas; }

    public void setPlacas(String placas) { this.placas = placas; }

    private String codigo_color;

    public String getCodigoColor() { return this.codigo_color; }

    public void setCodigoColor(String codigo_color) { this.codigo_color = codigo_color; }

    private int numero_asientos;

    public int getNumeroAsientos() { return this.numero_asientos; }

    public void setNumeroAsientos(int numero_asientos) { this.numero_asientos = numero_asientos; }
}
