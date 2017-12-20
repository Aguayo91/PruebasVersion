package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by jromeromar on 17/11/2017.
 */

public class FragmentInformationLugar extends FragmentSlideBase implements BaseView, View.OnClickListener {

    private View rootView;
    private ImageView imgInformation;
    private TextView tvOk;
    public static final String TAG = "FragmentInformationLuga";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_information_lugar, container,false);
        setPresenter();
        return rootView;
    }
    public static  FragmentInformationLugar getInstance(@Nullable Bundle args){
        FragmentInformationLugar fgt=new FragmentInformationLugar();
        if(args!=null){
            fgt.setArguments(args);
        }
        return fgt;
    }

    @Override
    public void initView() {
        tvOk=(TextView)rootView.findViewById(R.id.tvOk);
        imgInformation=(ImageView)rootView.findViewById(R.id.imgInformation);
    }

    @Override
    public void setListeners() {

        tvOk.setOnClickListener(this);
        imgInformation.setOnClickListener(this);
    }
    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvOk:
                navegarFragment(FragmentLugarTrabajo.getInstance(null),
                        FragmentLugarTrabajo.TAG,true);
                break;
            case R.id.imgInformation:
                navegarFragment(FragmentLugarTrabajo.getInstance(null),
                        FragmentLugarTrabajo.TAG,true);
                break;
        }
    }


    @Override
    public void setPresenter() {
    }

    @Override
    public void presentEvent(ViewEvent event) {

    }

}
