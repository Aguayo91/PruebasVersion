package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.TerminosLegalesPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Privacidad.ConsultaLegalesResponse;
import com.sociomas.core.WebService.Model.Response.Privacidad.DatoLegalRespose;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 08/12/17.
 */

public class TerminosLegalesPresenterImpl extends BasePresenterImpl implements TerminosLegalesPresenter {

    @Override
    public void consultarTerminos(final EnumTiposAviso tiposAviso) {
        onShowProgress();
        getControllerAPI().consultaLegales(new ServerRequest()).enqueue(new Callback<ConsultaLegalesResponse>() {
            @Override
            public void onResponse(Call<ConsultaLegalesResponse> call, Response<ConsultaLegalesResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        List<DatoLegalRespose> listDatoLegal = response.body().getDatoLegal();
                        Observable.fromIterable(listDatoLegal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .filter(new Predicate<DatoLegalRespose>() {
                                    @Override
                                    public boolean test(DatoLegalRespose datoLegalRespose) throws Exception {
                                        return datoLegalRespose.getId_tipo_aviso() == tiposAviso.getValue();
                                    }
                                }).map(new Function<DatoLegalRespose, DatoLegalRespose>() {
                            @Override
                            public DatoLegalRespose apply(DatoLegalRespose datoLegalRespose) throws Exception {
                                return datoLegalRespose;
                            }
                        }).subscribe(new Consumer<DatoLegalRespose>() {
                            @Override
                            public void accept(DatoLegalRespose datoLegalRespose) throws Exception {
                                ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                event.getModel().put(ViewEvent.RESOURCE_ID, R.id.webViewPrivacidad);
                                event.getModel().put(ViewEvent.ENTRY, datoLegalRespose);
                                notifyData(event);
                            }
                        });
                    } else {
                        ViewEvent event = new ViewEvent(ViewEventType.ERROR_EVENT_TYPE);
                        event.getModel().put(ViewEvent.MESSAGE, response.body().getMensajeError());
                        notifyData(event);
                    }
                } else {
                    onShowToast("Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                }
                onHideProgress();
            }

            @Override
            public void onFailure(Call<ConsultaLegalesResponse> call, Throwable t) {
                onShowToast("Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                onHideProgress();
            }
        });
    }

    public ControllerAPI getControllerAPI() {
        return ApplicationBase.getIntance().getControllerAPI();
    }
}
