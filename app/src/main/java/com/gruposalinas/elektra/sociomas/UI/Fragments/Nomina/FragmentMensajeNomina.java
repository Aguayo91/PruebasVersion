package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.CircleImageView;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Presenters.MensajeNominaPresenter;
import com.gruposalinas.elektra.sociomas.UI.Presenters.impl.MensajeNominaPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 05/09/17.
 */

public class FragmentMensajeNomina extends FragmentBaseTab implements View.OnClickListener , BaseView {

    public static final String TAG = FragmentMensajeNomina.class.getSimpleName();
    private View v;
    private CardView cardViewParent;
    private ScrollView scrollViewParent;
    private RelativeLayout rlParent;
    private TextView tvTitle;
    private CircleImageView ivProfile;
    private TextView tvMessage;
    private TextView tvAuthorMessage;
    private Button btnContinue;
    private MensajeNominaPresenter presenter;

    public static FragmentMensajeNomina getInstance(Bundle args) {
        FragmentMensajeNomina fragment = new FragmentMensajeNomina();
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
        v = inflater.inflate(R.layout.fragment_mensaje_nomina, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        cardViewParent = (CardView) v.findViewById(R.id.cardViewParent);
        scrollViewParent = (ScrollView) v.findViewById(R.id.scrollViewParent);
        rlParent = (RelativeLayout) v.findViewById(R.id.rlParent);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        ivProfile = (CircleImageView) v.findViewById(R.id.ivProfile);
        tvMessage = (TextView) v.findViewById(R.id.tvMessage);
        tvAuthorMessage = (TextView) v.findViewById(R.id.tvAuthorMessage);
        btnContinue = (Button) v.findViewById(R.id.btnContinue);
        setFontCompat();
    }

    private void setFontCompat() {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        tvAuthorMessage.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        btnContinue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

    }

    @Override
    public void setListeners() {
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue: {
                // TODO implementation button continue
                ((NominaActivity) getActivityInstance())
                         .performTransaction(
                                FragmentLiberarRecibos.getInstance(getArguments()),
                                FragmentLiberarRecibos.TAG,
                                R.id.container,
                                true);
            }
            break;
        }
    }

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
//                ConfiguracionResponse configuracionResponse = (ConfiguracionResponse) event.getModel().get(ViewEvent.ENTRY);
            }
            break;
            case SHOW_PROGRESS_EVENT_TYPE: {
                customProgressBar.show(getActivity());
            }
            break;
            case HIDE_PROGRESS_EVENT_TYPE: {
                customProgressBar.dismiss();
            }
            break;
        }
    }

    @Override
    public void setPresenter() {
        presenter = new MensajeNominaPresenterImpl();
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

}
