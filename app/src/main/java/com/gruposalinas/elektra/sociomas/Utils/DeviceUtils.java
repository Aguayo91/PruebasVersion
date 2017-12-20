package com.gruposalinas.elektra.sociomas.Utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 10/05/2017.
 */

public class DeviceUtils {

    public static String getName(){
        return Build.MANUFACTURER.concat(" ").concat(Build.MODEL);
    }
    public static String getDeviceModel(){
        return Build.MANUFACTURER;
    }
    public static String getVersionSO(){
       return Build.VERSION.RELEASE;
    }
    public static String getSistema(){
        return "ANDROID";
    }
    public static String getIdDispositivo(Context context){
        return  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    public static String getVersionApp(Context context){
        return context.getString(R.string.version_name);
    }
}
