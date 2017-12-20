package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by GiioToledo on 13/11/17.
 */

public interface MensajeBienvenidaPresenter extends BasePresenter {
    void seleccionSaludo(Context context);
}
