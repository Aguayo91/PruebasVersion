package com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Modificar;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Codigo.Verificar.VerificarSmsActivity;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Request.Codigo.SolicitarCodigoRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarTelActivity extends BaseAppCompactActivity implements View.OnClickListener, Callback<ServerResponse> {
    public static final String TAG="RESPUESTA";
    private Button btnConfirmar;
    private MaterialTextField txtNumeroTel;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_tel);
        txtNumeroTel=(MaterialTextField)findViewById(R.id.txtNumeroTel);
        btnConfirmar=(Button)findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(this);
        manager=new SessionManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtNumeroTel.setHasFocus(true);
            }
        },200);
    }

    @Override
    public void onClick(View view) {
        if(txtNumeroTel.getEditText().getText().toString().length()<10){
            txtNumeroTel.getEditText().setError(getString(R.string.revisa_numero_valido));
        }
        else{
            SolicitarCodigoRequest request=new SolicitarCodigoRequest();
            request.seNumerotelefono(manager.getString(Constants.MY_PHONE));
            request.setNuevoTelefono(txtNumeroTel.getEditText().getText().toString());
            customProgressBar.show(this);
            controllerAPI.solicitarCodigo(request).enqueue(this);
        }
    }




    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            if(response.isSuccessful()){
                if(!response.body().getError()){
                    startActivity(new Intent(this,VerificarSmsActivity.class));
                    finish();
                }
                else{
                    alertaAsync.displayMensaje(response.body().getMensajeError(),this);
                }
            }
            else{
                alertaAsync.displayMensaje(getString(R.string.Error_Conexion),this);
            }
        customProgressBar.dismiss();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        alertaAsync.displayMensaje(getString(R.string.Error_Conexion),this);
        customProgressBar.dismiss();
    }
}
