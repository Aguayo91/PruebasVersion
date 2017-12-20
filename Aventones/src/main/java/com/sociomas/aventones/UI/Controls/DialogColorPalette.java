package com.sociomas.aventones.UI.Controls;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterColor;
import com.sociomas.aventones.Utils.Utils;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by jmarquezu on 22/09/2017.
 */

public class DialogColorPalette extends Dialog implements  AdapterColor.ColorListener{


    private RecyclerView rvColor;
    private Button btAvanzadas;
    private int currentColor;
    private TextView tvColor;
    public ColorListener2 colorListener2;
    public Toolbar toolbar;

    private ArrayList<String> listcolors (){
        ArrayList colors = new ArrayList<>();

        colors.add("#FFFFFF");//BLANCO
        colors.add("#000000");//NEGRO
        colors.add("#696969");//GRIS
        colors.add("#C0C0C0");//PLATA
        colors.add("#FF0000");//ROJO
        colors.add("#0000FF");//AZUL
        colors.add("#008000");//VERDE
        return colors;
    }
    /**
     * Blanco #FFFFFF
     * Negro  #000000
     * Gris   #696969
     * Plata  #C0C0C0
     * Rojo   #FF0000
     * Azul   #0000FF
     * Verde  #008000
     * */

    private ArrayList<String> colorName(){
        ArrayList names = new ArrayList<>();
        names.add("Blanco");
        names.add("Negro");
        names.add("Gris");
        names.add("Plata");
        names.add("Rojo");
        names.add("Azul");
        names.add("Verde");
        return names;
    }

    public DialogColorPalette(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_color);


        rvColor=(RecyclerView)findViewById(R.id.rvColor);
        btAvanzadas = (Button)findViewById(R.id.btAvanzadas);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView tvTitulo=(TextView)findViewById(com.sociomas.core.R.id.titulo);
        AdapterColor adapterColor=new AdapterColor(listcolors(),colorName());
        adapterColor.setColorListener(this);
        rvColor.setLayoutManager(new LinearLayoutManager(getContext()));
        rvColor.setAdapter(adapterColor);
        btAvanzadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false);
            }
        });
        tvTitulo.setText(R.string.selecciona_color);
        //tvTitulo.setAllCaps(true);
        toolbar.hideOverflowMenu();
        //toolbar.
    }

    private void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                if(colorListener2 != null){
                    colorListener2.OnColorListener(Utils.getHexcolor(color));
                    dismiss();
                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
        });
        dialog.show();
    }


    @Override
    public void onSelectedColor(String selectedColor) {
        //btAvanzadas.setBackgroundColor(Color.parseColor(selectedColor));
        if(colorListener2 != null){
            colorListener2.OnColorListener(selectedColor);
        }
        dismiss();
    }
    public interface ColorListener2 {
        void OnColorListener(String colorseleccionado);
    }
    public void setColor2(ColorListener2 listener2){
        this.colorListener2 = listener2;
    }


}
