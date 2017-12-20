package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.AventonesReservadosActivity;
import com.sociomas.aventones.UI.Activities.Carros.CarsActivity;
import com.sociomas.aventones.UI.Activities.Encuentra.EncuentraTuAventonActivity;
import com.sociomas.aventones.UI.Activities.MisAventones.AventonesPublicados;

/**
 * Created by jmarquezu on 12/10/2017.
 */

public class DialogMenuAventones extends Dialog implements View.OnClickListener {

    private TextView tvPublica,tvEncuentra,tvReservados;
    private Toolbar toolbar;

    public DialogMenuAventones(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu_aventones);

        tvPublica = (TextView)findViewById(R.id.tvPublica);
        tvEncuentra = (TextView)findViewById(R.id.tvEncuentra);
        tvReservados = (TextView)findViewById(R.id.tvReservados);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        tvPublica.setOnClickListener(this);
        tvEncuentra.setOnClickListener(this);
        tvReservados.setOnClickListener(this);

        //toolbar.setTitle("Selecciona una Opción");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==tvPublica.getId()){
            Intent intent = new Intent(getContext(), AventonesPublicados.class);
            getContext().startActivity (intent);
        }
        else if(v.getId()==tvEncuentra.getId()){
            Intent intent = new Intent(getContext(), EncuentraTuAventonActivity.class);
            getContext().startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(),AventonesReservadosActivity.class);
            getContext().startActivity(intent);
        }
    }
}
