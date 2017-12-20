package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.WebService.Model.Response.Permisos.CatalogoPermisoRes;

/**
 * Created by oemy9 on 09/06/2017.
 */

public interface CallBackCatalogoPermisos extends CallBackBase {
    void OnSuccess(CatalogoPermisoRes response);
}
