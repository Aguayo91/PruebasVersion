package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.PrivacidadPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.PrivacidadPresenter;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Privacidad.Aviso;

/**
 * Created by jmarquezu on 14/11/2017.
 */

public class FragmentPrivacidad extends FragmentSlideBase implements PrivacidadPresenterImpl.PrivacidadView, View.OnClickListener {

    public static final String TAG = "FragmentPrivacidad";
    private WebView webView;
    private PrivacidadPresenter presenter;
    private CheckBox checkBox;
    private Button btnSiguiente;
    private TextView tvTitulo,tvComplemento,tvLeyenda,tvAcuerdo;
    private WebView webViewPrivacidad;
    private View rootView;

    public static FragmentPrivacidad getInstance(@Nullable  Bundle args){
        FragmentPrivacidad fragmentPrivacidad=new FragmentPrivacidad();
        if(args!=null){
            fragmentPrivacidad.setArguments(args);
        }
        return fragmentPrivacidad;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_privacidad,container,false);
        setPresenter();
        return rootView;
    }
    @Override
    public void initView() {
        btnSiguiente = (Button) rootView.findViewById(R.id.btnSiguiente);
        checkBox =  (CheckBox) rootView.findViewById(R.id.cbxAceptar);
        tvTitulo = (TextView) rootView.findViewById(R.id.tvBienvenida);
        tvAcuerdo=(TextView)rootView.findViewById(R.id.tvAcuerdo);
        webViewPrivacidad=(WebView) rootView.findViewById(R.id.webViewPrivacidad);
        tvComplemento = (TextView) rootView.findViewById(R.id.tvComplemento);
        btnSiguiente.setOnClickListener(this);
    }

    @Override
    public void setListeners() {

    }
    @Override
    public void setPresenter() {
        presenter=new PrivacidadPresenterImpl();
        presenter.register(this);
        presenter.obtenerAvisos(EnumTiposAviso.POLITICAS_PRIVACIDAD);
    }
    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

    @Override
    public void mostrarAviso(Aviso aviso, EnumTiposAviso tiposAviso) {
            tvTitulo.setText(aviso.getTitulo());
            tvComplemento.setText(aviso.getSubtitulo());
            tvAcuerdo.setText(tiposAviso==EnumTiposAviso.POLITICAS_PRIVACIDAD?R.string.he_leido_privacidad : R.string.he_leido_terminos);
            webViewPrivacidad.loadData(Utils.getFormatoWebView(aviso.getLeyenda()),"text/html; charset=utf-8", "UTF-8" );
            webViewPrivacidad.reload();
    }

    @Override
    public void inHabilitarBotonNext() {
        btnSiguiente.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.gris_claro));
        btnSiguiente.setEnabled(false);
    }

    @Override
    public void navegarNext() {
        if(getActivity()!=null) {
            if (!getActivity().isFinishing()) {
                navegarFragment(FragmentMensajeBienvenida.getInstance(null), FragmentMensajeBienvenida.TAG, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSiguiente:
                    presenter.aceptarAvisos(checkBox.isChecked());
                break;
        }
    }
}
