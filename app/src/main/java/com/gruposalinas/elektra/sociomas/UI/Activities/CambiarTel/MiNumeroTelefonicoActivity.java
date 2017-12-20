package com.gruposalinas.elektra.sociomas.UI.Activities.CambiarTel;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.EditText;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogRegistrandoTelefono;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

public class MiNumeroTelefonicoActivity extends BaseCoreCompactActivity implements MiNumeroTelefonicoPresenterImpl.viewMinumeroTel {

    private MiNumeroTelefonicoPresenter presenter;
    private EditText etNumero;
    private DialogRegistrandoTelefono dialogTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_numero_telefonico);
        setToolBar(getString(R.string.confGeneral));
        etNumero=(EditText)findViewById(R.id.etTel);
        setPresenter();
    }

    @Override
    public void setPresenter() {
        presenter=new MiNumeroTelefonicoPresenterImpl();
        presenter.register(this);
        presenter.consultarNumeroTel();
    }

    public void Omitir(View v){
        onBackPressed();
    }

    public void Registrar(View v){
            Utils.ocultarTeclado(this);
            presenter.validarDatos(etNumero.getText().toString());
    }

    @Override
    public void showDialog() {
        dialogTelefono = new DialogRegistrandoTelefono(this);
        dialogTelefono.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTelefono.show();
    }

    @Override
    public void hideDialog() {
        if(dialogTelefono!=null){
            dialogTelefono.dismiss();
        }
    }

    @Override
    public void validarNumero() {
            etNumero.setError(getString(R.string.ErrorNumero));
            etNumero.requestFocus();
    }

    @Override
    public void setMessageDialog(@StringRes int res) {
        if(dialogTelefono!=null){
            dialogTelefono.initMessage(res);
        }
    }

    @Override
    public void finishResponse() {
        finish();
    }

    @Override
    public void setNumeroTelfono(String numeroTelfono) {
        etNumero.setText(numeroTelfono);
    }

}
