package com.gruposalinas.elektra.sociomas.UI.Activities.Contactos;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

public class ContactosActivityV3 extends BaseCoreCompactActivity {

    private TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_v3);
        setToolBar(getString(R.string.misContactosSos));
        tvNombre = (TextView) findViewById(R.id.tvNombre);

        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/OswaldRegular.ttf");
        tvNombre.setTypeface(typeFace);

    }
}
