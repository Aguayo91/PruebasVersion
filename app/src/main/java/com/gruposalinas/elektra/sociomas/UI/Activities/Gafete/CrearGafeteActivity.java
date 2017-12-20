package com.gruposalinas.elektra.sociomas.UI.Activities.Gafete;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters.PagerAdapterCrearGafete;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentConsejos;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentSurfaceGafete;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.CredencializacionV2.FragmenSelfieInicio;

public class CrearGafeteActivity extends BaseAppCompactActivity  {
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_gafete);
        setToolBarDefualtHomeIndicator(getString(R.string.mi_gafete));
        navegarFragmento(new FragmenSelfieInicio(),FragmenSelfieInicio.TAG,false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        checkIfHidePreviewCamara();
        super.onBackPressed();

    }

    private void checkIfHidePreviewCamara(){
        if(currentFragment instanceof FragmentSurfaceGafete){
            ((FragmentSurfaceGafete)currentFragment).hidePreview();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                 if(getSupportFragmentManager().getBackStackEntryCount()>0){
                     checkIfHidePreviewCamara();
                     getSupportFragmentManager().popBackStack();
                 }
                break;
        }
        return  true;
    }


    public void navegarFragmento(Fragment fragment, String  Tag, boolean backStack){
        currentFragment=fragment;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
        ft.replace(R.id.container,currentFragment);
        if(backStack){
            ft.addToBackStack(Tag);
        }
        ft.commit();
    }
}
