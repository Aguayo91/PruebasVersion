package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentUnidadNegocio;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentUnidadNegocioSeleccion;
import com.gruposalinas.elektra.sociomas.UI.Presenters.ConsultaExpresPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.ConsultaExpresPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Nomina.ReciboNomina;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 05/09/17.
 */

public class FragmentConsultaTarjeta extends FragmentBaseNomina implements View.OnClickListener, BaseView {

    public static final String TAG = FragmentConsultaTarjeta.class.getSimpleName();

    private View v;
    private CardView cardViewParent;
    private RelativeLayout rlParent;
    private TextView tvTitle;
    private TextView tvSubtitle;

    private LinearLayout llControlesTarjeta;

    private EditText etNumberAccount;
//    private LinearLayout llControlesToken;
//    private RelativeLayout rlNIP;
//    private ImageView ivNIP;
//    private TextView tvNIP;
//    private RelativeLayout rlToken;

    //    private TextView tvToken;
//    private RelativeLayout rlHuella;
//    private ImageView ivHuella;
//    private TextView tvHuella;
    //    private Button btnBackSelIdentify;
    private Button btnConsultar;
    private ConsultaExpresPresenter presenter;

    private int optionSecurity = 0;
    private ScrollView scrollParent;

    public static FragmentConsultaTarjeta getInstance(Bundle args) {
        FragmentConsultaTarjeta fragment = new FragmentConsultaTarjeta();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_consulta_tarjeta, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void initView() {
        cardViewParent = (CardView) v.findViewById(R.id.cardViewParent);
        scrollParent = (ScrollView) v.findViewById(R.id.scrollParent);
        rlParent = (RelativeLayout) v.findViewById(R.id.rlParent);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvSubtitle = (TextView) v.findViewById(R.id.tvSubtitle);

        llControlesTarjeta = (LinearLayout) v.findViewById(R.id.llControlesTarjeta);

        etNumberAccount = (EditText) v.findViewById(R.id.etNumberAccount);
        btnConsultar = (Button) v.findViewById(R.id.btnConsultar);
        setFontCompat();
    }


    private void setFontCompat() {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

        btnConsultar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        etNumberAccount.setSelection(0);
        etNumberAccount.setTransformationMethod(new PasswordTransformationCustom());
        presenter.presentImageMsg(R.drawable.banner_nomina1, 1);
    }

    @Override
    public void setListeners() {
        btnConsultar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnConsultar) {
            if (etNumberAccount.getText().toString().trim().length() > 0) {
                etNumberAccount.setError(null);
                presenter.callQueryExpress(etNumberAccount.getText().toString().trim());
//                ((NominaActivity) getActivityInstance())
//                        .performTransaction(FragmentUnidadNegocio.getInstance(null),
//                                FragmentUnidadNegocio.TAG,
//                                R.id.container, true);
            } else {
                etNumberAccount.setError("Ingresa número de cuenta.");
                return;
            }
        }

    }

    @Override
    public void setPresenter() {
        presenter = new ConsultaExpresPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_RESULTSET_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                if (idR == R.id.btnConsultar) {
                    int errorCode = (int) event.getModel().get(ViewEvent.ERROR_CODE);
                    if (errorCode == 0) {
                        List<ReciboNomina> reciboNominas = (List<ReciboNomina>) event.getModel().get(ViewEvent.ENTRIES_LIST);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ViewEvent.ENTRIES_LIST, (Serializable) reciboNominas);
                        bundle.putString(ViewEvent.ENTRY, etNumberAccount.getText().toString().trim());
                        ((NominaActivity) getActivityInstance())
                                .performTransaction(
                                        FragmentLiberarRecibos.getInstance(bundle),
                                        FragmentLiberarRecibos.TAG,
                                        R.id.container,
                                        true);
                    } else {
                        showMsgDialog(getActivityInstance(), "Aviso", getString(R.string.server_error));
                    }
                }
            }
            break;
            case SHOW_TOAST_MESSAGE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                if (idR == R.id.cardViewParent) {

                }
            }
            break;
            case SHOW_ERROR_DIALOG_EVENT_TYPE: {
                String errorTitle = (String) event.getModel().get(ViewEvent.ERROR_TITLE);
                String errorMsg = (String) event.getModel().get(ViewEvent.ERROR_MSG);
                showMsgDialog(getActivityInstance(), errorTitle, errorMsg);
            }
            break;
            case PRESENT_OBJECT_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.etNumberAccount: {
                        String numberAccount = (String) event.getModel().get(ViewEvent.ENTRY);
                        etNumberAccount.setText(numberAccount);
                    }
                    break;
                    case R.id.civLogo: {
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
                                presenter.loadNumberAccount();
                            }
                        });


                    }
                    break;
                }
            }
        }
    }

    public  void TimerProceso() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0);
    }


}
