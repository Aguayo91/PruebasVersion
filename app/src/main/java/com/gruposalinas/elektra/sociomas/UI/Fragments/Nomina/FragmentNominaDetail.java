package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.TextViewMoney;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.CustomListeners.LiberaReciboListener;
import com.gruposalinas.elektra.sociomas.UI.Presenters.NominaDetailPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.NominaDetailPresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetalleCabecera;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetallePercepcionDeducciones;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;

import java.util.List;
import java.util.Objects;

/**
 * Created by gtoledo on 18/09/2017.
 */

public class FragmentNominaDetail extends FragmentBaseTab implements BaseView {

    public static final String TAG = FragmentNominaDetail.class.getSimpleName();
    private NominaDetailPresenter presenter;
    private View v;
    private CardView cvEmpresa;
    private CardView cvDatosEmpleado;
    private CardView cvDetallePago;
    private CardView cvPercepciones;
    private CardView cvDeducciones;
    private CardView cvCredito;
    private CardView cvImporte;

    private LinearLayout llEmpresa;
    private LinearLayout llDatosEmpleado;
    private LinearLayout llDetallePago;
    private LinearLayout llPercepciones;
    private LinearLayout llDeducciones;
    private LinearLayout llCredito;
    private LinearLayout llImporte;

    private TextView tvTitleEmpresa;
    private LinearLayout llCompania;
    private TextView tvTextoNoCompania;
    private TextView tvNoCompania;
    private LinearLayout llRegistroPatronal;
    private TextView tvTextoRegistroPatronal;
    private TextView tvRegistroPatronal;

    private TextView tvTitleEmpleado;
    private LinearLayout llNombre;
    private TextView tvTextoNombreEmpleado;
    private TextView tvNombreEmpleado;
    private LinearLayout llIMSS;
    private TextView tvTextoNoIMSS;
    private TextView tvNoIMSS;
    private LinearLayout llSaldoBase;
    private TextView tvTextoSaldoBaseCot;
    private TextView tvSaldoBaseCot;
    private LinearLayout llCentroCostos;
    private TextView tvTextCentroCosto;
    private TextView tvCentroCosto;
    private LinearLayout llTipoDeposito;
    private TextView tvTextoDepositoEn;
    private TextView tvDepositoEn;
    private LinearLayout llRFC;
    private TextView tvTextoRFC;
    private TextView tvRFC;

    private TextView tvTitleDetallePago;
    private LinearLayout llPeriodo;
    private TextView tvTextoPeriodo;
    private TextView tvPeriodo;
    private LinearLayout llDiasTrabajados;
    private TextView tvTextoDiasTrabajados;
    private TextView tvDiasTrabajados;
    private LinearLayout llFechaPago;
    private TextView tvTextoFechaPago;
    private TextView tvFechaPago;

    private TextView tvTitlePercepciones;
    private LinearLayout llListaPercepciones;

    private TextView tvTitleDeducciones;
    private LinearLayout llListaDeducciones;

    private TextView tvTitleCredito;
    private LinearLayout llListaCredito;
    private TextView tvTitleImporte;
    private TextViewMoney tvImporte;

