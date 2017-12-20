package com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentosTutorialAsistencias.FragmentAdvertenciaAdministracion;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentosTutorialAsistencias.FragmentAdvertenciaHorariosInicioFin;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentosTutorialAsistencias.FragmentAdvertenciaInicioJornada;
import com.gruposalinas.elektra.sociomas.UI.Adapters.PageAdapterJornadas;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by jmarquezu on 16/11/2017.
 */

public class DialogsAtencion extends DialogFragment {

    public static final String TAG = "DialogsAtencion";
    private PageAdapterJornadas pAJornadas;
    private ViewPager viewPager;
    private Dialog dialog;
    private boolean cerrar=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogs_atencion,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        PageAdapterJornadas adapter = new PageAdapterJornadas(getChildFragmentManager());
        //Se tienen que agregar en orden de inicio a fin
        adapter.addFragment(new FragmentAdvertenciaHorariosInicioFin());
        adapter.addFragment(new FragmentAdvertenciaAdministracion());
        adapter.addFragment(new FragmentAdvertenciaInicioJornada());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(cerrar && position==2){
                    dialog.dismiss();
                    FelicidadesDialogConfig felicidadesDialogConfig = new FelicidadesDialogConfig(getContext());
                    felicidadesDialogConfig.setAct(getActivity());
                    felicidadesDialogConfig.show();
                    cerrar=false;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    cerrar=false;
                }else if(position==2){
                    boolean handler = new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          cerrar=true;
                        }
                    },500);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.FILL;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}
