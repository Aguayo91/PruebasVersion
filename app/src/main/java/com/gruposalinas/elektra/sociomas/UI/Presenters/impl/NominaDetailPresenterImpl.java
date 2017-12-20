package com.gruposalinas.elektra.sociomas.UI.Presenters.impl;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Presenters.NominaDetailPresenter;
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
import com.sociomas.core.WebService.Model.Response.Nomina.ConcentradoNominaRespose;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaRecibosNominaResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetalleMensajes;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetallePercepcionDeducciones;
import com.sociomas.core.WebService.Model.Response.Nomina.PaymentSheetsResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtoledo on 18/09/2017.
 */

public class NominaDetailPresenterImpl extends BasePresenterImpl implements NominaDetailPresenter {
    private static final String TAG = NominaDetailPresenterImpl.class.getSimpleName();
    private ReciboNomina reciboNomina;

    @Override
    public void presentData(ReciboNominaDetalleResponse recibo) {
//        ReciboNominaDetalleResponse recibo = (ReciboNominaDetalleResponse) arguments.getSerializable(ViewEvent.ENTRIES_LIST);
        List<NominaDetallePercepcionDeducciones> percepcionDeducciones = recibo.getPercepcionDeduccions();
        List<NominaDetallePercepcionDeducciones> percepciones = new ArrayList<>();
        List<NominaDetallePercepcionDeducciones> deducciones = new ArrayList<>();
        List<NominaDetallePercepcionDeducciones> credito = new ArrayList<>();
        for (NominaDetallePercepcionDeducciones objeto : percepcionDeducciones) {
            if (objeto.getConcepto().contentEquals("PER")) {
                percepciones.add(objeto);
            } else if (objeto.getConcepto().contentEquals("DED")) {
                deducciones.add(objeto);
            } else if (objeto.getConcepto().contentEquals("CRE")) {
                credito.add(objeto);
            } else {
                // TODO OTHER KIND OF DATA
            }
        }
        ViewEvent eventPercepcion = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
        eventPercepcion.getModel().put(ViewEvent.RESOURCE_ID, R.id.llListaPercepciones);
        eventPercepcion.getModel().put(ViewEvent.ENTRIES_LIST, percepciones);
        eventPercepcion.getModel().put(ViewEvent.BOOLEAN_OBJECT, (percepciones.size() == 0) ? false : true);
        notifyData(eventPercepcion);

        ViewEvent eventDeduccion = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
        eventDeduccion.getModel().put(ViewEvent.RESOURCE_ID, R.id.llListaDeducciones);
        eventDeduccion.getModel().put(ViewEvent.ENTRIES_LIST, deducciones);
        eventDeduccion.getModel().put(ViewEvent.BOOLEAN_OBJECT, (deducciones.size() == 0) ? false : true);
        notifyData(eventDeduccion);

        ViewEvent eventCredito = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
        eventCredito.getModel().put(ViewEvent.RESOURCE_ID, R.id.llListaCredito);
        eventCredito.getModel().put(ViewEvent.ENTRIES_LIST, credito);
        eventCredito.getModel().put(ViewEvent.BOOLEAN_OBJECT, (credito.size() == 0) ? false : true);
        notifyData(eventCredito);

        ViewEvent eventData = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        eventData.getModel().put(ViewEvent.ENTRY, recibo.getCabecera());
        eventData.getModel().put(ViewEvent.BOOLEAN_OBJECT, (recibo.getCabecera() == null) ? false : true);
        eventData.getModel().put(ViewEvent.RESOURCE_ID, R.id.cvDatosEmpleado);
        notifyData(eventData);

        String msjsConcat = "";
        for (NominaDetalleMensajes ndm : recibo.getMensajes()) {
            msjsConcat += ndm.getMensaje().trim() + "\n";
        }
        ViewEvent eventMsjNomina = new ViewEvent(ViewEventType.PRESENT_OBJECT_EVENT_TYPE);
        eventMsjNomina.getModel().put(ViewEvent.RESOURCE_ID, R.id.tvMensajesRecibo);
        eventMsjNomina.getModel().put(ViewEvent.ENTRY, msjsConcat);
        notifyData(eventMsjNomina);

    }

