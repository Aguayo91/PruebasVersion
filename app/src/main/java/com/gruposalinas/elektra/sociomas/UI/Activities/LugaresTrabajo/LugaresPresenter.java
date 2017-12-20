package com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

/**
 * Created by oemy9 on 18/09/2017.
 */

public interface LugaresPresenter extends BasePresenter {
    void requestLugaresTrabajo(String numeroEmpleado);
    void requestLugaresTrabajo();
    void requestEmpleadosPlantilla();
    void onAutorizarRechazar(LugarTrabajo lugarTrabajo, boolean autorizar);
}
