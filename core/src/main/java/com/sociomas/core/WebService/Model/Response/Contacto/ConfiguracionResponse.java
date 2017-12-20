package com.sociomas.core.WebService.Model.Response.Contacto;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

/**
 * Created by oemy9 on 10/05/2017.
 */

public class ConfiguracionResponse extends ServerResponse {

    private String FECHA;

    public String getFECHA() { return this.FECHA; }

    public void setFECHA(String FECHA) { this.FECHA = FECHA; }

    private String NUM_JEFE;

    public String getNUMJEFE() { return this.NUM_JEFE; }

    public void setNUMJEFE(String NUM_JEFE) { this.NUM_JEFE = NUM_JEFE; }

    private String URL;

    public String getURL() { return this.URL; }

    public void setURL(String URL) { this.URL = URL; }

    private String VERSION;

    public String getVERSION() { return this.VERSION; }

    public void setVERSION(String VERSION) { this.VERSION = VERSION; }

    private int id_rol;

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}
