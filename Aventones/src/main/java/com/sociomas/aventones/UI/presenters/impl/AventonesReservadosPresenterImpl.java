package com.sociomas.aventones.UI.presenters.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.sociomas.aventones.UI.presenters.AventonesReservadosPresenter;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.WebService.Model.Request.SolicitarAventon.AventonesReservadosRequest;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventone;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.AventonesReservadosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 18/10/2017.
 */

public class AventonesReservadosPresenterImpl extends BasePresenterImpl implements AventonesReservadosPresenter {
    public static final String TAG = AventonesReservadosPresenterImpl.class.getSimpleName();
    private AventonesReservadosView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view = (AventonesReservadosView) view;
    }

    @Override
    public void getAventonesReservados() {
        ViewEvent eventProg = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProg);
        final AventonesReservadosRequest request = new AventonesReservadosRequest();

        ApplicationAventon.getIntance().getControllerAventon().aventonesReservados(request).enqueue(new Callback<AventonesReservadosResponse>() {
            @Override
            public void onResponse(Call<AventonesReservadosResponse> call, Response<AventonesReservadosResponse> response) {
                if(response.isSuccessful()) {

                    //showToast("El servidor respondio");
                    Gson gson = new Gson();
                    AventonesReservadosResponse aventonesReservadosResponse = response.body();
                    view.llenarLista(aventonesReservadosResponse.getAventones());

                }
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }

            @Override
            public void onFailure(Call<AventonesReservadosResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ViewEvent event = new ViewEvent(ViewEventType.SHOW_TOAST_MESSAGE);
                event.getModel().put(ViewEvent.MESSAGE, "Error de conexion");
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }
        });
    }

    public interface AventonesReservadosView extends BaseView {
        List<Aventones> llenarLista(List<Aventones> lista);
    }
}
