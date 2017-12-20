package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaHoy;

import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasPresenter;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 01/12/2017.
 */

public interface MisAsistenciasHoyPresenter extends BasePresenter {
    void obtenerAsistenciasHoy();
    boolean  isPlantilla();
    Plantilla getSelectedEmpleado();
}
