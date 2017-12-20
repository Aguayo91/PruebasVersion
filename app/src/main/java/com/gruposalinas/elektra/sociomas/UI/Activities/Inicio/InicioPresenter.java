package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 04/10/2017.
 */

public interface InicioPresenter extends BasePresenter {
    void sincronizarTokenFireBase();
    void checkIfUsuarioPilotoAventon();
    void obtenerConfiguracionAsync();
    void obtenerRangosMonitoreoEmpleado();
    void initTracking();

    void consultarNotificaciones();
}
