package com.sociomas.core.Utils.DbUtils;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DateUtils;
import com.sociomas.core.Utils.Enums.EnumLocationProvider;
import com.sociomas.core.Utils.OrmLite.DBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sociomas.core.DataBaseModel.Actividad;
import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackTimezone;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerTimezone;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.Movimiento;
import com.sociomas.core.WebService.Model.Request.Ubicaciones.RootMovimiento;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 30/03/2017.
 */

@SuppressWarnings("unused")
public class  GPSBDHelper extends DBUtils {
    public static final String TAG = "GUARDAR_BD_GPS";
    private Context context;
    private String dateString;
    private String hourString;
    private DBHelper mDBHelper;
    private SessionManager manager;
    public  interface LocationHelperListener{void onFinishSavedLocationListener();}
    private LocationHelperListener listener;

    public GPSBDHelper(Context context) {
        super(context);
        this.context = context;
        this.manager = new SessionManager(this.context);
    }

    public DBHelper getmDBHelper() {
        return mDBHelper;
    }

    public void setListener(LocationHelperListener listener) {
        this.listener = listener;
    }

    /*
    private void checkIfFechaError(){
        if (sessionManager.get(Constants.IS_ERROR_FECHA)) {
            Log.i(TAG, "ERROR EN FECHA");
            SecureDate secureDate = new SecureDate(this.context);
            date = secureDate.getDateCalculada();
        } else {
            Log.i(TAG, "NO HAY ERROR EN FECHA");
            date = new Date();
        }
    }*/

