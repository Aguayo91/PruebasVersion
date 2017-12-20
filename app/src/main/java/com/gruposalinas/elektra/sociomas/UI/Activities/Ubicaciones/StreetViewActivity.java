package com.gruposalinas.elektra.sociomas.UI.Activities.Ubicaciones;

import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.gruposalinas.elektra.sociomas.R;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

public class StreetViewActivity extends BaseCoreCompactActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);
        if(getIntent().hasExtra(Constants.SELECTED_ID_EMPLEADO)){
            SupportStreetViewPanoramaFragment fragment=(SupportStreetViewPanoramaFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.streetviewpanorama);
             fragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
                 @Override
                 public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                     if(savedInstanceState==null){
                         Ubicaciones selectedUbicacion= Utils.getFromJson(getIntent().getStringExtra(Constants.SELECTED_ID_EMPLEADO),Ubicaciones.class);
                         if(selectedUbicacion!=null) {
                             streetViewPanorama.setPosition(new LatLng(selectedUbicacion.getLatitud(),selectedUbicacion.getLongitud()));
                         }

                     }
                 }
             });

        }


    }
}
