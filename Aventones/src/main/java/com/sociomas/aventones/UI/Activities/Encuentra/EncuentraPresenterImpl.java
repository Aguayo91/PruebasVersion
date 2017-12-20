package com.sociomas.aventones.UI.Activities.Encuentra;
import com.google.gson.Gson;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.Model.Request.Consulta.ConsultarAventonRequest;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaAventonesResponse;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jromeromar on 11/10/2017.
 */

public class EncuentraPresenterImpl extends BasePresenterImpl implements  EncuentraPresenter {

    private EncuentraView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(EncuentraView)view;
    }

    @Override
    public void sugerenciaPosiciones(Double latitud, Double longitud) {

    }

    @Override
    public void buscarAventon(Prediction predictionInicio, Prediction predictionDestino, Integer rangoBusqueda) {
            if(predictionInicio==null){
                onShowAlert(R.string.ingresa_origen);

            }
            else if(predictionDestino==null){
                onShowAlert(R.string.ingresa_destino);
            }

            else{
                ConsultarAventonRequest request=new ConsultarAventonRequest();
                request.setRangoDestino(rangoBusqueda);
                request.setRangoOrigen(rangoBusqueda);
                request.setHoraLlegada(predictionDestino.getHoraSalidaLlegada());
                if (!predictionInicio.isPlace()) {
                    //es posición valida
                    if (predictionInicio.isPosicionValida()) {
                        request.setNumeroPosOrigen(predictionInicio.getIdPosicionValida());
                    }
                    //geocoding
                    else {
                        request.setOrigLongitud(predictionInicio.getLongitude());
                        request.setOrigLatitud (predictionInicio.getLatitude());
                    }
                } else {
                    request.setPlaceIdOrigen(predictionInicio.getPlaceId());
                }

                if (!predictionDestino.isPlace()) {
                    //es posición valida
                    if (predictionDestino.isPosicionValida()) {
                        request.setNumeroPosDestino(predictionDestino.getIdPosicionValida());
                    }
                    //geocoding
                    else {
                        request.setDestLongitud(predictionDestino.getLongitude());
                        request.setDestLatitud (predictionDestino.getLatitude());
                    }
                } else {
                    request.setPlaceIdDestino(predictionDestino.getPlaceId());
                }
                onShowProgress();


                ApplicationAventon.getIntance().getControllerAventon().consultarAventon(request).enqueue(new Callback<ConsultaAventonesResponse>() {
                    @Override
                    public void onResponse(Call<ConsultaAventonesResponse> call, Response<ConsultaAventonesResponse> response) {
                        if(response.isSuccessful()){
                            if(!response.body().isError()){
                                if (response.body().getListAventones() != null && (!response.body().getListAventones().isEmpty())) {
                                    view.setListAventon(response.body());
                                }
                                else{
                                    view.mostrarCaraTriste();
                                }
                            }
                            else{
                                onShowAlert(response.body().getMensaje());
                            }
                            onHideProgress();
                        }
                    }

                    @Override
                    public void onFailure(Call<ConsultaAventonesResponse> call, Throwable t) {
                        onShowAlert(R.string.Error_Conexion);
                    }
                });

            }
    }

    public  interface  EncuentraView extends BaseView{
        void setListAventon(ConsultaAventonesResponse response);
        void mostrarPosicionValida(Prediction prediction);
        void mostrarCaraTriste();
    }



}



