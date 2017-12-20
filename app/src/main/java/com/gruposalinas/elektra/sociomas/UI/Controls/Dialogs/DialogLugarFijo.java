package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;

import vn.luongvo.widget.iosswitchview.SwitchView;

/**
 * Created by Lenovo7657 on 18/11/2017.
 */

public class DialogLugarFijo extends Dialog implements View.OnClickListener {


    private TextView tvOk;
    private ImageView imgInformation;
    private TextView tvSiDentro;
    private SwitchView switchIOS;

    public DialogLugarFijo(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_information_lugar);
        tvOk=(TextView)findViewById(R.id.tvOk);
        imgInformation=(ImageView)findViewById(R.id.imgClose);
        tvSiDentro  =(TextView) findViewById(R.id.tvSiDentro);
        switchIOS=(SwitchView)findViewById(R.id.IOS);
        switchIOS.setChecked(true);
        imgInformation.setOnClickListener(this);
        tvOk.setOnClickListener(this);
//        tvSiDentro.setText(Html.fromHtml(this.getContext().getString(R.string.SiDentro)));
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
