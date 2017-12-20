package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 07/11/2017.
 */

public interface CargarGafetePresenter extends BasePresenter {
    void initCargaImagen();
    boolean hasImagenGafete();
    String getImagenGafete();
}
