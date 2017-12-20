package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;

/**
 * Created by GiioToledo on 14/11/17.
 */

public interface UnidadNegocioSeleccionPresenter extends BasePresenter {
    void loadUnidadesNegocio(Context context);
    void guardarUnidadNegocioEmpleado(Context context, UnidadNegocioResponse unr);
    void sendHideProgressEvent();
}
