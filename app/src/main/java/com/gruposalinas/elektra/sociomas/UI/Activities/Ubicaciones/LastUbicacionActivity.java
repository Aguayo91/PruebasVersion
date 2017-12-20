package com.gruposalinas.elektra.sociomas.UI.Activities.Ubicaciones;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Notification.CustomProgressBar;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.SearchUbicacionDialog;
import com.gruposalinas.elektra.sociomas.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.MapUtils;
import com.gruposalinas.elektra.sociomas.UI.Adapters.Ubicaciones.UserInfoWindowAdapter;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.LastLocation;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.RootUbicacion;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unused")
public class LastUbicacionActivity extends BaseAppCompactActivity
        implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener {
    public static final String TAG="LAST_UBICACION";
    private GoogleMap mMap;
    private RootUbicacion resMovimientos;
    private HashMap<String,Marker>listadoMarcadores=new HashMap<>();
    private SearchUbicacionDialog searchDialog;
    private Ubicaciones selectedMovimiento;
    private int contadorActualizacion;
    private Timer t =new Timer();
    private MapUtils mapUtils;
    private int selectedNivel=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_ubicacion);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        searchDialog=new SearchUbicacionDialog(this);
        this.setToolBar(getString(R.string.last_name));
        this.mapUtils=new MapUtils(this);
        mapFragment.getMapAsync(LastUbicacionActivity.this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MapUtils.posicionMexico,4));
        this.getUbicacionesEmpleados(selectedNivel);
        this.updateUbicaciones();
    }
    private void getUbicacionesEmpleados(int nivel){
         final CustomProgressBar customProgressBar=new CustomProgressBar(this);
         customProgressBar.show(this);
         ControllerAPI controllerUbicacion=new ControllerAPI(this);
         LastLocation lastLocation=new LastLocation();
         lastLocation.setNivel(nivel);
         controllerUbicacion.getLastUbicacion(lastLocation).enqueue(new Callback<RootUbicacion>() {
             @Override
             public void onResponse(Call<RootUbicacion> call, Response<RootUbicacion> response) {
                 if(response.isSuccessful()){
                     if(response.body().getError() && (response.body().getMensajeError()!=null)){
                        alertaAsync.displayMensaje(response.body().getMensajeError(),LastUbicacionActivity.this);
                     }
                     else {
                         callBackGoogleMaps(mapUtils.decrypUbicacionResponse(response.body()));
                     }
                     customProgressBar.dismiss();
                 }
             }

             @Override
             public void onFailure(Call<RootUbicacion> call, Throwable t) {
                 alertaAsync.displayMensaje(getString(R.string.Error_Conexion),LastUbicacionActivity.this);
                 customProgressBar.dismiss();

             }
         });
    }
    private void callBackGoogleMaps(RootUbicacion resMovimientos){
        this.resMovimientos=resMovimientos;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        View infoView = LayoutInflater.from(this).inflate(R.layout.info_window, null);
        mMap.setInfoWindowAdapter(new UserInfoWindowAdapter(infoView,LastUbicacionActivity.this,false));
        if(resMovimientos.getUbicaciones()!=null && (!resMovimientos.getUbicaciones().isEmpty())) {

            for (Ubicaciones movimiento : resMovimientos.getUbicaciones()) {
               try {
                   LatLng currentLatLng=new LatLng(movimiento.getLatitud(),movimiento.getLongitud());
                   MarkerOptions markerOptions=new MarkerOptions();
                   markerOptions.title(movimiento.getIdNumEmpleado());
                   markerOptions.snippet(movimiento.toJson());
                   markerOptions.icon(BitmapDescriptorFactory.fromResource(AdapterUtils.getImagenByMovimiento(movimiento)));
                   markerOptions.position(currentLatLng);
                   Marker marker=mMap.addMarker(markerOptions);
                   if(!listadoMarcadores.containsKey(movimiento.getIdNumEmpleado())){
                       listadoMarcadores.put(movimiento.getIdNumEmpleado(),marker);
                   }
                   builder.include(currentLatLng);
               }
               catch (Exception ex){
                   ex.printStackTrace();
               }
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    selectedMovimiento=new Gson().fromJson(marker.getSnippet(),Ubicaciones.class);
                    showPickerAsync();
                }
            });
            LatLngBounds bounds = builder.build();
            /*CUANDO HAY MÁS DE DOS MARCADORES EN EL LISTADO*/
            if(resMovimientos.getUbicaciones().size()>=2) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
            /*SOLAMENTE HAY UN MARCADOR*/
            else if(!resMovimientos.getUbicaciones().isEmpty() && resMovimientos.getUbicaciones().size()<2){
                LatLng currentLatLng=new LatLng(resMovimientos.getUbicaciones().get(0).getLatitud(),resMovimientos.getUbicaciones().get(0).getLongitud());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,10));
            }
            searchDialog.setItemArrayList(this.resMovimientos.getUbicaciones());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filtro:
                    showSearchDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void findMarker(final String idEmpleado){

                for(Map.Entry<String,Marker> item: listadoMarcadores.entrySet()){
                    if(!item.getKey().equals(idEmpleado)){
                        item.getValue().setVisible(false);
                    }
                    else{
                        item.getValue().setVisible(true);
                        item.getValue().showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(item.getValue().getPosition(),15.5f),1000,null);
                    }
                }

        }
    private void updateUbicaciones(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LastUbicacionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     if(LastUbicacionActivity.this.contadorActualizacion<3) {
                         updateAsync();
                         LastUbicacionActivity.this.contadorActualizacion++;
                     }
                    }
                });
            }
        },calendar.getTime(),MapUtils.INTERVALO_ACTUALIZACION);
    }
    private void updateAsync(){
        if(!LastUbicacionActivity.this.isFinishing()) {
            resMovimientos=null;
            mMap.clear();
            listadoMarcadores.clear();
            getUbicacionesEmpleados(selectedNivel);
        }
    }


    @Override
    public void onBackPressed() {
            t.cancel();
            super.onBackPressed();

    }

    private void showSearchDialog(){
        if(!searchDialog.isShowing()) {
            searchDialog.showAsync();
            searchDialog.setCallBack(new SearchUbicacionDialog.SearchUbicacionCallBack() {
                @Override
                public void onResult(Ubicaciones resultItem) {
                    if(resultItem!=null){
                        findMarker(resultItem.getIdNumEmpleado());
                    }
                }

                @Override
                public void OnValueNumberPickerChanged(int value) {
                    selectedNivel=value;
                    searchDialog.disabledNumberPicker();
                    getUbicacionesEmpleados(selectedNivel);
                    searchDialog.enabledNumberPicker();
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }




    private void showPickerAsync() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this
                ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.setTitle(R.string.ruta_empleado);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker,  int year, final int month, int dayOfMonth) {
        if(datePicker.isShown()) {
            Calendar calendarDia = Calendar.getInstance();
            calendarDia.set(year, month, dayOfMonth);
            calendarDia.set(Calendar.MINUTE, 0);
            String fechaBuscada = new SimpleDateFormat(Constants.DAY_FORMAT).format(calendarDia.getTime());
            if(selectedMovimiento!=null) {
                Intent i = new Intent(this, TrackingActivity.class);
                i.putExtra(Constants.SELECTED_FECHA_BUSCADA, fechaBuscada);
                i.putExtra(Constants.SELECTED_ID_EMPLEADO, selectedMovimiento.getIdNumEmpleado());
                startActivity(i);
            }

        }
    }
}








