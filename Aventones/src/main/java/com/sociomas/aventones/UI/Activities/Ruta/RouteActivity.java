package com.sociomas.aventones.UI.Activities.Ruta;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.MisAventones.AventonesPublicados;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
public class RouteActivity extends BaseCoreCompactActivity implements OnMapReadyCallback, RutaPresenterImpl.RutaView {
    private GoogleMap mMap;
    private TextView tvMinutosKm;
    private RutaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setPresenter();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void initView() {
        tvMinutosKm=(TextView)findViewById(R.id.tvMinutosKm);
    }

    @Override
    public void setPresenter() {
        presenter=new RutaPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());

    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Utils.posicionMexico,10));
        mMap.setBuildingsEnabled(true);
        presenter.generarRuta();
    }

    @Override
    public void onRutaGenerada(PolylineOptions lineOptionsRuta, LatLngBounds bounds) {
        if(mMap!=null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 3));
            mMap.addPolyline(lineOptionsRuta);
        }
    }
    @Override
    public void mostrarDetalleRuta(String detalleRuta) {
        tvMinutosKm.setText(detalleRuta);
    }
    public void navegarPublicados(View v){
        Intent intent=new Intent(this, AventonesPublicados.class);
        startActivity(intent);
    }
}
