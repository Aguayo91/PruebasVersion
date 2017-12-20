package com.sociomas.core.WebService.Services;


import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Request.SOS.SosRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by oemy9 on 10/04/2017.
 */

public interface APISOSInterface {
    @POST(Constants.AgregarEmpleado_ArchivoClipMultimedia)
     Call<ServerResponse>SOSEnvio(@Body SosRequest request);
}
