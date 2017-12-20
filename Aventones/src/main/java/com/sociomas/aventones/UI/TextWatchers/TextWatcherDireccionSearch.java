package com.sociomas.aventones.UI.TextWatchers;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import com.sociomas.aventones.Utils.WebServices.ConsultarAutocompletado;
import com.sociomas.core.Listeners.OnPredictionListener;

/**
 * Created by oemy9 on 31/08/2017.
 */

public class TextWatcherDireccionSearch implements TextWatcher {
    public static final String TAG="TEXT_WATCHER";
    private boolean searching;
    private Context context;
    private OnPredictionListener predictionListener;
    private AutoCompleteTextView autoCompleteTextView;
    public TextWatcherDireccionSearch(Context context, AutoCompleteTextView autoCompleteTextView){
        this.context=context;
        this.autoCompleteTextView=autoCompleteTextView;
        this.searching=false;
    }
    public void setPredictionListener(OnPredictionListener predictionListener) {
        this.predictionListener = predictionListener;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void afterTextChanged(Editable editable) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final String query=autoCompleteTextView.getText().toString();
                if(query.length()>5 && !searching) {
                    ConsultarAutocompletado consulta = new ConsultarAutocompletado(context);
                    consulta.getAutocompletado(query, predictionListener);
                    searching=true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            searching=false;
                        }
                    },500);
                }
                else if(query.isEmpty()) {
                    predictionListener.onInputClean(true);
                    searching=false;
                }
            }
        },500);

    }
}
