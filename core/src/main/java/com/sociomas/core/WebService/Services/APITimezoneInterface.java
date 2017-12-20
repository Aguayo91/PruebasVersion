package com.sociomas.core.WebService.Services;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Timezone.TimezoneResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oemy9 on 10/04/2017.
 */

public interface APITimezoneInterface {
    @GET(Constants.GET_TIMEZONE)
    public Call<TimezoneResponse> getTimezone(@Query("key") String key,
                                              @Query("format") String format,
                                              @Query("by") String by,
                                              @Query("lat") String lat,
                                              @Query("lng") String lng);
}
