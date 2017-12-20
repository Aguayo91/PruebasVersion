package com.sociomas.aventones.UI.Activities.Publicar;

import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import java.util.ArrayList;

/**
 * Created by oemy9 on 03/10/2017.
 */

public interface PublicaPresenter  extends BasePresenter {
        void sugerenciaPosiciones(Double latitud,
                                  Double longitud);

        void obtenerAutosListadoAsync();

        //SENCILLO
        boolean validarAventon(boolean isRedondo,boolean inicioGeocoding,
                               Prediction predictionInicio,
                               Prediction predictionDestino,
                               ArrayList<Dia>diaSeleccionadoRedondo,
                               ArrayList<Dia> diaSeleccionado,
                               Vehiculo selectedVehiculo);

        //SENCILLO
        void publicarAventon(boolean isRedondo,
                             Prediction predictionInico,
                             Prediction predictionDestino,
                             Prediction predictionInicioRedondo,
                             Prediction predictionDestinoRedondo,
                             ArrayList<Dia>diaSeleccionadoRedondo,
                             ArrayList<Dia>diasSeleccionados,
                             Vehiculo selectedVehiculo);


}
