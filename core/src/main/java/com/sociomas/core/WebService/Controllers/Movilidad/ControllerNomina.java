package com.sociomas.core.WebService.Controllers.Movilidad;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Controllers.MockInterceptor;
import com.sociomas.core.WebService.Model.Request.Nomina.PaymenSheetsRequest;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;
import com.sociomas.core.WebService.Services.APIServiceNomina;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Created by gtoledo on 26/09/2017.
 */

public class ControllerNomina extends ControllerBase implements APIServiceNomina {
    private APIServiceNomina service;
    public static final boolean inHardCode = false;
    private static int count = 0;

    public ControllerNomina(Context context, String urlBase) {
        super(context, urlBase);
        initRetrofit();
    }

    public ControllerNomina(Context context, String urlBase, HostnameVerifier hostnameVerifier) {
        super(context, urlBase, hostnameVerifier);
        initRetrofit();
    }

    public ControllerNomina(Context context) {
        super(context, ConstantsV2.DOMAIN_URL_NOMINA_STAGING);
        generateBaseUrl(ConstantsV2.DOMAIN_URL_NOMINA_STAGING);
    }

    public void generateMockRetrofit(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(Utils.hostnameVerifier())
                .addInterceptor(new com.sociomas.core.WebService.Controllers.MockInterceptor(context))
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0,TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create()).client(client).
                baseUrl(url).build();
    }

    public void initRetrofit() {
        if (inHardCode) {
            generateMockRetrofit(ConstantsV2.DOMAIN_URL_NOMINA_STAGING);
            this.service = retrofit.create(APIServiceNomina.class);
        } else {
            this.service=retrofit.create(APIServiceNomina.class);
        }
    }

    @Override
    public Retrofit generateBaseUrl(String url) {
        super.generateBaseUrl(url);
        initRetrofit();
        this.service=retrofit.create(APIServiceNomina.class);
        return retrofit;
    }

    @Override
    public Call<PaymentSheetsResponse> getPendingPayments(@Body PaymenSheetsRequest petition) {
        com.sociomas.core.WebService.Controllers.MockInterceptor.count = 0;
        Call<PaymentSheetsResponse> call = this.service.getPendingPayments(petition);
        return call;
    }

    @Override
    public Call<PaymentSheetsResponse> getPaymentSheetsList(@Body PaymenSheetsRequest petition) {
        com.sociomas.core.WebService.Controllers.MockInterceptor.count = 1;
        Call<PaymentSheetsResponse> call = this.service.getPaymentSheetsList(petition);
        return call;
    }

    @Override
    public Call<PaymentSheetsResponse> ejecutaLiberarNomina(@Body PaymenSheetsRequest petition) {
        Call<PaymentSheetsResponse> call = this.service.ejecutaLiberarNomina(petition);
        com.sociomas.core.WebService.Controllers.MockInterceptor.count = 9;
        return call;

    }

    @Override
    public Call<PaymentSheetsResponse> getNumberAccount(@Body PaymenSheetsRequest request) {
        Call<PaymentSheetsResponse> call = this.service.getNumberAccount(request);
        return call;
    }

    public Call<PaymentSheetsResponse> getConcentradoGeografia(@Body PaymenSheetsRequest request) {
        Call<PaymentSheetsResponse> call = this.service.getConcentradoGeografia(request);
        com.sociomas.core.WebService.Controllers.MockInterceptor.count = 2;
        return call;
    }

    private class MockInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {
            URI uri = chain.request().url().uri();
            String responseString = "";

            if(count == 0) {
                SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
                //"\"strJson\":\"" + securityItems.getRequestNominaEncrypt("{\"element\":[{\"banderaRet\":\"1\",\"banderaSumatoria\":null,\"beneficiario\":\"00799526\",\"compania\":\"0000079\",\"concepto\":\"ABONO POR NOMINA PAPERLESS\",\"conceptoComprobante\":\"NOMINA                                                                                              \",\"contrato\":null,\"contratos\":null,\"cuentaAbono\":\"01270673710100037219\",\"deducciones\":\"000000000000700\",\"descPuesto\":\"DESARROLLADOR DE SISTEMAS                                                       \",\"fechaFinPeriodo\":\"20160401\",\"fechaInicioPeriodo\":\"20160401\",\"folioAlnova\":\"1145\",\"folioOperacion\":\"25853\",\"idEmpresa\":\"PAPERLESS\",\"importe\":1300.0,\"importeSpecified\":true,\"llave\":\"A0ZD033007995260120170702\",\"nssEmpleado\":\"23659874563\",\"numPeriodo\":\"201707\",\"numeroRegistro\":\"1\",\"percepciones\":\"000000000002000\",\"rfcEmpleado\":\"GOPJ880621HBA     \",\"statusPago\":\"1\"}],\"errorCode\":\"0\",\"errorDescription\":\"Operacion efectuada exitosamente\"}") +
                responseString = "{" + "\"error\":false," + "\"mensajeError\":null," + "\"serverTime\":null," + "\"serverUTCTime\":null," + "\"dispositivo_activo\":0," + "\"strJson\":\"" + securityItems.getRequestNominaEncrypt("{" + "    \"element\": [" + "      {" + "        \"banderaRet\": \"1\"," + "        \"banderaSumatoria\": \"01\"," + "        \"beneficiario\": \"00432637\"," + "        \"compania\": \"0000013\"," + "        \"concepto\": \"ABONO POR NOMINA\"," + "        \"conceptoComprobante\": \"                                                                                                    \"," + "        \"contrato\": \"00\"," + "        \"contratos\": null," + "        \"cuentaAbono\": \"01271504990100720087\"," + "        \"deducciones\": \"000000000001527\"," + "        \"descPuesto\": \"DESARROLLADOR DE SISTEMAS                                                       \"," + "        \"fechaFinPeriodo\": \"20130807\"," + "        \"fechaInicioPeriodo\": \"20130801\"," + "        \"folioAlnova\": \"1867\"," + "        \"folioOperacion\": \"28908\"," + "        \"idEmpresa\": \"PAPERLESS\"," + "        \"importe\": 50152.0," + "        \"importeSpecified\": true," + "        \"llave\": \"0VB3001004326370120142303\"," + "        \"nssEmpleado\": \"14058617904\"," + "        \"numPeriodo\": \"201423\"," + "        \"numeroRegistro\": \"1\"," + "        \"percepciones\": \"000000000051679\"," + "        \"rfcEmpleado\": \"DIGV8601058S2     \"," + "        \"statusPago\": \"1\"" + "      }," + "      {" + "        \"banderaRet\": \"1\"," + "        \"banderaSumatoria\": \"00\"," + "        \"beneficiario\": \"00432637\"," + "        \"compania\": \"000HR13\"," + "        \"concepto\": \"DEPOSITO DE TERCEROS\"," + "        \"conceptoComprobante\": \"                                                                                                    \"," + "        \"contrato\": \"00\"," + "        \"contratos\": null," + "        \"cuentaAbono\": \"01271504990100720087\"," + "        \"deducciones\": \"000000000001595\"," + "        \"descPuesto\": \"                                                                                \"," + "        \"fechaFinPeriodo\": \"20130807\"," + "        \"fechaInicioPeriodo\": \"20130801\"," + "        \"folioAlnova\": \"1868\"," + "        \"folioOperacion\": \"28909\"," + "        \"idEmpresa\": \"PAPERLESS\"," + "        \"importe\": 31335.0," + "        \"importeSpecified\": true," + "        \"llave\": \"0V0M001004326371020142303\"," + "        \"nssEmpleado\": \"00000000000\"," + "        \"numPeriodo\": \"201423\"," + "        \"numeroRegistro\": \"1\"," + "        \"percepciones\": \"000000000032930\"," + "        \"rfcEmpleado\": \"DIGV8601058S2     \"," + "        \"statusPago\": \"1\"" + "      }" + "    ]," + "    \"errorCode\": \"0\"," + "    \"errorDescription\": \"Operacion efectuada exitosamente\"" + "  }") + "\"}";

//                count++;
            } else if (count == 1){
                SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
                responseString = "{" +
                        "\"error\":false," +
                        "\"mensajeError\":null," +
                        "\"serverTime\":null," +
                        "\"serverUTCTime\":null," +
                        "\"dispositivo_activo\":0," +
                        "\"strJson\":\"" + securityItems.getRequestNominaEncrypt("{\"recibos\":[{\"errorCode\":\"0\",\"errorDescription\":\"Operacion efectuada exitosamente\",\"listaRecibos\":[{\"cabecera\":{\"anio\":\"2017\",\"banderaContrato\":null,\"centroCostos\":\"000023656\",\"claveDepto\":\"0325\",\"creditoSalario\":\"0000000000\",\"fechaFinPago\":\"20160401\",\"fechaIniPago\":\"20160401\",\"fechaPago\":\"20170407\",\"llave\":\"A0ZD033007995260120170702\",\"nombreCompania\":\"Paperless S.A de C.V.                                       \",\"nombreEmpleado\":\"USUARIO NOMINA                               \",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numeroDetalle\":\"05\",\"numeroEmpresa\":\"033\",\"numeroIMSS\":\"23659874563\",\"numeroRegistro\":null,\"periodo\":\"07\",\"regsitroPatronal\":\"Y6815735102\",\"rfcEmpleado\":\"GOPJ880621HBA     \",\"salarioBaseCotiza\":\"0000006600\",\"salarioDiario\":\"0000000600\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegistro\":\"01\",\"totalCreditos\":\"0000000000\",\"totalDeduccciones\":\"0000000700\",\"totalFinal\":\"0000001300\",\"totalLetra\":\"(CINCO  PESOS 00/100 M.N.)                                                                                                                                                                                                                                \",\"totalPercepciones\":\"0000002000\",\"valesDespensa\":\"0000000000\"},\"mensajes\":[{\"detalleNumero\":\"05\",\"llave\":\"A0ZD033007995260120170702\",\"mensaje\":\"RECIBI DE: Papel Oro       S.A. de C.V. DE CONFORMIDAD. LA CANTIDAD ANOTADA EN EL PRESENTE RECIBO, EN PAGO DE MI SALARIO POR HORAS NORMALES, EXTRAS, COMISIONES Y BONIFICACIONES, DE ACUERDO CON LO DISPUESTO EN LA LEY FEDERAL DEL TRABAJO Y EN MI CONTRATO. HAGO CONSTAR QUE CON DICHA CANTIDAD ESTOY TOTALMENTE PAGADO HASTA LA FECHA, POR LO QUE NO TENGO NI ME RESERVO DERECHO ALGUNO QUE RECLAMAR POR NINGUN CONCEPTO.                                      \",\"tipoRegsitro\":\"03\"}],\"percepcionDeduccions\":[{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Aportacion trabaj. IMSS                                     \",\"detalleNumero\":\"01\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Des. C. Merma Auditoria                                     \",\"detalleNumero\":\"02\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"I.S.P.T.                                                    \",\"detalleNumero\":\"03\",\"dias\":\"  \",\"importe\":\"0000000300\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"PER\",\"conceptoNominal\":\"    \",\"descripcion\":\"Ingresos                                                    \",\"detalleNumero\":\"04\",\"dias\":\"  \",\"importe\":\"0000002000\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"}],\"templates\":null}]}]}") + "\"" +
                        "}";

