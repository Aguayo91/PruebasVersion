package com.sociomas.aventones.UI.Activities.SolicitudAceptarAventon;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 13/10/2017.
 */

public interface SolicitudConductorPresenter extends BasePresenter {
    void mostarInformacion();
    void aceptarRechazarAventon(boolean aceptar);
}
