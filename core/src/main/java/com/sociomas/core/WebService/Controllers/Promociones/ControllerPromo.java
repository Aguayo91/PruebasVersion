package com.sociomas.core.WebService.Controllers.Promociones;

import android.content.Context;

import com.sociomas.core.BuildConfig;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Model.Request.Promo.PromoRequest;
import com.sociomas.core.WebService.Model.Response.Promociones.PromoResponse;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import com.sociomas.core.WebService.Services.APIPromocionesInterface;
import com.sociomas.core.WebService.Services.APIServicePromo;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Body;

/**
 * Created by oemy9 on 06/11/2017.
 */

public class ControllerPromo extends ControllerBase  implements APIServicePromo{

    private Context ctx;
    private APIServicePromo service;

    public ControllerPromo(Context ctx){
        super(ctx, BuildConfig.URL_BASE_PROMO, Utils.hostnameVerifier(),true);
        service=retrofit.create(APIServicePromo.class);
    }


    @Override
    public Observable<PromoResponse> obtenerCategorias(@Body PromoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setListParametros(null);
        return service.obtenerCategorias(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PromoResponse> obtenerDetalleCategoria(@Body PromoRequest request) {
        SecurityItems securityItems=getInstanceToken();
        request.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        return service.obtenerDetalleCategoria(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
