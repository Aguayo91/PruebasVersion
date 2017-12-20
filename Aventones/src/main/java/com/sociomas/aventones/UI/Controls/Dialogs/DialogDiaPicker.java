package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterFrecuencia;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.UI.Controls.Pickers.FrecuenciaPicker;

import java.util.ArrayList;

/**
 * Created by jmarquezu on 28/09/2017.
 */

public class DialogDiaPicker extends Dialog  {
    private RecyclerView rvDia;
    ArrayList<Dia> listDias;
    FrecuenciaPicker.OnFrecuenciaListener listener;

    public DialogDiaPicker(Context context, FrecuenciaPicker.OnFrecuenciaListener listener) {
        super(context);
        this.listener=listener;
    }

    public DialogDiaPicker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picker_dia);
        FrecuenciaPicker picker = (FrecuenciaPicker) findViewById(R.id.frecuenciaPicker);
        picker.setListener(listener);
    }

}
