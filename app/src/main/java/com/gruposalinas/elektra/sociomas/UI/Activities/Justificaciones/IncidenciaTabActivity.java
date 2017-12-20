package com.gruposalinas.elektra.sociomas.UI.Activities.Justificaciones;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.os.Bundle;

import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseTabCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters.PagerAdapterIncidencias;
import com.gruposalinas.elektra.sociomas.R;
public class IncidenciaTabActivity extends BaseTabCompactActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicencia_tab);
        setToolBar("Justificaciones");
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tabLayout.addTab(tabLayout.newTab().setText("Mis justificaciones"));
        tabLayout.addTab(tabLayout.newTab().setText("Justificaciones de mi equipo"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PagerAdapterIncidencias pagerAdapterPermisos =new PagerAdapterIncidencias(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(pagerAdapterPermisos);
    }
}
