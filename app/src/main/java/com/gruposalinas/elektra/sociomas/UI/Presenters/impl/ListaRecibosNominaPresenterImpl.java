package com.gruposalinas.elektra.sociomas.UI.Presenters.impl;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.UI.Presenters.ListaRecibosNominaPresenter;
import com.gruposalinas.elektra.sociomas.Utils.DeviceUtils;
import com.gruposalinas.elektra.sociomas.Utils.Security.DecryptUtils;
import com.sociomas.core.MVP.BasePresenterImpl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.MVP.enums.ViewEventType;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Enums.EnumNomina;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerNomina;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption1Request;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption2Request;
import com.sociomas.core.WebService.Model.Request.Nomina.NominaJsonStringOption4Request;
import com.sociomas.core.WebService.Model.Request.Nomina.PaymenSheetsRequest;
import com.sociomas.core.WebService.Model.Response.Nomina.ConcentradoNominaRespose;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaLiberacionResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaRecibosNominaResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetalleCabecera;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.RecibosLiberacion;
import com.sociomas.core.WebService.Model.Response.Nomina.RecibosPendientesNominaResponse;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 07/09/17.
 */
public class ListaRecibosNominaPresenterImpl extends BasePresenterImpl implements ListaRecibosNominaPresenter {


    public static final String TAG = ListaRecibosNominaPresenterImpl.class.getSimpleName();
    ListaRecibosNominaResponse listaRecibos = new ListaRecibosNominaResponse();
    private int numberReceipts = 0;
    private List<ReciboNomina> nominaList;
    private int numberPaysheets;
    public Map<String, ReciboNomina> mapRecibos = new HashMap<>();

    public Map<String, ReciboNomina> getMapRecibos() {
        return mapRecibos;
    }

