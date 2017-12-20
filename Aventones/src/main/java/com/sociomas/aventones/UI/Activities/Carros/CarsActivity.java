package com.sociomas.aventones.UI.Activities.Carros;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Preferencias.PreferencesActivity;
import com.sociomas.aventones.UI.Adapters.AutosRecyclerAdapter;
import com.sociomas.aventones.UI.Controls.DialogColorPalette;
import com.sociomas.aventones.UI.Controls.Dialogs.DialogCars;
import com.sociomas.aventones.UI.Controls.EditTexts.CustomNumberPicker;
import com.sociomas.aventones.UI.Controls.EditTexts.EditTextBackground;
import com.sociomas.aventones.UI.Controls.EditTexts.EditTextMaxLength;
import com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.Listeners.RecyclerItemClickListener;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.UI.Controls.Notification.Alertas;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import java.util.ArrayList;
import java.util.List;
public class CarsActivity extends BaseCoreCompactActivity implements RecyclerItemClickListener, CarsPresenterImpl.CarsView {

    private CarPresenter presenter;
    private Vehiculo selectedVehiculo;
    private TextView btnColor;
    private String TextAuto;
    private ImageView imgColor,imgInfoPlacas,imgInfoCapacidad;
    private EditTextMaxLength txtPlacas;
    private EditTextBackground txtAutos;
    private RecyclerView recyclerView;
    private CustomNumberPicker customNumberPicker;
    private AutosRecyclerAdapter  mAdapter;
    private ArrayList<Vehiculo> autosRecycler=new ArrayList<Vehiculo>();
    private ControllerAventon controllerAventon=new ControllerAventon(this);
    private String selectedColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automoviles);
        this.setToolBar(getString(R.string.Automovil));
        ApplicationAventon.onCreate(getApplicationContext());
        setPresenter();
    }


    @Override
    public void initView() {
        imgColor = (ImageView) findViewById(R.id.imgColor);
        btnColor = (TextView) findViewById(R.id.btnColor);
        txtPlacas=(EditTextMaxLength) findViewById(R.id.txtPlacas);
        txtAutos=(EditTextBackground) findViewById(R.id.txtAutomoviles);
        customNumberPicker=(CustomNumberPicker)findViewById(R.id.NumberPicker);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        txtAutos = (EditTextBackground)findViewById(R.id.txtAutomoviles);
        txtPlacas =(EditTextMaxLength) findViewById(R.id.txtPlacas);
        customNumberPicker = (CustomNumberPicker)findViewById(R.id.NumberPicker);
        imgInfoPlacas=(ImageView)findViewById(R.id.imgInfoPlacas);
        imgInfoCapacidad=(ImageView)findViewById(R.id.imgInfoCapacidad);
        // showTipPlacayCapacidad();
    }

    @Override
    public void setListeners() {
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColors();
            }
        });
    }

    @Override
    public void setPresenter() {
        presenter=new CarsPresenterImpl();
        presenter.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setArguments(getIntent());
    }

    //Usuario da de alta un nuevo automovil al RecyclerView
    public void AgregarAuto(final View view) {
        agregarAutoAsync(false);
    }

    private void agregarAutoAsync(boolean navegarPreferencias){
        String auto, placa;
        int cap;
        auto = txtAutos.getText();
        placa = txtPlacas.getText();
        cap = customNumberPicker.getSelectedValue();
        if (selectedVehiculo != null) {
            selectedVehiculo.setNumeroAsientos(cap);
            selectedVehiculo.setPlacas(placa);
            selectedVehiculo.setModelo(auto);
            selectedVehiculo.setCodigoColor(selectedColor);
        } else {
            selectedVehiculo = new Vehiculo(selectedColor, auto, cap, placa);
        }
        presenter.agregarEditarAuto(selectedVehiculo,navegarPreferencias);
    }
        //metodo que se utiliza para mostrar colores desde el dialogo del color picker
        public void showColors () {
            DialogColorPalette dialogColorPalette = new DialogColorPalette(this);
            dialogColorPalette.setColor2(new DialogColorPalette.ColorListener2() {
                @Override
                public void OnColorListener(String colorseleccionado) {
                    if (!colorseleccionado.isEmpty()) {
                        btnColor.setBackgroundColor(Color.parseColor(colorseleccionado));
                        btnColor.setTextColor(Utils.obtenerReverse(Color.parseColor(colorseleccionado)));
                        selectedColor=colorseleccionado;
                    }
                }
            });
            dialogColorPalette.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogColorPalette.show();

        }
    //Se utiliza para editar los datos del automóvil
    @Override
    public void OnItemClickListener(int position, Object selectedItem) {
        this.selectedVehiculo=(Vehiculo)selectedItem;
        txtPlacas.setText(selectedVehiculo.getPlacas());
        txtAutos.setText(selectedVehiculo.getModelo());
        selectedColor=selectedVehiculo.getCodigoColor();
        btnColor.setBackgroundColor(Utils.getParseColor(this.selectedVehiculo.getCodigoColor()));
        customNumberPicker.setSelectedValue(Integer.valueOf(selectedVehiculo.getNumeroAsientos()));
    }
    //Cambiar a la siguiente Activity
    public void Next(View view) {
      agregarAutoAsync(true);
    }

    @Override
    public void setListVehiculo(List<Vehiculo> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new AutosRecyclerAdapter(this,list);
        mAdapter.setItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAutoModeloError(@StringRes int errorMsg) {
        txtAutos.setError(getString(errorMsg));
    }

    @Override
    public void onAutoPlacaError(@StringRes int errorMsg) {
        txtPlacas.setError(getString(errorMsg));
    }

    @Override
    public void onCleanCampos() {
        txtAutos.clearText();
        txtPlacas.clearText();
        customNumberPicker.setSelectedValue(1);
        selectedColor=null;
        selectedVehiculo=null;
    }

    @Override
    public void onAutoColorError(@StringRes int errorMsg) {
        showToast(getString(errorMsg));
    }

    @Override
    public void navegarPreferencias() {
        Intent intent=new Intent(this,PreferencesActivity.class);
        startActivity(intent);
    }

    //Se utiliza para mostrar el dialogo de placas
    public void infoPlacas (View view){
        //   DialogCars dialogo = new DialogCars(this);
        //   dialogo.showDialogPlacas();
        com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip
                .on(imgInfoPlacas)
                .autoHide(true, 1000)
                .corner(30)
                .textColor(Color.WHITE)
                .color(Color.BLACK)
                .position(com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip.Position.BOTTOM)
                .text(getString(R.string.introducePlaca))
                .show();
    }
    //se utiliza para mostrar el dialogo de informacion de capacidad de asientos disponibles
    public void infoCapacidad (View view){
        //  DialogCars dialogo = new DialogCars(this);
        //  dialogo.showDialogCapacidad();
        com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip
                .on(imgInfoCapacidad)
                .autoHide(true, 1000)
                .corner(30)
                .textColor(Color.WHITE)
                .color(Color.BLACK)
                .align(ViewTooltip.ALIGN.CENTER)
                .position(com.sociomas.aventones.UI.Controls.ToolTip.ViewTooltip.Position.BOTTOM)
                .text(getString(R.string.introduceCapacidad))
                .show();
    }


   /* //Muestra un mensaje al usuario con recomendaciones
    private void showTipPlacayCapacidad() {
        ShowTipsView showtips = new ShowTipsBuilder(this)
                .setTarget(imgColor).setTitle(getString(R.string.placa_tip_title))
                .setDescription(getString(R.string.tip_description))
                .setBackgroundAlpha(128).setDelay(800)
                .setCloseButtonColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setCloseButtonTextColor(Color.WHITE)
                .setButtonText(getString(R.string.entendido))
                .build();

        showtips.show(this);
    }*/

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
        switch (event.getEventType()) {
            case SHOW_DIALOG_EVENT_TYPE: {
                int id = (int) event.getModel().get(ViewEvent.RESOURCE_ID);
                if (id == R.id.RecyclerView) {
                    final boolean navegarPreferencias = (boolean) event.getModel().get(ViewEvent.BOOLEAN_OBJECT);
                    String msg = (String) event.getModel().get(ViewEvent.MESSAGE);
                    showMsgDialog(CarsActivity.this, "Aviso", msg,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(navegarPreferencias) {
                                        navegarPreferencias();
                                    }
                                    else{
                                        presenter.obtenerAutosListadoAsync();
                                    }
                                    dialog.dismiss();
                                }
                            }, getString(R.string.aceptar));
                }
            }
            break;
        }
    }
}