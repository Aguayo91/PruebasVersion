package com.gruposalinas.elektra.sociomas.UI.Activities.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 31/07/2017.
 * USADA PARA TODAS AQUELLAS ACTIVIDADES QUE USAN TABS EN LA APLICACIÓN
 */

public class BaseTabCompactActivity extends BaseAppCompactActivity {
    protected ViewPager mViewPager;
    protected TabLayout tabLayout;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
