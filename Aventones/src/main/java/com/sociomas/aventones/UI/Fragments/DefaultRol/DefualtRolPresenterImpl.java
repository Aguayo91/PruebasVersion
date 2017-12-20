package com.sociomas.aventones.UI.Fragments.DefaultRol;

import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.WebService.CallBacksAventones.CallBackVehiculo;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Request.ServerRequest;

import java.util.List;

/**
 * Created by oemy9 on 05/10/2017.
 */

public class DefualtRolPresenterImpl extends BasePresenterImpl implements DefualtRolPresenter, CallBackVehiculo {

    private DefaultRolView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(DefaultRolView) view;
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

    public interface  DefaultRolView extends BaseView{
        void setListVehiculo(List<Vehiculo> list);
    }

}
