package com.gruposalinas.elektra.sociomas.UI.Fragments.Slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 13/07/2017.
 */

@SuppressWarnings("unused")
public  class FragmentHorario extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentHorario() {
    }

    public static FragmentHorario newInstance(int sectionNumber) {
        FragmentHorario fragment = new FragmentHorario();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_horario, container, false);

        return rootView;
    }
}
