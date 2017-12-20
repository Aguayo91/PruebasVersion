package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by gtoledo on 18/09/2017.
 */

public class FragmentNominaDetailGeography extends FragmentBaseTab implements BaseView {

    public static final String TAG =FragmentNominaDetailGeography.class.getSimpleName();
    private View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_nomina_detail_geography, container, false);
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void presentEvent(ViewEvent event) {

    }

    @Override
    public Activity getActivityInstance() {
        return null;
    }
}
