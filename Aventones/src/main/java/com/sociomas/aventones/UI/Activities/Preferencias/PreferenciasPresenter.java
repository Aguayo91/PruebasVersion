package com.sociomas.aventones.UI.Activities.Preferencias;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 06/10/2017.
 */

public interface PreferenciasPresenter extends BasePresenter
{
    void obtenerPreferencias();
    void insertarEditarPreferencias(boolean editar,String observaciones,String tiempoTolerancia, int idRelCaracteristicaUsuario,ArrayList<CatPreferenciasEmpleado> caractEmpleado);
}