    @Override
    public void getFullPaymentSheets(Bundle bundle) {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
        nominaList = (List<ReciboNomina>) bundle.getSerializable(ViewEvent.ENTRIES_LIST);
        List<NominaJsonStringOption2Request> llavesList = new ArrayList<>();

        SecurityItems si = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.RECIBOS_DETALLE.get());
        request.setId_num_empleado(si.getIdEmployEncrypt());
        request.setToken(si.getTokenEncrypt());
        for (ReciboNomina rn : nominaList) {
            NominaJsonStringOption2Request option2Request = new NominaJsonStringOption2Request();
            option2Request.setLlave(rn.getLlave());
            llavesList.add(option2Request);
        }
        request.setJsonString(si.getRequestNominaEncrypt(gson.toJson(llavesList)));
        numberPaysheets = nominaList.size();
        getControllerNomina().getPaymentSheetsList(request).enqueue(new Callback<PaymentSheetsResponse>() {
            @Override
            public void onResponse(Call<PaymentSheetsResponse> call, Response<PaymentSheetsResponse> response) {
                try {
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_OK: {
                            if (response.body().getError()) {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, response.body().getMensajeError());
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                notifyData(eventErr);
                                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                                notifyData(eventHide);
                                return;
                            }
//                            SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                            String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                            Gson gson = new Gson();
                            Log.e(TAG, decryptStrJson);
                            ConcentradoNominaRespose
                                    concentradoNominaRespose = gson
                                    .fromJson(decryptStrJson, ConcentradoNominaRespose.class);
                            if (concentradoNominaRespose.getRecibos() != null) {
                                if (concentradoNominaRespose.getRecibos().size() > 0) {

                                    List<ListaRecibosNominaResponse> recibosCopy = new ArrayList<ListaRecibosNominaResponse>();
                                    for (ListaRecibosNominaResponse l : concentradoNominaRespose.getRecibos()) {
                                        for (ReciboNominaDetalleResponse r : l.getListaRecibos()) {
                                            ReciboNomina reciboNomina = new ReciboNomina();
                                            reciboNomina.setLlave(r.getCabecera().getLlave());
                                            int index = Collections.binarySearch(nominaList, reciboNomina, new FooComparator());
                                            l.setReciboParent(nominaList.get(index));
                                        }
                                        recibosCopy.add(l);
                                    }
                                    concentradoNominaRespose.setRecibos(recibosCopy);
                                    //TODO implement notify data from paysheets
                                    //region ParseRECIBOS
                                    ViewEvent eventResultset = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
                                    eventResultset.getModel().put(ViewEvent.ENTRIES_LIST, concentradoNominaRespose.getRecibos());
                                    eventResultset.getModel().put(ViewEvent.RESOURCE_ID, R.id.recyclerRecibos);
                                    notifyData(eventResultset);

                                    float totalAmount = 0;
                                    String textTotalAmount = "";
                                    for (ListaRecibosNominaResponse lrnr : concentradoNominaRespose.getRecibos()) {
                                        for (ReciboNominaDetalleResponse rndr : lrnr.getListaRecibos()) {
                                            try {
                                                totalAmount += Float.parseFloat(rndr.getCabecera().getTotalFinal());
                                                textTotalAmount = String.valueOf(totalAmount);
                                            } catch (Exception e) {
                                                Log.e(TAG, e.toString());
                                                textTotalAmount = "";
                                                break;
                                            }
                                        }

                                    }
                                    ViewEvent eventTotal = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                                    eventTotal.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvAmountTotal);
                                    eventTotal.getModel().put(ViewEvent.ENTRY, textTotalAmount);
                                    eventTotal.getModel().put(ViewEvent.BOOLEAN_OBJECT, textTotalAmount.trim().length() > 0 ? true : false);
                                    notifyData(eventTotal);
                                    //endregion
                                } else {
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                    eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
                                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                                    notifyData(eventErr);
                                }
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                                notifyData(eventErr);
                            }
                        }
                        break;
                        default: {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                            eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                            notifyData(eventErr);

                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    notifyData(eventErr);
                }
                mapRecibos.clear();
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }

            @Override
            public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                try {
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                } catch (Exception e) {
                    e.printStackTrace();
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");

                }
                notifyData(eventErr);
            }
        });
    }

    @Override
    public void callServiceNomina(String autenticador) {
        if (mapRecibos.size() > 0) {
            ViewEvent eventProgres = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
            notifyData(eventProgres);
            final SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
            Gson gson = new Gson();
            List<NominaJsonStringOption4Request> listLiberacion = new ArrayList<>();
            for (String key : mapRecibos.keySet()) {
//                ReciboNominaDetalleResponse rndr = mapRecibos.get(key);
//                ReciboNomina reciboNomina = new ReciboNomina();
//                reciboNomina.setLlave(key);
//                int index = Collections.binarySearch(nominaList, reciboNomina, new FooComparator());
                ReciboNomina rn = mapRecibos.get(key);
                NominaJsonStringOption4Request obj = new NominaJsonStringOption4Request();
                obj.setLlave(rn.getLlave());
                obj.setNum_empleado(rn.getBeneficiario());
                obj.setNum_cuenta_empleado(rn.getCuentaAbono());
                obj.setDispositivo_empleado("5"); //5.-TOKEN 4.-HUELLA
                obj.setIp_cliente(ApplicationBase.IPDeviceAddress);
                obj.setSistema("NETNETPUB"); // Consultar catalogo archivo
                obj.setValidacion(autenticador);
                obj.setVia_liberacion("2"); //2.-CONSULTAEXPRESS 1.-EBANKING
                obj.setId_usuario(Utils.getNumeroEmpleado(getView().getActivityInstance()));
                obj.setPais(ConstantsV2.PAIS);
                listLiberacion.add(obj);
            }
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
                                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
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
                                        event.getModel().put(ViewEvent.BOOLEAN_OBJECT, (mapRecibos.size() == numberPaysheets) ? false : true);
                                        event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);
                                        notifyData(event);
                                    } else {
                                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                        eventErr.getModel().put(ViewEvent.ERROR_MSG, msgErr);
                                        eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                        notifyData(eventErr);
                                    }
                                } catch (Exception e) {
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                    eventErr.getModel().put(ViewEvent.ERROR_MSG, decryptStrJson);
                                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                    notifyData(eventErr);

                                }





                            }
                            break;
                            default: {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                notifyData(eventErr);

                            }
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                        eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                        eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                        notifyData(eventErr);
                    }
                    ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                    notifyData(eventHide);
                    mapRecibos.clear();
                }

                @Override
                public void onFailure(Call<PaymentSheetsResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                    notifyData(eventHide);
                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                    notifyData(eventErr);
                }
            });
