package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sociomas.aventones.R;

/**
 * Created by jromeromar on 12/10/2017.
 */

public class DialogAceptarCancelar extends Dialog {

    private Context context;
    private TextView tvContent;
    private Button btnConfirmar;
    private Button btnCancelar;
    private String description;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    private String stringContent;
    public interface  OnDialogConfirmarListener{
        void onConfirmarDialog(boolean confirmado);
    }
    private OnDialogConfirmarListener listener;


    public void setListener(OnDialogConfirmarListener listener) {
        this.listener = listener;
    }

    public DialogAceptarCancelar(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public DialogAceptarCancelar setDescription(String description){
        this.description=description;
        return this;
    }

    public DialogAceptarCancelar setOnDialogoListener(OnDialogConfirmarListener listener){
        this.listener=listener;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo_base_aventones);
        tvContent=(TextView)findViewById(R.id.tvContenido);
        btnConfirmar=(Button)findViewById(R.id.btnConfirmar);
        btnCancelar=(Button)findViewById(R.id.btnCancelar);
        tvContent.setText(getContext().getString(R.string.SolicitarDialog));
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(listener!=null){
                       listener.onConfirmarDialog(true);
                   };
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener()
        {
                    @Override
                    public void onClick(View v) {
                        if(listener!=null){
                            listener.onConfirmarDialog(false);
                        }
                        dismiss();
                    }
        });
    }
    public void setTextContent(String Content){
        tvContent.setText(getContext().getString(Integer.parseInt(Content)));

    }

   public void btnCancelarGone(View v){
       btnCancelar.setVisibility(View.GONE);

   }
}