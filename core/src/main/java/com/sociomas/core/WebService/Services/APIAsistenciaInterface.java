package com.sociomas.core.WebService.Services;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaRequest;
import com.sociomas.core.WebService.Model.Response.Asistencia.AsistenciaResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by oemy9 on 10/04/2017.
 */

public interface APIAsistenciaInterface {
    @POST("ObtenerReporteAsistencia")
    Call<AsistenciaResponse>obtenerReporte(@Body AsistenciaRequest request);
}