//            if (numberReceipts < numberPaysheets) {
//                ReciboNomina reciboNomina = new ReciboNomina();
//                reciboNomina.setLlave(r.getCabecera().getLlave());
//                int index = Collections.binarySearch(nominaList, reciboNomina, new FooComparator());
//
//
//            }
        } else {
            ViewEvent eventNotif = new ViewEvent(ViewEventType.SHOW_DIALOG_EVENT_TYPE);
            eventNotif.getModel().put(ViewEvent.MESSAGE, "Debes seleccionar los recibos a liberar.");
            eventNotif.getModel().put(ViewEvent.TITLE, "Aviso");
            eventNotif.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);
            notifyData(eventNotif);
        }
    }


    class FooComparator
            implements Comparator<ReciboNomina> {
        public int compare(ReciboNomina a, ReciboNomina b) {
            return (a.getLlave().compareTo(b.getLlave()));
        }
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
                            eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                            notifyData(eventHide);
                            return;
                        }
//                        SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                        String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                        String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                        Gson gson = new Gson();
                        Log.e(TAG, decryptStrJson);
//                        numberReceipts++;
//                        if (numberReceipts == numberPaysheets) {
//                            numberReceipts = 0;
                        Log.e(TAG, decryptStrJson);
                        try {
                            Object object= gson.fromJson("{\"recibos\":[{\"recibos\":{\"errorCode\":-1,\"errorDescription\":\"El parametro \\\"dispositivo_empleado\\\" es incorrecto\",\"observaciones\":null,\"resultado\":null}}]}", Object.class);
                            ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                            event.getModel().put(ViewEvent.ENTRY, "Recibo liberado exitosamente");
                            event.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                            event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);
                            notifyData(event);
                        } catch (Exception e) {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, decryptStrJson);
                            eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                            notifyData(eventErr);

                        }
                            ViewEvent event = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
                            event.getModel().put(ViewEvent.ENTRY, "Recibos liberados exitosamente");

                            event.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);

                        if (mapRecibos.size() < nominaList.size()) {
                            event.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                        } else {
                            event.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                        }
                        notifyData(event);
                        mapRecibos.clear();
