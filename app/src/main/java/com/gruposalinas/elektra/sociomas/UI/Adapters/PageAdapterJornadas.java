package com.gruposalinas.elektra.sociomas.UI.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

/**
 * Created by jmarquezu on 16/11/2017.
 */

public class PageAdapterJornadas extends FragmentPagerAdapter {

    public ArrayList<Fragment> mArrayListFragment = new ArrayList<Fragment>();


    public PageAdapterJornadas(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment){
        this.mArrayListFragment.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mArrayListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mArrayListFragment.size();
    }


}
