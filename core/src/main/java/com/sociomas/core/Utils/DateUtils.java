package com.sociomas.core.Utils;

import android.content.Context;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oemy9 on 04/05/2017.
 */

public class DateUtils {
    private SessionManager manager;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_FORMAT);

    public DateUtils(Context context){
        this.context=context;
        this.manager=new SessionManager(this.context);
    }
    public void addDate(String key, Date date){
        manager.add(key,dateFormat.format(date));
    }
    public String getFormatDate(String key){
        return  manager.getString(Constants.ULTIMO_INTENTO_BORRADO);
    }

    public String getFormatTodayDate(){
        return dateFormat.format(new Date());
    }
    public long getDiferenciaDias(String key) throws ParseException {
        Date date = dateFormat.parse(manager.getString(key));
        long diasDiferencia = (new Date().getTime()-date.getTime()) / (24 * 60 * 60 * 1000);
        return diasDiferencia;
    }
}
