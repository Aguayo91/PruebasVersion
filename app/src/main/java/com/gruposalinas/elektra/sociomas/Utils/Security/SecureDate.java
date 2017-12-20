package com.gruposalinas.elektra.sociomas.Utils.Security;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;


import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by oemy9 on 04/04/2017.
 */

/*
*  GUARDA LA FECHA DEL SEVIDOR EN CADA REQUEST QUE ESTE DEVUELVE LA FECHA
* */
public class SecureDate {
    public static final String TAG="SECURE_DATE";
    private Context context;
    private SessionManager manager;
    public SecureDate(Context context){
        this.context=context;
        this.manager=new SessionManager(context);
    }

    /*GUARDA LOS TICKS DEL RELOJ DEL SO EN LA VARIABLE*/
    public  void saveTicks(){
        manager.add(Constants.LAST_REAL_TIME,SystemClock.elapsedRealtime());
    }

    /*
    * GUARDA LA FECHA DEL SERVER
    * */
    public void saveServerDate(String date){
        manager.add(Constants.LAST_HORA_FROM_SERVER, date);
        manager.add(Constants.LAST_REAL_TIME,SystemClock.elapsedRealtime());
        Log.i(TAG,"HORA GUARDADA CORRECTAMENTE:"+date);
    }
    /*
    * OBTIENE LA FECHA Y HORA CALCULADA DE ACUERDO A
    * ELPASEDREALTIME
    * */
    public Date getDateCalculada(){
        String formato=Constants.SECURE_DATE_FORMAT;
        SimpleDateFormat fUtc = new SimpleDateFormat(formato);
        fUtc.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat fLocal=new SimpleDateFormat(formato);

        SessionManager manager=new SessionManager(this.context);

        String timeZone=manager.getString(Constants.TIME_ZONE_NAME);
        //TODO MEJORAR IMPLEMENTACION
        timeZone=timeZone==null?TimeZone.getDefault().getID():timeZone;
        fLocal.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar calendarCalculado=Calendar.getInstance();
        if(manager.getString(Constants.LAST_HORA_FROM_SERVER)!=null){
            try {
                String dateServerStr=manager.getString(Constants.LAST_HORA_FROM_SERVER);

                Date utcTime=fUtc.parse(dateServerStr);
                String utcLocal=fLocal.format(utcTime);
                Date  localTime=fLocal.parse(utcLocal);

                long currentTime = SystemClock.elapsedRealtime();
                long difference = currentTime - manager.getLong(Constants.LAST_REAL_TIME);
                calendarCalculado.setTime(localTime);
                calendarCalculado.add(Calendar.MILLISECOND,(int)difference);

            } catch (ParseException e) {
                Log.e(TAG,e.getMessage());
            }
        }
        return calendarCalculado.getTime();
    }
    public String getHourCalculadaString(){
        Date dateCalculada=this.getDateCalculada();
        if(dateCalculada!=null) {
            SimpleDateFormat hourFormat = new SimpleDateFormat("kk:mm:ss");
            return hourFormat.format(dateCalculada);
        }
        return  null;
    }
    public String getDateCalculadaString(){
        Date dateCalculada=this.getDateCalculada();
        if(dateCalculada!=null) {
            SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd  kk:mm:ss");
            return hourFormat.format(dateCalculada);
        }
        return  null;
    }
}
