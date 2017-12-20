package com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gruposalinas.elektra.sociomas.R;
/**
 * Created by oemy9 on 22/05/2017.
 */

import java.util.Locale;

/**
 * Created by oemy9 on 22/05/2017.
 */

public class InputBox {

    public interface inputBoxCallBack{
        void OnResult(String result);
        void OnCancel();
    }
    private String textDefault;
    private String hint;
    private String title;
    private String errorText;
    private String positiveButtonText;
    private String negativeButtonText;
    private boolean showNeutral=false;
    private int inputType=0;

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public boolean isShowNeutral() {
        return showNeutral;
    }

    public void setShowNeutral(boolean showNeutral) {
        this.showNeutral = showNeutral;
    }

    public String getTextDefault() {
        return textDefault;
    }

    public void setTextDefault(String textDefault) {
        this.textDefault = textDefault;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }


    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    private inputBoxCallBack callBack;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setCallBack(inputBoxCallBack callBack) {
        this.callBack = callBack;
    }

    private Context context;

    public InputBox(Context context){
        this.context=context;
    }

    public void showAsync(){

        final AlertDialog.Builder builderDialogo=new AlertDialog.Builder(this.context);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View promptView = layoutInflater.inflate(R.layout.dialog_text, null);
        builderDialogo.setTitle(this.title);
        builderDialogo.setView(promptView);
        builderDialogo.setCancelable(true);

        final EditText input = (EditText) promptView
                .findViewById(R.id.txtResultado);
        input.requestFocus();
        input.setHint(this.hint);


        String positiveText=positiveButtonText!=null? positiveButtonText: this.context.getString(R.string.aceptar);
        String negativeText=negativeButtonText!=null? negativeButtonText: this.context.getString(R.string.cancelar);

        if(inputType!=0){
            input.setInputType(inputType);
        }

        if(textDefault!=null &&(!textDefault.isEmpty())){
            input.setText(textDefault);
            input.selectAll();
        }


        if(showNeutral) {
            builderDialogo.setNeutralButton(this.context.getString(R.string.cancelar).toUpperCase(Locale.ENGLISH), null);
        }
        builderDialogo.setPositiveButton(positiveText.toUpperCase(Locale.ENGLISH), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing here because we override this button later to change the close behaviour.
                //However, we still need this because on older versions of Android unless we
                //pass a handler the button doesn't get instantiated
            }
        });



        builderDialogo.setNegativeButton(negativeText.toUpperCase(Locale.ENGLISH), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(callBack!=null){
                    callBack.OnCancel();
                }
            }
        });

        final AlertDialog dialog=builderDialogo.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().toString().isEmpty()){
                    input.setError(errorText);
                }
                else{
                    if(callBack!=null){
                        callBack.OnResult(input.getText().toString());
                        dialog.dismiss();
                    }
                }
            }
        });

    }
}
