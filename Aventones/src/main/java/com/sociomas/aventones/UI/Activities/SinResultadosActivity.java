package com.sociomas.aventones.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Encuentra.EncuentraTuAventonActivity;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

public class SinResultadosActivity extends BaseCoreCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_resultados);
    }
    public void back(View view){
        Intent i = new Intent(this,EncuentraTuAventonActivity.class);
        startActivity(i);
    }
}
