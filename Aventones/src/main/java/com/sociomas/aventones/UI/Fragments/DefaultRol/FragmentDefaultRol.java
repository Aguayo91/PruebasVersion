package com.sociomas.aventones.UI.Fragments.DefaultRol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Carros.CarsActivity;
import com.sociomas.aventones.UI.Activities.Encuentra.EncuentraTuAventonActivity;
import com.sociomas.aventones.UI.Activities.Rol.AventonRolUsuarioActivity;
import com.sociomas.aventones.UI.Controls.BotonMenuAventones;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oemy9 on 25/10/2017.
 */

public class FragmentDefaultRol  extends Fragment implements DefualtRolPresenterImpl.DefaultRolView, View.OnClickListener {
    private View rootView;
    private  int animationDuration = 1000;
    private CircleProgressbar circleProgressbar, circleProgressbar2;
    private List<Vehiculo>listVehiculo=new ArrayList<>();
    private DefualtRolPresenter presenter;
    private BotonMenuAventones imgPublica,imgEncuentra;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootView=inflater.inflate(R.layout.fragment_default_v2,container,false);
       setPresenter();
       return rootView;
    }

    @Override
    public void onResume() {
        if(circleProgressbar!=null && circleProgressbar2!=null){
            circleProgressbar.setProgress(0);
            circleProgressbar2.setProgress(0);
        }
        super.onResume();
    }

    @Override
    public void initView() {
        circleProgressbar = (CircleProgressbar)rootView.findViewById(R.id.circle_loading);
        circleProgressbar2 = (CircleProgressbar)rootView.findViewById(R.id.circle_loading2);
        imgPublica=(BotonMenuAventones)rootView.findViewById(R.id.btn_publica_aventon);
        imgEncuentra=(BotonMenuAventones)rootView.findViewById(R.id.btn_encuentra_aventon);
    }

    @Override
    public void setListeners() {
        imgPublica.setOnClickListener(this);
        imgEncuentra.setOnClickListener(this);

    }

    @Override
    public void setPresenter() {
        presenter=new DefualtRolPresenterImpl();
        presenter.register(this);
//        presenter.obtenerAutosListadoAsync();
        AventonRolUsuarioActivity activity=(AventonRolUsuarioActivity)getActivity();
        activity.hideFloatActionsNinguno();
    }

    @Override
    public void setListVehiculo(List<Vehiculo> list) {
        this.listVehiculo=list;
    }

    @Override
    public void presentEvent(ViewEvent event) {

    }

    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i ==imgPublica.getId()){
            publica(v);
        }
        else if(i==imgEncuentra.getId()){
            encuentra(v);
        }
    }
    //Vamos a la activity de "Publica tu aventón"
    public void publica (View view){
        /*circleProgressbar.setClockwise(false);
        circleProgressbar.setProgressWithAnimation(100, animationDuration);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
                Intent intent = new Intent(getContext(),CarsActivity.class);
                if(listVehiculo!=null && (!listVehiculo.isEmpty())){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.SELECTED_AUTOS, (Serializable)listVehiculo);
                    intent.putExtras(bundle);
                }
                startActivity(intent);

        //    }
        //},500);

    }
    //Vamos a la activity de "Encuentra tú aventón"
    public void encuentra(View view){
        /*circleProgressbar2.setClockwise(false);
        circleProgressbar2.setProgressWithAnimation(100, animationDuration);*/
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
                Intent intent = new Intent(getContext(),EncuentraTuAventonActivity.class);
                startActivity(intent);

            /*}
        },500);*/
    }
}
