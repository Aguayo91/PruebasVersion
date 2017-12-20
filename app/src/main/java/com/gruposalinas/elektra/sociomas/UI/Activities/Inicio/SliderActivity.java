package com.gruposalinas.elektra.sociomas.UI.Activities.Inicio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentFotoPerfil;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentLugarTrabajo;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentMensajeBienvenida;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentPrivacidad;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Slider.FragmentTerminos;

@SuppressWarnings("unused")
public class SliderActivity extends BaseAppCompactActivity {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        navegarFragmento(FragmentPrivacidad.getInstance(null), FragmentPrivacidad.TAG,false,false);
    }

    public void navegarFragmento(Fragment fragment, String  Tag, boolean backStack){
        navegarFragmento(fragment,Tag,backStack,true);
    }

    public void navegarFragmento(Fragment fragment, String  Tag, boolean backStack,boolean animation){
        currentFragment=fragment;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if(animation) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        ft.replace(R.id.container,currentFragment);
        if(backStack){
            ft.addToBackStack(Tag);
        }
        ft.commit();
    }
}
