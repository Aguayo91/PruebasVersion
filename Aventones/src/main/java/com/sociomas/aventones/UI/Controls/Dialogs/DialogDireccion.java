package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterDireccionSearch;
import com.sociomas.aventones.UI.TextWatchers.TextWatcherDireccionSearch;
import com.sociomas.aventones.UI.Controls.EditTexts.EditTextBackground;
import com.sociomas.core.Listeners.DialogDirectionCompletedListener;
import com.sociomas.core.Listeners.OnPredictionListener;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.AutoComplete.PredictionSections;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by oemy9 on 31/08/2017.
 */

public class DialogDireccion extends Dialog implements OnPredictionListener,RecyclerItemClickListener,View.OnClickListener {

    private Context context;
    private TextWatcherDireccionSearch
            textWatcherDireccionDestino;

    private EditTextBackground txtDestino;
    private RecyclerView recyclerUbicaciones;
    private LinearLayout layoutElegirUbicacion;
    private ImageButton btnBack;
    private AdapterDireccionSearch adapterDireccionSearch;
    private DialogDirectionCompletedListener listener;
    public void setListener(DialogDirectionCompletedListener listener) {
        this.listener = listener;
    }

    public DialogDireccion(Context context){
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.context=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_direccion_dialog);
        //findViews editText
        txtDestino = (EditTextBackground)findViewById(R.id.txtDestino);
        //text watchers
        textWatcherDireccionDestino=new TextWatcherDireccionSearch(this.context,txtDestino.getTxtAutocomplete());
        textWatcherDireccionDestino.setPredictionListener(this);
        //pasamos text watchers a los autocompletes
        txtDestino.getTxtAutocomplete().addTextChangedListener(textWatcherDireccionDestino);
        //recycler view de autrocomplete
        recyclerUbicaciones = (RecyclerView)findViewById(R.id.recyclerUbicaciones);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerUbicaciones.setLayoutManager(layoutManager);
        //linear layout para picker
        layoutElegirUbicacion=(LinearLayout)findViewById(R.id.layoutElegirUbicacion);
        layoutElegirUbicacion.setOnClickListener(this);
        //btn back
        btnBack=(ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        setDialogParameters();

    }

    private void setDialogParameters(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.softInputMode=WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        lp.gravity=Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onPredictionResponse(ArrayList<PredictionSections> listPrediction) {
            adapterDireccionSearch = new AdapterDireccionSearch(context, listPrediction);
            adapterDireccionSearch.setItemClickListener(this);
            AlphaInAnimationAdapter alphaInAnimationAdapter=new AlphaInAnimationAdapter(adapterDireccionSearch);
            alphaInAnimationAdapter.setDuration(300);
            recyclerUbicaciones.setAdapter(alphaInAnimationAdapter);
    }

    @Override
    public void onInputClean(boolean clean) {
        if(adapterDireccionSearch!=null && (adapterDireccionSearch.getItemCount()>0)){
            adapterDireccionSearch.clearItems();
        }
    }

    @Override
    public void OnError(Throwable error) {
    }

    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        Prediction item=(Prediction)selectedItem;
        listener.onDialogCompletedListener(item);
        txtDestino.clearText();
        waitDismiss();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.layoutElegirUbicacion) {
            listener.onPickerDirectionListener(true);
            waitDismiss();

        } else if (i == R.id.btnBack) {
            dismiss();

        }
    }

    private void waitDismiss(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },100);
    }

    public void changeHintText(String text){
        txtDestino.setHintText(text);
    }
}