    @Override
    public void liberarRecibo(Bundle bundle) {
        ReciboNomina reciboNomina = (ReciboNomina) bundle.getSerializable(ViewEvent.ENTRY);
        ReciboNominaDetalleResponse recibo = (ReciboNominaDetalleResponse) bundle.getSerializable(ViewEvent.ENTRIES_LIST);
        ViewEvent eventProgres = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgres);
        SecurityItems securityItems = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        List<NominaJsonStringOption4Request> listLiberacion = new ArrayList<>();
        NominaJsonStringOption4Request obj = new NominaJsonStringOption4Request();
        obj.setLlave(reciboNomina.getLlave());
        obj.setNum_empleado(reciboNomina.getBeneficiario());
        obj.setNum_cuenta_empleado(reciboNomina.getCuentaAbono());
        obj.setDispositivo_empleado("");
        obj.setIp_cliente(ApplicationBase.IPDeviceAddress);
        obj.setSistema(DeviceUtils.getSistema());
        obj.setVia_liberacion(Constants.IDSistema); //Falta que lo proporcione servicios
        obj.setId_usuario(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        obj.setPais(ConstantsV2.PAIS);
        listLiberacion.add(obj);
        PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion("4");
        request.setId_num_empleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setJsonString(securityItems.getRequestNominaEncrypt(gson.toJson(listLiberacion)));
        getControllerNomina().ejecutaLiberarNomina(request).enqueue(nominaEjecucion);
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

    @Override
    public void getFullPaymentSheets(ReciboNomina reciboNomina) {
        ViewEvent eventProgress = new ViewEvent(ViewEventType.SHOW_PROGRESS_EVENT_TYPE);
        notifyData(eventProgress);
       this.reciboNomina = reciboNomina;
        List<NominaJsonStringOption2Request> llavesList = new ArrayList<>();

        SecurityItems si = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
        Gson gson = new Gson();
        final PaymenSheetsRequest request = new PaymenSheetsRequest();
        request.setAccion(EnumNomina.RECIBOS_DETALLE.get());
        request.setId_num_empleado(si.getIdEmployEncrypt());
        request.setToken(si.getTokenEncrypt());
//        for (ReciboNomina rn : nominaList) {
            NominaJsonStringOption2Request option2Request = new NominaJsonStringOption2Request();
            option2Request.setLlave(reciboNomina.getLlave());
            llavesList.add(option2Request);
//        }
        request.setJsonString(si.getRequestNominaEncrypt(gson.toJson(llavesList)));

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
                                notifyData(eventErr);
                                ViewEvent eventHide = new ViewEvent(ViewEventType.HIDE_PROGRESS_EVENT_TYPE);
                                notifyData(eventHide);
                                return;
                            }
//                            SecurityItems securityItems1 = new SecurityItems(Utils.getNumeroEmpleado(getView().getActivityInstance()));
//                            String decryptStrJson = securityItems1.getTextDecrypt(response.body().getStrJson());
                            String decryptStrJson = DecryptUtils.decryptAES(response.body().getStrJson());
                            Gson gson = new Gson();
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
//                                            int index = Collections.binarySearch(rec, reciboNomina, new ListaRecibosNominaPresenterImpl.FooComparator());
//                                            l.setReciboParent(nominaList.get(index));
                                        }
                                        recibosCopy.add(l);
                                    }
                                    concentradoNominaRespose.setRecibos(recibosCopy);
                                    //TODO implement notify data from paysheets
                                    //region ParseRECIBOS
//                                    ViewEvent eventResultset = new ViewEvent(ViewEventType.PRESENT_RESULTSET_EVENT_TYPE);
//                                    eventResultset.getModel().put(ViewEvent.ENTRIES_LIST, );
//                                    eventResultset.getModel().put(ViewEvent.RESOURCE_ID, R.id.recyclerRecibos);
//                                    notifyData(eventResultset);
                                    presentData(concentradoNominaRespose.getRecibos().get(0).getListaRecibos().get(0));

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
                                    notifyData(eventErr);
                                }
                            } else {
                                ViewEvent eventErr = new ViewEvent(ViewEventType.SHOW_ERROR_DIALOG_EVENT_TYPE);
                                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                                eventErr.getModel().put(ViewEvent.ERROR_MSG, "No tienes recibos pendientes de liberara");
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
                    Log.e(TAG, e.toString());
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
                eventErr.getModel().put(ViewEvent.ERROR_TITLE, "Aviso");
                try {
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, getView().getActivityInstance().getString(R.string.server_error));
                } catch (Exception e) {
                    e.printStackTrace();
                    eventErr.getModel().put(ViewEvent.ERROR_MSG, "Ha ocurrido un problema al realizar la petición. Intenta mas tarde.");

                }
                notifyData(eventErr);
            }
        });
    }
}
