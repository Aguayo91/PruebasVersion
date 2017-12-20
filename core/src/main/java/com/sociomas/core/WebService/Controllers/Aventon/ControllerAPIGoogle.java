package com.sociomas.core.WebService.Controllers.Aventon;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.maps.model.LatLng;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacksAventones.CallBackAutocomplete;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDetailPlace;
import com.sociomas.core.WebService.CallBacksAventones.CallBackDirections;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Model.Response.AutoComplete.ResponsePlace;
import com.sociomas.core.WebService.Model.Response.DetailPlace.DetailResponse;
import com.sociomas.core.WebService.Model.Response.Directions.DirectionResponse;
import com.sociomas.core.WebService.Services.APIServiceGoogle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 28/08/2017.
 */

public class ControllerAPIGoogle extends ControllerBase {

    private APIServiceGoogle googleService;

    public ControllerAPIGoogle(Context context){
        super(context,Constants.URL_BASE_GOOGLE, Utils.hostnameVerifierGoogle());
        googleService=retrofit.create(APIServiceGoogle.class);
    }

    public  void getAutoComplete(String input, final CallBackAutocomplete callBackAutocomplete){
        this.googleService.getAutoComplete(input,"es", Constants.API_KEY).enqueue(new Callback<ResponsePlace>() {
            @Override
            public void onResponse(Call<ResponsePlace> call, Response<ResponsePlace> response) {
                if(response.isSuccessful()){
                    ResponsePlace respuesta=response.body();
                    callBackAutocomplete.OnSuccess(respuesta);
                }
            }

            @Override
            public void onFailure(Call<ResponsePlace> call, Throwable t) {
                callBackAutocomplete.OnError(t);
            }
        });
    }

    public void getDetailPlace(String placeId, final CallBackDetailPlace callBackDetailPlace) {
        this.googleService.getDetailPlace(placeId, Constants.API_KEY).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if(response.isSuccessful()){
                    DetailResponse respuesta=response.body();
                    callBackDetailPlace.onSuccess(respuesta);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                callBackDetailPlace.OnError(t);
            }
        });
    }

    public void getDirections(String placeIdOrigin, String placeIdDestination, final CallBackDirections callBackDirections){
        this.googleService.getDirections("place_id:".concat(placeIdOrigin),"place_id:".concat(placeIdDestination),false,"driving",Constants.API_KEY).enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if(response.isSuccessful()){
                    callBackDirections.OnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                callBackDirections.OnError(t);
            }
        });
    }
    public void getDirections(LatLng locationInicio,String  placeIdDestination, final CallBackDirections callBackDirections){
        String destinationInico=  TextUtils.join(",",new Double[]{locationInicio.latitude,locationInicio.longitude});
        this.googleService.getDirections(destinationInico ,"place_id:".concat(placeIdDestination),false,"driving",Constants.API_KEY).enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if(response.isSuccessful()){
                    callBackDirections.OnSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                callBackDirections.OnError(t);
            }
        });

    }

    public void getDirections(LatLng locationInicio,LatLng locationFin, final CallBackDirections callBackDirections){
        String destinationInicio=  TextUtils.join(",",new Double[]{locationInicio.latitude,locationInicio.longitude});
        String destinationFin=TextUtils.join(",",new Double[]{locationFin.latitude,locationFin.longitude});
        this.googleService.getDirections(destinationInicio ,destinationFin,false,"driving",Constants.API_KEY).enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if(response.isSuccessful()){
                    callBackDirections.OnSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                callBackDirections.OnError(t);
            }
        });

    }

}
