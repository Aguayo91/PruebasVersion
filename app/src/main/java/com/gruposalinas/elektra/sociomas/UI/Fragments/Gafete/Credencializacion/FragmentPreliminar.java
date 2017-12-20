package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.Alertas;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.StringUtils;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumTiposArchivo;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Gafete.ArchivoAdjunto;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteCrearRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 21/08/2017.
 */

public class FragmentPreliminar extends Fragment implements View.OnClickListener {
    public static final String TAG="FRAGMENT_PREELIMINAR";
    private Button btnTomarNueva,btnEnviar;
    private ImageView imageFoto;
    private TextView tvNombre;
    private TextView tvApellidos;
    private TextView tvId;
    private TextView tvRotarTexto;
    private ImageView imgRotar;
    private SessionManager manager;
    private String base64Imagen;
    private Bitmap bitmapImagen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gafete_preeliminar, container, false);
        imageFoto=(ImageView)rootView.findViewById(R.id.imgFoto);
        tvNombre=(TextView)rootView.findViewById(R.id.tvNombre);
        tvApellidos=(TextView)rootView.findViewById(R.id.tvApellidos);
        tvId=(TextView)rootView.findViewById(R.id.tvId);
        btnTomarNueva=(Button)rootView.findViewById(R.id.btnTomarNueva);
        btnEnviar=(Button)rootView.findViewById(R.id.btnEnviar);
        tvRotarTexto=(TextView)rootView.findViewById(R.id.tvRotarTexto);
        imgRotar=(ImageView)rootView.findViewById(R.id.imgRotar);
        btnTomarNueva.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);
        tvRotarTexto.setOnClickListener(this);
        imgRotar.setOnClickListener(this);
        manager=new SessionManager(getContext());
        loadFoto();
        return  rootView;
    }


    private void loadDatosPersonales(){
        StringUtils utils=new StringUtils();
        if(manager.getString(Constants.SP_NAME)!=null) {
            List<String> nombreList = utils.getNombreSeparadoInternal(Utils.toTitleCase(manager.getString(Constants.SP_NAME)));
            tvId.setText(manager.getString(Constants.SP_ID));
            if (!nombreList.isEmpty() && (nombreList.size() >= 1)) {
                tvNombre.setText(nombreList.get(0));
                tvApellidos.setText(nombreList.get(1));
            }
        }
    }

    public static FragmentPreliminar newInstance(int sectionNumber) {
        FragmentPreliminar fragment = new FragmentPreliminar();
        return fragment;
    }

    private void rotarImagen(){
        if(bitmapImagen!=null){
            bitmapImagen=Utils.rotateBitmap(bitmapImagen,90);
            base64Imagen=Utils.encodeToBase64(bitmapImagen, Bitmap.CompressFormat.JPEG,90);
            setImagenToView(bitmapImagen);
        }
    }

    public void loadFoto(){
        loadDatosPersonales();
        base64Imagen=manager.getString(Constants.USUARIO_FOTO_FRONTAL);
        if(base64Imagen!=null && (!base64Imagen.isEmpty())){
            bitmapImagen= Utils.decodeBase64(manager.getString(Constants.USUARIO_FOTO_FRONTAL));
            if(bitmapImagen!=null){
                manager.add(Constants.USUARIO_FOTO_FRONTAL,"");
                setImagenToView(bitmapImagen);
            }
        }
    }
    private void setImagenToView(Bitmap bitmapImagen){
        imageFoto.setImageBitmap(bitmapImagen);
        imageFoto.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_in));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTomarNueva:
                ((CrearGafeteActivity)getActivity()).navegarFragmento(new FragmentSurfaceGafete(),FragmentSurfaceGafete.TAG,true);
                break;
            case R.id.btnEnviar:
                enviarAsyncArchivo();
                break;
            case R.id.tvRotarTexto:
            case R.id.imgRotar:
                rotarImagen();
                break;
        }
    }

    private void enviarAsyncArchivo(){
        final Alertas alertAsync=new Alertas(getContext());
        final CustomProgressBar customProgressBar=new CustomProgressBar(getContext());
        customProgressBar.show(getActivity());
        ArchivoAdjunto archivoAdjunto=new ArchivoAdjunto("foto.jpg",base64Imagen,"jpg");
        ArrayList<ArchivoAdjunto>lisArchivos=new ArrayList<>();
        lisArchivos.add(archivoAdjunto);
        GafeteCrearRequest request=new GafeteCrearRequest();
        request.setTipoArchivo(EnumTiposArchivo.FOTO.getValue());
        request.setArchivos(lisArchivos);
        ApplicationBase.getIntance().getControllerAPI().enviarArchivo(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        Intent intent=new Intent(getContext(), GafeteActivity.class);
                        intent.putExtra(Constants.GENERAR_CREDENCIAL,true);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                    else{
                        if(isAdded()) {
                            alertAsync.displayMensaje(response.body().getMensajeError(), getContext());
                        }
                    }
                    if(isAdded()) {
                        customProgressBar.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if(isAdded()) {
                    alertAsync.displayMensaje(getString(R.string.Error_Conexion), getContext());
                }
            }
        });
    }

}
