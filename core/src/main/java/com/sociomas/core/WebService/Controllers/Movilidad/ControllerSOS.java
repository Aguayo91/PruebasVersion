package com.sociomas.core.WebService.Controllers.Movilidad;
import android.content.Context;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;
import com.sociomas.core.WebService.Services.APISOSInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Created by oemy9 on 10/04/2017.
 */
public class ControllerSOS implements APISOSInterface {
    private Context context;
    private APISOSInterface service;
    public ControllerSOS(Context context){
        this.context=context;
         Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl(Constants.DOMAIN_URL).build();
        this.service=retrofit.create(APISOSInterface.class);
    }
    @Override
    public Call<ServerResponse> SOSEnvio(@Body SosRequest request) {
        SecurityItems securityItems=new SecurityItems(Utils.getNumeroEmpleado(this.context));
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setId_dispositivo(Utils.getDeviceID(this.context));
        return service.SOSEnvio(request);
    }
}
