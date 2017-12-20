package com.sociomas.aventones.UI.Activities.QrScan;

import com.sociomas.aventones.R;
import com.sociomas.aventones.Utils.ApplicationAventon;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.QrDbHelper;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.Aventon.ControllerAventon;
import com.sociomas.core.WebService.Model.Request.Alta.AltaQR;
import com.sociomas.core.WebService.Model.Request.Alta.ServerResponseAventones;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 13/10/2017.
 */

public class QRScanPresenterImpl extends BasePresenterImpl implements QRScanPresenter {

    private ControllerAventon controllerAventon;
    private QRScanView view;
    private ResultadoScan result;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(QRScanView)view;
    }


    public void enviarQrDatos(){
            if(result==null){
                onShowToast(R.string.error_ubicacion);
                return;
            }
            else if(result.getLatLngResult()==null){
                onShowToast(R.string.error_ubicacion);
                return;
            }
            onShowProgress();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_AVENTON);
            final AltaQR altaQR = new AltaQR();
            final QrDbHelper dbUtils=new QrDbHelper(getView().getActivityInstance());
            altaQR.setAccuracy(result.getAccuracy());
            altaQR.setLatitud(result.getLatLngResult().latitude);
            altaQR.setLongitud(result.getLatLngResult().longitude);
            altaQR.setDatosQR(result.getResultadoQr());
            altaQR.setFechaDispositivo(dateFormat.format(calendar.getTime()));

            if(Utils.hasInternet(getView().getActivityInstance())) {
                ApplicationAventon.getIntance().getControllerAventon().insertarQR(altaQR).enqueue(new Callback<ServerResponseAventones>() {
                    @Override
                    public void onResponse(Call<ServerResponseAventones> call, Response<ServerResponseAventones> response) {
                        if (response.isSuccessful()) {
                            onShowToast(response.body().getMensaje());
                        }
                        else{
                            onShowToast(R.string.qr_pendiente_mensaje);
                            dbUtils.agregarTagQR(altaQR.toPendiente());
                        }
                        onHideProgress();
                    }

                    @Override
                    public void onFailure(Call<ServerResponseAventones> call, Throwable t) {
                        onHideProgress();
                        onShowToast(R.string.qr_pendiente_mensaje);
                        dbUtils.agregarTagQR(altaQR.toPendiente());
                    }
                });
        }
        else {
                onHideProgress();
                onShowToast(R.string.qr_pendiente_mensaje);
                dbUtils.agregarTagQR(altaQR.toPendiente());
            }

    }


    @Override
    public void setResultadoCoordenadas(ResultadoScan r) {
        this.result=r;
    }
    public interface QRScanView extends BaseView {
        void requestGeocodingAsync();
    }
}
