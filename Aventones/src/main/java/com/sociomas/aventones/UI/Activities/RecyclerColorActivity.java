package com.sociomas.aventones.UI.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Adapters.AdapterColor;
import com.sociomas.aventones.UI.Controls.ControlColors;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class RecyclerColorActivity extends BaseAppCompatActivity {

    private ArrayList<String> colors;
    private RecyclerView rvEmergente;
    private RecyclerView.Adapter adapterRv;
    private ControlColors controler;
    private Button btAvanzadas;
    private int colorActual;


    private ArrayList<String> listcolors (){
        colors = new ArrayList<>();
        colors.add("-16777216");//Black
        colors.add("-16776961");//Blue
        colors.add("-16711681");//Cyan
        return colors;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_color);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setToolBar(getString(R.string.Automovil));
        rvEmergente = (RecyclerView)findViewById(R.id.rvColor);
        btAvanzadas = (Button)findViewById(R.id.btAvanzadas);
        rvEmergente.setHasFixedSize(true);
        rvEmergente.setLayoutManager(new GridLayoutManager(this,1));
        adapterRv = new AdapterColor(listcolors(),listcolors());
        rvEmergente.setAdapter(adapterRv);

        btAvanzadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false);
                //showColors();
            }
        });

    }



    private void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, colorActual, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                colorActual = color;
               /* btnColor.setBackgroundColor(color);
                btnColor.setTextColor(obtenerReverse(color));*/
                //imgColor.setBackgroundColor();
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
