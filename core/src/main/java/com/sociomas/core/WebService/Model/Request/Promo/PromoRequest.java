package com.sociomas.core.WebService.Model.Request.Promo;

import com.google.gson.annotations.SerializedName;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class PromoRequest extends ServerRequest {
    @SerializedName("lista_parametros")
    Parametros listParametros=null;


    public Parametros getListParametros() {
        return listParametros;
    }

    public void setListParametros(Parametros listParametros) {
        this.listParametros = listParametros;
    }

    public PromoRequest(Parametros listParametros) {
        this.listParametros = listParametros;
    }

    public PromoRequest(Integer idCategoria){
        listParametros=new  Parametros(idCategoria);
    }

    public PromoRequest(){

    }
}
