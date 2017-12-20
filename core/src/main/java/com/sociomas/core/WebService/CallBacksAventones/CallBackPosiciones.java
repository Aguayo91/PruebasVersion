package com.sociomas.core.WebService.CallBacksAventones;

import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;

/**
 * Created by oemy9 on 04/09/2017.
 */

public interface CallBackPosiciones extends CallBackBase {
    void OnSuccess(ArrayList<LugarTrabajo> listPosicionesValidas);
}
