package com.gruposalinas.elektra.sociomas.UI.Activities.Ubicaciones;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones.AdapterTimeLine;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones.UserInfoWindowAdapter;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecurityItems;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.MapUtils;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.LastLocation;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.RootUbicacion;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import de.mateware.snacky.Snacky;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unused")
public class TrackingActivity extends BaseAppCompactActivity implements OnMapReadyCallback,
        DatePickerDialog.OnDateSetListener, AdapterTimeLine.ItemClickListener {
    public static final String TAG="TAG";
    private GoogleMap mMap;
    private HashMap<Integer,Marker>listadoMarcadores=new HashMap<>();
    private RecyclerView recyclerTimeLine;
    private AdapterTimeLine adapterTimeLine;
    private RelativeLayout bottomSheet;
    private BottomSheetBehavior bsb;
    private boolean isShowingSheet = false;
    private String numeroEmpleado,fechaBuscada;
    private LatLngBounds.Builder builder;
    private FloatingActionButton fabVerMas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fabVerMas=(FloatingActionButton)findViewById(R.id.fabVerMas);
        recyclerTimeLine=(RecyclerView)findViewById(R.id.recyclerTimeLine);
        recyclerTimeLine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerTimeLine.setHasFixedSize(true);
        bottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);
        bsb = BottomSheetBehavior.from(bottomSheet);
        if(getIntent().hasExtra(Constants.SELECTED_ID_EMPLEADO) && getIntent().hasExtra(Constants.SELECTED_FECHA_BUSCADA)){
            this.numeroEmpleado=getIntent().getStringExtra(Constants.SELECTED_ID_EMPLEADO);
            this.fechaBuscada=getIntent().getStringExtra(Constants.SELECTED_FECHA_BUSCADA);
            getTrackingAsync(this.numeroEmpleado,this.fechaBuscada);
        }
        setToolBar(getString(R.string.ruta_empleado));
    }

    private void showPickerAsync() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this
                ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.setTitle(R.string.ruta_empleado);
        datePickerDialog.show();

    }
    private void getTrackingAsync(String numeroEmpleado, final String fechaBuscada){

        final LastLocation lastLocation=new LastLocation();
        lastLocation.setFechaFin(fechaBuscada);
        lastLocation.setFechaInicio(fechaBuscada);
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        lastLocation.setNumeroEmpleado(securityItems.getIdEmployEncrypt());
        final CustomProgressBar customProgressBar=new CustomProgressBar(this);
        customProgressBar.show(this);
        ControllerAPI controllerAPI = new ControllerAPI(this);
        controllerAPI.getTrackingUbicacion(lastLocation).enqueue(new Callback<RootUbicacion>() {
            @Override
            public void onResponse(Call<RootUbicacion> call, Response<RootUbicacion> response) {
                if(response.isSuccessful()){
                    if (response.body().getUbicaciones() == null || (response.body().getUbicaciones().isEmpty())) {
                        customProgressBar.dismiss();
                        Snackbar errorSnackBar = Snacky.builder()
                                .setActivty(TrackingActivity.this)
                                .setText("No se encontraron ubicaciones para este empleado en el día ".concat(fechaBuscada))
                                .setDuration(Snacky.LENGTH_LONG).error();
                        errorSnackBar.show();
                    }
                    else{
                        if(mMap!=null) {
                            mMap.clear();
                        }
                        ArrayList<Ubicaciones>listFiltradas=new ArrayList<Ubicaciones>();
                        ArrayList<Location>locationArrayList=new ArrayList<Location>();
                        for (Ubicaciones movimiento : response.body().getUbicaciones()) {
                                Location location=new Location("location");
                                location.setLongitude(movimiento.getLongitud());
                                location.setLatitude(movimiento.getLongitud());
                                locationArrayList.add(location);
                        }
                        boolean hasUbicaciones=false;
                        builder = new LatLngBounds.Builder();
                        int posicion=0;


                        Hashtable<String,ArrayList<Ubicaciones>> hashConsolidados=new Hashtable<String, ArrayList<Ubicaciones>>();


                        for(int j=0;j<locationArrayList.size();j++){
                           if(j>0 && j<locationArrayList.size()-1){


                               Ubicaciones movimiento=response.body().getUbicaciones().get(j);


                               if(movimiento.getNombrePosicionValida()!=null &&
                                       (!movimiento.getNombrePosicionValida().equals(""))){

                                   if(!hashConsolidados.containsKey(movimiento.getNombrePosicionValida()))
                                   {
                                       ArrayList<Ubicaciones>listUbicaciones=new ArrayList<Ubicaciones>();
                                       listUbicaciones.add(movimiento);
                                       hashConsolidados.put(movimiento.getNombrePosicionValida(),listUbicaciones);
                                   }
                                   else{
                                       ArrayList<Ubicaciones>listUbicaciones= hashConsolidados.get(movimiento.getNombrePosicionValida());
                                       listUbicaciones.add(movimiento);
                                       hashConsolidados.put(movimiento.getNombrePosicionValida(),listUbicaciones);
                                   }
                               }
                               else {
                                   if (locationArrayList.get(j).distanceTo(locationArrayList.get(j - 1)) > 100) {
                                       addMarker(movimiento,posicion);
                                       listFiltradas.add(movimiento);
                                       posicion++;
                                       hasUbicaciones = true;
                                   }
                               }

                           }

                        }

                        /*SE AGREGAN CONSOLIDADOS*/
                        for(Map.Entry<String,ArrayList<Ubicaciones>>item: hashConsolidados.entrySet()){
                            if(!item.getValue().isEmpty() && item.getValue().size()>=2) {
                                Ubicaciones ultimaUbicacion=item.getValue().get(item.getValue().size() - 1);
                                ultimaUbicacion.setFechaEntradaConsolidada(item.getValue().get(0).getFechaHora());
                                ultimaUbicacion.setFechaSalidaConsolidada(ultimaUbicacion.getFechaHora());
                                ultimaUbicacion.setZonaValidaConsolidada(item.getKey().toUpperCase(Locale.ENGLISH));
                                ultimaUbicacion.setConsolidado(true);
                                listFiltradas.add(ultimaUbicacion);
                                addMarker(ultimaUbicacion,listFiltradas.size()-1);
                                hasUbicaciones=true;
                            }
                        }

                        if(hasUbicaciones) {
                            LatLngBounds bounds = builder.build();
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                            mMap.moveCamera(cu);
                        }

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent i = new Intent(TrackingActivity.this,StreetViewActivity.class);
                                i.putExtra(Constants.SELECTED_ID_EMPLEADO,marker.getSnippet());
                                startActivity(i);
                            }
                        });
                        adapterTimeLine=new AdapterTimeLine(listFiltradas);
                        adapterTimeLine.setItemClickListener(TrackingActivity.this);
                        recyclerTimeLine.setAdapter(adapterTimeLine);
                        customProgressBar.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(Call<RootUbicacion> call, Throwable t) {

            }
        });
    }

    private void addMarker(Ubicaciones movimiento,int posicion){
        LatLng currentLatLng = new LatLng(movimiento.getLatitud(), movimiento.getLongitud());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(movimiento.getIdNumEmpleado());
        markerOptions.snippet(movimiento.toJson());
        markerOptions.icon(BitmapDescriptorFactory.fromResource(AdapterUtils.getImagenByMovimiento(movimiento)));
        markerOptions.position(currentLatLng);
        Marker marker = mMap.addMarker(markerOptions);
        listadoMarcadores.put(posicion, marker);
        builder.include(currentLatLng);
    }

    private void findMarker(final Integer  posicion){
        if(listadoMarcadores.containsKey(posicion)){
            Marker marker=listadoMarcadores.get(posicion);
            marker.setVisible(true);
            marker.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15.5f),1000,null);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MapUtils.posicionMexico,4));
        View infoView = LayoutInflater.from(this).inflate(R.layout.info_window, null);
        mMap.setInfoWindowAdapter(new UserInfoWindowAdapter(infoView,this,true));
    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, final int month, int dayOfMonth) {
        if(datePicker.isShown()) {
            Calendar calendarDia = Calendar.getInstance();
            calendarDia.set(year, month, dayOfMonth);
            calendarDia.set(Calendar.MINUTE, 0);
            this.fechaBuscada= new SimpleDateFormat(Constants.DAY_FORMAT).format(calendarDia.getTime());
            getTrackingAsync(this.numeroEmpleado,this.fechaBuscada);
        }
    }


    @Override
    public void OnClickItem(int position) {
        findMarker(position);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_filtro:
                showPickerAsync();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(bsb.getState()==BottomSheetBehavior.STATE_EXPANDED){
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else {
            super.onBackPressed();
        }
    }

    public  void onClickVerMas(View view){
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


}
