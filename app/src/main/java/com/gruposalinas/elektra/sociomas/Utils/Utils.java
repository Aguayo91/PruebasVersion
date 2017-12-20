package com.gruposalinas.elektra.sociomas.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import android.media.ExifInterface;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.IO.Services.LocationJobService;
import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.IO.Recievers.LocationAlarmReceiver;
import com.gruposalinas.elektra.sociomas.IO.Recievers.HoraReceiver;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DateUtils;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecureDate;
import com.sociomas.core.Utils.OrmLite.DBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sociomas.core.DataBaseModel.PanicoT;
import com.sociomas.core.DataBaseModel.RangoMonitoreo;
import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.Utils.Enums.EnumAlarma;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Model.Response.Ubicaciones.Ubicaciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by oemy9 on 30/03/2017.
 */
public class Utils {

    public static final String TAG = "UTILS";

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getMesNombre(int index) {
        java.text.DateFormatSymbols dfs = new java.text.DateFormatSymbols(new Locale("es"));
        return Utils.toTitleCase(dfs.getMonths()[index]);
    }

    public static void navegarWebView(String url, Context context) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
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

    public static Ubicaciones getFromJson(String jsonInput, Class<? extends Ubicaciones> output) {
        Gson gson = new Gson();
        Ubicaciones item = gson.fromJson(jsonInput, output);
        return item != null ? item : null;
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




    private static int getIdByTipo(EnumAlarma tipo) {
        int id = 0;
        switch (tipo) {
            case entrada:
                id = Constants.ALARM_ID_ENTRADA;
                break;
            case diez_minutos:
                id = Constants.ALARM_ID_DIEZ;
                break;
            case salida:
                id = Constants.ALARM_ID_SALIDA;
                break;

        }
        return id;
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static boolean userLogeado(Context context) {
        String spId= context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID,null);
        return !TextUtils.isEmpty(spId);
    }

    public static String getLastGPSUpdate(Context ctx) {
        SessionManager manager = new SessionManager(ctx);
        return manager.getString(Constants.LAST_TIME_GPS_UPDATE) != null ?
                manager.getString(Constants.LAST_TIME_GPS_UPDATE) : "";
    }

    public static Calendar getLastGPSUpdateCalendar(Context ctx) {
        SessionManager manager = new SessionManager(ctx);
        Calendar calendar = Calendar.getInstance();
        if (manager.getString(Constants.LAST_TIME_GPS_UPDATE) != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
                calendar.setTime(dateFormat.parse(manager.getString(Constants.LAST_TIME_GPS_UPDATE)));
                return calendar;
            } catch (Exception ex) {
                Log.i(TAG, "ERROR AL PARSEAR");
            }
        }
        return calendar;
    }

    public static void setLastHourGPSUpdate(Context ctx, String hour) {
        SessionManager manager = new SessionManager(ctx);
        manager.add(Constants.LAST_HOUR_GPS_UPDATE, hour);
        Log.i(TAG, "#HORA AGREGADA:" + hour);
    }

    public static String getLastHourGPSUpdate(Context ctx) {
        SessionManager manager = new SessionManager(ctx);
        return manager.getString(Constants.LAST_HOUR_GPS_UPDATE) != null ?
                manager.getString(Constants.LAST_HOUR_GPS_UPDATE) : "";
    }

    public static void setLastTimeGPSUpdate(Context ctx, String date) {
        SessionManager manager = new SessionManager(ctx);
        manager.add(Constants.LAST_TIME_GPS_UPDATE, date);
        Log.i(TAG, "#FECHA AGREGADA:" + date);
    }

