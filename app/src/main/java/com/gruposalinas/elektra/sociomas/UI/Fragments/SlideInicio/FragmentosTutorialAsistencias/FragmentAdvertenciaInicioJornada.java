package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentosTutorialAsistencias;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gruposalinas.elektra.sociomas.R;


public class FragmentAdvertenciaInicioJornada extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertencia_inicio_jornada,container,false);
        return view;
    }
}
