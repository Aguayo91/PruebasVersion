package com.gruposalinas.elektra.sociomas.UI.Activities.Nomina;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.TextViewMoney;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.PasswordTransformationCustom;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.NominaDetalleGeografiaPresenterImpl;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.Nomina.GeografiaDetalleCabecera;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;

import static com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaDetalleReciboActivity.RESULT_CODE_DETAIL;

/**
 * Created by GiioToledo on 23/11/17.
 */

public class NominaDetalleGeografiaActivity extends BaseAppCompactActivity implements BaseView {

    public static final String TAG = NominaDetalleGeografiaActivity.class.getSimpleName();

    FragmentManager mFm;
    FragmentTransaction mFt;
    private MenuItem itemLiberar;
    private NominaDetalleGeografiaPresenterImpl presenter;
    private TextView tvTotalIngresos;
    private TextView tvTotalDescuentos;
    private TextViewMoney tvImporteTotal;
    private ReciboNomina reciboNomina;
    private Button liberarRecibo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomina_detail_geography);
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
        super.initView();
        tvTotalIngresos = (TextView) findViewById(R.id.tvTotalIngresos);
        tvTotalDescuentos = (TextView) findViewById(R.id.tvTotalDescuentos);
        tvImporteTotal = (TextViewMoney) findViewById(R.id.tvImporteTotal);
        liberarRecibo = (Button) findViewById(R.id.liberarRecibo);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ViewEvent.ENTRY)) {
                reciboNomina = (ReciboNomina) getIntent().getExtras().getSerializable(ViewEvent.ENTRY);
                presenter.getFullPaymentSheetsGeografia(getActivityInstance(), reciboNomina);
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
        liberarRecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogoFAM();
            }
        });
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case SHOW_ERROR_DIALOG_EVENT_TYPE: {
                String errorTitle = (String) event.getModel().get(ViewEvent.ERROR_TITLE);
                String errorMsg = (String) event.getModel().get(ViewEvent.ERROR_MSG);
                showMsgDialog(getActivityInstance(), errorTitle, errorMsg);
            }
            break;
            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.rlParent: {
                        GeografiaDetalleCabecera concentrado = (GeografiaDetalleCabecera) event.getModel().get(ViewEvent.ENTRY);
                        String totalIngresos = concentrado.getTotalPercepciones();
                        String totalDeducciones = concentrado.getTotalDeducciones();
                        String totalImporte = concentrado.getTotalFinal();
                        tvTotalIngresos.setText(Utils.formatCantidadPesos(Float.parseFloat(totalIngresos)));
                        tvTotalDescuentos.setText(Utils.formatCantidadPesos(Float.parseFloat(totalDeducciones)));
                        tvImporteTotal.setAmount(totalImporte);
                    }
                    break;
                    case R.id.liberarRecibo: {
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

    @Override
    public void setPresenter() {
        super.setPresenter();
        presenter = new NominaDetalleGeografiaPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nomina_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mFm = getSupportFragmentManager();
        switch (id) {
            case android.R.id.home: {
                if (mFm.getBackStackEntryCount() > 0) {
                    mFm.popBackStackImmediate();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
}
