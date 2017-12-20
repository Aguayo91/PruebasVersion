package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;

import android.view.Window;

import android.view.WindowManager;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;



public class DialogPicture extends Dialog implements View.OnClickListener {

    private View layoutCamara, layoutGaleria,layoutEliminar,btnCancelar;
    private boolean showBtnDelete=false;
    private RelativeLayout rlPrincipal;
    TextView tvCamara, tvGaleria;
    ImageView imgCamara,imgGaleria;
    boolean clickable = true;

    public interface onPictureOptionSelectedListener{
        void onGallerySelected();
        void onCameraSelected();
        void onDeleteSelected();
    }

    private onPictureOptionSelectedListener listener;

    public void setOnPictureSelectedListener(onPictureOptionSelectedListener listener) {
        this.listener = listener;
    }

    public void setShowBtnDelete(boolean showBtnDelete) {
        this.showBtnDelete = showBtnDelete;
    }

    public DialogPicture(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_picture);
        layoutCamara=findViewById(R.id.layoutCamara);
        layoutGaleria=findViewById(R.id.layoutGaleria);
        layoutEliminar=findViewById(R.id.layoutEliminar);
        btnCancelar=findViewById(R.id.btnCancelar);
        rlPrincipal=(RelativeLayout)findViewById(R.id.rlPrincipal);
        tvCamara=(TextView)findViewById(R.id.tvCamara);
        tvGaleria=(TextView)findViewById(R.id.tvGaleria);
        imgCamara = (ImageView)findViewById(R.id.imageView);
        imgGaleria =  (ImageView)findViewById(R.id.imgGaleria);
        layoutGaleria.setOnClickListener(this);
        layoutCamara.setOnClickListener(this);
        layoutEliminar.setOnClickListener(this);
        layoutEliminar.setVisibility(showBtnDelete? View.VISIBLE: View.GONE);
        btnCancelar.setOnClickListener(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.layoutGaleria:
                    if(listener!=null){
                        listener.onGallerySelected();
                        dismiss();
                    }
                    break;
                case R.id.layoutCamara:
                    if(listener!=null){
                        listener.onCameraSelected();
                        dismiss();
                    }
                    break;


                case R.id.layoutEliminar:
                    if(listener!=null){
                        listener.onDeleteSelected();
                        dismiss();
                    }
                    break;

                case R.id.btnCancelar:
                     dismiss();
                    break;
            }
    }

    public void setTextos(String text1, String text2){
        tvCamara.setText(text1);
        tvGaleria.setText(text2);
    }

    public void setDrawable(int imagen1,int imagen2){
        imgCamara.setImageResource(imagen1);
        imgGaleria.setImageResource(imagen2);
    }
    public void setNoClickable(){
        clickable=false;
    }


}
