package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.SliderActivity;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by oemy9 on 14/11/2017.
 */

public class FragmentSlideBase extends Fragment implements BaseView{
    public void navegarFragment(Fragment fragment, String tag, boolean backStack)
    {
        if(getActivity()!=null) {
            SliderActivity act = (SliderActivity) getActivity();
            act.navegarFragmento(fragment, tag, backStack);
        }
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
        if(getActivity()!=null) {
            SliderActivity act = (SliderActivity) getActivity();
            act.presentEvent(event);
        }
    }

    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }
}
