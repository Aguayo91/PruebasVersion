package com.sociomas.aventones.UI.Activities.QrScan;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.google.zxing.Result;
import com.sociomas.core.Services.LocationUIService;
import com.sociomas.aventones.UI.Activities.ViewQr;
import com.sociomas.core.Async.GeocodingAsync;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.UI.Activities.BaseCoreCompactActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Geocoding.GeocodingResult;


public class QrScanActivity extends BaseCoreCompactActivity implements QRScanPresenterImpl.QRScanView, ViewQr.ResultHandler, GeocodingAsync.GeocodingInterface {

    private ViewQr mScannerView;
    public Double lat,lng,acc ;
    private GeocodingResult geocodingResult;
    double[] currentLatLng = null;
    private ResultadoScan r;
    private QRScanPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initQrReader();
        requestGeocodingAsync();
    }
    private void initQrReader(){
        mScannerView.startCamera();
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
    }

    @Override
    public void initView() {
        mScannerView = new ViewQr(this);
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void presentEvent(ViewEvent event) {
        super.presentEvent(event);
    }

    /*DESPUÉS DE ESCANEAR*/
    @Override
    public void handleResult(Result rawResult) {
        r.setResultadoQr(rawResult.getText());
        presenter.setResultadoCoordenadas(r);
        onBackPressed();
    }

    @Override
    public void OnGeocodingFinish(GeocodingResult result) {
        this.geocodingResult = result;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mScannerView.stopCameraPreview();
        mScannerView.stopCamera();
        presenter.enviarQrDatos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initQrReader();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mScannerView.stopCamera();
        mScannerView.stopCameraPreview();
    }

    @Override
    public void requestGeocodingAsync() {
        BroadcastReceiver bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROAD_CAST_LOCATION)) {
                    if(intent.hasExtra(Constants.CURRENT_LATLONG) && intent.hasExtra(Constants.CURRENT_ACCURACY)) {
                        r.setValores(intent.getDoubleArrayExtra(Constants.CURRENT_LATLONG));
                        r.setAccuracy(intent.getFloatExtra(Constants.CURRENT_ACCURACY, 0));
                    }
                }
            }
        };
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROAD_CAST_LOCATION);
        bManager.registerReceiver(bReceiver, intentFilter);
        startService(new Intent(QrScanActivity.this, LocationUIService.class));
    }

    @Override
    public void setPresenter() {
        presenter = new QRScanPresenterImpl();
        presenter.register(this);
        r=new ResultadoScan();
    }

    @Override
    public Activity getActivityInstance() {
        return this;
    }






}
