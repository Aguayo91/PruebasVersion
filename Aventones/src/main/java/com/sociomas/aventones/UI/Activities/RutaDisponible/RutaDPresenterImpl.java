package com.sociomas.aventones.UI.Activities.RutaDisponible;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Disponibles.AventonDisponibleInteractor;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaRutaResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.CoordenadasRutaModelResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.LstCoordenada;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by oemy9 on 12/10/2017.
 */

public class RutaDPresenterImpl extends BasePresenterImpl implements RutaDPresenter, CallBackBaseResponse {

    private Aventones response;
    private RutaDView  view;
    private AventonDisponibleInteractor interactor;

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)){
            response=(Aventones) intent.getSerializableExtra(ViewEvent.ENTRY);
            view.setSelectedAventon(response);
        }
    }

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(RutaDView) view;
        this.interactor=new AventonDisponibleInteractor();
        this.view.showDialogoRuta();

    }

    @Override
    public void generarRuta() {
        CoordenadasRutaModelResponse request = new CoordenadasRutaModelResponse();
        request.setIdTrayectoAventon(response.getId_trayecto_aventon());
        ApplicationAventon.getIntance().getControllerAventon().consultarCoordenadasRuta(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String jsonString = response.body().string();
                        Gson gson = new Gson();
                        ConsultaRutaResponse aventonesResponse = gson.fromJson(jsonString, ConsultaRutaResponse.class);
                        if(aventonesResponse.getLstCoordenadas()!=null){
                            ArrayList<LatLng> points =new ArrayList<>();
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (LstCoordenada c:aventonesResponse.getLstCoordenadas()) {
                                LatLng position = new LatLng(c.getLatitude(), c.getLongitude());
                                points.add(position);
                                builder.include(position);
                            }
                            view.onRutaGenerada(new PolylineOptions().width(5).color(ContextCompat.getColor(ApplicationAventon.getAppContext(), R.color.colorGrisInfo))
                                    .visible(true).zIndex(30).addAll(points).geodesic(true),builder.build());
                        }
                        view.hideDialogoRuta();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    view.hideDialogoRuta();
            }
        });
    }

    @Override
    public void solicitarAventon(Aventones selectedAventon) {
        onShowProgress();
        interactor.solicitarAventonAsync(selectedAventon,this);
    }
    @Override
    public void onSuccess(ServerResponse response) {
        if(!response.getError()){
                view.navegarSuccess();
        }
        else{
            view.hideDialogoConfirmarAventon();
            onShowAlert(response.getMensaje());
        }
        onHideProgress();
    }

    @Override
    public void OnError(Throwable error) {
        onShowAlert(R.string.Error_Conexion);
        onHideProgress();
    }

    public interface RutaDView extends  BaseView{
        void onRutaGenerada(PolylineOptions lineOptionsRuta, LatLngBounds bounds);
        void setSelectedAventon(Aventones aventon);
        void showDialogoRuta();
        void hideDialogoConfirmarAventon();
        void hideDialogoRuta();
        void navegarSuccess();
    }

}
