package com.sociomas.core.WebService.Model.Request.Horario;


import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Horario.Horario;

import java.util.ArrayList;

/**
 * Created by oemy9 on 16/06/2017.
 */

public class HorarioRequest extends ServerRequest {

    private ArrayList<Horario> horario;

    public ArrayList<Horario> getHorario() { return this.horario; }

    public void setHorario(ArrayList<Horario> horario) { this.horario = horario; }

}
