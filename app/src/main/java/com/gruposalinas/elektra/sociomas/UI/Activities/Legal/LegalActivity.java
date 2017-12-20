package com.gruposalinas.elektra.sociomas.UI.Activities.Legal;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Legal.FragmentOpcionesLegal;
import com.sociomas.core.DataBaseModel.Legal;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Directions.Leg;

import java.util.List;

public class LegalActivity extends BaseAppCompactActivity {

    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        setToolBarDefualtHomeIndicator(getString(R.string.legales));
        navegarFragmento(FragmentOpcionesLegal.getInstance(null),FragmentOpcionesLegal.TAG,false);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount()>0) {
                    getSupportFragmentManager().popBackStack();
                }
                else{
                    onBackPressed();
                }
            }
        });
    }

    public void navegarFragmento(Fragment fragment, String  Tag, boolean backStack){
        currentFragment=fragment;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameRoot,currentFragment);
        if(backStack){
            ft.addToBackStack(Tag);
        }
        ft.commit();
    }

    public void changeToolBarText(String text){
        setToolBarDefualtHomeIndicator(text);
    }



}
