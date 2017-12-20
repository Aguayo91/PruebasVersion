package com.sociomas.core.WebService.Model.Request.Contacto;

/**
 * Created by oemy9 on 12/05/2017.
 */

@SuppressWarnings("unused")
public class TelefonoContacto {
    private String telefono;
    private String id_codigo_internacional;

    public TelefonoContacto(String telefono,String id_codigo_internacional){
        this.telefono=telefono;
        this.id_codigo_internacional=id_codigo_internacional;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getId_codigo_internacional() {
        return id_codigo_internacional;
    }

    public void setId_codigo_internacional(String id_codigo_internacional) {
        this.id_codigo_internacional = id_codigo_internacional;
    }
}
