package com.gruposalinas.elektra.sociomas.UI.Presenters;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;

import android.os.Bundle;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 07/09/17.
 */

public interface ListaRecibosNominaPresenter extends BasePresenter {

    void getFullPaymentSheets(Bundle bundle);

    void callServiceNomina(String autenticador);

    void setRecibo(ReciboNomina recibo);

    void removeRecibo(ReciboNomina recibo);

    void setData(Bundle arguments);

    void reloadRecibos(String numberAccount);

    void presentImageMsg(int resource, int option);

    int getMapRecibosSelected();

    int getRecibosInMap();
}
