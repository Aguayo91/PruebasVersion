package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.CredencializacionV2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion.FragmentConsejos;


/**
 * Created by jmarquezu on 29/11/2017.
 */

public class FragmenSelfieInicio extends Fragment {
    public static final String TAG = "FragmenSelfieInicio";
    ImageView btnCamara;
    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomate_una_selfie, container, false);
        btnCamara = (ImageView) view.findViewById(R.id.btnCamara);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pasar a la siguiente Actividad
                ((CrearGafeteActivity)getActivity()).navegarFragmento(new FragmentConsejos(),
                        FragmentConsejos.TAG,true);
            }
        });
        return view;
    }

    public static FragmenSelfieInicio newInstance(int sectionNumber) {
        FragmenSelfieInicio fragment = new FragmenSelfieInicio();
        return fragment;
    }
}
