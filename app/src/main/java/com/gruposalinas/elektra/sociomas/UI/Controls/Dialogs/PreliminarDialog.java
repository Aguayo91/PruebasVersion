package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Controls.CircleImageView;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

/**
 * Created by jmarquezu on 29/11/2017.
 */

public class PreliminarDialog extends Dialog implements View.OnClickListener{

    private FloatingActionButton btnAceptar,btnRechazar;
    private CircleImageView imgAvatar;
    private SessionManager manager;
    private String base64Imagen;
    private Bitmap bitmapImagen;

    public PreliminarDialog(@NonNull Context context) {
        super(context);
    }

    public PreliminarDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PreliminarDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_credencializacion);
        setDialogParametros();

        btnAceptar = (FloatingActionButton)findViewById(R.id.btnAceptar);
        btnRechazar = (FloatingActionButton)findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(this);
        btnRechazar.setOnClickListener(this);
     //   loadFoto();
    }

    private void setDialogParametros(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity= Gravity.FILL;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btnAceptar):
                ///Accion de aceptar
                dismiss();
                break;
            case (R.id.btnCancelar):
                //Accion de cancelar
                onBackPressed();
                dismiss();
                break;
        }
    }

    public PreliminarDialog setBase64Imagen(String base64Imagen){
            bitmapImagen= Utils.decodeBase64(base64Imagen);
            if(bitmapImagen!=null){
                setImagenToView(bitmapImagen);
            }
            return this;
    }

    private void setImagenToView(Bitmap bitmapImagen){
        imgAvatar.setImageBitmap(bitmapImagen);
    }
}
