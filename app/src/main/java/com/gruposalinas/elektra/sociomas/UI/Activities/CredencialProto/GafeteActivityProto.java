package com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapters.PagerAdapterGafeteProto;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.Utils.Constants;


public class GafeteActivityProto extends BaseAppCompactActivity {
    private ViewPager mViewPager;
    private PagerAdapterGafeteProto pagerAdapter;
    private boolean mostrarGafete=false;
    public static final int POSITION_QR_VIEW=1;
    public static final int POSITION_CRED_VIEW=0;
    public MotionEvent event;
    public boolean selected;
    public int action;

    public GafeteActivityProto(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gafete);
        pagerAdapter = new PagerAdapterGafeteProto(getSupportFragmentManager(),2);
        //event = new MotionEvent();
        mViewPager = (ViewPager) findViewById(R.id.container);
        mostrarGafete= ApplicationBase.getIntance().getManager().get(Constants.MOSTRAR_NUEVO_GAFETE,false);
        pagerAdapter.setMostrarNuevoGafete(mostrarGafete);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(true,  new CubeOutTransformer());
        setToolBar(R.string.mi_gafete);
        //checkIfGenerarGafete();

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //mViewPager.onInterceptTouchEvent(event);

                selected = mViewPager.onTouchEvent(event);
                //Log.v("MyApp2","Boolean onTouchEvent: "+selected+event.toString());
                selected = mViewPager.onInterceptTouchEvent(event);
                action = event.getAction();
                Log.v("MyApp2","Boolean onInterceptTouchEvent: "+selected+event.toString());
                return selected;
            }
        });
    }
    private void checkIfGenerarGafete(){

        /*if(getIntent().getBooleanExtra(Constants.GENERAR_CREDENCIAL,false))
        {*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!mostrarGafete) {
                        FragmentGafeteParallaxVProto fragmentGafete = (FragmentGafeteParallaxVProto) pagerAdapter.getRegisteredFragment(0);
                        //FragmentQr fragmentGafete = (FragmentQr) pagerAdapter.getRegisteredFragment(1);

                        /*if (fragmentGafete != null) {
                            fragmentGafete.generarImagenAsync();
                        }*/
                    }

                }
            },100);
        /*}
        else{
            if(manager.getString(Constants.IMAGEN_CREDENCIAL)==null || (manager.getString(Constants.IMAGEN_CREDENCIAL).isEmpty())){
                NavegarActivityAsync(CrearGafeteActivity.class);
                finish();
            }
        }*/

    }


    public void setQr(){
        mViewPager.setCurrentItem(POSITION_QR_VIEW);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               FragmentQrProto fragmentQr =  (FragmentQrProto) pagerAdapter.getRegisteredFragment(POSITION_QR_VIEW);
                if(fragmentQr!=null){
                    fragmentQr.onAttach(getApplicationContext());
                    Log.v("MyApp2","Accion en el tiempo: " +action);
                }
            }
        },150);
    }

    public void setCred(){
        mViewPager.setCurrentItem(POSITION_CRED_VIEW);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentGafeteParallaxVProto fragmentParalax=  (FragmentGafeteParallaxVProto)pagerAdapter.getRegisteredFragment(POSITION_CRED_VIEW);
                if(fragmentParalax!=null){
                    fragmentParalax.onAttach(getApplicationContext());
                }
            }
        },150);
    }
}
