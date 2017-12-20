package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.UI.Controls.Layout.ParallaxLayerLayout;
import com.sociomas.core.UI.Controls.Layout.SensorTranslationUpdater;

/**
 * Created by oemy9 on 30/10/2017.
 */

public class FragmenGafeteParallax extends Fragment {


    private ParallaxLayerLayout parallaxLayout;
    private SensorTranslationUpdater translationUpdater;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_gafete_parallax,container,false);
        parallaxLayout = (ParallaxLayerLayout)rootView.findViewById(R.id.parallax);
        translationUpdater=new SensorTranslationUpdater(getContext());
        parallaxLayout.setTranslationUpdater(translationUpdater);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
    }

    @Override
    public void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
    }
}
