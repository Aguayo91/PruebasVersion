package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.SliderActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.MensajeBienvenidaPresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
/**
 * Created by GiioToledo on 13/11/17.
 */

public class FragmentMensajeBienvenida extends FragmentSlideBase implements BaseView, View.OnClickListener {

    public static final String TAG = FragmentMensajeBienvenida.class.getSimpleName();
    private MensajeBienvenidaPresenterImpl presenter;
    private View v;
    private TextView tvTitleMensaje;
    private TextView tvSubtitleMensaje;
    private Button btnContinuar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    public static FragmentMensajeBienvenida getInstance(@Nullable  Bundle args) {
        FragmentMensajeBienvenida fragment = new FragmentMensajeBienvenida();
        if(args!=null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_mensaje_bienvenida, container, false);
        presenter.register(this);
       // presenter.seleccionSaludo(getActivity());
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView() {
        tvTitleMensaje = (TextView) v.findViewById(R.id.tvTitleMensaje);
        tvSubtitleMensaje = (TextView) v.findViewById(R.id.tvSubtitleMensaje);
        btnContinuar = (Button) v.findViewById(R.id.btnContinuar);
        tvTitleMensaje.setText(getText(R.string.mensajeBienvenidaHorario));
    }

    @Override
    public void setListeners() {
        btnContinuar.setOnClickListener(this);

    }

    @Override
    public void setPresenter() {
        presenter = new MensajeBienvenidaPresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE:{
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.tvTitleMensaje: {
                        String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                        //tvTitleMensaje.setText(msg);
                    }
                    break;
                }
            }
            break;
        }
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnContinuar) {
            navegarFragment(FragmentFotoPerfil.getInstance(null),FragmentFotoPerfil.TAG,true);
        }
    }
}
