package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Justificar;

import android.os.Bundle;

import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

/**
 * Created by jr441 on 26/11/2017.
 */

public interface JustificarIncidenciasPresenter {
    void onAutorizarRechazarIncidenciaPlantilla(boolean autorizar, String comentario);
    void justificarIncidencia(String comentario);
    void onJefeSelected(String nombre, String numeroEmpleado);
    void obtenerInfoIncidencia();
}
