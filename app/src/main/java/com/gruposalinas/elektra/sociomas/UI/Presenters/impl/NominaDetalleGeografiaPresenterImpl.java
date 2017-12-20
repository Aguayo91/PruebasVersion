package com.gruposalinas.elektra.sociomas.UI.Presenters.impl;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Presenters.NominaDetalleGeografiaPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.DeviceUtils;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Enums.EnumNomina;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerNomina;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption2Request;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption4Request;
import com.sociomas.core.WebService.Model.Request.Nomina.PaymenSheetsRequest;
import com.sociomas.core.WebService.Model.Response.Nomina.GeografiaReciboDetalleConcentradoResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.GeografiaRecibosConcentradoResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaLiberacionResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.RecibosLiberacion;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class NominaDetalleGeografiaPresenterImpl extends BasePresenterImpl implements NominaDetalleGeografiaPresenter {

    public static final String TAG = NominaDetalleGeografiaPresenterImpl.class.getSimpleName();
    private ReciboNomina reciboNomina;

    @Override
    public void getFullPaymentSheetsGeografia(final Context context, ReciboNomina reciboNomina) {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
        this.reciboNomina = reciboNomina;
        List<NominaJsonStringOption2Request> llavesList = new ArrayList<>();

        SecurityItems si = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.RECIBOS_CONCENTRADO.get());
        request.setId_num_empleado(si.getIdEmployEncrypt());
        request.setToken(si.getTokenEncrypt());
//        for (ReciboNomina rn : nominaList) {
        NominaJsonStringOption2Request option2Request = new NominaJsonStringOption2Request();
        option2Request.setLlave(reciboNomina.getLlave());
        llavesList.add(option2Request);
//        }
        request.setJsonString(si.getRequestNominaEncrypt(gson.toJson(llavesList)));
        getControllerNomina().getConcentradoGeografia(request).enqueue(new Callback<PaymentSheetsResponse>() {
            @Override
            public void onResponse(Call<PaymentSheetsResponse> call, Response<PaymentSheetsResponse> response) {
                Log.v(TAG, response.body().toString());
                try {
//                    SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                    String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                    String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                    Gson gson = new Gson();
                    Log.v(TAG, decryptStrJson);
                    GeografiaRecibosConcentradoResponse geografiaResponse =
                            gson.fromJson(decryptStrJson, GeografiaRecibosConcentradoResponse.class);
                    if (geografiaResponse != null) {
                        if(geografiaResponse.getRecibos() != null) {
                            if (Integer.parseInt(geografiaResponse.getRecibos().get(0).getErrorCode()) == 0) {
                                GeografiaReciboDetalleConcentradoResponse concentrado = geografiaResponse.getRecibos().get(0).getListaRecibos().get(0);
                                if (concentrado != null) {
                                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                    event.getModel().put(ViewEvent.ENTRY, concentrado.getGeografiaDetalleCabecera());
                                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.rlParent);
                                    notifyData(event);
                                } else {
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                                    notifyData(eventErr);
                                }
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                                notifyData(eventErr);
                            }
                        } else {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                            notifyData(eventErr);
                        }
                    } else {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                        eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                        notifyData(eventErr);
                    }
                }catch (Exception e) {
                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                    notifyData(eventErr);
                }
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }

            @Override
            public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.MESSAGE, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                notifyData(eventErr);
            }
        });
    }

    @Override
    public void liberarRecibo(Bundle bundle) {
//        ReciboNomina reciboNomina = (ReciboNomina) bundle.getSerializable(ViewEvent.ENTRY);
//        ReciboNominaDetalleResponse recibo = (ReciboNominaDetalleResponse) bundle.getSerializable(ViewEvent.ENTRIES_LIST);
//        ViewEvent eventProgres = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
//        notifyData(eventProgres);
//        SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//        Gson gson = new Gson();
//        List<NominaJsonStringOption4Request> listLiberacion = new ArrayList<>();
//        NominaJsonStringOption4Request obj = new NominaJsonStringOption4Request();
//        obj.setLlave(reciboNomina.getLlave());
//        obj.setNum_empleado(reciboNomina.getBeneficiario());
//        obj.setNum_cuenta_empleado(reciboNomina.getCuentaAbono());
//        obj.setDispositivo_empleado(Utils.getDeviceID(getView().getActivityInstance()));
//        obj.setIp_cliente(ApplicationBase.IPDeviceAddress);
//        obj.setSistema(DeviceUtils.getSistema());
//        obj.setVia_liberacion(Constants.IDSistema); //Falta que lo proporcione servicios
//        obj.setId_usuario(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//        obj.setPais(ConstantsV2.PAIS);
//        obj.setValidacion(bundle.getString("VALIDATION"));
//        listLiberacion.add(obj);
//        PaymenSheetsRequest request = new PaymenSheetsRequest();
//        request.setAccion(EnumNomina.LIBERAR_NOMINA.get());
//        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
//        request.setToken(securityItems.getTokenEncrypt());
//        request.setJsonString(securityItems.getRequestNominaEncrypt(gson.toJson(listLiberacion)));
//        getControllerNomina().ejecutaLiberarNomina(request).enqueue(nominaEjecucion);


        ReciboNomina reciboNomina = (ReciboNomina) bundle.getSerializable(ViewEvent.ENTRY);
        ReciboNominaDetalleResponse recibo = (ReciboNominaDetalleResponse) bundle.getSerializable(ViewEvent.ENTRIES_LIST);
        ViewEvent eventProgres = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgres);
        final SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        List<NominaJsonStringOption4Request> listLiberacion = new ArrayList<>();
        NominaJsonStringOption4Request obj = new NominaJsonStringOption4Request();
        obj.setLlave(reciboNomina.getLlave());
        obj.setNum_empleado(reciboNomina.getBeneficiario());
        obj.setNum_cuenta_empleado(reciboNomina.getCuentaAbono());
        obj.setDispositivo_empleado("5"); //5.-TOKEN 4.-HUELLA
        obj.setIp_cliente(ApplicationBase.IPDeviceAddress);
        obj.setSistema("NETNETPUB");
        obj.setVia_liberacion("2"); //2.-CONSULTAEXPRESS 1.-EBANKING
        obj.setId_usuario(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        obj.setPais(ConstantsV2.PAIS);
        obj.setValidacion(bundle.getString("VALIDATION"));
        listLiberacion.add(obj);
        PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.LIBERAR_NOMINA.get());
        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setJsonString(securityItems.getRequestNominaEncrypt(gson.toJson(listLiberacion)));
        getControllerNomina().ejecutaLiberarNomina(request).enqueue(new Callback<PaymentSheetsResponse>() {
            @Override
            public void onResponse(Call<PaymentSheetsResponse> call, Response<PaymentSheetsResponse> response) {
                try {
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_OK: {
                            if (response.body().getError()) {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, response.body().getMensajeError());
                                notifyData(eventErr);
                                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                                notifyData(eventHide);
                                return;
                            }
//                        SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
                            String decryptStrJson = securityItems.getTextDecrypt(response.body().getStrJson());
//                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                            Gson gson = new Gson();
                            Log.e(TAG, decryptStrJson);
                            try {
                                ListaLiberacionResponse object= gson.fromJson(decryptStrJson, ListaLiberacionResponse.class);
                                boolean correcto = false;
                                String msgErr = "";
                                for (RecibosLiberacion rln : object.getRecibosLiberacionResponses()) {
                                    if (rln.getErrorCode() == 0) {
                                        correcto = true;
                                        continue;
                                    } else {
                                        correcto = false;
                                        msgErr = rln.getErrorDescription();
                                        break;
                                    }
                                }
                                if (correcto) {
                                    ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                    event.getModel().put(ViewEvent.ENTRY, "Recibo liberado exitosamente");
                                    event.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                    event.getModel().put(ViewEvent.RESOURCE_ID, R.id.liberarRecibo);
                                    notifyData(event);
                                } else {
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                    eventErr.getModel().put(ViewEvent.ERROR_MSG, msgErr);
                                    notifyData(eventErr);
                                }
                            } catch (Exception e) {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, decryptStrJson);
                                notifyData(eventErr);

                            }





                        }
                        break;
                        default: {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                            notifyData(eventErr);

                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                    notifyData(eventErr);
                }
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }

            @Override
            public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                notifyData(eventErr);
            }
        });

    }

    private Callback<PaymentSheetsResponse> nominaEjecucion = new Callback<PaymentSheetsResponse>() {
        @Override
        public void onResponse(Call<PaymentSheetsResponse> call, Response<PaymentSheetsResponse> response) {
            try {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK: {
                        if (response.body().getError()) {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, response.body().getMensajeError());
                            notifyData(eventErr);
                            ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                            notifyData(eventHide);
                            return;
                        }
//                        SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                        String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                        String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                        Gson gson = new Gson();
                        Log.e(TAG, decryptStrJson);


                        ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                        event.getModel().put(ViewEvent.ENTRY, "Recibo liberado exitosamente");
                        event.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                        event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);
                        notifyData(event);

                    }
                    break;
                    default: {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                        eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                        notifyData(eventErr);

                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
            notifyData(eventHide);
        }

        @Override
        public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
            Log.e(TAG, t.getMessage());
            ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
            notifyData(eventHide);
            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
            eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
            notifyData(eventErr);
        }
    };

    public ControllerNomina getControllerNomina() {
        return ApplicationBase.getIntance().getControllerNomina();
    }
}
