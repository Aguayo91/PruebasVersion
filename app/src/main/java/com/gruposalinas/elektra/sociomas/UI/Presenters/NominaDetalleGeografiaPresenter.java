package com.gruposalinas.elektra.sociomas.UI.Presenters;

import android.content.Context;
import android.os.Bundle;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;

/**
 * Created by GiioToledo on 23/11/17.
 */

public interface NominaDetalleGeografiaPresenter extends BasePresenter {

    void getFullPaymentSheetsGeografia(final Context context, ReciboNomina reciboNomina);

    void liberarRecibo(Bundle bundle);
}