    private TextView tvMensajesRecibo;
    private String transitionName;
    private LiberaReciboListener listenerLiberacion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_nomina_detail, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {

        cvEmpresa = (CardView) v.findViewById(R.id.cvEmpresa);
        cvDatosEmpleado = (CardView) v.findViewById(R.id.cvDatosEmpleado);
        cvDetallePago = (CardView) v.findViewById(R.id.cvDetallePago);
        cvPercepciones = (CardView) v.findViewById(R.id.cvPercepciones);
        cvDeducciones = (CardView) v.findViewById(R.id.cvDeducciones);
        cvCredito = (CardView) v.findViewById(R.id.cvCredito);
        cvImporte = (CardView) v.findViewById(R.id.cvImporte);

        llEmpresa = (LinearLayout) v.findViewById(R.id.llEmpresa);
        llDatosEmpleado = (LinearLayout) v.findViewById(R.id.llDatosEmpleado);
        llDetallePago = (LinearLayout) v.findViewById(R.id.llDetallePago);
        llPercepciones = (LinearLayout) v.findViewById(R.id.llPercepciones);
        llDeducciones = (LinearLayout) v.findViewById(R.id.llDeducciones);
        llCredito = (LinearLayout) v.findViewById(R.id.llCredito);
        llImporte = (LinearLayout) v.findViewById(R.id.llImporte);

        tvTitleEmpresa = (TextView) v.findViewById(R.id.tvTitleEmpresa);
        llCompania = (LinearLayout) v.findViewById(R.id.llCompania);
        tvTextoNoCompania = (TextView) v.findViewById(R.id.tvTextoNoCompania);
        tvNoCompania = (TextView) v.findViewById(R.id.tvNoCompania);
        llRegistroPatronal = (LinearLayout) v.findViewById(R.id.llRegistroPatronal);
        tvTextoRegistroPatronal = (TextView) v.findViewById(R.id.tvTextoRegistroPatronal);
        tvRegistroPatronal = (TextView) v.findViewById(R.id.tvRegistroPatronal);

        tvTitleEmpleado = (TextView) v.findViewById(R.id.tvTitleEmpleado);
        llNombre = (LinearLayout) v.findViewById(R.id.llNombre);
        tvTextoNombreEmpleado = (TextView) v.findViewById(R.id.tvTextoNombreEmpleado);
        tvNombreEmpleado = (TextView) v.findViewById(R.id.tvNombreEmpleado);
        llIMSS = (LinearLayout) v.findViewById(R.id.llIMSS);
        tvTextoNoIMSS = (TextView) v.findViewById(R.id.tvTextoNoIMSS);
        tvNoIMSS = (TextView) v.findViewById(R.id.tvNoIMSS);
        llSaldoBase = (LinearLayout) v.findViewById(R.id.llSaldoBase);
        tvTextoSaldoBaseCot = (TextView) v.findViewById(R.id.tvTextoSaldoBaseCot);
        tvSaldoBaseCot = (TextView) v.findViewById(R.id.tvSaldoBaseCot);
        llCentroCostos = (LinearLayout) v.findViewById(R.id.llCentroCostos);
        tvTextCentroCosto = (TextView) v.findViewById(R.id.tvTextCentroCosto);
        tvCentroCosto = (TextView) v.findViewById(R.id.tvCentroCosto);
        llTipoDeposito = (LinearLayout) v.findViewById(R.id.llTipoDeposito);
        tvTextoDepositoEn = (TextView) v.findViewById(R.id.tvTextoDepositoEn);
        tvDepositoEn = (TextView) v.findViewById(R.id.tvDepositoEn);
        llRFC = (LinearLayout) v.findViewById(R.id.llRFC);
        tvTextoRFC = (TextView) v.findViewById(R.id.tvTextoRFC);
        tvRFC = (TextView) v.findViewById(R.id.tvRFC);

        tvTitleDetallePago = (TextView) v.findViewById(R.id.tvTitleDetallePago);
        llPeriodo = (LinearLayout) v.findViewById(R.id.llPeriodo);
        tvTextoPeriodo = (TextView) v.findViewById(R.id.tvTextoPeriodo);
        tvPeriodo = (TextView) v.findViewById(R.id.tvPeriodo);
        llDiasTrabajados = (LinearLayout) v.findViewById(R.id.llDiasTrabajados);
        tvTextoDiasTrabajados = (TextView) v.findViewById(R.id.tvTextoDiasTrabajados);
        tvDiasTrabajados = (TextView) v.findViewById(R.id.tvDiasTrabajados);
        llFechaPago = (LinearLayout) v.findViewById(R.id.llFechaPago);
        tvTextoFechaPago = (TextView) v.findViewById(R.id.tvTextoFechaPago);
        tvFechaPago = (TextView) v.findViewById(R.id.tvFechaPago);

        tvTitlePercepciones = (TextView) v.findViewById(R.id.tvTitlePercepciones);
        llListaPercepciones = (LinearLayout) v.findViewById(R.id.llListaPercepciones);

        tvTitleDeducciones = (TextView) v.findViewById(R.id.tvTitleDeducciones);
        llListaDeducciones = (LinearLayout) v.findViewById(R.id.llListaDeducciones);

        tvTitleCredito = (TextView) v.findViewById(R.id.tvTitleCredito);
        llListaCredito = (LinearLayout) v.findViewById(R.id.llListaCredito);

        tvTitleImporte = (TextView) v.findViewById(R.id.tvTitleImporte);
        tvImporte = (TextViewMoney) v.findViewById(R.id.tvImporte);

        tvMensajesRecibo = (TextView) v.findViewById(R.id.tvMensajesRecibo);
        if (getArguments() != null) {
            if (getArguments().containsKey(ViewEvent.ENTRY)) {
                ReciboNomina reciboNomina = (ReciboNomina) getArguments().getSerializable(ViewEvent.ENTRY);
                presenter.getFullPaymentSheets(reciboNomina);
            } else {
                showMsgDialog(getActivityInstance(), "Aviso", getString(R.string.server_error), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                }, "ACEPTAR");
            }
        } else {
            showMsgDialog(getActivityInstance(), "Aviso", getString(R.string.server_error), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }, "ACEPTAR");
        }

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setPresenter() {
        presenter = new NominaDetailPresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case SHOW_ERROR_DIALOG_EVENT_TYPE: {
                String errorTitle = (String) event.getModel().get(ViewEvent.ERROR_TITLE);
                String errorMsg = (String) event.getModel().get(ViewEvent.ERROR_MSG);
                showMsgDialog(getActivityInstance(), errorTitle, errorMsg);
            }
            break;
            case SHOW_PROGRESS_EVENT_TYPE: {
                showProgressDialog(getActivityInstance());
            }
            break;
            case HIDE_PROGRESS_EVENT_TYPE: {
                hideProgressDialog();
            }
            break;
            case PRESENT_RESULTSET_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.llListaPercepciones: {
                        boolean showCV = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (showCV) {
                            cvPercepciones.setVisibility(View.VISIBLE);
                            List<NominaDetallePercepcionDeducciones> list = (List<NominaDetallePercepcionDeducciones>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                            llListaPercepciones.addView(generateListLayout(list));
                        } else {
                            cvPercepciones.setVisibility(View.GONE);
                        }
                    }
                    break;
                    case R.id.llListaDeducciones: {
                        boolean showCV = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (showCV) {
                            cvDeducciones.setVisibility(View.VISIBLE);
                            List<NominaDetallePercepcionDeducciones> list = (List<NominaDetallePercepcionDeducciones>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                            llListaDeducciones.addView(generateListLayout(list));
                        } else {
                            cvDeducciones.setVisibility(View.GONE);
                        }
                    }
                    break;
                    case R.id.llListaCredito: {
                        boolean showCV = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (showCV) {
                            cvCredito.setVisibility(View.VISIBLE);
                            List<NominaDetallePercepcionDeducciones> list = (List<NominaDetallePercepcionDeducciones>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                            llListaCredito.addView(generateListLayout(list));
                        } else {
                            cvCredito.setVisibility(View.GONE);
                        }
                    }
                    break;
                }
            }
            break;
            case PRESENT_OBJECT_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.cvDatosEmpleado: {
                        NominaDetalleCabecera cabecera = (NominaDetalleCabecera) event.getModel().get(ViewEvent.ENTRY);
                        setData(cabecera);
                    }
                    break;
                    case R.id.tvMensajesRecibo: {
                        String MsjsNomina = (String) event.getModel().get(ViewEvent.ENTRY);
                        tvMensajesRecibo.setText(MsjsNomina);
                    }
                    break;
                    case R.id.btnLiberar: {
                        String mensaje = (String) event.getModel().get(ViewEvent.ENTRY);
                        showMsgDialog(getActivityInstance(), "Aviso", mensaje, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                            }
                        }, "ACEPTAR");
                    }
                    break;
                }
            }
            break;
        }

    }

    public void setData(NominaDetalleCabecera data) {
        tvTitleEmpresa.setText(data.getNombreCompania().trim());
        tvNoCompania.setText(data.getNumCompania().trim());
        tvRegistroPatronal.setText(data.getRegsitroPatronal().trim());

        tvNombreEmpleado.setText(data.getNombreEmpleado().trim());
        tvNoIMSS.setText(data.getNumeroIMSS().trim());
        tvSaldoBaseCot.setText(Utils.formatCantidadPesos(Float.parseFloat(data.getSalarioBaseCotiza())));
        tvCentroCosto.setText(data.getCentroCostos().trim());
        tvDepositoEn.setText("MXN");
        tvRFC.setText(data.getRfcEmpleado().trim());

        tvPeriodo.setText(Utils.formatFechaNomina(data.getFechaIniPago().trim()) + " al " + Utils.formatFechaNomina(data.getFechaFinPago().trim()));
        tvFechaPago.setText(Utils.formatFechaNomina(data.getFechaPago().trim()));

        tvImporte.setAmount(data.getTotalFinal());
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

    public static FragmentNominaDetail getInstance(Bundle bundle) {
        FragmentNominaDetail fragment = new FragmentNominaDetail();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public LinearLayout generateListLayout(List<NominaDetallePercepcionDeducciones> list) {
        LinearLayout ll = new LinearLayout(getActivityInstance());
        ll.setOrientation(LinearLayout.VERTICAL);
        for (NominaDetallePercepcionDeducciones obj : list) {
            LinearLayout llHor = new LinearLayout(getActivityInstance());
            llHor.setOrientation(LinearLayout.HORIZONTAL);
            llHor.setWeightSum(2);
            TextView tv1 = new TextView(getActivityInstance());
            LinearLayout.LayoutParams llparams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            llparams1.weight = 1;
            llparams1.width = 0;
            llparams1.gravity = Gravity.LEFT;
            tv1.setLayoutParams(llparams1);
            tv1.setText(obj.getDescripcion().trim());
            tv1.setTextColor(getActivityInstance().getResources().getColor(R.color.gris_textos));
            tv1.setGravity(Gravity.LEFT);
            TextView tv2 = new TextView(getActivityInstance());
//            TextViewMoney tv2 = new TextViewMoney(getActivityInstance());
            LinearLayout.LayoutParams llparams2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            llparams2.weight = 1;
            llparams2.width = 0;
            llparams2.gravity = Gravity.RIGHT;
            tv2.setLayoutParams(llparams2);
            tv2.setText(Utils.formatCantidadPesos(Float.parseFloat(obj.getImporte())));
//            tv2.setAmount(Float.parseFloat(obj.getImporte()));
            tv2.setTextColor(getActivityInstance().getResources().getColor(R.color.gris_textos));
            tv2.setGravity(Gravity.RIGHT);
            llHor.addView(tv1);
            llHor.addView(tv2);
            ll.addView(llHor);
        }
        return ll;
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivityInstance()).unregisterReceiver(br);
    }

    BroadcastReceiver br;
    @Override
    public void onResume() {
        super.onResume();
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.liberarRecibo(getArguments());
            }
        };
        LocalBroadcastManager.getInstance(getActivityInstance())
                .registerReceiver(br, new IntentFilter(TAG));
    }

    public void setListenerLiberado(LiberaReciboListener listenerLiberado){
        this.listenerLiberacion = listenerLiberado;
    }
}