    /*
    * GENERA UN OBJETO DE TIPO
    * REGISTRO GPS  Y LO DEVUELVE
    * LISTO PARA ENVIAR AL SERVIDOR O EN SU DEFECTO GUARDARLO EN LA BD
    * //NOTA AUTOMATICAMENTE SE GUARDA EN LA BD CON EL MÉTODO ONPOSITIONSAVED
    * */
    public RegistroGPS generarPosicionActual(Location _location) {
        SessionManager sessionManager = new SessionManager(this.context);
        Date date=new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);
        dateString = dateFormatter.format(date);
        hourString = timeFormatter.format(date);
        EnumLocationProvider provider= EnumLocationProvider.valueOf(_location.getProvider());
        RegistroGPS registroGps = new RegistroGPS(
                _location.getLatitude(),
                _location.getLongitude(),
                this.context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        .getString(Constants.SP_ID, ""),
                dateString,
                hourString,
                Utils.getBatteryLevel(this.context),
                Utils.getJsonDate(date),
                Utils.generateMovementId(dateString, hourString), provider.getValue(), _location.getAccuracy());
        registroGps.setJsonDate(generateJsonDate(registroGps));
        registroGps.setVelocidad(_location.getSpeed());
        registroGps.setId_actividad(String.valueOf(System.currentTimeMillis()));
        onPositionSaved(registroGps, true);
        return registroGps;

    }

    private String generateJsonDate(RegistroGPS registro) {
        String jsonDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
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

    /*
    * GUARDA UN REGISTRO NUEVO EN LA BD LOCAL
    * O ACTUALIZA UN REGISTRO EN LA BD
    * */

    public void onPositionSaved(final RegistroGPS result, boolean startService) {
        Dao dao;

        try {
            if (result.isSuccess()) {
                Log.i(TAG, "Se ha mandado la ubicación exitosamente: " + result.getId());
            } else {
                Log.i(TAG, "No se ha mandado la ubicación. Guardando en cola. Hora = " + result.getFecha() + " " + result.getHora());
                Log.i(TAG, "jsonDate = " + result.getJsonDate());
            }
            boolean isNew = true;
            List<RegistroGPS> registrosTemp = null;
            dao = DBHelper.getHelper(this.context, mDBHelper).getGpsDAO();

            try {
                Log.i(TAG, "Test probando buscar registro con ID = " + result.getId());
                QueryBuilder queryBuilder = dao.queryBuilder();
                queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.ID, result.getId()));
                registrosTemp = dao.query(queryBuilder.prepare());

                Log.d(TAG, "ID = " + registrosTemp.get(0).getId() + "/" + registrosTemp.get(0).isSuccess());
                Log.e(TAG, "Result id = " + result.getId());
                Log.e(TAG, "registroTemp id = " + registrosTemp.get(0).getId());
                if (registrosTemp == null) {
                    Log.i(TAG, "No se ha encontrado nada");
                } else if (registrosTemp.size() == 0 && !registrosTemp.get(0).getId().equals(result.getId())) {
                    Log.i(TAG, "Ningún registro con id = " + result.getId());
                } else {
                    Log.i(TAG, "Recuperado registro con id = " + registrosTemp.get(0).getId());
                    isNew = false;
                }

            } catch (Exception e) {
                Log.e(TAG, "No se hizo la búsqueda el registro");
            }

            if (isNew) {
                Log.i(TAG, "Se creará la coordenada: " + result.getJsonDate());
                dao.create(result);
                if (startService) {
                    Utils.saveLastIDGPS(context, result.getId_actividad());
                    Utils.llamarBroadCastActividad(this.context);
                    Log.i(TAG, "Llamando a broadcast");
                } else {
                    Utils.saveLastIDGPS(context, null);
                }
                Log.i(TAG, "Coordenada creado exitosamente");
            } else {
                if (result.isSuccess()) {
                    Log.i(TAG, "Se actualizará la coordenada: " + result.getJsonDate());
                    dao.update(result);

                    Log.i(TAG, "Coordenada actualizada exitosamente");
                } else {
                    Log.i(TAG,"Falló el reintento de mandar la solicitud");
                }
            }


        } catch (SQLException e) {
            Log.e(TAG, "Error creando coordenada");
        }
    }


    public void saveOnServer(final List<RegistroGPS>gpsArrayList,Date fechaEnvio) {
        final SessionManager manager = new SessionManager(context);
        ControllerAPI controllerAPI = new ControllerAPI(this.context);
        RootMovimiento rootMovimiento = new RootMovimiento();
        ArrayList<Movimiento> listMovimientos = new ArrayList<>();
        String zonaHorario = manager.getString(Constants.TIME_ZONE_NAME);
        if (zonaHorario == null) {
            zonaHorario = TimeZone.getDefault().getID();
        }
       for(RegistroGPS r: gpsArrayList) {
           Movimiento movimiento = new Movimiento();
           movimiento.setDecBateria(String.valueOf(r.getBateria()));
           movimiento.setDtFechaHora(r.getJsonDate());
           movimiento.setProvider(r.getProvider());
           movimiento.setDecLatitud(String.valueOf(r.getLatitud()));
           movimiento.setDecLongitud(String.valueOf(r.getLongitud()));
           movimiento.setVelocidad(String.valueOf(r.getVelocidad()));
           movimiento.setAccuracy(String.valueOf(r.getAccuracy()));
           String actividad = this.getActividad(r.getId_actividad());
           movimiento.setActividad(actividad != null ? actividad : Constants.DESCONOCIDA);
           movimiento.setZonahorario(zonaHorario);
           listMovimientos.add(movimiento);
       }
        rootMovimiento.setMovimientos(listMovimientos);
        String numeroEmpleado=Utils.getNumeroEmpleado(this.context);
        SecurityItems securityItems=new SecurityItems(numeroEmpleado,fechaEnvio);
        rootMovimiento.setIdNumEmpleado(securityItems.getIdEmployEncrypt());
        rootMovimiento.setToken(securityItems.getTokenEncrypt());

        controllerAPI.registarMoviemientoEmpleado(rootMovimiento).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        String json = response.body().string();
                        ServerResponse serverResponse = gson.fromJson(json, ServerResponse.class);

                        Utils.saveServerTime(context,serverResponse.getServerTime());

                        //HAY UN ERROR CON LA SINCRONIZACIÓN DEL TOKEN Y LA FECHA DEL DISPOSITIVOS
                        if (serverResponse.getError()) {
                                callBackHttpResponse(gpsArrayList,false);
                                onErrorTokenGenerado();
                                listener.onFinishSavedLocationListener();
                        } else {
                                callBackHttpResponse(gpsArrayList,true);
                                listener.onFinishSavedLocationListener();
                        }

                    } else {
                      callBackHttpResponse(gpsArrayList,false);
                      listener.onFinishSavedLocationListener();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackHttpResponse(gpsArrayList,false);
                if(listener!=null) {
                    listener.onFinishSavedLocationListener();
                }
            }
        });
    }
    public  void callBackHttpResponse(List<RegistroGPS>gpsArrayList, boolean sucesss){
        for (RegistroGPS r: gpsArrayList){
            r.setSuccess(sucesss);
            onPositionSaved(r,false);
        }
    }


    /**
     * @MAXSIZE SE REFIERE A LA CANTIDAD QUE SE DEBE DE ENVIAR EN CASO DE QUE HAYA DATOS MÓVILES Y NO WIFI
     * 1 REGISTRO = 1 MINUTO
     * LA REGLA DE LAS 4 HORAS SE DEBE CUMPLIR
     */
    public void enviarPendientes(boolean sendMaxSize,Date fechaEnvio) {
        Dao dao;
        DBHelper dbHelper = null;
        try {
            String noEmpleado = this.context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
            dao = DBHelper.getHelper(this.context, dbHelper).getGpsDAO();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.setWhere(queryBuilder.where()
                    .eq(RegistroGPS.NUM_EMPELADO, noEmpleado)
                    .and().eq(RegistroGPS.SUCCESS, false));
            queryBuilder.limit((long) 20);

            List<RegistroGPS> registros = dao.query(queryBuilder.prepare());
            if (!sendMaxSize) {
                Log.i(TAG, "se envío registro wifi ");
                saveOnServer(registros,fechaEnvio);
            } else {
                if (registros.size() > 5) {
                    manager.add(Constants.READY_TO_UPDATE, true);
                }
                if (manager.get(Constants.READY_TO_UPDATE)) {
                    int size = registros.size() > 5 ? 5 : registros.size();
                    saveOnServer(registros.subList(0,size),fechaEnvio);
                    if (size <= 1) {
                        manager.add(Constants.READY_TO_UPDATE, false);
                    }
                }

            }

        } catch (SQLException e) {
            Log.e(TAG, "Error creando lista de registros");
        }
        catch (Exception e){
            Log.e(TAG,"Error creando lista de regustros");
        }
    }


    /*OBTIENE EL ÚLTIMO REGISTRO GUARDADO EN LA TABLA DE GPS*/
    public RegistroGPS getLastRegistro() {
        RegistroGPS ultimo = null;
        try {
            Dao dao;
            DBHelper dbHelper = null;
            dao = DBHelper.getHelper(context, dbHelper).getGpsDAO();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(RegistroGPS.ID_ACTIVIDAD, false); //DE EL ÚLTIMO AL PRIMERO
            queryBuilder.limit((long) 1);
            List<RegistroGPS> registros = dao.query(queryBuilder.prepare());
            if (registros.size() > 0) {
                ultimo = registros.get(0);
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return ultimo;
    }

    /*REVISA SI HAY PENDIENTES POR ENVIAR
    * REVISA SI HAY CONEXIÓN A WIFI Y SI ES ASÍ LOS ENVIA AL SERVER
    * */
    public void checkIfEnviarPendientes(Date fechaEnvio) {
        //   WifiManager wifi = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (Utils.hasInternet(this.context)) {
            if (mWifi.isConnected()) {
                this.enviarPendientes(false,fechaEnvio);
            } else {
                this.enviarPendientes(true,fechaEnvio);
            }
        } else {
            this.checkIfEliminarRegistros();
        }
    }

    private void onErrorTokenGenerado(){
        ControllerTimezone controllerTimezone=new ControllerTimezone(this.context);
        controllerTimezone.revisarHora();
        controllerTimezone.setCallBackTimezone(new CallBackTimezone() {
            @Override
            public void OnSuccess(Calendar calendarApi) {
                    checkIfEnviarPendientes(calendarApi.getTime());
            }

            @Override
            public void OnError(Throwable error) {
            }
        });
    }


    /*REVISA SI ES NECESARIO ELIMINAR LOS REGISTROS DE LA BD*/
    public boolean checkIfEliminarRegistros() {
        boolean resultado = false;
        DateUtils dateUtils = new DateUtils(this.context);
        String ultimoIntento = dateUtils.getFormatDate(Constants.ULTIMO_INTENTO_BORRADO);
        if (ultimoIntento != null && (ultimoIntento.equals(dateUtils.getFormatTodayDate()))) {
            resultado = false;
        } else {

            try {
                Dao dao = DBHelper.getHelper(this.context, mDBHelper).getGpsDAO();
                //QUERYBUILDER PARA EL ÚLTIMO ELEMENTO DE LA BD
                ArrayList<RegistroGPS> listRegistros = (ArrayList) dao.queryForEq(RegistroGPS.SUCCESS, true);
                if (listRegistros != null && (!listRegistros.isEmpty())) {
                    RegistroGPS primerElemento = listRegistros.get(0);
                    RegistroGPS ultimoElemento = listRegistros.get(listRegistros.size() - 1);
                    if (ultimoElemento != null && primerElemento != null) {
                        Log.i(TAG, "ELMINANDO PENDIENTES...");
                         /*OBTENER DIFERENCIA ENTRE DÍAS*/
                        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_FORMAT);
                /*OBTENEMOS FECHA DEL PRIMER DÍA Y EL ÚLTIMO*/
                        Date datePrimero = dateFormat.parse(primerElemento.getFecha());
                        Date dateUltimo = dateFormat.parse(ultimoElemento.getFecha());
                        //DIFERENCIA ENTRE DÍAS
                        long diasDiferencia = (dateUltimo.getTime() - datePrimero.getTime()) / (24 * 60 * 60 * 1000);
                        Log.i(TAG, "Días de diferencia:" + String.valueOf(diasDiferencia));
                        //¿LA DIFERENCIA ENTRE DÍAS ES MAYOR IGUAL A 2?
                        if (diasDiferencia >= Constants.INTERVALO_BORRADO) {
                            DeleteBuilder deleteBuilder = dao.deleteBuilder();
                            deleteBuilder.setWhere(deleteBuilder.where().eq(RegistroGPS.SUCCESS, true));
                            dao.delete(deleteBuilder.prepare());
                            resultado = true;
                        }
                        //SE PROCEDE A GUARDAR EL ÚLTIMO INENTO DE ELIMINAR
                        else {
                            dateUtils.addDate(Constants.ULTIMO_INTENTO_BORRADO, new Date());
                            resultado = false;
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }


    public ArrayList<RegistroGPS>getByDate(Date fechaFiltrar)
    {
        ArrayList<RegistroGPS>gpsArrayList=new ArrayList<RegistroGPS>();
        try {


            Dao   dao = DBHelper.getHelper(this.context, mDBHelper).getGpsDAO();
            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(RegistroGPS.ID_ACTIVIDAD,false);
            queryBuilder.setWhere(queryBuilder.where().
                    eq(RegistroGPS.FECHA, new SimpleDateFormat(Constants.DAY_FORMAT)
                            .format(fechaFiltrar)));
            gpsArrayList=(ArrayList<RegistroGPS>)dao.query(queryBuilder.prepare());
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return gpsArrayList;
    }
    /*REGRESA LOS ELEMENTOS NO SINCRONIZADOS DE LA BD*/
    public ArrayList<RegistroGPS>getNoSincronizados(){
        ArrayList<RegistroGPS>gpsArrayList=new ArrayList<RegistroGPS>();
        try {
            Dao dao = DBHelper.getHelper(this.context, mDBHelper).getGpsDAO();
            gpsArrayList=(ArrayList)dao.queryForEq(RegistroGPS.SUCCESS, false);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return gpsArrayList;
    }



    /**REGRESA EL NOMBRE DE LA ACTIVIDAD DE ACUERDO A SU ID /*/
    private String getActividad(String id_actividad){

        Actividad actividad=null;

        try{
            DBHelper dbHelper=null;
            Dao dao=DBHelper.getHelper(context,dbHelper).getActividadDAO();
            QueryBuilder queryBuilder =dao.queryBuilder();
            queryBuilder.setWhere(queryBuilder.where().eq(Actividad.ID_GPS,id_actividad));
            actividad= (Actividad)dao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return actividad!=null? actividad.getTipo():null;
    }

    public void onDestroy(){
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }
}