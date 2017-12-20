package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.sociomas.core.Utils.Utils;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by oemy9 on 08/08/2017.
 */

public class FragmentQr extends Fragment implements View.OnClickListener {
    private ImageView imageQr,imgBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr, container, false);
        imageQr=(ImageView)rootView.findViewById(R.id.imageQr);
        imgBack=(ImageView)rootView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        String codigoVerificador=Utils.getQrEmpleado(getContext()).getResultado();
        if(!codigoVerificador.isEmpty()) {
            Bitmap bitmapQr = QRCode.from(codigoVerificador).bitmap();
            imageQr.setImageBitmap(bitmapQr);
        }

        return rootView;
    }
    public void cambiarBrioPantallaMax(){

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Utils.setBrilloPantala(getContext(), 255);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:
                if(getActivity()!=null){
                    GafeteActivity act=(GafeteActivity)getActivity();
                    act.changeNavigationTab(GafeteActivity.POSITION_GAFETE);
                }
                break;
        }
    }
}
