package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.WebService.Model.Response.Incidencia.EmpleadoIncidencia;
import com.sociomas.core.WebService.Model.Response.Incidencia.ResponseIncidencia;

import java.util.HashMap;

/**
 * Created by oemy9 on 15/05/2017.
 */

public interface CallBackIncidencia  extends CallBackBase{
    void OnSuccess(ResponseIncidencia incidenciaResponse, HashMap<String, EmpleadoIncidencia> hashMapEmpleados);
}
