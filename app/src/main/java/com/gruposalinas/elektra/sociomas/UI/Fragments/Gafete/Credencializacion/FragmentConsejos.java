package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Credencializacion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.CrearGafeteActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by oemy9 on 21/08/2017.
 */

public class FragmentConsejos extends Fragment  implements View.OnClickListener{
    public static final String TAG="FRAGMENT_CONSEJOS";

    private ImageView imgMarco;
    private Button btnComenzar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consejos_gafete, container, false);
        btnComenzar=(Button)rootView.findViewById(R.id.btnComenzar);
        imgMarco=(ImageView)rootView.findViewById(R.id.MarcoCredencial);
        btnComenzar.setOnClickListener(this);
        Picasso.with(getContext()).load(R.drawable.img_profile_pic).resize(300,300)
                .centerInside()
                .into(imgMarco);
        return rootView;
    }

    public static FragmentConsejos newInstance(int sectionNumber) {
        FragmentConsejos fragment = new FragmentConsejos();
        return fragment;
    }


    @Override
    public void onClick(View view) {
        ((CrearGafeteActivity)getActivity()).navegarFragmento(new FragmentSurfaceGafete(),
                FragmentSurfaceGafete.TAG,true);
    }
}
