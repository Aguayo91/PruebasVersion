package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2;

import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by jmarquezu on 22/11/2017.
 */

public interface AsistenciaPresenterV2 extends BasePresenter {
    void seleccionaUnSaludo(Context context);
    boolean hasPlantilla();
}
