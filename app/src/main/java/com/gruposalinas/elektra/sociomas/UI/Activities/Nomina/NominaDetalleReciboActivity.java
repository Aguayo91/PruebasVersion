package com.gruposalinas.elektra.sociomas.UI.Activities.Nomina;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.TextViewMoney;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.CustomListeners.LiberaReciboListener;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.FragmentNominaDetail;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.PasswordTransformationCustom;
import com.gruposalinas.elektra.sociomas.UI.Presenters.NominaDetalleReciboPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.NominaDetalleReciboPresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetalleCabecera;
import com.sociomas.core.WebService.Model.Response.Nomina.NominaDetallePercepcionDeducciones;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;

import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 18/10/2017.
 */

public class NominaDetalleReciboActivity extends BaseAppCompactActivity implements BaseView {

    public static final String TAG = NominaDetalleReciboActivity.class.getSimpleName();
    public static final int RESULT_CODE_DETAIL = 1001;
    private NominaDetalleReciboPresenter presenter;
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
    private ReciboNomina reciboNomina;

    FragmentManager mFm;
    FragmentTransaction mFt;
    private MenuItem itemLiberar;
    //    private EditText etToken;
    private ScrollView scrollParent;
    private Button liberarRecibo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomina_recibo_detalle);
        setToolBar(getString(R.string.ToolbarTitleNomina));
        setPresenter();
        presenter.register(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(com.sociomas.core.R.drawable.flecha_cabeza);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public void initView() {
        scrollParent = (ScrollView) findViewById(R.id.scrollParent);
        cvEmpresa = (CardView) findViewById(R.id.cvEmpresa);
        cvDatosEmpleado = (CardView) findViewById(R.id.cvDatosEmpleado);
        cvDetallePago = (CardView) findViewById(R.id.cvDetallePago);
        cvPercepciones = (CardView) findViewById(R.id.cvPercepciones);
        cvDeducciones = (CardView) findViewById(R.id.cvDeducciones);
        cvCredito = (CardView) findViewById(R.id.cvCredito);
        cvImporte = (CardView) findViewById(R.id.cvImporte);

        llEmpresa = (LinearLayout) findViewById(R.id.llEmpresa);
        llDatosEmpleado = (LinearLayout) findViewById(R.id.llDatosEmpleado);
        llDetallePago = (LinearLayout) findViewById(R.id.llDetallePago);
        llPercepciones = (LinearLayout) findViewById(R.id.llPercepciones);
        llDeducciones = (LinearLayout) findViewById(R.id.llDeducciones);
        llCredito = (LinearLayout) findViewById(R.id.llCredito);
        llImporte = (LinearLayout) findViewById(R.id.llImporte);

        tvTitleEmpresa = (TextView) findViewById(R.id.tvTitleEmpresa);
        llCompania = (LinearLayout) findViewById(R.id.llCompania);
        tvTextoNoCompania = (TextView) findViewById(R.id.tvTextoNoCompania);
        tvNoCompania = (TextView) findViewById(R.id.tvNoCompania);
        llRegistroPatronal = (LinearLayout) findViewById(R.id.llRegistroPatronal);
        tvTextoRegistroPatronal = (TextView) findViewById(R.id.tvTextoRegistroPatronal);
        tvRegistroPatronal = (TextView) findViewById(R.id.tvRegistroPatronal);

        tvTitleEmpleado = (TextView) findViewById(R.id.tvTitleEmpleado);
        llNombre = (LinearLayout) findViewById(R.id.llNombre);
        tvTextoNombreEmpleado = (TextView) findViewById(R.id.tvTextoNombreEmpleado);
        tvNombreEmpleado = (TextView) findViewById(R.id.tvNombreEmpleado);
        llIMSS = (LinearLayout) findViewById(R.id.llIMSS);
        tvTextoNoIMSS = (TextView) findViewById(R.id.tvTextoNoIMSS);
        tvNoIMSS = (TextView) findViewById(R.id.tvNoIMSS);
        llSaldoBase = (LinearLayout) findViewById(R.id.llSaldoBase);
        tvTextoSaldoBaseCot = (TextView) findViewById(R.id.tvTextoSaldoBaseCot);
        tvSaldoBaseCot = (TextView) findViewById(R.id.tvSaldoBaseCot);
        llCentroCostos = (LinearLayout) findViewById(R.id.llCentroCostos);
        tvTextCentroCosto = (TextView) findViewById(R.id.tvTextCentroCosto);
        tvCentroCosto = (TextView) findViewById(R.id.tvCentroCosto);
        llTipoDeposito = (LinearLayout) findViewById(R.id.llTipoDeposito);
        tvTextoDepositoEn = (TextView) findViewById(R.id.tvTextoDepositoEn);
        tvDepositoEn = (TextView) findViewById(R.id.tvDepositoEn);
        llRFC = (LinearLayout) findViewById(R.id.llRFC);
        tvTextoRFC = (TextView) findViewById(R.id.tvTextoRFC);
        tvRFC = (TextView) findViewById(R.id.tvRFC);

        tvTitleDetallePago = (TextView) findViewById(R.id.tvTitleDetallePago);
        llPeriodo = (LinearLayout) findViewById(R.id.llPeriodo);
        tvTextoPeriodo = (TextView) findViewById(R.id.tvTextoPeriodo);
        tvPeriodo = (TextView) findViewById(R.id.tvPeriodo);
        llDiasTrabajados = (LinearLayout) findViewById(R.id.llDiasTrabajados);
        tvTextoDiasTrabajados = (TextView) findViewById(R.id.tvTextoDiasTrabajados);
        tvDiasTrabajados = (TextView) findViewById(R.id.tvDiasTrabajados);
        llFechaPago = (LinearLayout) findViewById(R.id.llFechaPago);
        tvTextoFechaPago = (TextView) findViewById(R.id.tvTextoFechaPago);
        tvFechaPago = (TextView) findViewById(R.id.tvFechaPago);

        tvTitlePercepciones = (TextView) findViewById(R.id.tvTitlePercepciones);
        llListaPercepciones = (LinearLayout) findViewById(R.id.llListaPercepciones);

        tvTitleDeducciones = (TextView) findViewById(R.id.tvTitleDeducciones);
        llListaDeducciones = (LinearLayout) findViewById(R.id.llListaDeducciones);

        tvTitleCredito = (TextView) findViewById(R.id.tvTitleCredito);
        llListaCredito = (LinearLayout) findViewById(R.id.llListaCredito);

        tvTitleImporte = (TextView) findViewById(R.id.tvTitleImporte);
        tvImporte = (TextViewMoney) findViewById(R.id.tvImporte);

        tvMensajesRecibo = (TextView) findViewById(R.id.tvMensajesRecibo);
//        etToken = (EditText) findViewById(R.id.etToken);
        liberarRecibo = (Button) findViewById(R.id.liberarRecibo);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ViewEvent.ENTRY)) {
                reciboNomina = (ReciboNomina) getIntent().getExtras().getSerializable(ViewEvent.ENTRY);
                if (reciboNomina.getBanderaSumatoria() != null) {
                    presenter.getFullPaymentSheetsGeografia(getActivityInstance(), reciboNomina);
                } else {
                    presenter.getFullPaymentSheets(reciboNomina);
                }
            } else {
                showMsgDialog(getActivityInstance(), "Aviso", getString(R.string.server_error), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                        Intent data = new Intent();
                        data.putExtra(ViewEvent.BOOLEAN_OBJECT, false);
                        setResult(RESULT_CODE_DETAIL, data);
                        finish();
                    }
                }, "ACEPTAR");
            }
        } else {
            showMsgDialog(getActivityInstance(), "Aviso", getString(R.string.server_error), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                    Intent data = new Intent();
                    data.putExtra(ViewEvent.BOOLEAN_OBJECT, false);
                    setResult(RESULT_CODE_DETAIL, data);
                    finish();
                }
            }, "ACEPTAR");
        }
    }

    @Override
    public void setListeners() {
        liberarRecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogoFAM();
            }
        });
    }

    public void showDialogoFAM() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityInstance());
        LayoutInflater li = (LayoutInflater) getActivityInstance().getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewFAM = li.inflate(R.layout.alertdialog_tokenfam, null);
        final EditText etToken = (EditText) viewFAM.findViewById(R.id.etToken);
        etToken.setTransformationMethod(new PasswordTransformationCustom(1));
        Button btnAceptar = (Button) viewFAM.findViewById(R.id.btnAceptar);
        builder.setView(viewFAM);
        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etToken.getText().toString().trim().length() > 0) {
                    etToken.setError(null);
                    final Bundle bundle = getIntent().getExtras();
                    bundle.putString("VALIDATION", etToken.getText().toString());
                    presenter.liberarRecibo(bundle);
                    alertDialog.dismiss();
                } else {
                    etToken.setError("Agrega tu Firma Azteca");
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case SHOW_ERROR_DIALOG_EVENT_TYPE: {
                String errorTitle = (String) event.getModel().get(ViewEvent.ERROR_TITLE);
                String errorMsg = (String) event.getModel().get(ViewEvent.ERROR_MSG);
                showMsgDialog(getActivityInstance(), errorTitle, errorMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, "ACEPTAR");
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
//                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                                Intent data = new Intent();
                                data.putExtra(ViewEvent.BOOLEAN_OBJECT, true);
                                Bundle mBundle = new Bundle();
                                mBundle.putSerializable(ViewEvent.ENTRY, reciboNomina);
                                data.putExtras(mBundle);
                                setResult(RESULT_CODE_DETAIL, data);
                                finish();
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
        String compania = data.getNombreCompania();
        if(TextUtils.isEmpty(compania)){ tvTitleEmpresa.setVisibility(View.INVISIBLE); }else {
            tvTitleEmpresa.setText((compania != null) ? compania.trim() : String.valueOf(compania));
        }
        String numCompania = data.getNumCompania();
        if (TextUtils.isEmpty(numCompania)) {tvNoCompania.setVisibility(View.INVISIBLE);} else {
            tvNoCompania.setText((numCompania != null) ? numCompania.trim() : String.valueOf(compania));
        }
        String registroPatronal = data.getRegsitroPatronal();
        if (TextUtils.isEmpty(registroPatronal)) { tvRegistroPatronal.setVisibility(View.INVISIBLE);} else {
            tvRegistroPatronal.setText((registroPatronal != null) ? registroPatronal.trim() : String.valueOf(registroPatronal));
        }
        String nombreEmpleado = data.getNombreEmpleado();
        if (TextUtils.isEmpty(nombreEmpleado)){tvNombreEmpleado.setVisibility(View.INVISIBLE);} else {
            tvNombreEmpleado.setText((nombreEmpleado != null) ? nombreEmpleado.trim() : String.valueOf(nombreEmpleado));
        }
        String numIMSS = data.getNumeroIMSS();
        if (TextUtils.isEmpty(numIMSS)){tvNoIMSS.setVisibility(View.INVISIBLE);} else {
            tvNoIMSS.setText((numIMSS != null) ? numIMSS.trim() : String.valueOf(numIMSS));
        }

        tvSaldoBaseCot.setText(Utils.formatCantidadPesos(Float.parseFloat(Utils.splitCantidadNomina(data.getSalarioBaseCotiza()))));
        String centroCostos = data.getCentroCostos();
        tvCentroCosto.setText((centroCostos != null) ? centroCostos.trim() : String.valueOf(centroCostos));
        tvDepositoEn.setText("MXN");
        String rfcEmpleado =data.getRfcEmpleado();
        if (TextUtils.isEmpty(rfcEmpleado)) {tvRFC.setVisibility(View.INVISIBLE);} else {
            tvRFC.setText((rfcEmpleado != null) ? rfcEmpleado.trim() : String.valueOf(rfcEmpleado));
        }

        tvPeriodo.setText(Utils.formatFechaNomina(data.getFechaIniPago().trim()) + " al " + Utils.formatFechaNomina(data.getFechaFinPago().trim()));
//        tvPeriodo.setText(data.getFechaIniPago().trim() + " al " + data.getFechaFinPago().trim());
        tvFechaPago.setText(Utils.formatFechaNomina(data.getFechaPago().trim()));
//        tvFechaPago.setText(data.getFechaPago().trim());

        tvImporte.setAmount(String.valueOf(Utils.splitCantidadNomina(data.getTotalFinal())));
    }

    public LinearLayout generateListLayout(List<NominaDetallePercepcionDeducciones> list) {
        LinearLayout ll = new LinearLayout(getActivityInstance());
        ll.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < list.size(); i++) {
            NominaDetallePercepcionDeducciones obj = list.get(i);
            LinearLayout llHor = new LinearLayout(getActivityInstance());
            llHor.setOrientation(LinearLayout.HORIZONTAL);
            llHor.setMinimumHeight(Utils.convertDipToPixel(30, getActivityInstance()));
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
            tv2.setText(Utils.formatCantidadPesos(Float.parseFloat(Utils.splitCantidadNomina(obj.getImporte()))));
//            tv2.setAmount(Float.parseFloat(obj.getImporte()));
            tv2.setTextColor(getActivityInstance().getResources().getColor(R.color.gris_textos));
            tv2.setGravity(Gravity.RIGHT);
            llHor.addView(tv1);
            llHor.addView(tv2);

            ll.addView(llHor);
            if (i != list.size() - 1) {
                View viewSeparador = new View(getActivityInstance());
                ViewGroup.LayoutParams lpSeparador = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.convertDipToPixel(1, getActivityInstance()));
                viewSeparador.setBackgroundColor(getResources().getColor(R.color.gris_claro));
                viewSeparador.setLayoutParams(lpSeparador);
                ll.addView(viewSeparador);
            }
        }
        return ll;
    }

    @Override
    public void setPresenter() {
        presenter = new NominaDetalleReciboPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }
}
