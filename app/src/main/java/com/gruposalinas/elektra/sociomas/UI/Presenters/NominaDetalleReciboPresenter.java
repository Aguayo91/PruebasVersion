package com.gruposalinas.elektra.sociomas.UI.Presenters;

import android.content.Context;
import android.os.Bundle;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 18/10/2017.
 */

public interface NominaDetalleReciboPresenter extends BasePresenter {
    void presentData(ReciboNominaDetalleResponse arguments);
    void liberarRecibo(Bundle bundle);
    void getFullPaymentSheets(ReciboNomina reciboNomina);

    void getFullPaymentSheetsGeografia(Context context, ReciboNomina reciboNomina);
}
