package com.sociomas.aventones.UI.Activities.RutaDisponible;

import com.sociomas.aventones.UI.Activities.Ruta.RutaPresenter;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;

/**
 * Created by oemy9 on 12/10/2017.
 */

public interface RutaDPresenter extends RutaPresenter {
    void solicitarAventon(Aventones selectedAventon);

}
