package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
/**
 * Created by oemy9 on 27/11/2017.
 */
public interface JustificacionSelectionPresenter extends BasePresenter {
    void obtenerJustificaciones(String numeroEmpleado);
    boolean isPlantilla();
}
