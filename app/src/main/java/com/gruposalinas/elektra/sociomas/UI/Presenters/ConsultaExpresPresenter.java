package com.gruposalinas.elektra.sociomas.UI.Presenters;


import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public interface ConsultaExpresPresenter extends BasePresenter {
    void callQueryExpress(String dataAccess);

    void loadNumberAccount();

    void presentImageMsg(int resource, int option);
}
