package com.gruposalinas.elektra.sociomas.UI.Activities.Servicios;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;

public class Aseguradoras extends BaseAppCompactActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aseguradoras);
        setToolBar(getString(R.string.polizas));
    }
    public void telUno(View v){
      callPhone("018008306787");
    }
    public void telDos(View v){
        callPhone("018005001500");
    }
    public void telTres(View v){
        callPhone("018008002880");
    }
    private   void callPhone(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
}
