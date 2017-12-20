package com.gruposalinas.elektra.sociomas.UI.Activities.Jornada;

import com.google.android.gms.maps.model.LatLng;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumJornada;
import com.sociomas.core.Utils.Enums.EnumLocationProvider;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaManualRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 18/11/2017.
 */

public class JornadaPresenterImpl extends BasePresenterImpl implements JornadaPresenter, Callback<ServerResponse> {

    private EnumJornada currentJornada;
    private JornadaView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(JornadaView)view;
    }


    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if(response.isSuccessful()){
                if(!response.body().getError()){
                    view.onSuccessRegistro(currentJornada);
                }
                else{
                    onShowToast(R.string.Error_Conexion);
                }
        }
        onHideProgress();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        onShowToast(R.string.Error_Conexion);
        onHideProgress();
    }

    @Override
    public void ingresarAsistenciaManual(EnumJornada jornada, LatLng location) {
        onShowProgress();
        this.currentJornada=jornada;
        AsistenciaManualRequest request=new AsistenciaManualRequest();
        String zonaHorario = ApplicationBase.getIntance().getManager().getString(Constants.TIME_ZONE_NAME);
        request.setIdTipoRegistroManualPosicion(this.currentJornada.getValue());
        request.setDecBateria(String.valueOf(Utils.getBatteryLevel(ApplicationBase.getIntance().getApplicationContext())));
        request.setZonahorario(zonaHorario);
        request.setProvider(EnumLocationProvider.GPS.getValue());
        request.setDecLatitud(location!=null? String.valueOf(location.latitude): "0.0");
        request.setDecLongitud(location!=null? String.valueOf(location.longitude):"0.0");
        ApplicationBase.getIntance().getControllerAPI().ingresarAsistenciaManual(request).enqueue(this);

    }

    public interface  JornadaView extends BaseView{
            void onSuccessRegistro(EnumJornada jornada);
    }
}
