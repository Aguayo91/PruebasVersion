package com.sociomas.core.WebService.CallBacks;


import com.sociomas.core.WebService.Model.Response.Incidencia.CatalogoResponse;

/**
 * Created by oemy9 on 05/06/2017.
 */

public interface CallBackCatalogo  extends CallBackBase{
    void OnSuccess(CatalogoResponse response);
}
