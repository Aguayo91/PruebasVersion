package com.sociomas.aventones.UI.Activities.Inicio;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.sociomas.aventones.R;
import com.sociomas.aventones.UI.Activities.Carros.CarsActivity;
import com.sociomas.aventones.UI.Activities.Encuentra.EncuentraTuAventonActivity;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Request.Alta.Vehiculo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AventonInicioActivity extends BaseCoreCompactActivity implements AventonPresenterImpl.AventonView {

    private AventonPresenter presenter;
    private  int animationDuration = 1000;
    private CircleProgressbar circleProgressbar, circleProgressbar2;
    private List<Vehiculo>listVehiculo=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        this.setToolBar("Elige una opción");
        ApplicationAventon.onCreate(getApplicationContext());
        setPresenter();
    }

    //Reiniciamos la animacion del circleProgressBar
    @Override
    public void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_buttons);
        circleProgressbar = (CircleProgressbar)findViewById(R.id.circle_loading);
        circleProgressbar2 = (CircleProgressbar)findViewById(R.id.circle_loading2);
        circleProgressbar.setProgressWithAnimation(0);
        circleProgressbar2.setProgressWithAnimation(0);
    }

    @Override
    public void initView() {
        circleProgressbar = (CircleProgressbar)findViewById(R.id.circle_loading);
        circleProgressbar2 = (CircleProgressbar)findViewById(R.id.circle_loading2);
    }

    @Override
    public void setPresenter() {
        presenter=new AventonPresenterImpl();
        presenter.register(this);
        presenter.obtenerAutosListadoAsync();
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    //Vamos a la activity de "Publica tu aventón"
    public void publica (View view){
        circleProgressbar.setClockwise(false);
        circleProgressbar.setProgressWithAnimation(100, animationDuration);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AventonInicioActivity.this,CarsActivity.class);
                if(listVehiculo!=null && (!listVehiculo.isEmpty())){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constants.SELECTED_AUTOS, (Serializable)listVehiculo);
                    intent.putExtras(bundle);
                }
                startActivity(intent);

            }
        },500);

    }
    //Vamos a la activity de "Encuentra tú aventón"
    public void encuentra(View view){
        circleProgressbar2.setClockwise(false);
        circleProgressbar2.setProgressWithAnimation(100, animationDuration);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AventonInicioActivity.this,EncuentraTuAventonActivity.class);
                startActivity(intent);

            }
        },500);

    }

    @Override
    public void setListVehiculo(List<Vehiculo> list) {
        this.listVehiculo=list;
    }
}