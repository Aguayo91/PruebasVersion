package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl.UnidadNegocioPresenterImpl;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.UnidadNegocioPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.aventones.Utils.AnimationUtils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;

/**
 * Created by GiioToledo on 13/11/17.
 */

public class FragmentUnidadNegocio extends FragmentSlideBase implements BaseView, View.OnClickListener {
    public static final String TAG = FragmentUnidadNegocio.class.getSimpleName();
    private View v;
    private Button btnAceptar;
    private Button btnOtraUnidad;
    private TextView tvTitleMensaje;
    private UnidadNegocioPresenter presenter;
    private ImageView ivUnidadNegocio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static FragmentUnidadNegocio getInstance(@Nullable Bundle args) {
        FragmentUnidadNegocio fragmentUnidadNegocio = new FragmentUnidadNegocio();
        if (args != null) {
            fragmentUnidadNegocio.setArguments(args);
        }
        return fragmentUnidadNegocio;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_unidad_negocio, container, false);
        setPresenter();
        return v;
    }

    @Override
    public void initView() {
        ivUnidadNegocio = (ImageView) v.findViewById(R.id.ivUnidadNegocio);
        tvTitleMensaje = (TextView) v.findViewById(R.id.tvTitleMensaje);
        btnAceptar = (Button) v.findViewById(R.id.btnAceptar);
        btnOtraUnidad = (Button) v.findViewById(R.id.btnOtraUnidad);
    }

    @Override
    public void setListeners() {
        btnAceptar.setOnClickListener(this);
        btnOtraUnidad.setOnClickListener(this);
        if (ApplicationBase.getIntance().getUnidadNegocioResponse() == null) {
            presenter.consultaUnidadNegocio(getActivityInstance());
        } else {
            presenter.consultaUnidadNegocio(getActivityInstance());
        }
    }


    @Override
    public void setPresenter() {
        presenter = new UnidadNegocioPresenterImpl();
        presenter.register(this);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE: {
                int idR = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (idR) {
                    case R.id.ivUnidadNegocio: {
                        ApplicationBase.getIntance().setUnidadNegocioResponse((UnidadNegocioResponse) event.getModel().get(ViewEvent.ENTRY));
                        setData(ApplicationBase.getIntance().getUnidadNegocioResponse());
                    }
                    break;
                }
            }
            break;
            case ERROR_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.ivUnidadNegocio: {
                        ivUnidadNegocio.setImageResource(R.drawable.logo_gs_white);
                    }
                    break;
                }
            }
            break;
        }
    }

    private void setData(UnidadNegocioResponse unidadNegocioResponse) {
        ivUnidadNegocio.setAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        ivUnidadNegocio.setImageBitmap(Utils.convertBase64ToBitmap(unidadNegocioResponse.getLogoBase64()));
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                Bundle bundle = new Bundle();
                bundle.putSerializable(ViewEvent.ENTRY, ApplicationBase.getIntance().getUnidadNegocioResponse());
                navegarFragment(FragmentLugarTrabajo.getInstance(bundle), FragmentLugarTrabajo.TAG, true);
                break;
            case R.id.btnOtraUnidad:
                navegarFragment(FragmentUnidadNegocioSeleccion.getInstance(null), FragmentUnidadNegocioSeleccion.TAG, true);
                break;
        }
    }
}
