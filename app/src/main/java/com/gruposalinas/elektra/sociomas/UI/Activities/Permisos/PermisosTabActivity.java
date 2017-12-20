package com.gruposalinas.elektra.sociomas.UI.Activities.Permisos;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseTabCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters.PagerAdapterPermisos;
import com.gruposalinas.elektra.sociomas.R;

    public class PermisosTabActivity extends BaseTabCompactActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_horarios_tab);
            setToolBar("Permisos");
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            tabLayout.addTab(tabLayout.newTab().setText("Mis permisos"));
            tabLayout.addTab(tabLayout.newTab().setText("Permisos de mi equipo"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            PagerAdapterPermisos pagerAdapterPermisos =new PagerAdapterPermisos(getSupportFragmentManager(),tabLayout.getTabCount());
            mViewPager.setCurrentItem(0);
            mViewPager.setAdapter(pagerAdapterPermisos);
        }
    }