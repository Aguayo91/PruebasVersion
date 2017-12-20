package com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto.FragmentGafeteParallaxVProto;
import com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto.FragmentQrProto;

public class PagerAdapterGafeteProto extends SmartFragmentStatePagerAdapter {
    private  int tabs;
    private FragmentManager fm;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    private boolean mostrarNuevoGafete=true;

    public PagerAdapterGafeteProto(FragmentManager fm, int tabs){
        super(fm);this.tabs=tabs;this.fm= fm;
    }

    public void setMostrarNuevoGafete(boolean mostrarNuevoGafete) {
        this.mostrarNuevoGafete = mostrarNuevoGafete;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= new FragmentGafeteParallaxVProto();
                break;
            case 1:
                fragment=new FragmentQrProto();
                break;
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}