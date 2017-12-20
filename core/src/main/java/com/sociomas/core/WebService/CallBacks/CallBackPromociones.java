package com.sociomas.core.WebService.CallBacks;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import java.util.ArrayList;

/**
 * Created by oemy9 on 17/08/2017.
 */

public interface CallBackPromociones extends CallBackBase {
    void OnSuccess(ArrayList<PromocionesResponse> listPromociones);
}
