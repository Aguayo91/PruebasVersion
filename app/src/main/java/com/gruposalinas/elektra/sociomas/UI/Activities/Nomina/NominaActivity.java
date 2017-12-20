package com.gruposalinas.elektra.sociomas.UI.Activities.Nomina;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Perfil.ConfiguracionActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.FragmentConsultaTarjeta;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.FragmentLiberarRecibos;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina.FragmentNominaDetail;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;

/**
 * Created by Giovanni Toledo Toledo mail: giio.toledo@gmail.com on 05/09/17.
 */

public class    NominaActivity extends BaseAppCompactActivity {

    FragmentManager mFm;
    FragmentTransaction mFt;
    private MenuItem itemSeleccionar;
    private static Drawable iconHome;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomina);
        initView();
        setToolBar(R.string.ToolbarTitleNomina);
        setSupportActionBar(toolbar);


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    toolbar.setNavigationIcon(R.drawable.flecha_cabeza);
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.syncState();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home: {
                        if (mFm.getBackStackEntryCount() > 0) {
                            mFm.popBackStackImmediate();
                        } else {
                            finish();
                        }
                    }
                    break;
                    case com.sociomas.core.R.id.action_settings: {
                        startActivity(new Intent(getString(com.sociomas.core.R.string.action_cofiguraciones)));
                    }
                    break;
                    case com.sociomas.core.R.id.action_notification: {
                        startActivity(new Intent(getString(com.sociomas.core.R.string.action_buzon)));

                    }
                    break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mFm.getBackStackEntryCount() > 0) {
                    mFm.popBackStackImmediate();
                } else {
                    drawer = (DrawerLayout) findViewById(com.sociomas.core.R.id.drawer_layout);
                    if (drawer != null) {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            drawer.openDrawer(GravityCompat.START);
                        }
                    } else {
                        onBackPressed();
                    }
                }
            }
        });
    }

    public void initView() {
        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        mFt.add(R.id.container,
                new FragmentConsultaTarjeta(),
                FragmentConsultaTarjeta.TAG)
                .commit();
    }

    public void performTransaction(Fragment fragment, String Tag, int id, boolean backStack) {
        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        mFt.add(id, fragment, Tag);
        if (backStack) {
            mFt.addToBackStack(Tag);
        }
        mFt.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mFm = getSupportFragmentManager();
        switch (id) {
            case android.R.id.home: {
                if (mFm.getBackStackEntryCount() > 0) {
                    mFm.popBackStackImmediate();
                } else {
                    finish();
                }
            }
            break;
        }
        return true;
    }

    public void showHideItemMenu(boolean show) {
        itemSeleccionar.setVisible(show);
    }
}
