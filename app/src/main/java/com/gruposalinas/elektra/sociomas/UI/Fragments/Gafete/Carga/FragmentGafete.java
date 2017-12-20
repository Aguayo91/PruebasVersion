package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteObtenerRequest;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafete;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 08/08/2017.
 */

public class FragmentGafete extends Fragment implements CargaGafetePresenterImpl.CargaGafeteView {
    private ImageView imageCredencial,imageCarganado;
    private View rootView;
    private CargarGafetePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gafete, container, false);
        setPresenter();
        return rootView;
    }

    @Override
    public void initView() {
        this.imageCredencial=(ImageView)rootView.findViewById(R.id.imageCredencial);
        this.imageCarganado=(ImageView)rootView.findViewById(R.id.imageCarganado);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setPresenter() {
        presenter=new CargaGafetePresenterImpl();
        presenter.register(this);
        presenter.initCargaImagen();
    }

    @Override
    public void presentEvent(ViewEvent event) {

    }


    @Override
    public void setImagenGafete(String base64Imagen) {
        try {
            imageCredencial.setImageBitmap(Utils.decodeBase64(base64Imagen));
            this.imageCarganado.setVisibility(View.VISIBLE);
            imageCarganado.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void navegarCredencializacion() {
        Intent intent=new Intent(getActivity(), CrearGafeteActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }
}
