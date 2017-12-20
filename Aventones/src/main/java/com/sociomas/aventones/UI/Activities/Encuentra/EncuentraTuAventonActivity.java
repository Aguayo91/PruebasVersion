package com.sociomas.aventones.UI.Activities.Encuentra;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sociomas.core.Listeners.CallBackLocationGenerated;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Disponibles.AventonesDisponiblesActivity;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogDireccion;
import com.sociomas.aventones.UI.Controls.EditTexts.EditBackgroundHorario;
import com.sociomas.aventones.UI.Controls.EditTexts.EditTextBackground;
import com.sociomas.aventones.UI.Controls.SeekBarkRangoProgressListener;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.aventones.Utils.Utils;
import com.sociomas.core.Listeners.DialogDirectionCompletedListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseLocationActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.Aventones.ConsultaAventonesResponse;
import com.sociomas.core.WebService.Model.Response.Geocoding.GeocodingResult;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class EncuentraTuAventonActivity extends BaseLocationActivity implements EncuentraPresenterImpl.EncuentraView,
        DialogDirectionCompletedListener, CallBackLocationGenerated {

    private EncuentraPresenter presenter;
    private RelativeLayout Calendar2;
    private EditTextBackground txtInicio;
    private EditBackgroundHorario txtDestino;
    private boolean inicioByGeocoding =false;
    private boolean txtInicioClicked=false;
    private Prediction predictionInicio, predictionDestino;
    private DialogDireccion dialogDireccion;
    private GeocodingResult geocodingResult;
    private DiscreteSeekBar dsSeekbar;
    private TextView tvTitleRango;
    private RelativeLayout Relative1,Relative2;
    private LatLng latLngOrigen,latLngDestino;
    private SeekBarkRangoProgressListener seekbarRangoListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuentra_tu_aventon);
        setToolBar(R.string.encuentra_aventon);
        ApplicationAventon.onCreate(getApplicationContext());
        ApplicationAventon applicationAventon=new ApplicationAventon();
        applicationAventon.onCreate(this);
        setCallBackLocationGenerated(this);
        setPresenter();
    }

    @Override
    public void initView() {
        Calendar2 = (RelativeLayout)findViewById(R.id.RelativeCalendar2);
        Relative1=(RelativeLayout)findViewById(R.id.Relative1);
        Relative2=(RelativeLayout)findViewById(R.id.Relative2);
        txtDestino = (EditBackgroundHorario) findViewById(R.id.txtDestino);
        txtInicio =(EditTextBackground)findViewById(R.id.txtInicio);
        dsSeekbar =(DiscreteSeekBar)findViewById(R.id.dsSeekbar);
        txtInicio.getTxtAutocomplete().setFocusable(false);
        txtInicio.getTxtAutocomplete().setCursorVisible(false);
        txtDestino.getTxtAutocomplete().setFocusable(false);
        txtDestino.getTxtAutocomplete().setCursorVisible(false);
        tvTitleRango=(TextView)findViewById(R.id.tvTitleRango);
    }

    @Override
    public void setListeners() {
        dialogDireccion = new DialogDireccion(EncuentraTuAventonActivity.this);
        dialogDireccion.setListener(EncuentraTuAventonActivity.this);
        setAutocompleteListener();
        seekbarRangoListener=new SeekBarkRangoProgressListener();
        dsSeekbar.setOnProgressChangeListener(seekbarRangoListener);
    }

    @Override
    public void setPresenter() {
        presenter=new EncuentraPresenterImpl();
        presenter.register(this);
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }



    private void setAutocompleteListener() {
        txtDestino.getTxtAutocomplete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDireccion.show();
                dialogDireccion.changeHintText(getString(R.string.elige_destino));
                txtInicioClicked = false;
            }
        });
        txtInicio.getTxtAutocomplete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDireccion.show();
                dialogDireccion.changeHintText(getString(R.string.elige_origen));
                txtInicioClicked = true;
            }
        });
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

    /*MÉTODO QUE REVISA SI ESTÁ POR GEOCODING EL DESTINO O EL ORGEN*/
    private void checkIfGeocoding(){
        if (inicioByGeocoding) {
            predictionInicio=new Prediction();
            predictionInicio.setLatitude(geocodingResult.getCurrentLocation()[0]);
            predictionInicio.setLongitude(geocodingResult.getCurrentLocation()[1]);
            predictionInicio.setIsPlace(false);
        }
        if(predictionDestino!=null) {
            predictionDestino.setHoraSalidaLlegada(txtDestino.getSelectedHora());
        }
    }

      //Se reliza la consulta al webservice
      public void enviarBack(View view) {
          checkIfGeocoding();
          presenter.buscarAventon(predictionInicio,predictionDestino,seekbarRangoListener.getRangoBusqueda());
      }

    public void back(View view){
              Relative1.setVisibility(View.VISIBLE);
              Relative2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setListAventon(ConsultaAventonesResponse aventonesResponse) {
        Intent intent = new Intent(EncuentraTuAventonActivity.this, AventonesDisponiblesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ViewEvent.ENTRY, aventonesResponse);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void mostrarCaraTriste() {
        Relative1.setVisibility(View.INVISIBLE);
        Relative2.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarPosicionValida(Prediction prediction) {

    }


    @Override
    public void onLocationGenerated(LatLng latLng) {

    }
}