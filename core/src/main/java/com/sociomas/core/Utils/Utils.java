package com.sociomas.core.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.google.gson.Gson;
import com.sociomas.core.BuildConfig;
import com.sociomas.core.DataBaseModel.Empleado;
import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.R;
import com.sociomas.core.Utils.Enums.EnumDensidad;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Security.QrGeneratorEmpleado;
import com.sociomas.core.Utils.Security.SecureDate;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by oemy9 on 30/03/2017.
 */
public class Utils {

    public static final String TAG = "UTILS";

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static  int getBrilloPantalla(Context ctx){
        try {
            ContentResolver cResolver = ctx.getApplicationContext().getContentResolver();
            return  android.provider.Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 255);
        }catch (SecurityException e){
            Log.v("SegurityExeption","Error: "+e);
            ContentResolver cResolver = ctx.getApplicationContext().getContentResolver();
//            android.provider.Settings.System.get;
            return 255;
        }
    }

    public static void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static  void getDensidadAgain(Activity act){
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(act, "low", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(act, "medium", Toast.LENGTH_SHORT).show();

                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(act, "high", Toast.LENGTH_SHORT).show();
                break;
              case DisplayMetrics.DENSITY_XXXHIGH:
                  Toast.makeText(act, "high", Toast.LENGTH_SHORT).show();
                 break;

        }
    }

    public static EnumDensidad getDensityNombre(Context context) {

        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
           return EnumDensidad.xxxhdpi;
        }
        if (density >= 3.0) {
            return EnumDensidad.xxhdpi;
        }
        if (density >= 2.0) {
            return EnumDensidad.xhdpi;
        }
        if (density >= 1.5) {
            return EnumDensidad.hdpi;
        }
        if (density >= 1.0) {
            return EnumDensidad.mdpi;
        }
        return EnumDensidad.ldpi;
    }

    public static void setBrilloPantala(Context ctx, int brightness) {
        try {
            if (Settings.System.canWrite(ctx)) {
                if (brightness < 0)
                    brightness = 0;
                else if (brightness > 255)
                    brightness = 255;
                ContentResolver cResolver = ctx.getApplicationContext().getContentResolver();
                SessionManager manager = new SessionManager(ctx);
                manager.add(Constants.CURRENT_BRILLO, getBrilloPantalla(ctx));
                android.provider.Settings.System.putInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                android.provider.Settings.System.putInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
            }
        } catch (Exception ex) {
            Log.d(TAG, "setBrilloPantala: " + ex.getMessage());
        }

    }


    public static String getMesNombre(int index) {
        java.text.DateFormatSymbols dfs = new java.text.DateFormatSymbols(new Locale("es"));
        return Utils.toTitleCase(dfs.getMonths()[index]);
    }

    public static void callPhone(Context ctx, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        ctx.startActivity(intent);
    }

    public static String getFormatoWebView(String html){
       return   String.format("<html><body bgcolor='#EEEEEE'>%s</body></html>",html);
    }

    public static void navegarWebView(String url, Context context) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static String getTiempoToleranciaFormato(int duracion) {
        return String.format("00:%s:00", duracion);
    }


    public static int getTiempoTolerancia(String tiempo) {
        if (tiempo != null && (!tiempo.isEmpty())) {
                String[] tiempoDividio = tiempo.split(":");
                for (String t : tiempoDividio) {
                    int n = Integer.parseInt(t);
                    if (n > 0) {
                        return n;
                    }
                }
        }
        return 0;
    }

    //obtener el color inverso en el texto(Color)
    public static int obtenerReverse(int bgColor) {
        return Color.rgb(255 - Color.red(bgColor),
                255 - Color.green(bgColor),
                255 - Color.blue(bgColor));
    }


    @ColorInt
    public static int getParseColor(String color) {
        String parseColor = color;
        try {
            if (parseColor != null && !parseColor.equals("null")) {
                if (!color.contains("#")) {
                    parseColor = "#" + color;
                }
            } else {
                parseColor = "#ffffff";
            }

            return Color.parseColor(parseColor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Color.parseColor("#ffffff");
    }


    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static String toUppperCaseFirst(String text) {
        return text.substring(0, 1).toUpperCase(Locale.ENGLISH).concat(text.substring(1).toLowerCase());
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        if (input != null) {
            for (char c : input.toLowerCase(Locale.ENGLISH).toCharArray()) {
                if (Character.isSpaceChar(c)) {
                    nextTitleCase = true;
                } else if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                }

                titleCase.append(c);
            }
        }
        return titleCase.toString();
    }

    public static boolean servicioEjecutando(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void safeClose(FileOutputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void safeClose(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void safeClose(InputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }

    public static boolean isMockSettingOn(Context context, Location location) {
        boolean isMockSetting;
        if (Build.VERSION.SDK_INT >= 18) {
            isMockSetting = location.isFromMockProvider();
        } else {
            isMockSetting = !Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
        }
        return isMockSetting;
    }

    public static String getVersionSO() {
        return String.valueOf(Build.VERSION.RELEASE);
    }
    /*
        LANZA TODOS LOS SERVICIOS DESDE UN BROADCAST
        *UBICACIÓN
        *HORA
        *ALARMAS
     */

    public static boolean hasInternet(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Intent getIntentGPS(Context ctx) {
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        return new Intent(action);
    }

    /*REVISA SI EL GPS ESTÁ ENCENDIDO*/
    public static boolean IsGpsEncendido(Context ctx) {
        LocationManager manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /*
    * ESTE MÉTODO SOLO SE MANDA A LLAMAR CUANDO  NO LA FLAG DEL WEBSERVIE ERROR=FALSE
    * QUIERE DECIR QUE ENTONCES QUE NO HAY ERROR ALGUNO EN LA FECHA
    * */
    public static void saveServerTime(Context context, String serverTime) {
        //SUENA REDUDANTE PERO CON RETROFIT SI NOS ENVIA UN NULL
        if (serverTime != null) {
                SessionManager manager = new SessionManager(context);
                SecureDate secureDate = new SecureDate(context);
                secureDate.saveServerDate(serverTime);
                Log.i(TAG,secureDate.getDateCalculadasString());
        }
    }


    /*REVISA SI HAY ERROR DE FECHA*/
    public static void checkIfFechaError(Context context, String serveUtc, String serverTime) {
        SessionManager sessionManager = new SessionManager(context);

        if (!TextUtils.isEmpty(serveUtc) && !serveUtc.equals("null")) {
            //SESSION MANAGER
            if (!serverTime.equals("null")) {
                SecureDate secureDate = new SecureDate(context);
                secureDate.saveServerDate(serverTime);
            }

            //FORMATO FECHA ACTUAL DISPOSITIVO EN UTC
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
            f.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {

                //FECHA ACTUAL DEL DISPOTIVIO
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                //FECHA ACTUAL DEL SERVER
                Calendar calendarServer = Calendar.getInstance();
                calendarServer.setTimeZone(TimeZone.getTimeZone("UTC"));
                calendarServer.setTime(f.parse(serveUtc));

                int resta = 0;

                //VALIDACION POR DÍA
                if (calendar.get(Calendar.DAY_OF_YEAR) != calendarServer.get(Calendar.DAY_OF_YEAR)) {
                    sessionManager.add(Constants.IS_ERROR_FECHA, true);
                    return;
                }
                //ES EL MISMO DÍA
                else {

                    //VALIDACIÓN POR HORA
                    if (calendar.get(Calendar.HOUR_OF_DAY) != calendarServer.get(Calendar.HOUR_OF_DAY)) {
                        sessionManager.add(Constants.IS_ERROR_FECHA, true);
                        return;
                    }
                    //LA HORA ES IGUAL VALIDACIÓN POR MINUTO
                    else {
                        //VALIDACIÓN POR MINUTO
                        if (calendar.get(Calendar.MINUTE) >= calendarServer.get(Calendar.MINUTE)) {
                            resta = calendar.get(Calendar.MINUTE) - calendarServer.get(Calendar.MINUTE);
                        } else {
                            resta = calendarServer.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                        }
                        if (resta >= 3) {
                            sessionManager.add(Constants.IS_ERROR_FECHA, true);
                        } else {
                            sessionManager.add(Constants.IS_ERROR_FECHA, false);
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            sessionManager.add(Constants.IS_ERROR_FECHA, false);
        }

    }

    public static void saveLastIDGPS(Context context, String lastId) {
        SessionManager manager = new SessionManager(context);
        manager.add(Constants.LAST_ID_FROM_GPS, lastId);
    }

    public static String getLastIDGPS(Context context) {
        SessionManager manager = new SessionManager(context);
        return manager.getString(Constants.LAST_ID_FROM_GPS);
    }

    public static String getLastTimeUpdate(Context ctx) {
        SessionManager manager = new SessionManager(ctx);
        return manager.getString(Constants.LAST_TIME_UPDATE) != null ?
                manager.getString(Constants.LAST_TIME_UPDATE) : "";
    }

    public static void setLastTimeUpdate(Context ctx) {
        SessionManager manager = new SessionManager(ctx);
        manager.add(Constants.LAST_TIME_UPDATE, new SimpleDateFormat("HH:mm").format(new Date()));
    }

    /**
     * Obtiene el número de empleado que ha iniciado sesión
     * @param context
     * @return
     */
    public static String getNumeroEmpleado(Context context){
        SessionManager manager=new SessionManager(context);
        return  manager.getString(Constants.SP_ID);

    }

    /**
     * Obtiene la clave de la unidad de negocio
     */
    public static String getUnidadNegocioClave(Context context){
        SessionManager manager=new SessionManager(context);
        return manager.getString(Constants.CLAVE_UNIDAD);
    }

    /**
     * Revisa si la clave de unidad de negocio es grupo salinas o no
     * @return
     */
    public static boolean usuarioGrupoSalinas(Context context){
        if(TextUtils.isEmpty(getUnidadNegocioClave(context))){
            return false;
        }
        else{
            return  getUnidadNegocioClave(context).equalsIgnoreCase("DGS");
        }
    }

    /**
     * Funciona para actualizar el badge de notificaciones cuando se agrega o se elimina
     * @param ctx
     * @param remove
     */
    public static  void updateNotificacionEstatus(Context ctx, boolean remove){
        SessionManager manager=new SessionManager(ctx);
        int currentBadge=manager.getInt(Constants.ContadorNotificaciones);
        //Se elimina del badge
        if(remove) {
            if (currentBadge > 0) {
                currentBadge--;
            }
        }
        //Se agrega  badge
        else {
            currentBadge++;
        }
        manager.add(Constants.ContadorNotificaciones,currentBadge);
        ShortcutBadger.applyCount(ctx,currentBadge);
    }

    /**
     * Setea el valor del estatus del badge
     * @param ctx
     * @param value
     */
    public static  void setNotificacionEstatus(Context ctx, int value){
        SessionManager manager=new SessionManager(ctx);
        manager.add(Constants.ContadorNotificaciones,value);
        ShortcutBadger.applyCount(ctx,value);
    }





    public static Empleado getCurrentEmpleado(Context context) {
        SessionManager manager = new SessionManager(context);
        Empleado empl = new Empleado();
        empl.setIdEmployee(manager.getString(Constants.SP_ID));
        empl.setName(manager.getString(Constants.SP_NAME));
        return empl;
    }
    
    public static  String getJustNombreEmpleado(Context ctx)
    {
        Empleado empleado=getCurrentEmpleado(ctx);
        if(empleado!=null) {
            int index = empleado.getName().indexOf(" ");
            return !TextUtils.isEmpty(empleado.getName()) && index!=-1  ? empleado.getName().substring(0, index) : "";
        }
        return "";
    }

    public static QrGeneratorEmpleado getQrEmpleado(Context context) {
        Empleado empleado = getCurrentEmpleado(context);
        QrGeneratorEmpleado qrGenerator = new QrGeneratorEmpleado(empleado.getIdEmployee(), empleado.getName());
        return qrGenerator;
    }
    public static byte[]decodeBase64String(String base64){
        try {
            return Base64.decode(base64.getBytes(), Base64.DEFAULT);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    public static Calendar combinarCalendars(Calendar calendarDate, Calendar calendarHora) {
        calendarDate.set(Calendar.HOUR_OF_DAY, calendarHora.get(Calendar.HOUR_OF_DAY));
        calendarDate.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
        calendarDate.set(Calendar.SECOND, 0);
        return calendarDate;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), 0);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public Bitmap scaleImage(Activity activity, Bitmap bmp) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int displayWidth = metrics.widthPixels;
        float imageWidth = bmp.getWidth();
        float imageHeight = bmp.getHeight();
        float aspectRatio = imageWidth / imageHeight;
        int finalWidth = 0;
        if (imageWidth > displayWidth) {
            finalWidth = displayWidth;
        } else {
            finalWidth = (int) imageWidth;
        }
        float finalHeight = finalWidth / aspectRatio;

        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, finalWidth,
                (int) finalHeight, true);
        return bitmap;
    }

    public static Bitmap getThumbnailBitmap(Context context, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 250) ? (originalSize / 250) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    public static void saveImagenPerfilWallpaper(Context context, Bitmap bitmap, boolean wallpaper) {
        if (bitmap != null) {
            SessionManager manager = new SessionManager(context);
            manager.add(wallpaper ? Constants.WALLPAPER_PERFIL_IMAGE : Constants.USUARIO_PERFIL_IMAGE, encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 80));
        }
    }

    public static Bitmap getImagenPerfilWallpaper(Context context, boolean wallpaper) {
        SessionManager manager = new SessionManager(context);
        String base64Perfil = manager.getString(wallpaper ? Constants.WALLPAPER_PERFIL_IMAGE : Constants.USUARIO_PERFIL_IMAGE);
        Bitmap selectedBitmap = base64Perfil != null ? decodeBase64(base64Perfil) : null;
        return selectedBitmap;
    }

    public static void ocultarTeclado(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static Date parseJsonString(String date) {
        String value = date.replaceFirst("\\D+([^\\)]+).+", "$1");
        //Timezone could be either positive or negative
        String[] timeComponents = value.split("[\\-\\+]");
        long time = Long.parseLong(timeComponents[0]);
        int timeZoneOffset = 0;
        if (timeComponents.length > 1) {
            timeZoneOffset = Integer.valueOf(timeComponents[1]) * 36000; // (("0100" / 100) * 3600 * 1000)
        }
        //If Timezone is negative
        if (value.indexOf("-") > 0) {
            timeZoneOffset *= -1;
        }
        time += timeZoneOffset;
        return new Date(time);
    }

    public static boolean nuevaVersionDisponible(String vDispositivo, String vWS) {
        boolean nuevaVersion = false;
        if (!vWS.equals("1")) {
            String splitDispositivo[] = vDispositivo.split("\\.");
            String splitWS[] = vWS.split("\\.");
            for (int j = 0; j < splitWS.length; j++) {
                if (Character.isDigit(splitWS[j].charAt(0)) && Character.isDigit(splitDispositivo[j].charAt(0))) {
                    int nDispositivo = Integer.parseInt(splitDispositivo[j]);
                    int nWS = Integer.parseInt(splitWS[j]);
                    if (nWS > nDispositivo) {
                        nuevaVersion = true;
                        break;
                    } else {
                        //EN LA PRIMERA POSICIÓN
                        if (j == 0) {
                            if (nWS < nDispositivo)
                                nuevaVersion = false;
                            break;
                        }
                    }

                }
            }
        }
        return nuevaVersion;
    }

    public static void intentNavegacionWaze(Context context, String label, Double latitude, Double longitude) {
        try {
            String url = String.format(Locale.ENGLISH, "geo:0,0?q=") + Uri.encode(String.format("%s@%f,%f", label, latitude, longitude), "UTF-8");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Asegúrate de tener instalado google maps o waze", Toast.LENGTH_LONG).show();
        }
    }

    public static String getStreetViewPreviewUrl(double lat, double lng) {
        return String.format("https://maps.googleapis.com/maps/api/streetview?size=400x400&location=%s,%s&fov=90&heading=235&pitch=10", lat, lng);
    }

    public static boolean telefonoValidado(Context context) {
        SessionManager manager = new SessionManager(context);
        return manager.get(Constants.TELEFONO_VALIDADO, false);
    }


    /*UTILERIAS ADRIAN*/
    /*
* REVISA SI LA URL DEL WEBSERVICE ES HTTPS O HTTP
*
* IMPORTANTE LLAMAR ESTE METODO EN CADA UNA DE LAS LLAMADAS AL WEB SERVICE
*
* */
    public static HttpURLConnection checkIfHttps(URL url) throws IOException {
        HttpURLConnection urlConnection;
        if (url.getProtocol().toLowerCase(Locale.ENGLISH).equals("https")) {
            HttpsURLConnection urlHttpsConnection = (HttpsURLConnection) url.openConnection();
            urlHttpsConnection.setHostnameVerifier(Utils.hostnameVerifier());
            urlConnection = urlHttpsConnection;
        } else {
            urlConnection = (HttpURLConnection) url.openConnection();
        }
        return urlConnection;
    }


    /*
    * FORMATEA JSON
    * */
    public static String JsonWellFormat(String badFormat) {
        return badFormat.replace("\\", "");
    }


    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public static String generateMovementId(String fecha, String hora) {
        return fecha.toLowerCase(Locale.ENGLISH).trim().replace("/", "")
                + hora.toLowerCase(Locale.ENGLISH).trim().replace(":", "");
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    /*TODO AGREGAR PERMISO AQUÍ*/
    public static float getBatteryLevel(Context context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if (level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float) level / (float) scale) * 100.0f;
    }


    public static HostnameVerifier hostnameVerifier(){
        HostnameVerifier   hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("sociomas.com.mx", session);
            }
        };

        return hostnameVerifier;
    }

    public static HostnameVerifier hostnameVerifierPromociones() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("portalsocio.gs", session);
            }
        };

        return hostnameVerifier;

    }

    public static HostnameVerifier hostnameVerifierGoogle() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("maps.googleapis.com", session);
            }
        };

        return hostnameVerifier;

    }


    public static String fecha_actual() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }

    public static String fecha_permiso() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");


        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    public static String formatCantidadPesos(float mCantidad) {
        String cantidad = "";
        try {
            NumberFormat formatter = NumberFormat.getInstance();
            cantidad = formatter.format(mCantidad);
        } catch (Exception e) {
            cantidad = "$" + mCantidad;
        }
        return cantidad;
    }

    @SuppressLint("LongLogTag")
    private String generateJsonDate(RegistroGPS registro) {
        //Setting jsonDate
        String jsonDate = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(registro.getFecha() + " " + registro.getHora());
        } catch (java.text.ParseException e) {
            Log.e("ddd", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);
        registro.setJsonDate(jsonDate);

        return jsonDate;
    }

    public static String getJsonDate(Date date) {
        Log.i(TAG, "TEST date antes del JsonParse = " + date);
        return "/Date(" + date.getTime() + "-0600)/";
    }

    public static void llamarBroadCastActividad(Context context) {
        com.sociomas.core.Utils.DateUtils dateUtils = new com.sociomas.core.Utils.DateUtils(context);
        dateUtils.addDate(Constants.ULTIMA_VEZ_APP_FOREGROUND, new Date());
        Intent i = new Intent("com.gruposalinas.elektra.movilidadgs.INICIAR_RECONOCIMIENTO_ACTIVIDAD");
        context.sendBroadcast(i);
    }

    public static void llamarBroadCastInicio(Context context) {
        Intent i = new Intent(Constants.INCIAR_SERVICIOS_BROADCAST);
        context.sendBroadcast(i, "android.permission.RECEIVE_BOOT_COMPLETED");
    }

    public static String getMapaUbicacion(double lat, double lng) {
        return String.format("https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=12&size=400x400&" +
                "&markers=color:green|%s,%s&key=%s", lat, lng, lat, lng, Constants.API_KEY);
    }

    public static String getMapaUbicacionRezised(double lat, double lng, int width, int heigth) {
        return String.format("https://maps.googleapis.com/maps/api/staticmap?center=%s,%s&zoom=12&size="
                + width + "x" + heigth + "&" +
                "&markers=color:green|%s,%s&key=%s", lat, lng, lat, lng, Constants.API_KEY);
    }

    public static String encryptNumberAccount(String textNumber) {
        String numberAccount = "";
        if (textNumber != null) {
            if (textNumber.trim().length() > 0) {
                numberAccount = textNumber
                        .substring
                                (0, textNumber.length() - 4)
                        .replaceAll("[0-9]", "*")
                        .concat
                                (textNumber
                                        .substring(textNumber.length() - 4, textNumber.length()
                                        )
                                );
            }
        }
        return numberAccount;
    }

    public static void setBadgCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),   source.getHeight(), matrix,
                true);
    }
}
