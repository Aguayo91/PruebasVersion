package com.sociomas.core.WebService.Controllers.Movilidad;
import android.content.Context;

import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Security.SecureDate;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackTimezone;
import com.sociomas.core.WebService.Model.Response.Timezone.TimezoneResponse;
import com.sociomas.core.WebService.Services.APITimezoneInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oemy9 on 10/04/2017.
 */
public class ControllerTimezone  {

    private Context context;
    private APITimezoneInterface service;
    private CallBackTimezone callBackTimezone;
    private String utcFormat;
    private String reportTime;

    public ControllerTimezone(Context context){
        this.context=context;
         Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl(Constants.DOMAIN_URL_TIMEZONE).build();
        this.service=retrofit.create(APITimezoneInterface.class);
    }
    public Call<TimezoneResponse> getTimezone(String lat, String lng)
    {
         return   this.service.getTimezone(Constants.KEY,Constants.FORMAT,Constants.BY,lat,lng);
    }

    public void setCallBackTimezone(CallBackTimezone callBackTimezone) {
        this.callBackTimezone = callBackTimezone;
    }

    public void revisarHora(){
        if (Utils.hasInternet(this.context)) {

            final ControllerTimezone controllerTimezone = new ControllerTimezone(this.context);
            GPSBDHelper gpsbdHelper = new GPSBDHelper(this.context);
            RegistroGPS registroGPS = gpsbdHelper.getLastRegistro();

            if (registroGPS != null) {
                controllerTimezone.getTimezone(
                        String.valueOf(registroGPS.getLatitud()),
                        String.valueOf(registroGPS.getLongitud())).
                        enqueue(new Callback<TimezoneResponse>() {

                            @Override
                            public void onResponse(Call<TimezoneResponse> call, Response<TimezoneResponse> response) {

                                if (response.isSuccessful()) {
                                    try {
                                        SimpleDateFormat formatApi = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                                        Date dateLocalTime = formatApi.parse(response.body().getFormatted());
                                        reportTime = formatApi.format(dateLocalTime);
                                        Calendar calendarApi = Calendar.getInstance();
                                        calendarApi.setTime(dateLocalTime);
                                        calendarApi.setTimeZone(TimeZone.getDefault());
                                        SimpleDateFormat format = new SimpleDateFormat(Constants.SECURE_DATE_FORMAT);
                                        format.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        utcFormat = format.format(calendarApi.getTime());
                                        SecureDate secureDate = new SecureDate(context);
                                        secureDate.saveServerDate(utcFormat);

                                            /*GUARDAR TIME ZONE*/
                                        SessionManager manager = new SessionManager(context);
                                        manager.add(Constants.TIME_ZONE_NAME, response.body().getZoneName());
                                        manager.add(Constants.LAST_TIME_ZONE_UPDATE,response.body().getFormatted());

                                        if(callBackTimezone!=null){
                                            callBackTimezone.OnSuccess(calendarApi);
                                        }

                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<TimezoneResponse> call, Throwable t) {
                                if(callBackTimezone!=null){
                                    callBackTimezone.OnError(new Throwable("Error al iniciar conexión"));
                                }
                            }
                        });

            }

        }
        //NO HAY INTERNET
        else {
            callBackTimezone.OnError(new Throwable("No hay conexión a internet"));
        }

    }

}

