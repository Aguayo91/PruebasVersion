package com.gruposalinas.elektra.sociomas.UI.Activities.Login;
import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 12/09/2017.
 */

public interface LoginPresenter extends BasePresenter {
    void validarCredenciales(String noEmpleado, String llaveMaestra);
    void checkEmpleado(String noEmpleado);
}