//                count = 0;
            } else if (count == 2){
                SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
                responseString = "{" +
                        "\"error\":false," +
                        "\"mensajeError\":null," +
                        "\"serverTime\":null," +
                        "\"serverUTCTime\":null," +
                        "\"dispositivo_activo\":0," +
                        "\"strJson\":\"" + securityItems.getRequestNominaEncrypt("{\"recibos\":[{\"errorCode\":\"0\",\"errorDescription\":\"Operacion efectuada exitosamente\",\"listaRecibos\":[{\"cabecera\":{\"anio\":\"2017\",\"banderaContrato\":null,\"centroCostos\":\"000023656\",\"claveDepto\":\"0325\",\"creditoSalario\":\"0000000000\",\"fechaFinPago\":\"20160401\",\"fechaIniPago\":\"20160401\",\"fechaPago\":\"20170407\",\"llave\":\"A0ZD033007995260120170702\",\"nombreCompania\":\"Paperless S.A de C.V.                                       \",\"nombreEmpleado\":\"USUARIO NOMINA                               \",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numeroDetalle\":\"05\",\"numeroEmpresa\":\"033\",\"numeroIMSS\":\"23659874563\",\"numeroRegistro\":null,\"periodo\":\"07\",\"regsitroPatronal\":\"Y6815735102\",\"rfcEmpleado\":\"GOPJ880621HBA     \",\"salarioBaseCotiza\":\"0000006600\",\"salarioDiario\":\"0000000600\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegistro\":\"01\",\"totalCreditos\":\"0000000000\",\"totalDeduccciones\":\"0000000700\",\"totalFinal\":\"0000001300\",\"totalLetra\":\"(CINCO  PESOS 00/100 M.N.)                                                                                                                                                                                                                                \",\"totalPercepciones\":\"0000002000\",\"valesDespensa\":\"0000000000\"},\"mensajes\":[{\"detalleNumero\":\"05\",\"llave\":\"A0ZD033007995260120170702\",\"mensaje\":\"RECIBI DE: Papel Oro       S.A. de C.V. DE CONFORMIDAD. LA CANTIDAD ANOTADA EN EL PRESENTE RECIBO, EN PAGO DE MI SALARIO POR HORAS NORMALES, EXTRAS, COMISIONES Y BONIFICACIONES, DE ACUERDO CON LO DISPUESTO EN LA LEY FEDERAL DEL TRABAJO Y EN MI CONTRATO. HAGO CONSTAR QUE CON DICHA CANTIDAD ESTOY TOTALMENTE PAGADO HASTA LA FECHA, POR LO QUE NO TENGO NI ME RESERVO DERECHO ALGUNO QUE RECLAMAR POR NINGUN CONCEPTO.                                      \",\"tipoRegsitro\":\"03\"}],\"percepcionDeduccions\":[{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Aportacion trabaj. IMSS                                     \",\"detalleNumero\":\"01\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Des. C. Merma Auditoria                                     \",\"detalleNumero\":\"02\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"I.S.P.T.                                                    \",\"detalleNumero\":\"03\",\"dias\":\"  \",\"importe\":\"0000000300\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"PER\",\"conceptoNominal\":\"    \",\"descripcion\":\"Ingresos                                                    \",\"detalleNumero\":\"04\",\"dias\":\"  \",\"importe\":\"0000002000\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"}],\"templates\":null}]}]}") + "\"" +
                        "}";
            } else if (count == 6) {
                SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(context));
                responseString = "{" +
                        "\"error\":false," +
                        "\"mensajeError\":null," +
                        "\"serverTime\":null," +
                        "\"serverUTCTime\":null," +
                        "\"dispositivo_activo\":0," +
                        "\"strJson\":\"" + securityItems.getRequestNominaEncrypt("{\"recibos\":[{\"errorCode\":\"0\",\"errorDescription\":\"Operacion efectuada exitosamente\",\"listaRecibos\":[{\"cabecera\":{\"anio\":\"2017\",\"banderaContrato\":null,\"centroCostos\":\"000023656\",\"claveDepto\":\"0325\",\"creditoSalario\":\"0000000000\",\"fechaFinPago\":\"20160401\",\"fechaIniPago\":\"20160401\",\"fechaPago\":\"20170407\",\"llave\":\"A0ZD033007995260120170702\",\"nombreCompania\":\"Paperless S.A de C.V.                                       \",\"nombreEmpleado\":\"USUARIO NOMINA                               \",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numeroDetalle\":\"05\",\"numeroEmpresa\":\"033\",\"numeroIMSS\":\"23659874563\",\"numeroRegistro\":null,\"periodo\":\"07\",\"regsitroPatronal\":\"Y6815735102\",\"rfcEmpleado\":\"GOPJ880621HBA     \",\"salarioBaseCotiza\":\"0000006600\",\"salarioDiario\":\"0000000600\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegistro\":\"01\",\"totalCreditos\":\"0000000000\",\"totalDeduccciones\":\"0000000700\",\"totalFinal\":\"0000001300\",\"totalLetra\":\"(CINCO  PESOS 00/100 M.N.)                                                                                                                                                                                                                                \",\"totalPercepciones\":\"0000002000\",\"valesDespensa\":\"0000000000\"},\"mensajes\":[{\"detalleNumero\":\"05\",\"llave\":\"A0ZD033007995260120170702\",\"mensaje\":\"RECIBI DE: Papel Oro       S.A. de C.V. DE CONFORMIDAD. LA CANTIDAD ANOTADA EN EL PRESENTE RECIBO, EN PAGO DE MI SALARIO POR HORAS NORMALES, EXTRAS, COMISIONES Y BONIFICACIONES, DE ACUERDO CON LO DISPUESTO EN LA LEY FEDERAL DEL TRABAJO Y EN MI CONTRATO. HAGO CONSTAR QUE CON DICHA CANTIDAD ESTOY TOTALMENTE PAGADO HASTA LA FECHA, POR LO QUE NO TENGO NI ME RESERVO DERECHO ALGUNO QUE RECLAMAR POR NINGUN CONCEPTO.                                      \",\"tipoRegsitro\":\"03\"}],\"percepcionDeduccions\":[{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Aportacion trabaj. IMSS                                     \",\"detalleNumero\":\"01\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"Des. C. Merma Auditoria                                     \",\"detalleNumero\":\"02\",\"dias\":\"  \",\"importe\":\"0000000200\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"DED\",\"conceptoNominal\":\"    \",\"descripcion\":\"I.S.P.T.                                                    \",\"detalleNumero\":\"03\",\"dias\":\"  \",\"importe\":\"0000000300\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"},{\"anio\":\"2017\",\"concepto\":\"PER\",\"conceptoNominal\":\"    \",\"descripcion\":\"Ingresos                                                    \",\"detalleNumero\":\"04\",\"dias\":\"  \",\"importe\":\"0000002000\",\"llave\":\"A0ZD033007995260120170702\",\"numCompania\":\"003265\",\"numEmpleado\":\"00799526\",\"numEmpresa\":\"033\",\"periodo\":\"07\",\"tipoCompania\":\"01\",\"tipoPeriodo\":\"02\",\"tipoRegsitro\":\"02\"}],\"templates\":null}]}]}") + "\"" +
                        "}";
            }
            Response response = null;

            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString))
                    .addHeader("content-type", "application/json")
                    .build();

            return response;
        }
    }
}
