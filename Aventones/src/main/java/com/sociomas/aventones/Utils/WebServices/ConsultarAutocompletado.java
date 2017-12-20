package com.sociomas.aventones.Utils.WebServices;

import android.content.Context;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.Listeners.OnPredictionListener;
import com.sociomas.core.WebService.CallBacksAventones.CallBackAutocomplete;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPosiciones;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAPIGoogle;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.ListLugaresTrabajoRequest;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.AutoComplete.PredictionSections;
import com.sociomas.core.WebService.Model.Response.AutoComplete.ResponsePlace;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class ConsultarAutocompletado {

    private Context context;
    private ControllerAPIGoogle controllerAPIGoogle;
    private ControllerAPI controllerAPI;

    public ConsultarAutocompletado(Context context) {
        this.context = context;
        this.controllerAPI=new ControllerAPI(this.context);
        this.controllerAPIGoogle=new ControllerAPIGoogle(this.context);
    }

    public void getAutocompletado(final String query, final OnPredictionListener predictionListener){
        controllerAPIGoogle.getAutoComplete(query, new CallBackAutocomplete() {
            @Override
            public void OnSuccess(final ResponsePlace responsePlace) {
                    /*LISTADO DE SUGERENCIAS DE GOOGLE*/
                final ArrayList<PredictionSections> listPredictions=new ArrayList<PredictionSections>();
                PredictionSections section=new PredictionSections();
                section.setHeaderTitle(context.getString(R.string.sugerencias_google));
                section.setListPrediction(responsePlace.getPredictions());
                listPredictions.add(section);
                predictionListener.onPredictionResponse(listPredictions);
                //CONSULTA A LA API DE SOCIO MÁS CON EL LISTADO DE POSICIONES VALIDAS*/
                controllerAPI.consultarPosicionesAutoCompleted(new ListLugaresTrabajoRequest(query), new CallBackPosiciones() {
                    @Override
                    public void OnSuccess(ArrayList<LugarTrabajo> listPosicionesValidas) {
                            //EL RESULTADO NO ES NULLO
                        if(listPosicionesValidas!=null) {
                                //CREAMOS UN LISTADO DE PREDICICONES DE ACUERDO AL RESULTADO
                            ArrayList<Prediction>listPredictionsValidas=new ArrayList<Prediction>();
                            for (LugarTrabajo posicion : listPosicionesValidas) {
                                Prediction p=new Prediction();
                                p.setDescription(Utils.toUppperCaseFirst(posicion.getVaNombrePos()));
                                p.setLatitude(posicion.getDecLatitud());
                                p.setLongitude(posicion.getDecLongitud());
                                p.setIsPlace(false);
                                p.setIdPosicionValida(posicion.getVaNumeroPos());
                                listPredictionsValidas.add(p);
                            }
                            PredictionSections sectionsValida=new PredictionSections();
                            sectionsValida.setHeaderTitle(context.getString(R.string.posiciones_validas));
                            sectionsValida.setListPrediction(listPredictionsValidas);
                            listPredictions.add(sectionsValida);
                            predictionListener.onPredictionResponse(listPredictions);
                        }
                    }
                    @Override
                    public void OnError(Throwable error) {
                        predictionListener.onPredictionResponse(listPredictions);
                    }
                });
                predictionListener.onPredictionResponse(listPredictions);
            }
            @Override
            public void OnError(Throwable error) {
                predictionListener.OnError(error);
            }
        });
    }
}
