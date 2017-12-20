package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Progress.ProgressBubble;

/**
 * Created by jromeromar on 07/12/2017.
 */

public class DialogRegistrandoTelefono extends Dialog {

    private TextView tvMessage;
    private ProgressBubble progressBubble;

    public DialogRegistrandoTelefono(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_registrando_telefono);
        tvMessage=(TextView)findViewById(R.id.tvMessage);
        progressBubble=(ProgressBubble)findViewById(R.id.progressBubble);
        progressBubble.startBubbleAnimation();

        setDialogParameters();
    }

    private void setDialogParameters(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity= Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

    public void initMessage(@StringRes int tituloDialogo){
        initMessage(getContext().getString(tituloDialogo));
    }

    public void initMessage(String Message){

     tvMessage.setText(Message);

    }
}