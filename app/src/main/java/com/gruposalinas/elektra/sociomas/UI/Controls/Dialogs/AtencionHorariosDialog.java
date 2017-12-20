package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Jornada.IniciarJornadaActivity;
import com.sociomas.core.MVP.ViewEvent;


/**
 * Created by jmarquezu on 15/11/2017.
 */

public class AtencionHorariosDialog extends Dialog {

    private Button aceptar,cancelar;
    private FragmentTransaction ft;
    private Activity activity;

    public AtencionHorariosDialog(@NonNull Context context, FragmentTransaction fragmentTransaction,Activity activity) {
        super(context);this.ft=fragmentTransaction; this.activity=activity;
    }

    public AtencionHorariosDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AtencionHorariosDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_advertencia_horarios_dialog_v2);

        aceptar = (Button)findViewById(R.id.btnAceptar);
        cancelar =  (Button)findViewById(R.id.btnCancelar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent i = new Intent(getContext().getApplicationContext(),IniciarJornadaActivity.class);
                i.putExtra(ViewEvent.BOOLEAN_OBJECT,true);
                getContext().startActivity(i);
                activity.finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
