package com.sociomas.aventones.UI.Activities.Inicio;

import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.CallBacksAventones.CallBackVehiculo;
import com.sociomas.core.WebService.Model.Request.Alta.ListaAutoRequest;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 05/10/2017.
 */

public class AventonPresenterImpl extends BasePresenterImpl implements AventonPresenter, CallBackVehiculo {

    private AventonView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(AventonView)view;
    }

    @Override
    public void obtenerAutosListadoAsync() {
        ApplicationAventon.getIntance().getControllerAventon().obtenerListadoAutoAsync(new ServerRequest(),this);
    }

    @Override
    public void onSucces(List<Vehiculo> listVehiculo) {
            view.setListVehiculo(listVehiculo);
    }

    @Override
    public void OnError(Throwable error) {
        onShowAlert(error.getMessage());
    }

    public interface  AventonView extends BaseView{
        void setListVehiculo(List<Vehiculo>list);
    }

}
