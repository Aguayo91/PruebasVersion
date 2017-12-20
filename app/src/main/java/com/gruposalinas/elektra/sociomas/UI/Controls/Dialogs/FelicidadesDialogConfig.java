package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivityV2;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Utils;

/**
 * Created by jr441 on 18/11/2017.
 */

public class FelicidadesDialogConfig extends Dialog implements View.OnClickListener{

    private TextView nombre;
    private Button siguiente;
    private ImageView cerrar;
    private Activity act;


    public FelicidadesDialogConfig(@NonNull Context context) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_felicidades);
        setDialogParametros();
        nombre = (TextView)findViewById(R.id.tvFelicidades);
        siguiente = (Button)findViewById(R.id.boton_continuar);
        cerrar = (ImageView)findViewById(R.id.img_cerrar);
        siguiente.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        loadNombreUsuario();
    }

    private void loadNombreUsuario(){
        String nombreEmpleado=Utils.getJustNombreEmpleado(getContext());
        nombre.setText("¡Felicidades "+ nombreEmpleado+"!");
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), InicioActivityV2.class);
        switch (v.getId()) {
            case (R.id.boton_continuar):
                navegarNextActivity();
                break;
            case (R.id.img_cerrar):
                navegarNextActivity();
                break;
        }
    }

    private void navegarNextActivity(){
        Intent i = new Intent(getContext(), InicioActivityV2.class);
        if(act!=null){
            act.finish();
        }
        getContext().startActivity(i);
    }
    private void setDialogParametros(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity=Gravity.FILL;
        getWindow().setAttributes(lp);
    }
}
