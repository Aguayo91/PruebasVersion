package com.gruposalinas.elektra.sociomas.UI.Activities.CredencialProto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Layout.ParallaxLayerLayoutProto;

/**
 * Created by oemy9 on 31/10/2017.
 */

public class FragmentGafeteParallaxVProto extends Fragment implements ParallaxLayerLayoutProto.DirectionListener {

    private static final String TAG = "FragmentGafeteParallaxVProto";
    private ParallaxLayerLayoutProto parallax;
    private SensorTranslationUpdaterProto translationUpdater;
    private View layoutPlaca;
    private TextView tvNombre;
    private TextView tvApellidos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_gafete_parallax_proto,container,false);
        parallax=(ParallaxLayerLayoutProto)rootView.findViewById(R.id.parallax);
        tvApellidos=(TextView)rootView.findViewById(R.id.tvApellidos);
        tvNombre=(TextView)rootView.findViewById(R.id.tvNombre);
        layoutPlaca=rootView.findViewById(R.id.layoutPleca);
        translationUpdater=new SensorTranslationUpdaterProto(getContext());
       // initParalax(translationUpdater);
        return rootView;
    }

    public void initParalax (SensorTranslationUpdaterProto translationUpdater){
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
        ((GafeteActivityProto)getActivity()).setQr();
        FragmentQrProto iniciar = new FragmentQrProto();
        //translationUpdater.unregisterSensorManager();
        Log.v("MyApp","OnLeftListener");
    }

    @Override
    public void onRightListener() {
    }

    @Override
    public void onCenterListener() {

    }

}
