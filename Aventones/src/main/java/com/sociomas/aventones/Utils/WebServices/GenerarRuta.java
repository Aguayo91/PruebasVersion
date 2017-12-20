package com.sociomas.aventones.Utils.WebServices;

import android.content.Context;

import com.sociomas.core.WebService.Controllers.Aventon.ControllerAPIGoogle;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class GenerarRuta {

    private Context context;
    private Prediction predictionInicio, predictionDestino;
    private ControllerAPIGoogle controllerAPI;


    public GenerarRuta(Context context){
        this.context=context;
        this.controllerAPI=new ControllerAPIGoogle(this.context);
    }

    


}
