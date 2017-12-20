package com.sociomas.aventones.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogFrecuencia;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

public class DummyActivity extends BaseCoreCompactActivity {
    private Button boton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

    }
}
