package com.sociomas.core.WebService.Model.Response.Horario;


import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oemy9 on 15/06/2017.
 */

public class ResponseHorario  extends ServerResponse implements Serializable {
    private ArrayList<Horario> horario_plantilla;

    public ArrayList<Horario> getHorarioPlantilla() { return this.horario_plantilla; }

    public void setHorarioPlantilla(ArrayList<Horario> horario_plantilla) { this.horario_plantilla = horario_plantilla; }

    private ArrayList<Horario> horario;

    public ArrayList<Horario> getHorario() { return this.horario; }

    public void setHorario(ArrayList<Horario> horario) { this.horario = horario; }

}
