package com.sociomas.core.Utils;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;
import com.sociomas.core.R;
/**
 * Created by oemy9 on 10/05/2017.
 */

public class DeviceUtils {
    public static String getName(){
        return Build.MANUFACTURER.concat(" ").concat(Build.MODEL);
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

    public static String getDeviceIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddresses) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String sAddr = inetAddress.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                // drop ip6 port suffix
                                int delim = sAddr.indexOf('%');
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "-1.-1.-1.-1";
    }

}
