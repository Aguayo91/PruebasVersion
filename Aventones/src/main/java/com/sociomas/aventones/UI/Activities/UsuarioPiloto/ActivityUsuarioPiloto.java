package com.sociomas.aventones.UI.Activities.UsuarioPiloto;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.sociomas.aventones.R;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

public class ActivityUsuarioPiloto extends BaseCoreCompactActivity  implements UsuarioPilotoPresenterImpl.UsuarioPilotoView, View.OnClickListener {

    private MaterialTextField txtNumeroEmpleado;
    private Button btnConfirmar;
    private UsuarioPilotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_piloto);
        setToolBar(getString(R.string.usuario_piloto));
        setPresenter();
    }

    @Override
    public void initView() {
        txtNumeroEmpleado=(MaterialTextField)findViewById(R.id.txtNumeroEmpleado);
        btnConfirmar=(Button)findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(this);
    }

    @Override
    public void setListeners() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtNumeroEmpleado.setHasFocus(true);
            }
        },200);

    }

    @Override
    public void setPresenter() {
        presenter=new UsuarioPilotoPresenterImpl();
        presenter.register(this);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void onClick(View v) {
        presenter.agregarUsuario(txtNumeroEmpleado.getEditText().getText().toString());
    }

    @Override
    public void navegarBack() {
        onBackPressed();
    }
}
