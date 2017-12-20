package com.sociomas.aventones.UI.Activities.Ruta;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.CoordenadasIda;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.PublicaResponse;
import java.util.ArrayList;

/**
 * Created by oemy9 on 09/10/2017.
 */

public class RutaPresenterImpl extends BasePresenterImpl implements RutaPresenter {
    private PublicaResponse response;
    private RutaView view;

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRY)){
           response=(PublicaResponse)intent.getSerializableExtra(ViewEvent.ENTRY);
        }
    }

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(RutaView)view;
    }

    @Override
    public void generarRuta() {
        ArrayList<LatLng> points =new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (CoordenadasIda c: response.getListCoordenadasIda()) {
            LatLng position = new LatLng(c.getLatitudIda(), c.getLongitudIda());
            points.add(position);
            builder.include(position);
        }
        view.onRutaGenerada(new PolylineOptions().width(5).color(ContextCompat.getColor(ApplicationAventon.getAppContext(),R.color.colorGrisInfo))
                .visible(true).zIndex(30).addAll(points).geodesic(true),builder.build());
        view.mostrarDetalleRuta(String.format("%s (%s)",response.getDuracionTrayectoIdaText(),response.getDistanciaTrayectoIdaText()));

    }
    public interface  RutaView{
        void onRutaGenerada(PolylineOptions lineOptionsRuta, LatLngBounds bounds);
        void mostrarDetalleRuta(String detalleRuta);
    }
}
