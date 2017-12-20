package com.sociomas.core.WebService.Model.Response.Contacto;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;

/**
 * Created by oemy9 on 11/05/2017.
 */

public class RootClave extends ServerResponse {

    private ArrayList<Clave> claves;

    public ArrayList<Clave> getClaves() { return this.claves; }

    public void setClaves(ArrayList<Clave> claves) { this.claves = claves; }
}
