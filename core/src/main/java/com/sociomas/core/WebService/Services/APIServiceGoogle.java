package com.sociomas.core.WebService.Services;
import com.sociomas.core.WebService.Model.Response.AutoComplete.ResponsePlace;
import com.sociomas.core.WebService.Model.Response.DetailPlace.DetailResponse;
import com.sociomas.core.WebService.Model.Response.Directions.DirectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oemy9 on 28/08/2017.
 */
public interface APIServiceGoogle {
    @GET("place/autocomplete/json")
    Call<ResponsePlace> getAutoComplete(@Query("input") String input, @Query("language") String lenguaje,
                                        @Query("key") String key);
    @GET("place/details/json")
    Call<DetailResponse>getDetailPlace(@Query("placeid") String placeId, @Query("key") String key);
    @GET("directions/json")
    Call<DirectionResponse>getDirections(@Query("origin") String origin,
                                         @Query("destination") String destination,
                                         @Query("sensor") boolean sensor,
                                         @Query("mode") String mode, @Query("key") String key);

}
