package com.gruposalinas.elektra.sociomas.UI.TextWatchers;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.gruposalinas.elektra.sociomas.Utils.Utils;

/**
 * Created by oemy9 on 08/09/2017.
 */

public class TextWatcherSMS implements TextWatcher {

    private EditText codigoUno, codigoDos;
    private Activity activity;

    public TextWatcherSMS setActivity(Activity activity){
        this.activity=activity;
        return this;
    }

    public TextWatcherSMS setCodigoUno(EditText codigoUno){
        this.codigoUno=codigoUno;
        return  this;
    }

    public TextWatcherSMS setCodigoDos(EditText codigoDos){
        this.codigoDos=codigoDos;
        return this;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(codigoUno.hasFocus()) {
            if (s.length() == 3) {
                codigoDos.requestFocus();
            }
        }
        else if(codigoDos.hasFocus()){
            if(s.length()==3){
                Utils.ocultarTeclado(activity);
            }
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
