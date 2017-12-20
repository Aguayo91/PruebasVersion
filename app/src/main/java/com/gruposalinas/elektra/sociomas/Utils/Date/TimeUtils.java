package com.gruposalinas.elektra.sociomas.Utils.Date;

/**
 * Created by oemy9 on 24/03/2017.
 */
import android.content.Context;
import android.util.Log;


import com.gruposalinas.elektra.sociomas.Utils.Security.SecureDate;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class TimeUtils {

    private Context context;

    public TimeUtils(Context context){
        this.context=context;
    }

    public static final String TAG="UTILS_TIME";
    /*
        REGRESA LA DIFERENCIA DE  LA HORA ACTUAL - LA HORA DE ENTRADA A
        O SALIDA DEL EMPLEADO
        HORA DE ENTRADA POR EJEMPLO=09:00:00
        HORA DE SALIDA  POR EJEMPLO=19:00:00
    */
    public long getDiferenceInMillis(String input){
        long time=0;
        //FORMATO 24HRS
        SimpleDateFormat  df = new SimpleDateFormat("kk:mm:ss");
        try {

            //CALENDARIO DEL DIA DE HORA DE ENTRADA
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(input));

            //CALENADARIO ESTABLECIENDO EL DÍA DE HOY + HORA DE ENTRADA
             SessionManager sessionManager=new SessionManager(this.context);


            Calendar calendarioHora=Calendar.getInstance();
            if(sessionManager.get(Constants.IS_ERROR_FECHA)){
                SecureDate secureDate=new SecureDate(this.context);
                calendarioHora.setTime(secureDate.getDateCalculada());
            }
            calendarioHora.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY));
            calendarioHora.set(Calendar.SECOND,0);
            calendarioHora.set(Calendar.MINUTE, c.get(Calendar.MINUTE));


            //RESTA ENTRE EL DÍA DE HOY HORA ACTUAL Y HORA DE ENTRADA
            time = calendarioHora.getTimeInMillis() - System.currentTimeMillis();

            Log.i(TAG,"Milisegundos:"+time+" hora:"+input);


        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return time;
    }

    /*
    * EL MISMO QUE  EL ANTERIOR PERO DA LA POSIBILIDAD DE AGREGAR MÁS MINUTOS
    * */
    public long getDiferenceInMillis(String input, int minutosAgregados){
        long time=0;
        //FORMATO 24 HRS
        SimpleDateFormat  df = new SimpleDateFormat("kk:mm:ss");
        try {

            //CALENDARIO DEL DIA DE HORA DE ENTRADA
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(input));
            //CALENADARIO ESTABLECIENDO EL DÍA DE HOY + HORA DE ENTRADA
            Calendar calendarioEntrada=Calendar.getInstance();
            calendarioEntrada.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY));
            calendarioEntrada.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
            calendarioEntrada.set(Calendar.SECOND,0);
            calendarioEntrada.add(Calendar.MINUTE,minutosAgregados);
            //RESTA ENTRE EL DÍA DE HOY HORA ACTUAL Y HORA DE ENTRADA
            time=calendarioEntrada.getTimeInMillis()-System.currentTimeMillis();
            Log.i(TAG,"Milisegundos extra minutos :"+time);


        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return time;
    }


    public  long getMinituos(long millis){
        long diffMinutos = millis/(60 * 1000) % 60;
        return diffMinutos;
    }

    public String getAmigableFormat(String input,boolean entrada){
        long time=this.getDiferenceInMillis(input);
        long diffSegundos = time /1000 % 60;
        long diffMinutos = time/(60 * 1000) % 60;
        long diffHoras = time/(60 * 60 * 1000) % 24;
        long diffDias = time/(24 * 60 * 60 * 1000);
        StringBuilder builder = new  StringBuilder();
        builder.append("Faltan ");
        if(diffHoras>0){
            builder.append(diffHoras);
            builder.append(" horas");
        }
        if(diffMinutos>0){
            if(diffHoras>0){
                builder.append(" ");
            }
            builder.append(diffMinutos);
            builder.append(" minutos");
        }
        else{
            builder.append(diffSegundos);
            builder.append(" segundos");
        }

        builder.append(" hasta la hora de "+(entrada?"entrada":"salida"));
        return builder.toString();

    }

    public  String getDateDifferenceForDisplay(Date inputdate) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();

        now.setTime(new Date());
        then.setTime(inputdate);
        then.set(Calendar.YEAR,now.get(Calendar.YEAR));

        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;

        // Calculate difference in seconds
        long diffSegundos = diff/1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffSegundos<60){
            return   diffSegundos>0?  "Hace "+ diffSegundos +" segundos": "Hace 1 segundo";
        }
        if (diffMinutes < 60) {
            return "Hace "+ diffMinutes+ ( diffMinutes>1? " minutos": " minuto");

        } else if (diffHours < 24) {
            return "Hace "+ diffHours + (diffHours>1 ? " horas": " hora");

        } else if (diffDays < 7) {
            return "Hace "+diffDays + (diffHours>1?" días": " día");

        } else {

            SimpleDateFormat todate = new SimpleDateFormat("MMM dd",
                    Locale.ENGLISH);

            return todate.format(inputdate);
        }

    }
}
