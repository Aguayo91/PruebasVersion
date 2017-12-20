package com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gruposalinas.elektra.sociomas.UI.Fragments.Justificaciones.FragmentIncidenciasPlantilla;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Justificaciones.FragmentMisIncidencias;

/**
 * Created by oemy9 on 25/07/2017.
 */

public class PagerAdapterIncidencias extends FragmentStatePagerAdapter {
    private  int tabs;
    public PagerAdapterIncidencias(FragmentManager fm, int tabs){
        super(fm);this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new FragmentMisIncidencias();
                break;
            case 1:
                fragment=new FragmentIncidenciasPlantilla();
                break;

        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}

