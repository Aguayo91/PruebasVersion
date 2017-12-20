package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;

/**
 * Created by GiioToledo on 08/12/17.
 */

public interface TerminosLegalesPresenter extends BasePresenter {
    void consultarTerminos(EnumTiposAviso tiposAviso);
}
