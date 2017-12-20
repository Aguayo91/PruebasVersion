package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto.GafeteActivityProto;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafetePreviewActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.StringUtils;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Controls.Notification.CustomProgressBar;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumTiposArchivo;
import com.sociomas.core.Utils.Manager.SessionManager;

import com.gruposalinas.elektra.sociomas.UI.Controls.CircleImageView;
import com.sociomas.core.WebService.Model.Request.Gafete.ArchivoAdjunto;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteCrearRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.SlideInicio.UnidadNegocioResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GiioToledo on 01/12/17.
 */

public class FragmentPreliminarGafete extends FragmentBaseTab implements BaseView, View.OnClickListener {

    public static final String TAG = FragmentPreliminarGafete.class.getSimpleName();

    private View v;
    private PreliminarGafetePresenterImpl presenter;
    private CircleImageView imgAvatar;
    private FloatingActionButton btnAceptar;
    private FloatingActionButton btnCancelar;
    private SessionManager manager;
    private TextView tvNombre;
    private TextView tvApellidos;
    private TextView tvSocio;
    private TextView tvPuesto;
    private String imgBase64;
    private ImageView imgGrupo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.item_dialog_credencializacion, container, false);
        presenter.register(this);
        return v;
    }

    @Override
    public void initView() {
        imgAvatar = (CircleImageView) v.findViewById(R.id.imgAvatar);
        btnAceptar = (FloatingActionButton) v.findViewById(R.id.btnAceptar);
        btnCancelar = (FloatingActionButton) v.findViewById(R.id.btnCancelar);
        tvNombre = (TextView) v.findViewById(R.id.tvNombre);
        tvApellidos = (TextView) v.findViewById(R.id.tvApellidos);
        tvSocio = (TextView) v.findViewById(R.id.tvSocio);
        tvPuesto = (TextView) v.findViewById(R.id.tvPuesto);
        imgGrupo = (ImageView) v.findViewById(R.id.imgGrupo);
        manager=new SessionManager(getContext());
    }

    @Override
    public void setListeners() {
        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        imgBase64 = manager.getString(Constants.USUARIO_FOTO_FRONTAL);
        imgAvatar.setImageBitmap(Utils.convertBase64ToBitmap(imgBase64));

        StringUtils stringUtils=new StringUtils();
        List<String> nombreSeparado=stringUtils.getNombreSeparadoInternal(com.sociomas.core.Utils.Utils.getCurrentEmpleado(getActivityInstance()).getName());
        if(nombreSeparado!=null && !nombreSeparado.isEmpty()) {
            tvNombre.setText(nombreSeparado.get(0));
            tvApellidos.setText(nombreSeparado.get(1));
        }
        tvSocio.setText(getString(R.string.socio).concat(com.sociomas.core.Utils.Utils.getNumeroEmpleado(getContext())));
        if (manager.getString(Constants.PUESTO_EMPLEADO) != null) {
            tvPuesto.setText(manager.getString(Constants.PUESTO_EMPLEADO));
        } else {
            tvPuesto.setVisibility(View.GONE);
        }
        presenter.loadUnidadNegocio(getActivityInstance());
    }

    @Override
    public void setPresenter() {
        presenter = new PreliminarGafetePresenterImpl();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        switch (event.getEventType()) {
            case PRESENT_OBJECT_EVENT_TYPE:{
                int id = (int)event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.imgGrupo: {
                        imgGrupo.setVisibility(View.VISIBLE);
                        UnidadNegocioResponse unr  = (UnidadNegocioResponse) event.getModel().get(ViewEvent.ENTRY);
                        imgGrupo.setImageBitmap(com.sociomas.core.Utils.Utils.decodeBase64(unr.getLogoBase64()));

                    }
                    break;
                }
            }
            break;
            case ERROR_EVENT_TYPE: {
                int id = (int)event.getModel().get(ViewEvent.RESOURCE_ID);
                switch (id) {
                    case R.id.imgGrupo :{
                        imgGrupo.setVisibility(View.GONE);
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
        int id = v.getId();
        if (id == R.id.btnAceptar) {
            enviarAsyncArchivo();
        } else if(id == R.id.btnCancelar) {
            ((CrearGafeteActivity)getActivityInstance()).getSupportFragmentManager().popBackStackImmediate();
        }
    }
    private void enviarAsyncArchivo(){
        final Alertas alertAsync=new Alertas(getContext());
        final CustomProgressBar customProgressBar=new CustomProgressBar(getContext());
        customProgressBar.show(getActivity());
        ArchivoAdjunto archivoAdjunto=new ArchivoAdjunto("foto.jpg",imgBase64,"jpg");
        ArrayList<ArchivoAdjunto> lisArchivos=new ArrayList<>();
        lisArchivos.add(archivoAdjunto);
        GafeteCrearRequest request=new GafeteCrearRequest();
        request.setTipoArchivo(EnumTiposArchivo.FOTO.getValue());
        request.setArchivos(lisArchivos);
        ApplicationBase.getIntance().getControllerAPI().enviarArchivo(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(getActivity()!=null) {
                            Intent intent = new Intent(getActivity(), GafeteActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                    else{
                        if(isAdded()) {
                            alertAsync.displayMensaje(response.body().getMensajeError(), getContext());
                        }
                    }
//                    if(isAdded()) {
//                        customProgressBar.dismiss();
//                    }
                }
                if(isAdded()) {
                    customProgressBar.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if(isAdded()) {
                    customProgressBar.dismiss();
                    alertAsync.displayMensaje(getString(R.string.Error_Conexion), getContext());
                }
            }
        });
    }
}
