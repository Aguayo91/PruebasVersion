package com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga.FragmentQr;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.FragmentGafeteParallaxV;

public class PagerAdapterGafete extends SmartFragmentStatePagerAdapter {
    private  int tabs;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    private boolean mostrarNuevoGafete=false;

    public PagerAdapterGafete(FragmentManager fm, int tabs){
        super(fm);this.tabs=tabs;
    }

    public void setMostrarNuevoGafete(boolean mostrarNuevoGafete) {
        this.mostrarNuevoGafete = mostrarNuevoGafete;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= new FragmentGafeteParallaxV();
                break;
            case 1:
                fragment=new FragmentQr();
                break;
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}