package com.sociomas.aventones.UI.Activities.Publicar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Ruta.RouteActivity;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogAutoPicker;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogDiaPicker;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogDireccion;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogRuta;
import com.sociomas.aventones.UI.Controls.EditTexts.CustomNumberPicker;
import com.sociomas.aventones.UI.Controls.EditTexts.EditBackgroundHorario;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.aventones.UI.Controls.Pickers.AutoPicker;
import com.sociomas.aventones.UI.Controls.Pickers.FrecuenciaPicker;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.Listeners.CallBackLocationGenerated;
import com.sociomas.core.Listeners.DialogDirectionCompletedListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Activities.BaseLocationActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.Geocoding.GeocodingResult;
import com.sociomas.core.WebService.Model.Response.PublicaAventon.PublicaResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.luongvo.widget.iosswitchview.SwitchView;

public class PublicaActivity extends BaseLocationActivity implements
        DialogDirectionCompletedListener, PublicarPrensenterImpl.PublicarView, SwitchView.OnCheckedChangeListener,
        FrecuenciaPicker.OnFrecuenciaListener,DialogAutoPicker.onVehiculoListener, CallBackLocationGenerated {

    private PublicaPresenter presenter;
    private RelativeLayout relativeCalendar,relativeCalendarRedondo, relativeRedondo, relativeSencillo;
    private EditBackgroundHorario txtDestino, txtDestinoRedondo;
    private EditBackgroundHorario txtInicio, txtInicioRedondo;
    private boolean inicioByGeocoding = false;
    private boolean txtInicioClicked = false;
    private boolean isRedondo=false;
    private AutoPicker autoPicker;
    private CustomNumberPicker numberPicker,numberPickerRedondo;
    private DialogAutoPicker dialogAutoPicker;
    private Vehiculo selectedVehiculo;
    private Prediction predictionInicio, predictionDestino;
    private Prediction predictionInicioRedondo, predictionDestinoRedondo;
    private DialogDireccion dialogDireccion;
    private GeocodingResult geocodingResult;
    private FrecuenciaPicker picker;
    private TextView tvWeek,tvWeek1,tvWeek2;
    private ArrayList<Dia> listDias,listDiasRedondo;
    private DialogDiaPicker dialogDiaPicker;
    private SwitchView switchViewSencillo, switchViewRedondo;
    private DialogRuta dialogRuta;
    private Calendar calendarioInicio,calendarioDestino;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publica);
        setToolBar(getString(R.string.publica_aventon));
        ApplicationAventon applicationAventon=new ApplicationAventon();
        applicationAventon.onCreate(this);
        setCallBackLocationGenerated(this);
        setPresenter();
    }

    @Override
    public void initView() {
        relativeCalendar =(RelativeLayout)findViewById(R.id.rlDias);
        txtInicio = (EditBackgroundHorario) findViewById(R.id.txtInicio);
        txtInicioRedondo = (EditBackgroundHorario)findViewById(R.id.txtInicioRedondo);
        txtDestino = (EditBackgroundHorario) findViewById(R.id.txtDestino);
        numberPicker=(CustomNumberPicker) findViewById(R.id.NumberPicker);
        numberPickerRedondo=(CustomNumberPicker)findViewById(R.id.NumberPickerRedondo);
        txtDestinoRedondo = (EditBackgroundHorario) findViewById(R.id.txtDestinoRedondo);
        tvWeek = (TextView)findViewById(R.id.tvWeek);
        tvWeek1 = (TextView)findViewById(R.id.tvWeek1);
        tvWeek2 = (TextView)findViewById(R.id.tvWeek2);
        autoPicker=(AutoPicker)findViewById(R.id.autoPicker);
        switchViewSencillo = (SwitchView)findViewById(R.id.switchview);
        switchViewRedondo = (SwitchView)findViewById(R.id.switchview1);
        relativeRedondo = (RelativeLayout)findViewById(R.id.relativeRedondo);
        relativeSencillo =(RelativeLayout)findViewById(R.id.relativeSencillo);
        relativeCalendarRedondo=(RelativeLayout)findViewById(R.id.relativeCalendarRedondo);
        dialogRuta=new DialogRuta(this);
        dialogAutoPicker=new DialogAutoPicker(this);

    }

    @Override
    public void setListeners() {
        dialogDireccion = new DialogDireccion(PublicaActivity.this);
        dialogDireccion.setListener(PublicaActivity.this);
        switchViewSencillo.setOnCheckedChangeListener(this);
        switchViewRedondo.setOnCheckedChangeListener(this);
        relativeCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDias();
            }

        });
        relativeCalendarRedondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDias();
            }
        });
        dialogAutoPicker.setListener(this);
        autoPicker.setDialogAutoPicker(dialogAutoPicker);
        this.setAutocompleteListener();
    }

    @Override
    public void onCheckedChanged(SwitchView switchView, boolean b) {
        if(switchView.getId()==switchViewSencillo.getId()){
            checkIfGeocoding();
            boolean res= presenter.validarAventon(isRedondo,inicioByGeocoding,predictionInicio,predictionDestino,listDiasRedondo,listDias,selectedVehiculo);
            switchView.setChecked(res);
            if(res){
                showRedondo();
            }
        }
        else if(switchView.getId()==switchViewRedondo.getId()){
            hideRedondo();
        }
    }

    @Override
    public void setPresenter() {
        this.presenter=new PublicarPrensenterImpl();
        this.presenter.register(this);
        this.presenter.obtenerAutosListadoAsync();
    }

    private void hideRedondo(){
        relativeRedondo.setVisibility(View.GONE);
        relativeSencillo.setVisibility(View.VISIBLE);
        switchViewRedondo.setChecked(true);
        isRedondo=false;
    }

    private void showRedondo(){
        relativeRedondo.setVisibility(View.VISIBLE);
        relativeSencillo.setVisibility(View.GONE);
        txtDestinoRedondo.setText(txtDestino.getText());
        txtInicioRedondo.setText(txtInicio.getText());
        numberPickerRedondo.setSelectedValue(numberPicker.getSelectedValue());
        predictionInicioRedondo=predictionInicio;
        predictionDestinoRedondo=predictionDestino;
        tvWeek1.setText(tvWeek.getText());
        intercambiarDiasRedondo();
        switchViewSencillo.setChecked(false);
        isRedondo=true;
    }



    public void showDias(){
        dialogDiaPicker = new DialogDiaPicker(this, this);
        dialogDiaPicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDiaPicker.show();
    }

    /*LISTENERS QUE MUESTRAN EL DIALOGO DE LA DIRECCCIÓN*/
    private void setAutocompleteListener() {
        txtDestino.getTxtAutocomplete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDireccion.show();
                txtInicioClicked = false;
            }
        });
        txtInicio.getTxtAutocomplete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDireccion.show();
                txtInicioClicked = true;
            }
        });
    }

    public void PublicarAventon(View v) {
        checkIfGeocoding();

        if(presenter.validarAventon(isRedondo,inicioByGeocoding,predictionInicio,predictionDestino,
                listDiasRedondo,listDias,selectedVehiculo)){


            presenter.publicarAventon(isRedondo,predictionInicio,predictionDestino,
                    predictionInicioRedondo,predictionDestinoRedondo,
                    listDias,listDiasRedondo,selectedVehiculo);
        }
    }

    /*MÉTODO QUE REVISA SI ESTÁ POR GEOCODING EL DESTINO O EL ORGEN*/
    private void checkIfGeocoding(){

        if(predictionInicio==null || predictionDestino==null){
            return;
        }
        if (inicioByGeocoding) {
            predictionInicio = new Prediction();
            predictionInicio.setLatitude(geocodingResult.getCurrentLocation()[0]);
            predictionInicio.setLongitude(geocodingResult.getCurrentLocation()[1]);
        }
        predictionInicio.setHoraSalidaLlegada(txtInicio.getSelectedHora());
        predictionInicio.setCalendar(txtInicio.getDate());
        predictionDestino.setHoraSalidaLlegada(txtDestino.getSelectedHora());
        predictionDestino.setCalendar(txtDestino.getDate());

        if(selectedVehiculo!=null){
            selectedVehiculo.setAsientosDisponiblesIda(numberPicker.getSelectedValue());
            selectedVehiculo.setAsientosDisponiblesVuelta(numberPickerRedondo.getSelectedValue());
        }
        //SI ES UN VIAJE REDONDO
        if(isRedondo){
            predictionDestinoRedondo.setHoraSalidaLlegada(txtDestinoRedondo.getSelectedHora());
            predictionInicioRedondo.setHoraSalidaLlegada(txtInicioRedondo.getSelectedHora());
        }
    }

    @Override
    public void onDialogCompletedListener(Prediction selectedItem) {
        if (txtInicioClicked) {
            predictionInicio = selectedItem;
            txtInicio.getTxtAutocomplete().setText(Utils.getStringDetailPrediction(predictionInicio));
            inicioByGeocoding=false;
        } else {
            predictionDestino = selectedItem;
            txtDestino.getTxtAutocomplete().setText(Utils.getStringDetailPrediction(predictionDestino));
        }
    }

    @Override
    public void onPickerDirectionListener(boolean pickerDirectionLaunch) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        //TENEMOS RESULTADO DEL GEOCODING
        if (currentLatLng != null) {
            customProgressBar.show(this);
            builder.setLatLngBounds(new LatLngBounds(currentLatLng, currentLatLng));
            try {
                startActivityForResult(builder.build(this), Constants.PLACE_PIKER_REQUEST);
            } catch (Exception ex){
                customProgressBar.dismiss();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PLACE_PIKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                if (txtInicioClicked) {
                    txtInicio.getTxtAutocomplete().setText(place.getName());
                    predictionInicio = new Prediction(place.getId());
                    inicioByGeocoding=false;
                } else {
                    txtDestino.getTxtAutocomplete().setText(place.getName());
                    predictionDestino = new Prediction(place.getId());
                }
            }
            customProgressBar.dismiss();
        }
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }

    @Override
    public void onFinishDaySelection(ArrayList<Dia> listDias,String diasSeleccionado) {
        if(!listDias.isEmpty()){
            if(!isRedondo) {
                this.listDias = listDias;
                tvWeek.setText(diasSeleccionado);
            }
            else{
                this.listDiasRedondo=listDias;
                tvWeek2.setText(diasSeleccionado);
            }
            dialogDiaPicker.dismiss();
        }else{
            showToast(getString(R.string.selecciona_dia));
        }
    }

    private void intercambiarDiasRedondo(){
        this.listDiasRedondo=this.listDias;
        tvWeek2.setText(Utils.joinDias(listDias));
    }

    @Override
    public void showDialogoRuta() {
        dialogRuta.show();
    }
    @Override
    public void hideDialogoRuta() {
        dialogRuta.hide();
    }

    @Override
    public void updateTextoRuta(String status) {
        if (dialogRuta != null) {
            dialogRuta.setStatus(status);
        }
    }

    public void onCloseDaySelection() {
        dialogDiaPicker.dismiss();
    }

    @Override
    public void mostrarPosicionValida(Prediction prediction) {
        this.predictionDestino=prediction;
        this.txtDestino.setText(prediction.getDescription());
    }

    @Override
    public void onAventonGenerated(PublicaResponse response) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY,response);
        Intent intent=new Intent(this, RouteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onVehiculoSeleccionado(Vehiculo selectedVehiculo) {
        this.selectedVehiculo=selectedVehiculo;
        autoPicker.setText(this.selectedVehiculo.getModelo());
        numberPicker.setSelectedValue(selectedVehiculo.getAsientosDisponibles());
        numberPicker.setMaxValue(selectedVehiculo.getAsientosDisponibles());
        numberPickerRedondo.setMaxValue(selectedVehiculo.getAsientosDisponibles());
    }

    @Override
    public void setListVehiculo(List<Vehiculo> list) {
        if(!list.isEmpty()) {
            this.selectedVehiculo=list.get(0);
            autoPicker.setText(selectedVehiculo.getModelo());
            autoPicker.setListAutos(list);
            numberPicker.setSelectedValue(selectedVehiculo.getAsientosDisponibles());
            numberPicker.setMaxValue(selectedVehiculo.getAsientosDisponibles());
        }
    }
    @Override
    public void onLocationGenerated(LatLng latLng) {
        presenter.sugerenciaPosiciones(latLng.latitude,latLng.longitude);
    }
}

