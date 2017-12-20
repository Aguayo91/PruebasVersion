package com.gruposalinas.elektra.sociomas.UI.Fragments.Legal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Legal.LegalActivity;
import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.FragmentUnidadNegocio;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;

/**
 * Created by oemy9 on 07/12/2017.
 */

public class FragmentOpcionesLegal extends Fragment implements View.OnClickListener {

    private TextView tvAviso,tvTerminos;

    public static final String TAG = "FragmentOpcionesLegal";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_opciones_legal,container,false);
        tvAviso=(TextView)rootView.findViewById(R.id.tvAviso);
        tvTerminos=(TextView)rootView.findViewById(R.id.tvTerminos);
        tvAviso.setOnClickListener(this);
        tvTerminos.setOnClickListener(this);
        return rootView;
    }

    public static FragmentOpcionesLegal getInstance(@Nullable  Bundle args){
        FragmentOpcionesLegal fragmentOpcionesLegal=new FragmentOpcionesLegal();
        if(args!=null){
            fragmentOpcionesLegal.setArguments(args);
        }
        return fragmentOpcionesLegal;
    }

    @Override
    public void onResume() {
        super.onResume();
        LegalActivity lgtAct=(LegalActivity)getActivity();
        lgtAct.changeToolBarText(getString(R.string.legales));
    }

    @Override
    public void onClick(View v) {

        LegalActivity lgtAct=(LegalActivity)getActivity();

        if(v.getId()==tvAviso.getId()){
            Bundle bundle=new Bundle();
            bundle.putString(ViewEvent.ENTRY,EnumTiposAviso.POLITICAS_PRIVACIDAD.name());
            lgtAct.navegarFragmento(FragmentTerminosLegales.getInstance(bundle),FragmentTerminosLegales.TAG,true);
        }
        else if(v.getId()==tvTerminos.getId()){
            Bundle bundle=new Bundle();
            bundle.putString(ViewEvent.ENTRY,EnumTiposAviso.TERMINOS_CONDICIONES.name());
            lgtAct.navegarFragmento(FragmentTerminosLegales.getInstance(bundle),FragmentTerminosLegales.TAG,true);
        }
    }

}
