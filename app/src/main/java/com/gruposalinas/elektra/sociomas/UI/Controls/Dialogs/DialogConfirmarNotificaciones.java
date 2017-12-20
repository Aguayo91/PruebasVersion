package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogAceptarCancelar;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by jr441 on 27/11/2017.
 */

public class DialogConfirmarNotificaciones extends Dialog implements View.OnClickListener {

    private Button btnCancelar, btnAceptar;
    private OnClickLDialogConfirmarListener listener;
    public DialogConfirmarNotificaciones(@NonNull Context context) {
        super(context);
    }

    public DialogConfirmarNotificaciones(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogConfirmarNotificaciones(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_confirmar);

        btnCancelar =  (Button)findViewById(R.id.btnCancelar);
        btnAceptar =  (Button)findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAceptar:
                listener.onOkListener();
                dismiss();
                break;

            case R.id.btnCancelar:
                dismiss();
                break;
        }
    }
    public void setListener(OnClickLDialogConfirmarListener listener) {
        this.listener = listener;
    }
    public interface OnClickLDialogConfirmarListener{
        void onOkListener ();
    }
}
