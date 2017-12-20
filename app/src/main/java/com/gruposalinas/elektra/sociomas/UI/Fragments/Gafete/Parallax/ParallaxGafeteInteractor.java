package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Parallax;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.ConstantsV2;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.CallBacks.CallBackBase;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteImagenRequest;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafeteImagen;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 22/11/2017.
 */

public class ParallaxGafeteInteractor extends BaseInteractor {

    //SHAREDPREFERENCES DE GAFETE
    SessionManager manager;

    public ParallaxGafeteInteractor(){
        super(ApplicationBase.getIntance().getApplicationContext(), ApplicationBase.getIntance().getControllerAPI());
        manager=ApplicationBase.getIntance().getManager();
    }

    public interface  CallBackGafete extends CallBackBase{
        void onSuccess(ResponseGafeteImagen gafeteImagen);
    }

    /**
     * Revisa si  existe el gafete en sharedPreferences, en caso contrario hace la petición al servicio
     * @param request
     * @param callBackGafete
     */
    public void getGafeteAsync(GafeteImagenRequest request, final CallBackGafete callBackGafete){
        //¿EXISTE EN EL CACHE?
        if(!TextUtils.isEmpty( manager.getString(Constants.JSON_GAFETE_RESPONSE))){
           ResponseGafeteImagen response=new Gson().fromJson(manager.getString(Constants.JSON_GAFETE_RESPONSE), ResponseGafeteImagen.class);
           callBackGafete.onSuccess(response);
        }
        //NO EXISTE SE HACE LA PETICIÓN AL WS
        else {
            requestGafeteAsync(request,callBackGafete);
        }
    }

    /**
     * Hace la petición al servicio para obtener gafete
     * @param request
     * @param callBackGafete
     */
    public void requestGafeteAsync(GafeteImagenRequest request, final CallBackGafete callBackGafete){
        getControllerAPI().obtenerImagenesGafete(request).enqueue(new Callback<ResponseGafeteImagen>() {
            @Override
            public void onResponse(Call<ResponseGafeteImagen> call, Response<ResponseGafeteImagen> response) {
                if (response.isSuccessful()) {
                     //EXISTE LA FOTO EN BASE64 DEL USUARIO
                    if(response.body().isExisteFoto()) {
                        manager.add(Constants.JSON_GAFETE_RESPONSE, new Gson().toJson(response.body()));
                        manager.add(Constants.HAS_GAFETE,true);
                    }
                    else{
                        manager.add(Constants.HAS_GAFETE,false);
                    }
                    if(!TextUtils.isEmpty(response.body().getPuestoEmpleado())){
                        manager.add(Constants.PUESTO_EMPLEADO, response.body().getPuestoEmpleado());
                    }

                    callBackGafete.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseGafeteImagen> call, Throwable t) {
                callBackGafete.OnError(t);
            }
        });
    }
}