//                        } else {
//                            callServiceNomina();
//                        }
                    }
                    break;
                    default: {
                        ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                        eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                        eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                        eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
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
            try {
                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
            } catch (Exception e) {
                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                eventErr.getModel().put(ViewEvent.ERROR_MSG, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");
            }

            notifyData(eventErr);
        }
    };

    @Override
    public void setRecibo(ReciboNomina recibo) {
        if (mapRecibos.containsKey(recibo.getLlave())) {
            //TODO actualizar recibo
        } else {
            mapRecibos.put(recibo.getLlave(), recibo);
        }
    }

    @Override
    public void removeRecibo(ReciboNomina recibo) {
        if (mapRecibos.containsKey(recibo.getLlave())) {
            mapRecibos.remove(recibo.getLlave());
        } else {
            //
        }
    }

    @Override
    public void setData(Bundle arguments) {

        nominaList = (List<ReciboNomina>) arguments.getSerializable(ViewEvent.ENTRIES_LIST);
        ViewEvent eventResultset = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
        eventResultset.getModel().put(ViewEvent.ENTRIES_LIST, nominaList);
        eventResultset.getModel().put(ViewEvent.RESOURCE_ID, R.id.recyclerRecibos);
        notifyData(eventResultset);
        numberPaysheets = nominaList.size();
        float totalAmount = 0;
        String textTotalAmount = "";
        for (ReciboNomina rn : nominaList) {
            try {
                totalAmount += Float.parseFloat(rn.getImporte());
                textTotalAmount = String.valueOf(totalAmount);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                textTotalAmount = "";
                break;
            }
        }

        ViewEvent eventTotal = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        eventTotal.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvAmountTotal);
        eventTotal.getModel().put(ViewEvent.ENTRY, textTotalAmount);
        eventTotal.getModel().put(ViewEvent.BOOLEAN_OBJECT, textTotalAmount.trim().length() > 0 ? true : false);
        notifyData(eventTotal);
    }

    @Override
    public void reloadRecibos(String numberAccount) {
        callGetPendingPayments(numberAccount);
    }

    @Override
    public void presentImageMsg(int resource, int option) {
        ViewEvent eventPresentMsg = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        eventPresentMsg.getModel().put(ViewEvent.RESOURCE_ID, R.id.cardView);
        eventPresentMsg.getModel().put(ViewEvent.NEXT_VIEW, option);
        eventPresentMsg.getModel().put(ViewEvent.ENTRY, resource);
        notifyData(eventPresentMsg);
    }

    @Override
    public int getMapRecibosSelected() {
        if (mapRecibos.size() > 0) {
            return mapRecibos.size();
        } else {
            ViewEvent eventNotif = new ViewEvent(ViewEventType.SHOW_DIALOG_EVENT_TYPE);
            eventNotif.getModel().put(ViewEvent.MESSAGE, "Debes seleccionar los recibos a liberar.");
            eventNotif.getModel().put(ViewEvent.TITLE, "Aviso");
            eventNotif.getModel().put(ViewEvent.RESOURCE_ID, R.id.btnLiberar);
            notifyData(eventNotif);
            return 0;
        }
    }

    @Override
    public int getRecibosInMap() {
        return mapRecibos.size();
    }

    private void callGetPendingPayments(String dataAccess) {
       SecurityItems securityItems = new SecurityItems( Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.RECIBOS_PENDIENTES.get());
        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        NominaJsonStringOption1Request nominaJsonString = new NominaJsonStringOption1Request();
        nominaJsonString.setNoCuenta(dataAccess);//01270673710100037219
        request.setJsonString(securityItems.getRequestNominaEncrypt(gson.toJson(nominaJsonString)));
//        String encryptPetition = securityItems.getRequestNominaEncrypt(gson.toJson(request));
        Log.e("NOMINA", gson.toJson(request) + "\n\n" + gson.toJson(request));
        getControllerNomina().getPendingPayments(request).enqueue(new Callback<PaymentSheetsResponse>() {
            @Override
            public void onResponse(Call<PaymentSheetsResponse> call, Response<PaymentSheetsResponse> response) {
                try {
                    switch (response.code()) {
                        case HttpURLConnection.HTTP_OK :{
                            if (response.body().getError()) {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, response.body().getMensajeError());
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                                notifyData(eventErr);
                                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                                notifyData(eventHide);
                                return;
                            }
//                            SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                            String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                            Gson gson = new Gson();
                            RecibosPendientesNominaResponse
                                    recibosPendientesNominaResponse = gson
                                    .fromJson(decryptStrJson, RecibosPendientesNominaResponse.class);
                            if (recibosPendientesNominaResponse.getRecibosPendientes() != null) {
                                if (recibosPendientesNominaResponse.getRecibosPendientes().size() > 0) {
                                    Bundle mBundle = new Bundle();
                                    mBundle.putSerializable(ViewEvent.ENTRIES_LIST, (Serializable) recibosPendientesNominaResponse.getRecibosPendientes());
                                   setData(mBundle);

                                } else {
                                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                    eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
                                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                                    notifyData(eventErr);
                                }
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
                                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, false);
                                notifyData(eventErr);
                            }
                            Log.e(TAG, decryptStrJson);
                        }
                        break;
                        default: {
                            ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                            eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                            eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                            eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                            notifyData(eventErr);

                        }
                        break;
                    }




                } catch (Exception e) {
                    e.printStackTrace();
                    ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                    eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                    eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
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
                eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                eventErr.getModel().put(ViewEvent.BOOLEAN_OBJECT, true);
                notifyData(eventErr);
                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                notifyData(eventHide);
            }
        });
    }

    public ControllerNomina getControllerNomina() {
        return ApplicationBase.getIntance().getControllerNomina();
    }
}
