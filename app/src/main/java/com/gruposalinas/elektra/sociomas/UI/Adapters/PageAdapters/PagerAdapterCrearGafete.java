package com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentConsejos;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentPreliminar;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentSurfaceGafete;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.CredencializacionV2.FragmenSelfieInicio;

public class PagerAdapterCrearGafete extends  SmartFragmentStatePagerAdapter {
    private  int tabs;
    private FragmentManager fm;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    public PagerAdapterCrearGafete(FragmentManager fm, int tabs){
        super(fm);this.tabs=tabs;
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {
          Fragment fragment=null;

        switch (position){
            case 0:
                fragment= FragmenSelfieInicio.newInstance(position);
                break;
            case 1:
                //fragment= FragmentSurfaceGafete.newInstance(position);
                fragment= FragmentConsejos.newInstance(position);

                break;
            case 2:
                fragment= FragmentPreliminar.newInstance(position);
                break;

        }

        return  fragment;
    }


    @Override
    public int getCount() {
        return tabs;
    }
}