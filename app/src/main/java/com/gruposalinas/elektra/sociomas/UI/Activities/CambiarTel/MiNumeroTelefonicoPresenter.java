package com.gruposalinas.elektra.sociomas.UI.Activities.CambiarTel;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by jmarquezu on 07/12/2017.
 */

public interface MiNumeroTelefonicoPresenter extends BasePresenter {
    void validarDatos (String numero);
    void consultarNumeroTel();
}
