package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Legal.LegalActivity;
import com.gruposalinas.elektra.sociomas.UI.Activities.Nomina.NominaActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Base.FragmentBaseTab;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;

/**
 * Created by GiioToledo on 12/12/17.
 */

public class FragmentBaseNomina extends FragmentBaseTab implements BaseView {

    public void navegarFragment(Fragment fragment, String tag, boolean backStack)
    {
        if(getActivity()!=null) {
            NominaActivity act = (NominaActivity) getActivity();
            act.performTransaction(fragment, tag, R.id.container, backStack);
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
            NominaActivity act = (NominaActivity) getActivity();
            act.presentEvent(event);
        }
    }

    @Override
    public Activity getActivityInstance() {
        return this.getActivity();
    }

}
