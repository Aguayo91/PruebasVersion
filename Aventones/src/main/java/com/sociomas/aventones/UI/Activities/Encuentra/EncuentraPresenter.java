package com.sociomas.aventones.UI.Activities.Encuentra;


import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;

/**
 * Created by jromeromar on 11/10/2017.
 */

public interface EncuentraPresenter extends BasePresenter {
    void sugerenciaPosiciones(Double latitud,
                              Double longitud);

    void buscarAventon(Prediction predictionInicio,
                       Prediction predictionDestino,
                       Integer rangoBusqueda);

}
