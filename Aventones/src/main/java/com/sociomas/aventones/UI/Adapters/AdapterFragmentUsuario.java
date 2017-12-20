package com.sociomas.aventones.UI.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sociomas.aventones.UI.Fragments.FragmentPasajeroConductor;
import com.sociomas.aventones.UI.Fragments.FragmentQrConductor;

/**
 * Created by jmarquezu on 06/10/2017.
 */

public class AdapterFragmentUsuario extends FragmentStatePagerAdapter{

    private  int tabs;
    public AdapterFragmentUsuario(FragmentManager fm, int tabs){
        super(fm);
        this.tabs=tabs;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        //fragment = new FragmentPasajeroConductor();
        fragment = new FragmentQrConductor();

        return fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}