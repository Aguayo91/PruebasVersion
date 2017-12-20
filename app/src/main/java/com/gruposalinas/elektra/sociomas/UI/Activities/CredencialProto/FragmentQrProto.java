package com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.Gafete.GafeteActivity;
import com.sociomas.core.UI.Controls.Layout.ParallaxLayerLayoutProto;
import com.sociomas.core.Utils.Utils;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by jmarquezu on 07/11/2017.
 */

public class FragmentQrProto extends Fragment implements ParallaxLayerLayoutProto.DirectionListener{

    private ImageView imageQr;
    private SensorTranslationUpdaterProto translationUpdater;
    private ParallaxLayerLayoutProto parallax;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr_proto, container, false);
        parallax= (ParallaxLayerLayoutProto)rootView.findViewById(R.id.paralax);
        imageQr=(ImageView)rootView.findViewById(R.id.imageQr);
        translationUpdater = new SensorTranslationUpdaterProto(getContext());
        String codigoVerificador= Utils.getQrEmpleado(getContext()).getResultado();
        if(!codigoVerificador.isEmpty()) {
            Bitmap bitmapQr = QRCode.from(codigoVerificador).bitmap();
            imageQr.setImageBitmap(bitmapQr);
        }
        return rootView;
    }

    public void initParalax(SensorTranslationUpdaterProto translationUpdater){
        parallax.setTranslationUpdater(translationUpdater);
        parallax.setDirectionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
        initParalax(translationUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
        //parallax.
    }

    @Override
    public void onLeftListener() {
    }

    @Override
    public void onRightListener() {
        ((GafeteActivityProto)getActivity()).setCred();
        FragmentGafeteParallaxVProto fragment = new FragmentGafeteParallaxVProto();
        //translationUpdater.unregisterSensorManager();
        //fragment.onResume();
        Log.v("MyApp","OnRigthListener");
    }

    @Override
    public void onCenterListener() {

    }
}
