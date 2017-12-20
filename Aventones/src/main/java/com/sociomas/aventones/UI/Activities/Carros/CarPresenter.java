package com.sociomas.aventones.UI.Activities.Carros;

import android.content.Intent;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;

/**
 * Created by oemy9 on 05/10/2017.
 */

public interface CarPresenter extends BasePresenter {
    void agregarEditarAuto(Vehiculo selectedVehiculo,boolean navegarPreferencias);
    void obtenerAutosListadoAsync();


}
