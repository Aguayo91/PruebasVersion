package com.gruposalinas.elektra.sociomas.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Manager.SessionManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.http.PUT;

/**
 * Created by oemy9 on 11/05/2017.
 */

public class SupportUtils  extends Utils{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1;

    public static void checkIfGooglePlayServices(Activity activity){
        GoogleApiAvailability apiAvailability=GoogleApiAvailability.getInstance();
        int result=apiAvailability.isGooglePlayServicesAvailable(activity);
        if(result!= ConnectionResult.SUCCESS){
            if (apiAvailability.isUserResolvableError(result)) {
                apiAvailability.getErrorDialog(activity,result,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
        }
    }

    public static boolean isModeloNoSoportado(){
        boolean noSoportado=false;
        String modelos[]=new String[]{"ALE-L23","SM-G532M"};
        for(String modelo: modelos) {
            noSoportado= Build.MODEL.equalsIgnoreCase(modelo);
            if(noSoportado)
                break;
        }
        return noSoportado;
    }

    public static boolean isHuawei(){
        return  Build.MANUFACTURER.equalsIgnoreCase("HUAWEI");
    }



    public static void ifHuaweiAlert(final Context context) {
        final SessionManager manager=new SessionManager(context);
        final String saveIfSkip = "skipProtectedAppsMessage";
        boolean skipMessage = manager.get(saveIfSkip);
        if (!skipMessage) {
            Intent intent = new Intent();
            intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            if (isCallable(intent,context)) {
                final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(context);
                dontShowAgain.setText("No mostrar de nuevo");
                dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        manager.add(saveIfSkip,isChecked);
                    }
                });
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Huawei APLICACIONES PROTEGIDAS")
                        .setMessage(String.format("%s requiere que habilites la aplicación en 'APLICACIONES PROTEGIDAS' para funcionar correctamente.", context.getString(R.string.app_name)))
                        .setView(dontShowAgain)
                        .setPositiveButton("APLICACIONES PROTEGIDAS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                huaweiProtectedApps(context);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            } else {
                manager.add(saveIfSkip, true);
            }
        }
    }

    private static boolean isCallable(Intent intent, Context context) {
        List<ResolveInfo> list =context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private static void huaweiProtectedApps(Context context) {
        try {
            String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial(context);
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static String getUserSerial(Context context) {
        //noinspection ResourceType
        Object userManager = context.getSystemService("user");
        if (null == userManager) return "";
        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return "";
    }
}
