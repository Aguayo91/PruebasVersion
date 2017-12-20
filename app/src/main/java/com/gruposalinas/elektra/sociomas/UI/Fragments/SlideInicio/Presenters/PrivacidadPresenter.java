package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;

/**
 * Created by oemy9 on 16/11/2017.
 */

public interface PrivacidadPresenter  extends BasePresenter{
    void obtenerAvisos(EnumTiposAviso tipoAviso);
    void aceptarAvisos(boolean acepto);
}
