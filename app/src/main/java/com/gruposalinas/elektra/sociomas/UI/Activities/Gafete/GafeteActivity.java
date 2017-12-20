package com.gruposalinas.elektra.sociomas.UI.Activities.Gafete;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters.PagerAdapterGafete;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga.FragmentQr;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Utils;

public class GafeteActivity extends BaseAppCompactActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private PagerAdapterGafete pagerAdapter;
    private boolean mostrarGafete=false;
    private boolean showingQr=false;
    public static final int POSITION_QR=1;
    public static final int POSITION_GAFETE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gafete);
        pagerAdapter = new PagerAdapterGafete(getSupportFragmentManager(),2);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setPageTransformer(true,  new CubeOutTransformer());
        setToolBar(R.string.mi_gafete);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void changeNavigationTab(int position){
        mViewPager.setCurrentItem(position,true);
    }

    @Override
    public void onPageSelected(int position) {
        if(position==POSITION_QR){
            FragmentQr ft=(FragmentQr)pagerAdapter.getRegisteredFragment(POSITION_QR);
            if (ft != null) {
                // not null
            } else {
                ft = (FragmentQr) pagerAdapter.getItem(POSITION_QR);
            }
            ft.cambiarBrioPantallaMax();
            showingQr=true;
        }
        else{
            cambiarDefaultBrillo();
            showingQr=false;
        }
    }


    @Override
    protected void onStop() {
        if(showingQr){
            cambiarDefaultBrillo();
        }
        super.onStop();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void cambiarDefaultBrillo(){


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            SessionManager manager = ApplicationBase.getIntance().getManager();
            Utils.setBrilloPantala(this, manager.getInt(Constants.CURRENT_BRILLO, 200));
        }
    }

    @Override
    public void onBackPressed() {

        if(mViewPager.getCurrentItem()==POSITION_QR){
                mViewPager.setCurrentItem(POSITION_GAFETE);
        }
        else {
            super.onBackPressed();
        }
    }
}
