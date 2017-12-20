package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.util.Log;

import com.facebook.stetho.server.http.HttpStatus;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Interactors.LugarTrabajoInteractor;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.LugarTrabajoPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Enums.EnumTipoLugarTrabajo;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.CallBacksAventones.CallBackPosiciones;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Request.SlideInicio.ModificarPrefLugarTrabajoRequest;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EditarTiendaItem;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarTiendaRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadEmpresaEmpleadoResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 17/11/2017.
 */

public class LugarTrabajoPresenterImpl extends BasePresenterImpl implements LugarTrabajoPresenter, CallBackPosiciones, Callback<ServerResponse> {

    private static final String TAG = LugarTrabajoPresenterImpl.class.getSimpleName();
    private LugarTrabajoView view;
    private ArrayList<LugarTrabajo> lugaresSeleccionados;
    private String lastQuery;
    private LugarTrabajoInteractor interactor;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view = (LugarTrabajoView) view;
        this.interactor = new LugarTrabajoInteractor();
    }

    @Override
    public void consultaUnidadNegocio() {
        onShowProgress();
        interactor.descargarImagenUnidadTrabajo(new Subscriber<UnidadEmpresaEmpleadoResponse>() {
            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(UnidadEmpresaEmpleadoResponse response) {
                Bitmap bmp = Utils.convertBase64ToBitmap(response.getUnidadNegocioResponse().getLogoBase64());
                view.showImageBitMap(bmp);
            }

            @Override
            public void onError(Throwable t) {
                onShowToast(t.getMessage());
            }

            @Override
            public void onComplete() {
                onHideProgress();
            }
        });

    }

    @Override
    public void buscarLugarTrabajo(String query) {
        if (!query.isEmpty()) {
            this.lastQuery = query;
            interactor.consultarPosicionesRequest(query, this);
        } else {
            view.onQueryEmpty();
        }
    }

    @Override
    public void setListLugaresSeleccionados(ArrayList<LugarTrabajo> listLugares) {
        if (listLugares != null) {
            this.lugaresSeleccionados = listLugares;
        }
    }

    public void enviarLugaresSeleccionadosAsync(boolean lugarVariable) {
        if (lugaresSeleccionados != null && (!lugaresSeleccionados.isEmpty())) {
            onShowProgress();
            interactor.enviarLugarTrabajo(lugaresSeleccionados, this);
        } else {
            if (lugarVariable) {
                if(getView().getActivityInstance()!=null) {
                    onShowToast(R.string.selecciona_un_lugar);
                }
            } else {
                view.navegarFragment();
            }
        }
    }

    @Override
    public void cambiarLugarTrabajo(Context context, EnumTipoLugarTrabajo lugarTrabajo) {
        onShowProgress();
        if(lugarTrabajo==EnumTipoLugarTrabajo.VARIABLE){
            view.clearLugaresTrabajo();
        }
        interactor.cambiarLugarTrabajo(lugarTrabajo.getValue(), new CallBackBaseResponse() {
            @Override
            public void onSuccess(ServerResponse response) {
                onHideProgress();
            }

            @Override
            public void OnError(Throwable error) {
                onShowToast(error.getMessage());
                onHideProgress();
            }
        });
    }


    @Override
    public void OnSuccess(ArrayList<LugarTrabajo> listLugarTrabajo) {
        if (listLugarTrabajo != null && (!listLugarTrabajo.isEmpty())) {
            view.setListLugarTrabajo(listLugarTrabajo);
        }
        onHideProgress();
    }

    @Override
    public void OnError(Throwable error) {
        onShowToast(error.getMessage());
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if (!response.body().getError()) {
            view.navegarFragment();
        } else {
            onShowToast(response.body().getMensajeError());
        }
        onHideProgress();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        onShowToast(R.string.Error_Conexion);
        onHideProgress();
    }

    public interface LugarTrabajoView extends BaseView {
        void onQueryEmpty();
        void clearLugaresTrabajo();
        void setListLugarTrabajo(ArrayList<LugarTrabajo> listLugarTrabajo);
        void showImageBitMap(Bitmap bmp);
        void navegarFragment();
    }

}