package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.sociomas.aventones.R;
import com.sociomas.core.Listeners.CallBackLatLngGenerated;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDetailPlace;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDirections;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAPIGoogle;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import com.sociomas.core.WebService.Model.Response.DetailPlace.DetailResponse;
import com.sociomas.core.WebService.Model.Response.DetailPlace.Location;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by oemy9 on 30/08/2017.
 */

public class DialogRuta extends Dialog  implements CallBackLatLngGenerated   {
    private Context context;
    private Prediction predictionInicio, predictionDestino;
    private ControllerAPIGoogle controllerAPIGoogle;
    private ControllerAPI controllerAPI;
    private CallBackDirections callBackDirections;
    private CallBackLatLngGenerated callBackLatLngGenerated;
    private TextView tvRutaStatus;
    private int count=0;

    public DialogRuta(Context context){
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.context=context;
        this.controllerAPIGoogle=new ControllerAPIGoogle(this.context);
        this.controllerAPI=new ControllerAPI(this.context);
        this.setCallBackLatLngGenerated(this);
    }

    public DialogRuta setPredicitonInicio(Prediction predictionInicio){
        this.predictionInicio=predictionInicio;
        return this;
    }
    public DialogRuta setPredictionDestino(Prediction predictionDestino)
    {
        this.predictionDestino=predictionDestino;
        return this;
    }
    public DialogRuta setCallBack(CallBackDirections callBack){
        this.callBackDirections=callBack;
        return this;
    }


    public void setCallBackLatLngGenerated(CallBackLatLngGenerated callBackLatLngGenerated) {
        this.callBackLatLngGenerated = callBackLatLngGenerated;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
        PulsatorLayout pulsator = (PulsatorLayout)findViewById(R.id.pulsator);
        tvRutaStatus=(TextView)findViewById(R.id.tvRutaStatus);
        pulsator.start();
        //onPlaceGenerate(predictionInicio);
        //onPlaceGenerate(predictionDestino);
        setDialogParameters();
    }

    public void setStatus(String status){
        this.tvRutaStatus.setText(status);
    }

    public void onPlaceGenerate(final Prediction prediction){
        if(prediction.isPlace() && prediction.getPlaceId()!=null) {
            this.controllerAPIGoogle.getDetailPlace(prediction.getPlaceId(), new CallBackDetailPlace() {
                @Override
                public void onSuccess(DetailResponse response) {
                    count++;
                    Location location=response.getResult().getGeometry().getLocation();
                    prediction.setLatitude(location.getLat());
                    prediction.setLongitude(location.getLng());
                    callBackLatLngGenerated.onGenerated(true);
                }

                @Override
                public void OnError(Throwable error) {

                }
            });
        }
        else{
            count++;
            callBackLatLngGenerated.onGenerated(true);
        }
    }

    public void generarRuta(){
        if(callBackDirections!=null) {
            controllerAPIGoogle.getDirections(new LatLng(predictionInicio.getLatitude(), predictionInicio.getLongitude()),
                    new LatLng(predictionDestino.getLatitude(), predictionDestino.getLongitude()), callBackDirections);
        }
    }

    private void setDialogParameters(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.softInputMode=WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        lp.gravity=Gravity.CENTER;
        getWindow().setAttributes(lp);

    }
    @Override
    public void onGenerated(boolean generated) {
        if(count==2){
            generarRuta();
        }
    }
}
