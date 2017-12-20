package com.sociomas.aventones.UI.Activities;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.sociomas.aventones.R;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAPIGoogle;

/**
 * Created by oemy9 on 01/09/2017.
 */

public class BaseAppCompatActivity  extends AppCompatActivity{
    private ControllerAPIGoogle controllerAPI=new ControllerAPIGoogle(this);
    public void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    protected void setToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.flecha_cabeza);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView tvTitulo = (TextView) findViewById(R.id.titulo);
            if (tvTitulo != null) {
                tvTitulo.setText(title);
            }
        }
    }
    protected  void setToolBar(@StringRes int title){
        setToolBar(getString(title));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
