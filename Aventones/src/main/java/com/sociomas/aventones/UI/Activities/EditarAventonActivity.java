package com.sociomas.aventones.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.MisAventones.AventonesPublicados;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class EditarAventonActivity extends BaseAppCompatActivity {

    private DiscreteSeekBar discreteseekbar;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_aventon);

        setToolBar(R.string.editaaventon);

        discreteseekbar = (DiscreteSeekBar)findViewById(R.id.discretSeekBar);
        texto = (TextView)findViewById(R.id.tvTitleTime);
        discreteseekbar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                texto.setText(String.valueOf(value)+" "+getString(R.string.MinTolerancia));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


    }
    public void PublicaActivity(View view){

        Intent intent = new Intent(this,AventonesPublicados.class);
        startActivity(intent);

    }

}
