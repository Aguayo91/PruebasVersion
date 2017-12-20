package com.sociomas.aventones.UI.Activities.Disponibles;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;

/**
 * Created by oemy9 on 13/10/2017.
 */

public interface AventonDisponiblePresenter extends BasePresenter {
    void obtenerListadoAventones();
    void solicitarAventon(Aventones selectedAventon);
}
