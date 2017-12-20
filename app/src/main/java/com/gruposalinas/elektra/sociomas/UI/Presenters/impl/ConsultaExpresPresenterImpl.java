package com.gruposalinas.elektra.sociomas.UI.Presenters.impl;

import com.gruposalinas.elektra.sociomas.UI.Presenters.ConsultaExpresPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.MVP.BasePresenterImpl;

import android.util.Log;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecurityItems;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumNomina;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerNomina;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption1Request;
import com.sociomas.core.WebService.Model.Request.Nomina.PaymenSheetsRequest;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaAccountResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.RecibosPendientesNominaResponse;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 06/09/17.
 */

public class ConsultaExpresPresenterImpl extends BasePresenterImpl implements ConsultaExpresPresenter {

    public static final String TAG = ConsultaExpresPresenterImpl.class.getSimpleName();
    private String numberAccount;

    @Override
    public void callQueryExpress(String dataAccess) {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
//        if (numberAccount != null) {
//            dataAccess = numberAccount;
//        }
        callGetPendingPayments(dataAccess);

    }

    @Override
    public void loadNumberAccount() {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
        final SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.CONSULTA_CUENTA.get());
        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        getControllerNomina().getNumberAccount(request).enqueue(new Callback<PaymentSheetsResponse>() {
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
//                            SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
                            String decryptStrJson = securityItems.getTextDecrypt(response.body().getStrJson());
//                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
//                            String decryptStrJson =response.body().getStrJson();
                            Gson gson = new Gson();
                            NominaAccountResponse
                                    nominaAccountResponse = gson
                                    .fromJson(decryptStrJson, NominaAccountResponse.class);
                            if (nominaAccountResponse.getNumberAccount() != null) {
                                if (nominaAccountResponse.getNumberAccount().trim().length() > 0) {
                                    ViewEvent eventData = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                    eventData.getModel().put(ViewEvent.ENTRY,nominaAccountResponse.getNumberAccount().trim());
                                    eventData.getModel().put(ViewEvent.RESOURCE_ID, R.id.etNumberAccount);
                                    notifyData(eventData);
                                    numberAccount = nominaAccountResponse.getNumberAccount();
                                } else {
                                    Log.e(TAG, "No se encontro número de cuenta");
                                    numberAccount = null;
                                }
                            } else {
                                Log.e(TAG, "No se encontro número de cuenta");
                                numberAccount = null;
                            }
                        }
                        break;
                        default:{
                            Log.e(TAG, "No se encontro número de cuenta");
                            numberAccount = null;
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    numberAccount = null;

                }
                ViewEvent eventProgress = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventProgress);
            }

            @Override
            public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                numberAccount = null;
                ViewEvent eventProgress = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventProgress);
            }
        });

    }

    private void callGetPendingPayments(String dataAccess) {
        final SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.RECIBOS_PENDIENTES.get());
        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        NominaJsonStringOption1Request nominaJsonString = new NominaJsonStringOption1Request();
        nominaJsonString.setNoCuenta(dataAccess);//01270673710100037219
        request.setJsonString(securityItems.getRequestNominaEncrypt(gson.toJson(nominaJsonString)));
//        String encryptPetition = securityItems.getRequestNominaEncrypt(gson.toJson(request));
        Log.e("NOMINA", gson.toJson(request));
        getControllerNomina().getPendingPayments(request).enqueue(new Callback<PaymentSheetsResponse>() {
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
                            Log.e(TAG, response.body().getStrJson());
//                            SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
                            String decryptStrJson = securityItems.getTextDecrypt(response.body().getStrJson());
//                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
//                            String decryptStrJson =response.body().getStrJson();
                            Gson gson = new Gson();
                            Log.e(TAG,decryptStrJson);
                            RecibosPendientesNominaResponse
                                    recibosPendientesNominaResponse = gson
                                    .fromJson(decryptStrJson, RecibosPendientesNominaResponse.class);
                            if (recibosPendientesNominaResponse != null) {
                                if (recibosPendientesNominaResponse.getRecibosPendientes() != null) {
                                    if (recibosPendientesNominaResponse.getRecibosPendientes().size() > 0) {
                                        ViewEvent eventData = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
                                        eventData.getModel().put(ViewEvent.ENTRIES_LIST, recibosPendientesNominaResponse.getRecibosPendientes());
                                        eventData.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnConsultar);
                                        eventData.getModel().put(ViewEvent.ERROR_CODE, Integer.valueOf(recibosPendientesNominaResponse.getErrorCode()));
                                        notifyData(eventData);
                                    } else {
                                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                        eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
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
                            Log.e(TAG, decryptStrJson);
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
                Log.e(TAG, t.toString());
                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                try {
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                } catch (Exception e) {
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
                }

                notifyData(eventErr);
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }
        });
    }

    @Override
    public void presentImageMsg(int resource, int option) {
        ViewEvent eventPresentMsg = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        eventPresentMsg.getModel().put(ViewEvent.RESOURCE_ID, R.id.civLogo);
        eventPresentMsg.getModel().put(ViewEvent.NEXT_VIEW, option);
        eventPresentMsg.getModel().put(ViewEvent.ENTRY, resource);
        notifyData(eventPresentMsg);
    }

    public ControllerNomina getControllerNomina() {
        return ApplicationBase.getIntance().getControllerNomina();
    }
}
