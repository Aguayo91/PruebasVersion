package com.sociomas.aventones.UI.Activities.Publicar;

import com.google.android.gms.maps.model.LatLng;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.CallBacksAventones.CallBackVehiculo;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Request.Publicar.PublicarRequest;
import com.sociomas.core.WebService.Model.Request.Publicar.RecurrenteIda;
import com.sociomas.core.WebService.Model.Request.Publicar.RecurrenteVuelta;
import com.sociomas.core.WebService.Model.Request.Publicar.TipoAventon;
import com.sociomas.core.WebService.Model.Request.Publicar.Trayecto;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.PublicaResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by oemy9 on 03/10/2017.
 */

public class PublicarPrensenterImpl extends BasePresenterImpl implements  PublicaPresenter,
        CallBackVehiculo,
        PublicaInteractor.InteractorPublicaListener {

    private Prediction predictionInicio, predictionDestino;
    private Prediction predictionInicioRedondo,predictionDestinoRedondo;
    private PublicarRequest publicarRequest;
    private Trayecto trayecto;
    private PublicaInteractor interactor;
    private PublicarView view;
    private ArrayList<Dia> listDias,listDiasRedondo;
    private Vehiculo selectedVehiculo;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view = (PublicarView) view;
        this.interactor = new PublicaInteractor();
    }


    @Override
    public boolean validarAventon(boolean isRedondo, boolean inicioGeocoding,
                                  Prediction predictionInicio,
                                  Prediction predictionDestino,
                                  ArrayList<Dia> listDiasRedondo,
                                  ArrayList<Dia> listDias,
                                  Vehiculo selectedVehiculo)
    {

        boolean res = false;
        if (!inicioGeocoding && predictionInicio == null) {
            onShowAlert(R.string.ingresa_origen);
        } else if (!inicioGeocoding && (predictionInicio == null)) {
            onShowAlert(R.string.ingresa_origen);
        } else if (predictionDestino == null) {
            onShowAlert(R.string.ingresa_destino);
        } else if (listDias == null || (listDias.isEmpty())) {
            onShowAlert(R.string.ingresa_un_dia);
        } else if (predictionInicio.getHoraSalidaLlegada() == null || (predictionInicio.getHoraSalidaLlegada().isEmpty())) {
            onShowAlert(R.string.elige_hora_salida);

        } else if (predictionDestino.getHoraSalidaLlegada() == null || (predictionDestino.getHoraSalidaLlegada().isEmpty())) {
            onShowAlert(R.string.elige_hore_llegada);

        } else if (selectedVehiculo == null) {
            onShowAlert(R.string.selecciona_vehiculo);
        }
        else if(predictionInicio.getCalendar().after(predictionDestino.getCalendar())){
            onShowAlert(R.string.selecciona_hora_anterior);
        }
        else if((predictionDestino.getCalendar().getTimeInMillis()-predictionInicio.getCalendar().getTimeInMillis())
                <TimeUnit.MINUTES.toMillis(30)){
            onShowAlert(R.string.min_diferencia);
        }else {
            if (!isRedondo) {
                this.listDias = listDias;
                res = true;
            } else {
                if (listDiasRedondo == null || (listDiasRedondo.isEmpty())) {
                    onShowAlert(R.string.ingresa_dia_redondo);
                } else {
                    this.listDias = listDias;
                    this.listDiasRedondo = listDiasRedondo;
                    res = true;
                }
            }
        }
        return res;
    }

    @Override
    public void publicarAventon(boolean isRedondo, Prediction predictionInicio,
                                Prediction predictionDestino, Prediction predictionInicioRedondo,
                                Prediction predictionDestinoRedondo, ArrayList<Dia>diaSeleccionadoRedondo,
                                ArrayList<Dia>diasSeleccionados, Vehiculo selectedVehiculo)
     {
        view.showDialogoRuta();
        this.predictionInicio = predictionInicio;
        this.predictionDestino = predictionDestino;
        this.predictionInicioRedondo=predictionInicioRedondo;
        this.predictionDestinoRedondo=predictionDestinoRedondo;
        view.updateTextoRuta("Calculando ruta...");
        this.publicarRequest = new PublicarRequest();
        publicarRequest.setTipoAventon(new TipoAventon(Integer.valueOf(selectedVehiculo.getIdAutomovil()), isRedondo));
        this.trayecto = new Trayecto();
        //Revisamos si es place id, posición valida o viene de geocoding para el origen
        if (!this.predictionInicio.isPlace()) {
            if (this.predictionInicio.isPosicionValida()) {
                trayecto.setNumeroPosOrigenIda(this.predictionInicio.getIdPosicionValida());
            } else {
                trayecto.setLongitudOrigenIda(this.predictionInicio.getLongitude());
                trayecto.setLatitudOrigenIda(this.predictionInicio.getLatitude());
            }
        }
        //es un placeid
        else {
            trayecto.setPlaceIdOrigenIda(this.predictionInicio.getPlaceId());
        }

        //lo mismo para el destino
        if (!predictionDestino.isPlace()) {
            //es posición valida
            if (predictionDestino.isPosicionValida()) {
                trayecto.setNumeroPosDestinoIda(this.predictionDestino.getIdPosicionValida());
            }
            //geocoding
            else {
                trayecto.setLatitudDestinoIda(this.predictionDestino.getLongitude());
                trayecto.setLongitudDestinoIda(this.predictionDestino.getLatitude());
            }
        } else {
            trayecto.setPlaceIdDestinoIda(this.predictionDestino.getPlaceId());
        }
         trayecto.setHoraSalidaIda(this.predictionInicio.getHoraSalidaLlegada());
         trayecto.setHoraLlegadaIda(this.predictionDestino.getHoraSalidaLlegada());
         publicarRequest.setRecurrenteIda(getRecurrentesDiasIda(this.listDias));
         trayecto.setAsientosDisponiblesIda(selectedVehiculo.getAsientosDisponiblesIda());

        //SI NO ES REDONDO SE PUBLICA EL AVENTÓN
        if(!isRedondo) {
            publicarRequest.setTrayecto(trayecto);
           interactor.publicarAventonAsync(publicarRequest, this);
        }
        //ES UN VIAJEREDONDO
        else{

            if (!this.predictionInicioRedondo.isPlace()) {
                if (this.predictionInicioRedondo.isPosicionValida()) {
                    trayecto.setNumeroPosOrigenVuelta(this.predictionInicioRedondo.getIdPosicionValida());
                } else {
                    trayecto.setLongitudOrigenVuelta(this.predictionInicioRedondo.getLongitude());
                    trayecto.setLatitudOrigenVuelta(this.predictionInicioRedondo.getLatitude());
                }
            }
            //es un placeid
            else {
                trayecto.setPlaceIdOrigenVuelta(this.predictionInicioRedondo.getPlaceId());
            }

            //lo mismo para el destino
            if (!this.predictionDestinoRedondo.isPlace()) {
                //es posición valida
                if (this.predictionDestinoRedondo.isPosicionValida()) {
                    trayecto.setNumeroPosDestinoVuelta(this.predictionDestinoRedondo.getIdPosicionValida());
                }
                //geocoding
                else {
                    trayecto.setLatitudDestinoVuelta(this.predictionDestinoRedondo.getLongitude());
                    trayecto.setLongitudDestinoVuelta(this.predictionDestinoRedondo.getLatitude());
                }
            } else {
                trayecto.setPlaceIdDestinoVuelta(this.predictionDestinoRedondo.getPlaceId());
            }

            trayecto.setHoraSalidaVuelta(this.predictionInicioRedondo.getHoraSalidaLlegada());
            trayecto.setHoraLlegadaVuelta(this.predictionDestinoRedondo.getHoraSalidaLlegada());
            trayecto.setAsientosDisponiblesVuelta(selectedVehiculo.getAsientosDisponiblesVuelta());
            publicarRequest.setRecurrenteVuelta(getRecurrenteDiasVuelta(this.listDiasRedondo));
            publicarRequest.setRecurrenteIda(getRecurrentesDiasIda(this.listDias));
            publicarRequest.setTrayecto(trayecto);
            interactor.publicarAventonAsync(publicarRequest, this);

        }


    }



    public List<RecurrenteIda> getRecurrentesDiasIda(ArrayList<Dia>listDias){
        ArrayList<RecurrenteIda>listIda=new ArrayList<>();
        for(Dia item: listDias){
            listIda.add(new RecurrenteIda(item.getId()));
        }
        return listIda;
    }
    public List<RecurrenteVuelta>getRecurrenteDiasVuelta(ArrayList<Dia>listDias){
        ArrayList<RecurrenteVuelta>listIda=new ArrayList<>();
        for(Dia item: listDias){
            listIda.add(new RecurrenteVuelta(item.getId()));
        }
        return listIda;
    }

    @Override
    public void sugerenciaPosiciones(Double latitud, Double longitud) {
        onShowProgress();
        LatLng latLng=new LatLng(latitud,longitud);
        interactor.sugerenciaPosicionesAsync(latLng,this);
    }

    @Override
    public void obtenerAutosListadoAsync() {
        onShowProgress();
        ApplicationAventon.getIntance().getControllerAventon().obtenerListadoAutoAsync(new ServerRequest(),this);
    }

    @Override
    public void onSucces(List<Vehiculo> listVehiculo) {
        onHideProgress();
        view.setListVehiculo(listVehiculo);
    }

    @Override
    public void onAventonGenerated(PublicaResponse response) {
        if(response!=null){
            if(response.getListCoordenadasIda()!=null && (!response.getListCoordenadasIda().isEmpty())){
                view.onAventonGenerated(response);
                view.hideDialogoRuta();
            }
            else{
                if(response.getMensaje()!=null){
                    view.hideDialogoRuta();
                    onShowAlert(response.getMensaje());
                }
            }
        }
        onHideProgress();
    }


    @Override
    public void listadoSugerenciasAsync(ArrayList<LugarTrabajo> listPosiciones) {
        if(listPosiciones!=null && (!listPosiciones.isEmpty())) {
            LugarTrabajo p=listPosiciones.get(0);
            Prediction prediction = new Prediction();
            prediction.setIsPlace(false);
            prediction.setLatitude(p.getDecLatitud());
            prediction.setLongitude(p.getDecLongitud());
            prediction.setDescription(p.getVaNombrePos());
            prediction.setIdPosicionValida(p.getVaNumeroPos());
            view.mostrarPosicionValida(prediction);
        }
        onHideProgress();
    }

    @Override
    public void OnError(Throwable error) {
        onHideProgress();
    }

    public  interface  PublicarView extends BaseView{
            void setListVehiculo(List<Vehiculo>list);
            void showDialogoRuta();
            void hideDialogoRuta();
            void updateTextoRuta(String status);
            void mostrarPosicionValida(Prediction prediction);
            void onAventonGenerated(PublicaResponse response);
    }
}
