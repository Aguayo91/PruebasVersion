package com.sociomas.aventones.UI.Activities.Publicar;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDetailPlace;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDirections;
import com.sociomas.core.WebService.Model.Request.PosicionCercana.PosicionRequest;
import com.sociomas.core.WebService.Model.Request.Publicar.PublicarRequest;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.DetailPlace.DetailResponse;
import com.sociomas.core.WebService.Model.Response.DetailPlace.Location;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.PublicaResponse;
import com.sociomas.core.WebService.Model.Response.Sugerencia.SugerenciaResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 04/10/2017.
 */

public class PublicaInteractor {
    private Prediction predictionInicio, predictionDestino;
    private boolean generadoInicio, generadoDestino;


    public interface InteractorPublicaListener extends CallBackBase {
        void listadoSugerenciasAsync(ArrayList<LugarTrabajo>listPosiciones);
        void onAventonGenerated(PublicaResponse response);
    }

    private InteractorPublicaListener interactorPublicaListener;

    public void setInteractorPublicaListener(InteractorPublicaListener interactorPublicaListener) {
        this.interactorPublicaListener = interactorPublicaListener;
    }

    public void sugerenciaPosicionesAsync(LatLng latLng, final InteractorPublicaListener listener){
         ApplicationAventon.getIntance().getControllerAventon().consultarPosicionesValida(new PosicionRequest(latLng.latitude,latLng.longitude,200)).enqueue(new Callback<SugerenciaResponse>() {
                @Override
                public void onResponse(Call<SugerenciaResponse> call, Response<SugerenciaResponse> response) {
                    if(response.isSuccessful()){
                        listener.listadoSugerenciasAsync(response.body().getListPosicionesEmpleado());

                    }
                }

                @Override
                public void onFailure(Call<SugerenciaResponse> call, Throwable t) {
                        listener.OnError(t);
                }
            });
    }

    public void getDetailPlace(final Prediction predictionInicio, final Prediction predictionDestino){
       if(this.interactorPublicaListener==null){
           return;
       }

        else if(generadoInicio && generadoDestino){
            return;
        }
        this.predictionInicio=predictionInicio;
        this.predictionDestino=predictionDestino;
        if(predictionInicio==null && !generadoInicio){
          //  interactorPublicaListener.onPlaceGenerateOrigendError(new Throwable("Ingresa un origen valido"));
            generadoInicio=true;
            getDetailPlace(predictionInicio,predictionDestino);
        }
        else if(predictionDestino==null && !generadoDestino ){
           // interactorPublicaListener.onPlaceGenerateDestinoError(new Throwable("Ingresa un destino valido"));
            generadoDestino=true;
            getDetailPlace(predictionInicio,predictionDestino);
        }

        /*INICIO ID DE PLACE ES NULLO*/
        if(this.predictionInicio.getPlaceId()==null  && this.predictionInicio.getLatitude()==null && !generadoInicio){
         //   interactorPublicaListener.onPlaceGenerateOrigendError(new Throwable("Ingresa un origen valido"));
            generadoInicio=true;
            getDetailPlace(predictionInicio,predictionDestino);
        }
        /*DESTINO ID ES NULLO*/
        else if(this.predictionDestino.getPlaceId()==null && this.predictionDestino.getLatitude()==null  &&!generadoDestino){
           // interactorPublicaListener.onPlaceGenerateDestinoError(new Throwable("Ingresa un destino valido"));
            generadoDestino=true;
            getDetailPlace(predictionInicio,predictionDestino);
        }
        /*CASO EN DONDE USAN EL GEOCODING O UBICACIÓN VALIDA DE CORPORTATIVO*/
        else if(predictionInicio.getPlaceId()==null && predictionInicio.getLatitude()!=null && !generadoInicio){
           // interactorPublicaListener.placeGeneratedOrigen(predictionInicio);
            generadoInicio=true;
            getDetailPlace(predictionInicio,predictionDestino);

        }
        else if(predictionDestino.getPlaceId()==null && predictionDestino.getLatitude()!=null && !generadoDestino){
           // interactorPublicaListener.placeGeneratedDestino(predictionDestino);
            generadoDestino=true;
            getDetailPlace(predictionInicio,predictionDestino);
        }


        /*EL PLACE ID NO ES NULLO, SE CONSULTA DETALLES D ELA UBICACIÓN*/
        else if(this.predictionInicio.getPlaceId()!=null && !generadoInicio) {
            ApplicationAventon.getIntance().getControllerGoogle().getDetailPlace(predictionInicio.getPlaceId(),
                    new CallBackDetailPlace() {
                @Override
                public void onSuccess(DetailResponse response) {
                   //     interactorPublicaListener.placeGeneratedOrigen(getInformacionDetalles(predictionInicio,response));
                        generadoInicio=true;
                        if(!generadoDestino) {
                            getDetailPlace(predictionInicio, predictionDestino);
                        }
                }

                @Override
                public void OnError(Throwable error) {
                        generadoInicio=true;
                        if(!generadoDestino) {
                            getDetailPlace(predictionInicio, predictionDestino);
                        }
                }
            });
        }
        else if(this.predictionDestino.getPlaceId()!=null && !generadoDestino){
            ApplicationAventon.getIntance().getControllerGoogle().getDetailPlace(predictionDestino.getPlaceId(), new CallBackDetailPlace() {
                @Override
                public void onSuccess(DetailResponse response) {
                   //     interactorPublicaListener.placeGeneratedDestino(getInformacionDetalles(predictionDestino,response));
                        generadoDestino=true;
                        if(!generadoInicio) {
                            getDetailPlace(predictionInicio, predictionDestino);
                        }

                }
                @Override
                public void OnError(Throwable error) {
                        generadoDestino=true;
                        if(!generadoInicio) {
                            getDetailPlace(predictionInicio, predictionDestino);
                        }
                }
            });
        }
    }

    public void getDirections(Prediction predictionInicio, Prediction predictionDestino, CallBackDirections callBackDirections){
        ApplicationAventon.getIntance().getControllerGoogle().getDirections(new LatLng(predictionInicio.getLatitude(), predictionInicio.getLongitude()),
                new LatLng(predictionDestino.getLatitude(), predictionDestino.getLongitude()), callBackDirections);
    }

    public void publicarAventonAsync(PublicarRequest request,final InteractorPublicaListener listener){
        ApplicationAventon.getIntance().getControllerAventon().publicarAventon(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try {
                            PublicaResponse pResponse=new Gson().fromJson(response.body().string(),PublicaResponse.class);
                            listener.onAventonGenerated(pResponse);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t!=null){

                }
            }
        });
    }

    /*EXTRAE INFORMACIÓN DETALLE*/
    private Prediction getInformacionDetalles(Prediction prediction,DetailResponse response){
        Location location=response.getResult().getGeometry().getLocation();
        prediction.setLatitude(location.getLat());
        prediction.setLongitude(location.getLng());
        Document doc=Jsoup.parse(response.getResult().getAdrAddress());
        Elements elements=doc.select("span");
        for(Element element: elements){
                if(element.className().equals("extended-address")){
                    prediction.setColonia(element.text());
                }
                else if(element.className().equals("postal-code")){
                    prediction.setCodigoPostal(element.text());
                }
                else if(element.className().equals("region")){
                    prediction.setEstadoRepublica(element.text());
                }
                else if(element.className().equals("country-name")){
                    prediction.setPais(element.text());
                }
        }
        return prediction;
    }

}
