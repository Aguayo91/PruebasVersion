package com.sociomas.core.WebService.Controllers.Movilidad;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sociomas.core.R;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackPromociones;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Model.Request.Promociones.PromocionesRequest;
import com.sociomas.core.WebService.Model.Response.Promociones.PromocionesResponse;
import com.sociomas.core.WebService.Services.APIPromocionesInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 10/04/2017.
 */
public class ControllerPromociones extends ControllerBase {
    private APIPromocionesInterface service;
    private CallBackPromociones callBackPromociones;
    public void setCallBackPromociones(CallBackPromociones callBackPromociones) {this.callBackPromociones = callBackPromociones;}
    public ControllerPromociones(Context context, String urlBase) {
        super(context, urlBase);
    }
    public ControllerPromociones(Context context){
        super(context, Constants.DOMAIN_URL_PROMOCIONES,Utils.hostnameVerifierPromociones());
        this.service=retrofit.create(APIPromocionesInterface.class);
    }
    public  void obtenerCategoriasAsync(){
        PromocionesRequest request=new PromocionesRequest();
        request.setParametros("[{valor: 'ELEKTRA'}, {valor: 'null'}, {valor: '99'}, {valor: '99'}]");
        request.setUrl("XOY6sh8pCCeV4xkr4TliUfh18EEPgvWj+jjqoFjXovPu6W3jNKvYe6+xfG6nFS85Zkod1u0Jqvwn8vXjJ6c5NA==");
        service.obtenerPromociones(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        readJsonAsync(response.body().string(),true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t!=null){
                    Toast.makeText(context,context.getString(R.string.Error_Conexion),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void  obtenerPromocionesAsync(String idCategoria) {
        PromocionesRequest request=new PromocionesRequest();
        request.setParametros(String.format("[{valor: 'TV AZTECA'}, {valor: '%s'}, {valor: '99'}, {valor: '99'}]",idCategoria));
        request.setUrl("XOY6sh8pCCeV4xkr4TliUfh18EEPgvWj+jjqoFjXovPu6W3jNKvYe6+xfG6nFS85bGIw/UGzJPzMH9jEDG2yMw==");
        service.obtenerPromociones(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        readJsonAsync(response.body().string(),false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t.getMessage()!=null){
                    callBackPromociones.OnError(t);
                }
            }
        });

    }

    private void readJsonAsync(String json, boolean categoria){
        try {
            if(!json.isEmpty()){
                //JSON QUE PROVIENE DEL WEB SERVICE
                JSONObject jsonResult=new JSONObject(json);
                //EXTREAMOS LA INFORMACIÓN QUE VIENE EN EL NODO "D" QUE ES EL PRINCIPAL
                JSONObject jsonCategoria=new JSONObject(jsonResult.get("d").toString());
                //SE USA PARA VER SI SE OBTIENE EL DETALLE DE LA CATEGORIA O SOLAMENTE EL NOMBRE DE LA MISMA
                String categoriaNombre=categoria?"GetCatResult":"GetCatDataResult";
                //CREAMOS OTRO JSON OBJECT PARA EXTRAR EL RESULTADO FINAL DE LA CATEGORIA
                JSONArray jsonCategoriaArray=new JSONArray(jsonCategoria.get(categoriaNombre).toString());
                //PROMOCIONES RESPONSE
                ArrayList<PromocionesResponse>listPromociones=new ArrayList<>();
                //RECORREMOS EL ARRAY DE CATEGORIAS


                HashMap<String,PromocionesResponse> hashPromociones=new HashMap<>();

                for(int j=0;j<jsonCategoriaArray.length();j++){
                    //CREAMOS OBJETO DE TIPO PROMOCIONES RESPONSE

                    PromocionesResponse promocionesResponse=new Gson().fromJson(jsonCategoriaArray.get(j).toString(), PromocionesResponse.class);


                    if(!hashPromociones.containsKey(promocionesResponse.getINTEDESC()) && !categoria) {
                        hashPromociones.put(promocionesResponse.getINTEDESC(), promocionesResponse);
                    }
                    else {
                        listPromociones.add(promocionesResponse);
                    }

                }

                if(!categoria) {
                    for (PromocionesResponse item : hashPromociones.values()) {
                        listPromociones.add(item);
                    }
                }

                if(callBackPromociones!=null){
                    callBackPromociones.OnSuccess(listPromociones);
                }
            }
        } catch (Exception e) {
            callBackPromociones.OnError(e);
        }
    }

}
