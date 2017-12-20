package com.sociomas.core.WebService.Services;
import com.sociomas.core.WebService.Model.Request.Promociones.PromocionesRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by oemy9 on 17/08/2017.
 */

public interface APIPromocionesInterface {
    @POST("ServicioGet")
    Call<ResponseBody> obtenerPromociones(@Body PromocionesRequest request);

}
