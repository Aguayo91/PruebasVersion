package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import android.content.Context;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.MensajeBienvenidaPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;

import java.util.Calendar;

/**
 * Created by GiioToledo on 13/11/17.
 */

public class MensajeBienvenidaPresenterImpl extends BasePresenterImpl implements MensajeBienvenidaPresenter {
    public static final String TAG = MensajeBienvenidaPresenterImpl.class.getSimpleName();
    @Override
    public void seleccionSaludo(Context context) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        String msgB = "";
        if (hora > 6 && hora < 12) {
            msgB = "¡Buenos días!";
        } else if (hora >= 12 && hora < 18) {
            msgB = "¡Buenas tardes!";
        } else if (hora >= 18 && hora < 23) {
            msgB = "¡Buenas noches!";
        } else {
            msgB = "¡A poco estas trabajando en este momento!";
        }
        Empleado empleado= Utils.getCurrentEmpleado(context);
        msgB=msgB.concat("\n").concat(empleado.getName());
        ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        event.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvTitleMensaje);
        event.getModel().put(ViewEvent.MESSAGE, msgB);
        notifyData(event);
    }
}
