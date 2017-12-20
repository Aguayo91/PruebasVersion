package com.sociomas.core.WebService.Model.Request.Contacto;


import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 12/05/2017.
 */

@SuppressWarnings("unused")
public class RootTelefonoContacto extends ServerRequest
{

    private ArrayList<TelefonoContacto> contactos;
    public ArrayList<TelefonoContacto> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<TelefonoContacto> contactos) {
        this.contactos = contactos;
    }
}
