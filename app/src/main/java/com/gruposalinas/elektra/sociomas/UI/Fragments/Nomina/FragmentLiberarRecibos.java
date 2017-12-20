package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaDetalleGeografiaActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaDetalleReciboActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Nomina.ItemDecorations.ItemDecorationLineDashed;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Nomina.RecyclerAdapterRecibosNomina;
import com.gruposalinas.elektra.sociomas.UI.Controls.TextViews.TextViewMoney;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.CustomListeners.LiberaReciboListener;
import com.gruposalinas.elektra.sociomas.UI.Presenters.ListaRecibosNominaPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.ListaRecibosNominaPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Nomina.ListaRecibosNominaResponse;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNominaDetalleResponse;

import java.io.Serializable;
import java.util.List;

import eu.davidea.flipview.FlipView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 07/09/17.
 */

public class FragmentLiberarRecibos extends FragmentBaseNomina implements BaseView, View.OnClickListener, LiberaReciboListener {
    public static final String TAG = FragmentLiberarRecibos.class.getSimpleName();
    private View v;
    private ListaRecibosNominaPresenter presenter;
    private CardView cardViewParent;
    private ScrollView scrollViewParent;
    private RelativeLayout rlParent;
    private LinearLayout llHeader;
    private TextView tvHeader1;
    private TextView tvHeader2;
    private TextView tvHeader3;
    private RecyclerView recyclerRecibos;
    private LinearLayout llFooterTotal;
    private TextView tvFooterTotal1;
    private TextView tvFooterTotal2;
    private TextViewMoney tvAmountTotal;
    private Button btnLiberar;
    private RecyclerAdapterRecibosNomina adapterRecibos;
    private List<ReciboNomina> list;
    private Bundle mSaved;
    private boolean reload;
    private SwipeRefreshLayout swipeRefreshRecibos;
    private FlipView fvTotalCheck;
//    private LinearLayout llFieldsText;
//    private TextInputLayout tilToken;
//    private EditText etToken;