    //VER https://stackoverflow.com/questions/38344220/job-scheduler-not-running-on-android-n
    public static void runScheduleJob(Context context, boolean reboot) {
        Log.i(TAG, "initJobService: Iniciando servicio");
        ComponentName serviceCmpt = new ComponentName(context, LocationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(10012438, serviceCmpt);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putBoolean(Constants.FROM_REBOOT, reboot);
        builder.setExtras(bundle);
        builder.setPersisted(true);
        JobScheduler jobScheduler=(JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(1));
        }
        //HAY UN ISSUE REPORTADO EN VERSIONES ANDROID M CON DISPOSITIVOS samsung
        //VER MÁS AQUÍ https://stackoverflow.com/questions/33235754/jobscheduler-posting-jobs-twice-not-expected/33293101#33293101
        else if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M && Build.BRAND.equalsIgnoreCase("samsung")) {
            builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(1));
        }
        else{
            builder.setPeriodic(TimeUnit.MINUTES.toMillis(1));
        }
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobScheduler.schedule(builder.build());
        if (jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE) {
                Log.e(TAG, "Error al crear el jobService");
        }
     }


    public static void runAlarm(Context context, boolean reboot) {
        int intervalo = !SupportUtils.isModeloNoSoportado() ? 1 : 2;
        Log.i(TAG, "INTERVALO: ".concat(String.valueOf(intervalo)));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, LocationAlarmReceiver.class);
        gpsTrackerIntent.putExtra(Constants.FROM_REBOOT, reboot);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Calendar cActual=Calendar.getInstance();
        Calendar cMinuto=Calendar.getInstance();
        cMinuto.add(Calendar.MINUTE,intervalo);
        cMinuto.set(Calendar.SECOND,0);
        long difMillis=cMinuto.getTimeInMillis()-cActual.getTimeInMillis();
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), difMillis, pendingIntent);

        if (reboot) {
            SecureDate secureDate = new SecureDate(context);
            secureDate.saveTicks();
        }
    }

    public static void runAlarm(Context context, long milisegundos, EnumAlarma tipo) {
        int id = getIdByTipo(tipo);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent horaReceiverIntent = new Intent(context, HoraReceiver.class);
        horaReceiverIntent.putExtra(Constants.TIPO_ALARMA, tipo.toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, horaReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.add(Calendar.MILLISECOND, (int) milisegundos);
        alarmStartTime.set(Calendar.SECOND,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    alarmStartTime.getTimeInMillis(),
                    pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(),
                    pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    alarmStartTime.getTimeInMillis(),
                    pendingIntent);
        }
    }

    public static String getProvider(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = "";
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = "GPS";
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = "NETWORK";
        } else if (manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            provider = "PASSIVE";
        }
        return provider;
    }

    public static String getVersionSO() {
        return String.valueOf(Build.VERSION.RELEASE);
    }


    public static void sendNotification(Context context, String description) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.app_socio_mas_x2);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, InicioActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
        NotificationManagerCompat.from(context).notify(0, builder.build());

    }

    public static void sendNotification(Context context, String title, String description, Intent intent, boolean sound) {
        long[] pattern = new long[]{1000, 500, 1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(description))
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE, 1, 2).setSound(defaultSound).setCategory(android.support.v4.app.NotificationCompat.CATEGORY_MESSAGE).setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat.from(context).notify(0, builder.build());

    }

    public static void sendNotification(Context context, String title, String description, boolean sound) {
        long[] pattern = new long[]{1000, 500, 1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(description))
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, InicioActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE, 1, 2).setSound(defaultSound).setCategory(android.support.v4.app.NotificationCompat.CATEGORY_MESSAGE).setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat.from(context).notify(0, builder.build());
    }


    /*
    *  OBTIENE  EL RANGO DE HORA DE ENTRADA Y HORA DE SALIDA DE ACUERDO AL DÍA DE HOY
    * */
    public static RangoMonitoreo getHorarioByNumeroDia(Context context) throws SQLException {
        RangoMonitoreo ultimoRango = null;
        try {
            Calendar c = Calendar.getInstance();
            Integer diaActual = c.get(Calendar.DAY_OF_WEEK) - 1;
            Dao dao;
            DBHelper dbHelper = null;
            List<RangoMonitoreo> registros = null;
            dao = DBHelper.getHelper(context, dbHelper).getRangosMonitoreoDao();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.setWhere(queryBuilder.where().eq(RangoMonitoreo.NUMERO_DIA, diaActual.toString()));
            registros = dao.query(queryBuilder.prepare());
            if (registros != null && registros.size() > 0) {
                ultimoRango = registros.get(registros.size() - 1);
                Log.i(TAG, "Ultimo:" + ultimoRango.getHoraInicial() + "/" + ultimoRango.getHoraFinal());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ultimoRango;
    }

    /*
        LANZA TODOS LOS SERVICIOS DESDE UN BROADCAST
        *UBICACIÓN
        *HORA
        *ALARMAS
     */
    public static void llamarBroadCastActividad(Context context) {
        DateUtils dateUtils = new DateUtils(context);
        dateUtils.addDate(Constants.ULTIMA_VEZ_APP_FOREGROUND, new Date());
        Intent i = new Intent("com.gruposalinas.elektra.movilidadgs.INICIAR_RECONOCIMIENTO_ACTIVIDAD");
        context.sendBroadcast(i);
    }

    public static void llamarBroadCastInicio(Context context) {
        Intent i = new Intent(Constants.INCIAR_SERVICIOS_BROADCAST);
        context.sendBroadcast(i, "android.permission.RECEIVE_BOOT_COMPLETED");
    }

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
    public static void saveServerTime(String serverTime) {
        //SUENA REDUDANTE PERO CON RETROFIT SI NOS ENVIA UN NULL
        if (serverTime != null) {
            if (!serverTime.equals("null")) { //CON LA IMPLEMENTACIÓN DEL WS ANTEIORIR NOS MANDA DE ESTA MANERA NULL {
                SessionManager manager = new SessionManager(ApplicationBase.getAppContext());
                SecureDate secureDate = new SecureDate(ApplicationBase.getAppContext());
                secureDate.saveServerDate(serverTime);
                manager.add(Constants.IS_ERROR_FECHA, false);
            }
        }
    }


    /*REVISA SI HAY ERROR DE FECHA*/
    public static void checkIfFechaError(String serveUtc, String serverTime) {
        SessionManager sessionManager = new SessionManager(ApplicationBase.getAppContext());

        if (!TextUtils.isEmpty(serveUtc) && !serveUtc.equals("null")) {
            //SESSION MANAGER
            if (!serverTime.equals("null")) {
                SecureDate secureDate = new SecureDate(ApplicationBase.getAppContext());
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

    //TODO AGREGAR MENSAJE DE ERROR PARA VERSIÓN PREVIA
    public static void guardarPanico(int responseCode, String mensajeError) {
        DBHelper dbHelper = null;
        try {
            Dao dao = dbHelper.getHelper(ApplicationBase.getAppContext(), dbHelper).getPanicoDao();
            PanicoT panicoT = new PanicoT();
            panicoT.setFecha(new Date());
            panicoT.setHttpCode(responseCode);
            panicoT.setOk(responseCode == 200);
            panicoT.setMensajeErrorPanico(mensajeError);
            dao.create(panicoT);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

        double ratio = (originalSize > 350) ? (originalSize / 350) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public static byte[] convertBitmapToByteArray(Bitmap b) {
        int bytes = b.getByteCount();
//or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
//int bytes = b.getWidth()*b.getHeight()*4;

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        b.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        byte[] array = buffer.array(); //Get the und
        return array;
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
            String url = String.format(Locale.ENGLISH, "geo:0,0?q=") + android.net.Uri.encode(String.format("%s@%f,%f", label, latitude, longitude), "UTF-8");
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
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

    //Parseo de fecha en formato JSON a normal
    @SuppressLint("SimpleDateFormat")
    public static String parseJsonDate(String jsonDate) {
        String timeString = jsonDate.substring(jsonDate.indexOf("(") + 1, jsonDate.indexOf(")"));
        String[] timeSegments = timeString.split("\\-");
        // May have to handle negative timezones
        long timeZoneOffSet = Integer.valueOf(timeSegments[1]) * 36000; // (("0100" / 100) * 3600 * 1000)

        long millis = Long.valueOf(timeSegments[0]);
        Date time = new Date(millis + timeZoneOffSet);

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        dateFormatter.setLenient(false);

        String dateString = dateFormatter.format(time);

        return dateString;
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

    //Parsea del formato Json WCF a formato normal
    static String wcfHourParser(String hour) {

        return hour;
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


    public static HostnameVerifier hostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
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
                return hv.verify("movil.gs", session);
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
            cantidad = "$" + formatter.format(mCantidad);
        } catch (Exception e) {
            cantidad = "$" + mCantidad;
        }
        return cantidad;
    }

    public static String splitCantidadNomina(String mCantidad) {
        String cantidad = "";
        try {
            NumberFormat formatter = NumberFormat.getInstance();
            if (!mCantidad.contains(".")) {
                String value = mCantidad.substring(0, mCantidad.length() -2) + "." + mCantidad.substring(mCantidad.length() -2, mCantidad.length());
                mCantidad = value;
            }
            cantidad = mCantidad;
        } catch (Exception e) {
            cantidad = mCantidad;
        }
        return cantidad;
    }

    public static String formatFirstLetters(String texto) {
        String result = "";
        if (texto != null) {
            if (texto.trim().length() > 0) {
                String[] cads = texto.split("\\s");
                if (cads.length > 1) {
                    result = cads[0].substring(0, 1).toUpperCase(Locale.getDefault()) + cads[1].substring(0, 1).toUpperCase(Locale.getDefault());
                } else if (cads.length > 0) {
                    if (cads[0].trim().length() > 0) {
                        result = cads[0].length() > 1 ? cads[0].substring(0, 2).toUpperCase(Locale.getDefault()) : cads[0].substring(0, 1).toUpperCase(Locale.getDefault());
                    } else {
                        result = "";
                    }
                } else {
                    result = "";
                }
            } else {
                result = "";
            }
        } else {
            result = "";
        }
        return result;
    }

    /**
     * Return parse fecha from yyyyMMdd to DD/MM/YYYY
     *
     * @param fecha
     * @return
     */
    public static String formatFechaNomina(String fecha) {
        if (fecha != null) {
            if (fecha.trim().length() > 0) {
                //Format obtain yyyyMMdd
                try {
                    //input date format
                    SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
                    //output date format
                    SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
                    Date date = dFormat.parse(fecha);
                    fecha = dFormatFinal.format(date).toUpperCase(Locale.getDefault());
                    return fecha;
                } catch (Exception e) {
                    return fecha;
                }
            } else {
                return fecha;
            }
        } else {
            return "";
        }
    }

    /**
     * Convert amount of DP to Pixels
     * @param dip
     * @param context
     * @return
     */
    public static int convertDipToPixel(int dip, Context context) {

        //Resources r = boardContext.getResources();
        //float px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpis, r.getDisplayMetrics());

        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * scale + 0.5f);

        return px;

    }

    public static Bitmap convertBase64ToBitmap(String dataBase64) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inMutable = true;
        byte[] decodedString = Base64.decode(dataBase64.getBytes(), Base64.DEFAULT);
        Bitmap decodedBmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedBmp;
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

}
