package com.gruposalinas.elektra.sociomas.UI.Activities.Zonas_Ubicaciones;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gruposalinas.elektra.sociomas.UI.Activities.LugaresTrabajo.LugaresActivity;
import com.gruposalinas.elektra.sociomas.UI.Controls.Dialogs.DialogOptions;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.IO.AsyncTasks.GeocodingAsync;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumConsulta;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EditarTiendaItem;
import com.sociomas.core.WebService.Model.Request.Zonas_Ubicaciones.EliminarTiendaRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Model.Response.Zonas.LugarTrabajo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//TODO PASAR A MVP
public class MapaZonaActivity extends BaseZonaActivity  implements OnMapReadyCallback, GeocodingAsync.GeocodingInterface{

    private TextView tvZonaNombre,tvZonaNombreFull;
    private TextView tvCalle,tvCalleFull;
    private ImageView imagePreviewStreet,imagePreviewStreetFull,imgActionFull;
    private ProgressBar progressbar;
    private Button btnAction;
    private GeocodingAsync geocodingAsync;
    private RelativeLayout bottomSheet;
    private LugarTrabajo lugarTrabajo;
    private boolean isEliminar=false;
    private DialogOptions dialogOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_zona);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.setToolBar(getString(R.string.mi_lugar_trabajo));
        this.tvCalle=(TextView)findViewById(R.id.tvCalle);
        this.tvZonaNombre=(TextView)findViewById(R.id.tvZonaNombre);
        this.imagePreviewStreet=(ImageView)findViewById(R.id.imagePreviewStreet);
        this.geocodingAsync=new GeocodingAsync(this);
        this.geocodingAsync.setGeocodingInterface(this);
        this.btnAction=(Button)findViewById(R.id.btnAction);
        this.dialogOptions=new DialogOptions(this);
        this.checkAction();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(getIntent().hasExtra(Constants.SELECTED_ZONA_TIENDA)){
            lugarTrabajo =(LugarTrabajo) getIntent().getSerializableExtra(Constants.SELECTED_ZONA_TIENDA);
            tvZonaNombre.setText(lugarTrabajo.getVaNombrePos());
            geocodingAsync.execute(new Double[]{lugarTrabajo.getDecLatitud(), lugarTrabajo.getDecLongitud()});

            /*MAPA*/
            LatLng currentPosition=new LatLng(lugarTrabajo.getDecLatitud(), lugarTrabajo.getDecLongitud());
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(currentPosition);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_green));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
            googleMap.addMarker(markerOptions);

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(currentPosition);
            circleOptions.radius(lugarTrabajo.getIntRangoAceptado());
            circleOptions.strokeColor(Color.BLACK);
            circleOptions.fillColor(ContextCompat.getColor(this,R.color.colorPrimaryTransparente));
            circleOptions.strokeWidth(2);
            googleMap.addCircle(circleOptions);

            /*PREVIEW DE STREETVIEW*/
            String url= Utils.getStreetViewPreviewUrl(lugarTrabajo.getDecLatitud(), lugarTrabajo.getDecLongitud());

            Picasso.with(this).load(url)
                    .resize(120,60).centerCrop().into(imagePreviewStreet);
        }
    }

    @Override
    public void OnGeocodingFinish(String result) {
        tvCalle.setText(result);
    }


    public void onClickAction(View view){
        agregarEliminarAsync(this.isEliminar);
    }

    public void onClickIndicaciones(View view){
     //   Utils.intentNavegacion(this,tvCalle.getText().toString());
        Utils.intentNavegacionWaze(this,tvCalle.getText().toString(), lugarTrabajo.getDecLatitud(), lugarTrabajo.getDecLongitud());
    }


    /*REVISA SI LA ACCION ES ELIMINAR O AGREGAR*/
    private void checkAction(){
        this.isEliminar=getIntent().getBooleanExtra(Constants.ELIMINAR_UBICACION,false);
        this.btnAction.setText(getString(isEliminar? R.string.eliminar: R.string.agregar));
    }


    private void agregarEliminarAsync(final  boolean isEliminar){

        final DialogOptions dialogOptions=new DialogOptions(this);
        dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
            @Override
            public void OnDismiss(boolean accepted) {
                if(accepted){
                    customProgressBar.show(MapaZonaActivity.this);
                    final boolean isPlantilla=getIntent().getBooleanExtra(Constants.IS_PLANTILLA,false);
                    String numeroEmpleado=isPlantilla?getIntent().getStringExtra(Constants.SELECTED_ID_EMPLEADO): Utils.getNumeroEmpleado(MapaZonaActivity.this);
                    EliminarTiendaRequest request=new EliminarTiendaRequest();
                    request.setIdNumEmpleado(numeroEmpleado);
                    request.setTipo(isPlantilla? EnumConsulta.LineaDirecta.toString():EnumConsulta.Mias.toString());
                    ArrayList<EditarTiendaItem> posiciones=new ArrayList<>();
                    posiciones.add(new EditarTiendaItem(lugarTrabajo.getVaNumeroPos(), lugarTrabajo.getVaComentario()));
                    request.setPosiciones(posiciones);
                    Call<ServerResponse>call=isEliminar?controllerAPI.eliminarUbicacion(request)
                            :controllerAPI.registrarPropuestaUbicacion(request);
                    call.enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            if(response.isSuccessful()){
                                if(!response.body().getError()) {
                                    dialogOptions.showExito(isPlantilla);
                                    dialogOptions.setCallBackDialgoOptions(new DialogOptions.CallBackDialgoOptions() {
                                        @Override
                                        public void OnDismiss(boolean accepted) {
                                            Intent intent=new Intent(MapaZonaActivity.this,LugaresActivity.class);
                                            intent.putExtra(Constants.IS_PLANTILLA,isPlantilla);
                                            startActivity(intent);
                                        }
                                    });

                                }
                                else{
                                    snackBarBuilder.showError(response.body().getMensajeError());
                                }

                            }
                            customProgressBar.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            snackBarBuilder.showError(getString(R.string.Error_Conexion));
                            customProgressBar.dismiss();
                        }
                    });
                }
            }
        });
        dialogOptions.showDialogoBase(this);
    }

}
