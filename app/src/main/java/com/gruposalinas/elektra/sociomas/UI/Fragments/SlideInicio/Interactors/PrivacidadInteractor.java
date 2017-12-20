package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Interactors;
import com.sociomas.core.Utils.DbUtils.DBUtils;
import com.sociomas.core.Utils.Enums.EnumTiposAviso;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.WebService.CallBacks.CallBackBaseResponse;
import com.sociomas.core.WebService.Model.Request.Privacidad.AvisoRequest;
import com.sociomas.core.WebService.Model.Request.ServerRequest;
import com.sociomas.core.WebService.Model.Response.Privacidad.Aviso;
import com.sociomas.core.WebService.Model.Response.Privacidad.PrivacidadResponse;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 20/11/2017.
 */

public class PrivacidadInteractor  extends BaseInteractor{


    public DBUtils dbUtils;

    public PrivacidadInteractor() {
        super(ApplicationBase.getIntance().getApplicationContext(), ApplicationBase.getIntance().getControllerAPI());
        this.dbUtils=new DBUtils(getContext());
    }

    public DBUtils getDbUtils() {
        return dbUtils;
    }


    public void obtenerAvisosAsync(EnumTiposAviso tiposAviso, Callback<PrivacidadResponse>callback){
      getControllerAPI().devuelveAviso(new ServerRequest()).enqueue(callback);
    }

    public void aceptarAvisoAsync(Aviso currentAviso, final CallBackBaseResponse callBackBase){
        AvisoRequest request=new AvisoRequest(1,currentAviso.getIdTipoAviso(),currentAviso.getVersion());
        getControllerAPI().insertarPosicionesAviso(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        callBackBase.onSuccess(response.body());
                    }
                    else {
                        callBackBase.OnError(new Throwable(response.body().getMensajeError()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }

}
