package com.sociomas.core.WebService.Services;
import com.sociomas.core.WebService.Model.Request.Promo.PromoRequest;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by oemy9 on 06/11/2017.
 */

public interface APIServicePromo  {
    @POST("ObtenerCategorias")
    Observable<PromoResponse>obtenerCategorias(@Body PromoRequest request);
    @POST("ObtenerDetalleCategoria")
    Observable<PromoResponse>obtenerDetalleCategoria(@Body PromoRequest request);
}
