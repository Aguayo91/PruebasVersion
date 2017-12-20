package com.sociomas.aventones.UI.Activities.RutaDisponible;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.SolicitarAventon.SolicitarAventonScreen;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogAceptarCancelar;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogRuta;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.WebService.Model.Response.Aventones.Aventones;

public class RutaAventonesDisponibles extends BaseCoreCompactActivity implements OnMapReadyCallback,RutaDPresenterImpl.RutaDView, DialogAceptarCancelar.OnDialogConfirmarListener {

    private RutaDPresenter presenter;
    private GoogleMap mMap;
    private DialogRuta dialogRuta;
    private Aventones selectedAventon;
    private DialogAceptarCancelar dialogAceptarCancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_aventones_disponibles);
        setToolBar(getString(R.string.la_ruta));
        setPresenter();

    }

    @Override
    public void setPresenter() {
        presenter=new RutaDPresenterImpl();
        presenter.register(this);
        presenter.setArguments(getIntent());

    }

    @Override
    public void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dialogRuta=new DialogRuta(this);
        dialogAceptarCancelar=new DialogAceptarCancelar(this);
        dialogAceptarCancelar.setOnDialogoListener(this);

    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Utils.posicionMexico,10));
        presenter.generarRuta();
    }
    public void solAventon(View v){
            dialogAceptarCancelar.show();

    }


    @Override
    public void onRutaGenerada(PolylineOptions lineOptionsRuta, LatLngBounds bounds) {
        if(mMap!=null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 3));
            mMap.addPolyline(lineOptionsRuta);
        }
    }

    @Override
    public void setSelectedAventon(Aventones aventon) {
        this.selectedAventon=aventon;
    }


    @Override
    public void showDialogoRuta() {
        dialogRuta.show();
    }

    @Override
    public void hideDialogoConfirmarAventon() {
        if(dialogAceptarCancelar!=null){
            dialogAceptarCancelar.dismiss();
        }
    }

    @Override
    public void hideDialogoRuta() {
        dialogRuta.hide();
    }

    @Override
    public void navegarSuccess() {
        Intent intent=new Intent(this, SolicitarAventonScreen.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,selectedAventon);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    @Override
    public void onConfirmarDialog(boolean confirmado) {
        if(confirmado){
            presenter.solicitarAventon(this.selectedAventon);
        }
    }
}
