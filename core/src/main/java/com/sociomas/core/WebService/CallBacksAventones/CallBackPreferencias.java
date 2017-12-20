package com.sociomas.core.WebService.CallBacksAventones;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CaractEmpleado;
import com.sociomas.core.WebService.Model.Response.PrefenciaAventon.CatPreferenciasEmpleado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jromeromar on 28/09/2017.
 */

public interface CallBackPreferencias extends CallBackBase {
    void onSuccess(CaractEmpleado caractEmpleado, ArrayList<CatPreferenciasEmpleado> listPreferencias);
}
