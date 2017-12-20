package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by jromeromar on 17/11/2017.
 */

public class DialogHorario extends Dialog implements View.OnClickListener {

    private ImageView imgClose;
    private TextView tvOk;

    public DialogHorario(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_horario);
        imgClose = (ImageView)findViewById(R.id.imgClose);
        tvOk=(TextView)findViewById(R.id.tvOk);
        imgClose.setOnClickListener(this);
        tvOk.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose:
                dismiss();
                break;

            case R.id.tvOk:
                dismiss();
                break;
                }
        }
    }