package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia;
import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 08/09/2017.
 */

public interface AsistenciaPresenter extends BasePresenter {
    void getAsistenciasAsync(String numeroEmpleado);
}
