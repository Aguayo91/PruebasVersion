package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones.Expandible;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;

/**
 * Created by oemy9 on 20/10/2017.
 */

public interface IncidenciaPresenter extends BasePresenter {
    void obtenerIncidencias();
    void rechazarAprobar(ListadoIncidencias selectedItem, boolean autorizar);
    void validarJustificacion(ListadoIncidencias selectedItem);
}