    public static FragmentLiberarRecibos getInstance(Bundle args) {
        FragmentLiberarRecibos fragment = new FragmentLiberarRecibos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        reload = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_liberar_recibos, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        cardViewParent = (CardView) v.findViewById(R.id.cardViewParent);
        scrollViewParent = (ScrollView) v.findViewById(R.id.scrollViewParent);
        rlParent = (RelativeLayout) v.findViewById(R.id.rlParent);
        llHeader = (LinearLayout) v.findViewById(R.id.llHeader);
        recyclerRecibos = (RecyclerView) v.findViewById(R.id.recyclerRecibos);
        llFooterTotal = (LinearLayout) v.findViewById(R.id.llFooterTotal);
        tvFooterTotal1 = (TextView) v.findViewById(R.id.tvFooterTotal1);
        tvFooterTotal2 = (TextView) v.findViewById(R.id.tvFooterTotal2);
        tvAmountTotal = (TextViewMoney) v.findViewById(R.id.tvAmountTotal);
        btnLiberar = (Button) v.findViewById(R.id.btnLiberar);
        swipeRefreshRecibos = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshRecibos);
        fvTotalCheck = (FlipView) v.findViewById(R.id.fvTotalCheck);

//        llFieldsText = (LinearLayout) v.findViewById(R.id.llFieldsText);
//        tilToken = (TextInputLayout) v.findViewById(R.id.tilToken);
//        etToken = (EditText) v.findViewById(R.id.etToken);
        presenter.setData(getArguments());
//        presenter.getFullPaymentSheets(getArguments());
    }

    @Override
    public void setListeners() {
        btnLiberar.setOnClickListener(this);
        swipeRefreshRecibos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reloadRecibos(getArguments().getString(ViewEvent.ENTRY));
                swipeRefreshRecibos.setRefreshing(true);
            }
        });
        fvTotalCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fvTotalCheck.flip(!fvTotalCheck.isFlipped());
                adapterRecibos.checkedAllItems(!adapterRecibos.flipAllItems);
                if (adapterRecibos.flipAllItems) {
                    for (ReciboNomina rn : list) {
                        presenter.setRecibo(rn);
                    }
                } else {
                    for (ReciboNomina rn : list) {
                        presenter.removeRecibo(rn);
                    }
                }
                if (presenter.getRecibosInMap() > 0) {
                    btnLiberar.setBackgroundResource(R.drawable.shape_button_oval_yellow);
                    btnLiberar.setEnabled(true);
                } else {
                    btnLiberar.setBackgroundResource(R.drawable.curve_black);
                    btnLiberar.setEnabled(false);
                }
            }
        });
    }

    public void setRecyclerData(final List<ReciboNomina> list) {
        this.list = list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityInstance(), LinearLayoutManager.VERTICAL, false);
        recyclerRecibos.setLayoutManager(linearLayoutManager);
        adapterRecibos = new RecyclerAdapterRecibosNomina(getContext(), list, this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivityInstance(), DividerItemDecoration.VERTICAL);
        recyclerRecibos.addItemDecoration(itemDecoration);
        recyclerRecibos.setAdapter(adapterRecibos);
        adapterRecibos.setListenerLongClick(new RecyclerAdapterRecibosNomina.LongClickListener() {
            @Override
            public void setRecibo(ReciboNomina recibo) {
                presenter.setRecibo(recibo);
                if (presenter.getRecibosInMap() > 0) {
                    btnLiberar.setBackgroundResource(R.drawable.shape_button_oval_yellow);
                    btnLiberar.setEnabled(true);
                } else {
                    btnLiberar.setBackgroundResource(R.drawable.curve_black);
                    btnLiberar.setEnabled(false);
                }
                if (presenter.getRecibosInMap() == list.size()) {
                    fvTotalCheck.flip(true);
                    adapterRecibos.flipAllItems = true;
                } else {
                    fvTotalCheck.flip(false);
                    adapterRecibos.flipAllItems = false;
                }
            }

            @Override
            public void removeRecibo(ReciboNomina recibo) {
                presenter.removeRecibo(recibo);
                if (presenter.getRecibosInMap() > 0) {
                    btnLiberar.setBackgroundResource(R.drawable.shape_button_oval_yellow);
                    btnLiberar.setEnabled(true);
                } else {
                    btnLiberar.setBackgroundResource(R.drawable.curve_black);
                    btnLiberar.setEnabled(false);
                }
                if (presenter.getRecibosInMap() == list.size()) {
                    fvTotalCheck.flip(true);
                    adapterRecibos.flipAllItems = true;
                } else {
                    fvTotalCheck.flip(false);
                    adapterRecibos.flipAllItems = false;
                }
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
                final boolean sinRecibos;
                if (event.getModel().containsKey(ViewEvent.BOOLEAN_OBJECT)) {
                    sinRecibos = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                } else {
                    sinRecibos = true;
                }
                showMsgDialog(getActivityInstance(), errorTitle, errorMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!sinRecibos) {
                            ((NominaActivity) getActivityInstance()).finish();
                        } else {
                            swipeRefreshRecibos.setRefreshing(true);
                            presenter.reloadRecibos(getArguments().getString(ViewEvent.ENTRY));
                            if (presenter.getRecibosInMap() == list.size()) {
                                fvTotalCheck.flip(false);
                                adapterRecibos.flipAllItems = false;
                            } else {
                            }
                        }
                        if (presenter.getRecibosInMap() > 0) {
                            btnLiberar.setBackgroundResource(R.drawable.shape_button_oval_yellow);
                            btnLiberar.setEnabled(true);
                        } else {
                            btnLiberar.setBackgroundResource(R.drawable.curve_black);
                            btnLiberar.setEnabled(false);
                        }
                    }
                }, getActivityInstance().getString(R.string.aceptar));
                swipeRefreshRecibos.setRefreshing(false);
            }
            break;
            case SHOW_DIALOG_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.btnLiberar: {
                        String title = (String) event.getModel().get(ViewEvent.TITLE);
                        String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                        showMsgDialog(getActivityInstance(), title, msg);
                    }
                    break;
                }
            }
            break;
            case PRESENT_RESULTSET_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.recyclerRecibos: {
                        swipeRefreshRecibos.setRefreshing(false);
                        List<ReciboNomina> list = (List<ReciboNomina>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                        setRecyclerData(list);
                    }
                    break;
                }
            }
            break;
            case PRESENT_OBJECT_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.tvAmountTotal: {
                        String total = (String) event.getModel().get(ViewEvent.ENTRY);
                        boolean showControl = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                        if (showControl) {
                            tvAmountTotal.setAmount(Float.parseFloat(total), getString(R.string.symbolMoney));
                        } else {
                            llFooterTotal.setVisibility(View.GONE);
                        }
                    }
                    break;
                    case R.id.btnLiberar: {
                        String msg = (String) event.getModel().get(ViewEvent.ENTRY);
                        reload = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
//                        if (reload) {
//                            reload = false;
//                            presenter.reloadRecibos(getArguments().getString(ViewEvent.ENTRY));
//                        } else {
                            presenter.presentImageMsg(R.drawable.banner_nomina2, 2);
//                        }
//                        showMsgDialog(getActivityInstance(), "Aviso", msg, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                getActivityInstance().finish();
//
//                            }
//                        }, "Aceptar");
                    }
                    break;
                    case R.id.cardView: {
                        int resource = (int) event.getModel().get(ViewEvent.ENTRY);
                        int option = (int) event.getModel().get(ViewEvent.NEXT_VIEW);
                        Bundle bundle = new Bundle();
                        bundle.putInt(ViewEvent.RESOURCE_ID, resource);
                        bundle.putInt(ViewEvent.ENTRY, option);
                        FragmentDialogMessageNomina fragmentDialogMessageNomina = FragmentDialogMessageNomina.newInstance(bundle);
                        fragmentDialogMessageNomina.setCancelable(false);
                        fragmentDialogMessageNomina.show(getActivity().getSupportFragmentManager(), FragmentDialogMessageNomina.TAG);
                        fragmentDialogMessageNomina.setOnDismissListener(new FragmentDialogMessageNomina.MyDismissListener() {
                            @Override
                            public void onDismiss() {
                                if (reload) {
                                    reload = false;
                                    swipeRefreshRecibos.setRefreshing(true);
                                    presenter.reloadRecibos(getArguments().getString(ViewEvent.ENTRY));
                                } else {
                                    ((NominaActivity) getActivityInstance()).finish();
                                }
                            }
                        });
                    }
                    break;
                }
            }
            break;
        }

    }

    @Override
    public void setPresenter() {
        presenter = new ListaRecibosNominaPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnLiberar) {
//            if (etToken.getText().toString().trim().length() > 0) {
//                etToken.setError(null);
//                presenter.callServiceNomina(etToken.getText().toString());
//            } else {
//                etToken.setError("Agrega tu Firma Azteca");
//            }
            if (presenter.getMapRecibosSelected() > 0) {
                showDialogoFAM();
            } else {
                // no hay recibos seleccionados pero ya se muestra mensaje de que no se ha seleccionado
            }
        } else if (i == R.id.rlParentNominaReciboItem) {
            ReciboNomina reciboNomina = (ReciboNomina) v.getTag();
//            ReciboNominaDetalleResponse rndr = (ReciboNominaDetalleResponse)v.getTag();
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(ViewEvent.ENTRY, (Serializable) reciboNomina);
//            FragmentNominaDetail fragment = FragmentNominaDetail.getInstance(mBundle);
//            ((NominaActivity) getActivityInstance())
//                    .performTransaction(
//                            fragment,
//                            FragmentNominaDetail.TAG,
//                            R.id.container,
//                            true);
//            fragment.setListenerLiberado(this);
            Intent intent = null;
            if (reciboNomina.getBanderaSumatoria() != null) {
                intent = new Intent(getActivityInstance(), NominaDetalleGeografiaActivity.class);
            } else {
                intent = new Intent(getActivityInstance(), NominaDetalleReciboActivity.class);
            }
            intent.putExtras(mBundle);
            startActivityForResult(intent, NominaDetalleReciboActivity.RESULT_CODE_DETAIL);

        }
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
                    presenter.callServiceNomina(etToken.getText().toString());
                    alertDialog.dismiss();
                } else {
                    etToken.setError("Agrega tu Firma Azteca");
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case NominaDetalleReciboActivity.RESULT_CODE_DETAIL: {
                    boolean shouldReload = data.getBooleanExtra(ViewEvent.BOOLEAN_OBJECT, false);
                    if (shouldReload) {
                        Bundle mBundle = data.getExtras();
                        ReciboNomina reciboNomina = (ReciboNomina) mBundle.getSerializable(ViewEvent.ENTRY);
                        String numberAccount = getArguments().getString(ViewEvent.ENTRY);
                        presenter.reloadRecibos(numberAccount);
                    } else {
                        // Nothing to do
                    }
                }
                break;
            }
        }
    }

    BroadcastReceiver br;

    @Override
    public void reciboLiberado(ReciboNomina reciboNomina) {
        reload = true;
    }

}
