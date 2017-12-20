package com.sociomas.aventones.Utils;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.android.gms.maps.model.LatLng;
import com.sociomas.aventones.UI.Controls.Model.Dia;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.Model.Response.AutoComplete.Prediction;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oemy9 on 29/08/2017.
 */

public class Utils {
    public static LatLng posicionMexico=new LatLng(19.432608,-99.133209);

    public  static  String getHexcolor(@ColorInt int color){
        return String.format("#%06X", (0xFFFFFF & color));
    }



    public static String toUppperCaseFirst(String text){
        return text.substring(0,1).toUpperCase(Locale.ENGLISH).concat(text.substring(1).toLowerCase());
    }

    public static void ocultarTeclado(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static ArrayList<Dia> getDiasSemana(){
        int count=0;
        ArrayList<Dia>listDias=new ArrayList<>();
        java.text.DateFormatSymbols dfs=new java.text.DateFormatSymbols(new Locale("es"));
        for(String dia: dfs.getWeekdays()){
            if(!dia.equalsIgnoreCase("domingo")) {
                listDias.add(new Dia(count, dia, false));
                count++;
            }
        }
        listDias.add(new Dia(count++,"domingo",false));
        return listDias;
    }

    public static String joinDias(ArrayList<Dia>listDias){
        ArrayList<String>listDiasNombre=new ArrayList<>();
        for(Dia dia: listDias)
        {
            listDiasNombre.add(dia.getNombre().substring(0,3));
        }
       return TextUtils.join(",",listDiasNombre);
    }


    public static String getStringDetailPrediction(Prediction p){
       return p.getStructuredFormatting()!=null? p.getStructuredFormatting().getMainText():
                p.getDescription();
    }

    public static SecurityItems getCurrentEmployEncrypt(String numeroEmpleado){
        SecurityItems securityItems=new SecurityItems(numeroEmpleado);
        return  securityItems;
    }
}
