package com.sociomas.aventones.UI.Activities.MisPasajeros;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 18/10/2017.
 */

public interface PasajerosPresenter extends BasePresenter {
    void obtenerPasajeros();
    void aceptarRechazarAventon(boolean aceptar,int idRelAsientosReservados);
}
