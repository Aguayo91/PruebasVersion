package com.sociomas.aventones.UI.Activities.SolicitarAventon;

import android.content.Intent;

import com.sociomas.aventones.R;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;
import com.sociomas.core.WebService.Model.Response.Aventones.PreferenciasSolicitud;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 12/10/2017.
 */

public class SolicitarAventonPresenterImpl  extends BasePresenterImpl implements SolicitarAventonPresenter, CallBackBaseResponse {

    private Aventones aventon;
    private SolicitarAventonView view;
    private SolicitarAventonInteractor interactor;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(SolicitarAventonView)view;
        this.interactor=new SolicitarAventonInteractor();
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)) {
            aventon = (Aventones)intent.getSerializableExtra(ViewEvent.ENTRY);
        }
        ViewEvent event=new ViewEvent(ViewEventType.REFRESH_VIEW);
        event.getModel().put(ViewEvent.MESSAGE,aventon.getNombre_completo());
        notifyData(event);
    }

    @Override
    public void solicitarAventon() {
        interactor.solicitarAventonAsync(aventon,this);
    }

    @Override
    public void cargarOpcionesProhibido() {
        ArrayList<PreferenciasSolicitud> items= new ArrayList<>();
        items.add(new PreferenciasSolicitud(R.drawable.ic_cerveza,"",""));
        items.add(new PreferenciasSolicitud(R.drawable.ic_armas,"",""));
        view.setListPreferenciasProhibidas(items);
    }

    @Override
    public void OnError(Throwable error) {

    }

    @Override
    public void onSuccess(ServerResponse response) {

    }

    public interface SolicitarAventonView extends BaseView{
        void setListPreferenciasProhibidas(ArrayList<PreferenciasSolicitud>list);
    }
}
