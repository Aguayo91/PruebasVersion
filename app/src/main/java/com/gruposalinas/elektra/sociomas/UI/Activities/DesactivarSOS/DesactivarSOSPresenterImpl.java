package com.gruposalinas.elektra.sociomas.UI.Activities.DesactivarSOS;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;

/**
 * Created by oemy9 on 07/09/2017.
 */

public class DesactivarSOSPresenterImpl extends BasePresenterImpl implements DesactivarSosPresenter {
    private DesactivarView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(DesactivarView)getView();
    }

    @Override
    public void validarNumeroEmpleado(String numeroEmpleado) {
         if(numeroEmpleado.isEmpty()){
            view.onErrorNumeroEmpleado("Ingresa el número de empleado");
        }
        else if (!numeroEmpleado.equals(ApplicationBase.getIntance().getManager().getString(Constants.SP_ID))) {
            view.onErrorNumeroEmpleado("El número de empleado no coincide");
        }
        else{
            view.onSuccess();
        }
    }
    public interface DesactivarView extends BaseView
    {
        void onErrorNumeroEmpleado(String mensajeError);
        void onSuccess();
    }
}
