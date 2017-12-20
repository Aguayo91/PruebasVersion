package com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar;
import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 07/09/2017.
 */

public interface VerificarSmsPresenter extends BasePresenter {
    void validarCodigo(String primerSegmento, String segundoSegmento, String telefono);
    void reenviarCodigo(String numeroTelefono);
    void requestLlamadaTelefonica(String numeroTelefono);

}
