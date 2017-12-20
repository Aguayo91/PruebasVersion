package com.gruposalinas.elektra.sociomas.UI.Fragments.Slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import com.gruposalinas.elektra.sociomas.R;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

/**
 * Created by oemy9 on 13/07/2017.
 */

@SuppressWarnings("unused")
public class FragmentTerminos extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private CheckBox checkBoxNoMostrar;
    private Button btnAceptar, btnCancelar;
    private SessionManager manager;
    public static FragmentTerminos newInstance(int sectionNumber) {
        FragmentTerminos fragment = new FragmentTerminos();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        this.manager=new SessionManager(getContext());
        this.checkBoxNoMostrar=(CheckBox)rootView.findViewById(R.id.checkNoMostrar);
        this.checkBoxNoMostrar.setOnClickListener(this);
        this.btnAceptar=(Button)rootView.findViewById(R.id.btnAceptar);
        this.btnCancelar=(Button)rootView.findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkNoMostrar:
            manager.add(Constants.MOSTRAR_SLIDER, !checkBoxNoMostrar.isChecked());
                break;
            case R.id.btnCancelar:
                getActivity().finish();
                break;
        }
    }
}
