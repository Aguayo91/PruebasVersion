package com.gruposalinas.elektra.sociomas.UI.Presenters;

import android.os.Bundle;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;


/**
 * Created by gtoledo on 18/09/2017.
 */

public interface NominaDetailPresenter extends BasePresenter {
    void presentData(ReciboNominaDetalleResponse arguments);
    void liberarRecibo(Bundle bundle);
    void getFullPaymentSheets(ReciboNomina reciboNomina);
}
