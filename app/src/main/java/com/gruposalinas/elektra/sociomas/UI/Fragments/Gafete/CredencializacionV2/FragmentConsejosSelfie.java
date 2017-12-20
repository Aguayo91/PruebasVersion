package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.CredencializacionV2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by jmarquezu on 29/11/2017.
 */

public class FragmentConsejosSelfie extends Fragment {


    Button btnComenzar;
    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_consejos_gafete_v2, container, false);
        btnComenzar = (Button) view.findViewById(R.id.btnComenzar);
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pasar a la siguiente Actividad
            }
        });
    }
}
