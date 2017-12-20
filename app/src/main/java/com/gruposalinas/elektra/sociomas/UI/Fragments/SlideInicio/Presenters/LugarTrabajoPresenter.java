package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.Utils.Enums.EnumTipoLugarTrabajo;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;

/**
 * Created by oemy9 on 17/11/2017.
 */

public interface LugarTrabajoPresenter extends BasePresenter {
    void buscarLugarTrabajo(String query);
    void setListLugaresSeleccionados(ArrayList<LugarTrabajo>listLugares);
    void enviarLugaresSeleccionadosAsync(boolean lugarVariable);
    void cambiarLugarTrabajo(Context context, EnumTipoLugarTrabajo lugarTrabajo);
    void consultaUnidadNegocio();
}
