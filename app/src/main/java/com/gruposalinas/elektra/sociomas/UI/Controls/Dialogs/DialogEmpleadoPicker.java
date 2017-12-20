package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Adapters.AdapterEmpleadosRecyclerPicker;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jmarquezu on 28/11/2017.
 */

public class DialogEmpleadoPicker extends Dialog {

    private DBUtils dbUtils;
    private ArrayList<Plantilla> listPlantilla = new ArrayList<>();
    private AdapterEmpleadosRecyclerPicker adapter;
    private RecyclerView rv;
    private TextView tvTitulo;
    private String titulo=null;
    private EditText sv;
    private RecyclerItemClickListener itemClickListener;
    private ImageView imgCerrar;

    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DialogEmpleadoPicker(@NonNull Context context) {
        super(context);
    }

    public DialogEmpleadoPicker(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected DialogEmpleadoPicker(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_dialog_plantilla);
        sv = (EditText)findViewById(R.id.svEmpleados);
        rv=(RecyclerView)findViewById(R.id.rvPlantilla);
        imgCerrar = (ImageView)findViewById(R.id.imgCerrar);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        if(titulo!=null){
         tvTitulo.setText(titulo);
        }

        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void initDialogPlantilla(@StringRes int tituloDialogo,  boolean mostrarMiUsuario){
        initDialogPlantilla(getContext().getString(tituloDialogo),mostrarMiUsuario);
    }

    public void initDialogPlantilla(String titulo,boolean mostrarMiUsuario){
        dbUtils=new DBUtils(getContext());
        listPlantilla = (ArrayList<Plantilla>) dbUtils.obtenerEmpleadoPlantilla();
        Empleado empleado = Utils.getCurrentEmpleado(getContext());
        Plantilla plantilla = new Plantilla(empleado.getIdEmployee(),empleado.getName());
        if(!listPlantilla.isEmpty()){
            if (mostrarMiUsuario){
                listPlantilla.add(plantilla);
            }
            Collections.reverse(listPlantilla);
            adapter = new AdapterEmpleadosRecyclerPicker(getContext(),listPlantilla);
            if(itemClickListener!=null){
                adapter.setItemClickListener(itemClickListener);
            }
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(adapter);
        }

        RxTextView.textChanges(sv).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence query) throws Exception {
                if(!TextUtils.isEmpty(query.toString())) {
                    adapter.getFilter().filter(query.toString());
                }
            }
        });

        if(tvTitulo!=null){
            tvTitulo.setText(titulo);
        }
    }
    public void setTitulo (String titulo){
        this.titulo=titulo;
    }
}
