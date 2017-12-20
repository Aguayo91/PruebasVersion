package com.sociomas.core.WebService.Services;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Nomina.PaymenSheetsRequest;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gtoledo on 27/09/2017.
 */

public interface APIServiceNomina {

    @POST(Constants.LiberacionNomina_Consulta)
    Call<PaymentSheetsResponse> getPendingPayments(@Body PaymenSheetsRequest petition);

    @POST(Constants.LiberacionNomina_Consulta)
    Call<PaymentSheetsResponse> getPaymentSheetsList(@Body PaymenSheetsRequest petition);

    @POST(Constants.LiberacionNomina_Consulta)
    Call<PaymentSheetsResponse> ejecutaLiberarNomina(@Body PaymenSheetsRequest petition);

    @POST(Constants.LiberacionNomina_Consulta)
    Call<PaymentSheetsResponse> getNumberAccount(@Body PaymenSheetsRequest request);

    @POST(Constants.LiberacionNomina_Consulta)
    Call<PaymentSheetsResponse> getConcentradoGeografia(@Body PaymenSheetsRequest request);
}

