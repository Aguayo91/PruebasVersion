package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.server.http.HttpStatus;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.UnidadNegocioPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadEmpresaEmpleadoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 14/11/17.
 */

public class UnidadNegocioPresenterImpl extends BasePresenterImpl implements UnidadNegocioPresenter {

    public static final String TAG = UnidadNegocioPresenterImpl.class.getSimpleName();

    @Override
    public void consultaUnidadNegocio(Context context) {
        sendShowProgressEvent();
        SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
        ServerRequest request = new ServerRequest();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        getControllerAPI().consultaUnidadNegocio(request).enqueue(new Callback<UnidadEmpresaEmpleadoResponse>() {
            @Override
            public void onResponse(Call<UnidadEmpresaEmpleadoResponse> call, Response<UnidadEmpresaEmpleadoResponse> response) {
                try {
                    if (response.code() == HttpStatus.HTTP_OK) {
                        Log.v(TAG, response.body().toString());
                        if (response.body() != null) {
                            if (!response.body().getError()) {
                                ApplicationBase.getIntance().getManager().add(Constants.CLAVE_UNIDAD, response.body().getUnidadNegocioResponse().getClave());
                                ViewEvent eventP = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                eventP.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                                eventP.getModel().put(ViewEvent.ENTRY, response.body().getUnidadNegocioResponse());
                                notifyData(eventP);
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                                notifyData(eventErr);
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                        notifyData(eventErr);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                    notifyData(eventErr);
                }
                sendHideProgressEvent();
            }

            @Override
            public void onFailure(Call<UnidadEmpresaEmpleadoResponse> call, Throwable t) {
                Log.v(TAG, t.toString());
                sendHideProgressEvent();
                ViewEvent eventErr = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.RESOURCE_ID, R.id.ivUnidadNegocio);
                notifyData(eventErr);
            }
        });
    }

    private void sendShowProgressEvent() {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
    }

    private void sendHideProgressEvent() {
        ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
        notifyData(eventHide);
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
