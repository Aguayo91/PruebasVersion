package com.gruposalinas.elektra.sociomas.UI.Fragments.Legal;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Legal.LegalActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.TerminosLegalesPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.TerminosLegalesPresenter;
import com.sociomas.core.DataBaseModel.Legal;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Privacidad.DatoLegalRespose;

import java.util.List;

/**
 * Created by oemy9 on 07/12/2017.
 */

public class FragmentTerminosLegales extends FragmentBaseLegal implements BaseView{
    public static final String TAG = "FragmentTerminosLegales";
    private WebView webViewPrivacidad;
    private TextView tvComplemento;
    private TextView tvBienvenida;
    private TerminosLegalesPresenter presenter;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView=inflater.inflate(R.layout.fragment_terminos_legales,container,false);
        presenter.register(this);
        return  rootView;
    }

    public static FragmentTerminosLegales getInstance(Bundle bundle){
        FragmentTerminosLegales ft=new FragmentTerminosLegales();
        if(bundle!=null){
            ft.setArguments(bundle);
        }
        return ft;
    }

    private void loadData(){
        if(getArguments().containsKey(ViewEvent.ENTRY)){
            LegalActivity lgtAct=(LegalActivity)getActivity();
            String currentTipo=getArguments().getString(ViewEvent.ENTRY);
            EnumTiposAviso aviso=EnumTiposAviso.fromString(currentTipo);
            lgtAct.changeToolBarText(aviso.toString());
            DBUtils dbUtils=new DBUtils(getContext());
            List<Legal>legals=dbUtils.getListLegal();
            if(legals!=null && (!legals.isEmpty())){
                Legal l=legals.get(0);
                if (aviso==EnumTiposAviso.POLITICAS_PRIVACIDAD) {
                    tvComplemento.setVisibility(View.VISIBLE);
                    tvComplemento.setText(getString(R.string.privacidad));
                    //tvBienvenida.setText(EnumTiposAviso.POLITICAS_PRIVACIDAD.getValue());
                    tvBienvenida.setText(EnumTiposAviso.POLITICAS_PRIVACIDAD.toString());
                } else if (aviso==EnumTiposAviso.TERMINOS_CONDICIONES){
                    tvComplemento.setText("Aplicación Móvil Socio MAS");
                    //tvBienvenida.setText(EnumTiposAviso.TERMINOS_CONDICIONES.getValue());
                    tvBienvenida.setText(EnumTiposAviso.TERMINOS_CONDICIONES.toString());
                }
                webViewPrivacidad.loadData(Utils.getFormatoWebView(aviso==EnumTiposAviso.POLITICAS_PRIVACIDAD
                        ?l.getPivacidad(): l.getTerminos()),"text/html; charset=utf-8", "UTF-8" );
                final CustomProgressBar customProgressBar=new CustomProgressBar(getContext());
                customProgressBar.show(getActivity());
                webViewPrivacidad.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        customProgressBar.dismiss();
                    }
                });
            } else {
                presenter.consultarTerminos(aviso);
            }
        }
    }

    @Override
    public void initView() {
        webViewPrivacidad=(WebView)rootView.findViewById(R.id.webViewPrivacidad);
        tvComplemento = (TextView) rootView.findViewById(R.id.tvComplemento);
        tvBienvenida = (TextView) rootView.findViewById(R.id.tvBienvenida);
        loadData();
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setPresenter() {
        presenter = new TerminosLegalesPresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.webViewPrivacidad: {
                        DatoLegalRespose datoLegalRespose = (DatoLegalRespose) event.getModel().get(ViewEvent.ENTRY);
                        if (datoLegalRespose.getId_tipo_aviso()==EnumTiposAviso.POLITICAS_PRIVACIDAD.getValue()) {
                            tvComplemento.setVisibility(View.VISIBLE);
                            tvComplemento.setText(datoLegalRespose.getSubtitulo());
                            tvBienvenida.setVisibility(View.GONE);
//                            tvBienvenida.setText(datoLegalRespose.getTitulo());
                        } else if (datoLegalRespose.getId_tipo_aviso()==EnumTiposAviso.TERMINOS_CONDICIONES.getValue()){
                            tvComplemento.setText(datoLegalRespose.getSubtitulo());
                            tvBienvenida.setText(datoLegalRespose.getTitulo());
                            tvBienvenida.setVisibility(View.VISIBLE);
                        }
                        webViewPrivacidad.loadData(Utils.getFormatoWebView(datoLegalRespose.getLeyenda()),"text/html; charset=utf-8", "UTF-8" );
                        webViewPrivacidad.setWebViewClient(new WebViewClient(){
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                            }
                        });
                    }
                    break;
                }
            }
            break;
            case SHOW_ERROR_DIALOG_EVENT_TYPE: {
                String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                showMsgDialog(getActivityInstance(),
                        "Aviso",
                        msg,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((LegalActivity)getActivityInstance()).getSupportFragmentManager().popBackStackImmediate();
                            }
                        },
                "Acepter");
            }
            break;
        }
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }
}
