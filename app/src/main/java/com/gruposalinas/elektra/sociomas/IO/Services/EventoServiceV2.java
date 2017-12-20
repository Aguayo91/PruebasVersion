 package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.gruposalinas.elektra.sociomas.Utils.Security.SecureDate;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.OrmLite.DBHelper;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sociomas.core.DataBaseModel.ObtenerEstatusAlerta;
import com.sociomas.core.Utils.Manager.SessionManager;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Contacto.Evento;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 /**
 * Created by oemy9 on 21/04/2017.
 */

public class EventoServiceV2 extends Service {

     public static final String TAG="EVENTO_SERVICE";
     private DBHelper mDBHelper;



     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
         onEventGenerator();
         this.mDBHelper=new DBHelper(this);
         Handler handler=new Handler();

         /*ENVIAR PENDIENTES*/
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                sendNoEnviados();
             }
         },20000);


         return START_STICKY;
     }

     @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String  getFechaEvento()
    {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);
        String dateString = dateFormatter.format(date);
        String hourString = timeFormatter.format(date);
        return generateJsonDate(dateString,hourString);

    }

    private String generateJsonDate(String fecha ,String hora){
        String jsonDate;
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(fecha + " " + hora);
        } catch (java.text.ParseException e) {
            Log.e("error", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);

        return jsonDate;
    }

    private void onEventGenerator()
    {
        Date date;
        /*ACTUALIZACIÓN 04/04/2017*/
        SessionManager manager=new SessionManager(this);
        if(manager.get(Constants.IS_ERROR_FECHA)) {
            SecureDate secureDate=new SecureDate(this);
            date=secureDate.getDateCalculada();
        }
        else{
            date = new Date();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);

        SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);

        String  dateString = dateFormatter.format(date);
        String  hourString = timeFormatter.format(date);

        int Evento=manager.getInt(Constants.EVENTO);
        String numeroEmpleado=manager.getString(Constants.SP_ID);


        ObtenerEstatusAlerta obtener=new ObtenerEstatusAlerta(
                numeroEmpleado,false,getFechaEvento(),
                Evento,Utils.getBatteryLevel(this),
                Utils.generateMovementId(dateString,hourString));
        saveInBD(obtener);

    }
     public void updateInBD(Evento evento){
         String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
         try {
            Dao dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getObtenerEstatusAlertasDAO();
             QueryBuilder queryBuilder = dao.queryBuilder();
                 queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.ID, noEmpleado)
                         .and().eq(ObtenerEstatusAlerta.IDEVENTO, evento.getIdEvento()));
                 ObtenerEstatusAlerta estatusAlerta= (ObtenerEstatusAlerta)dao.queryForFirst(queryBuilder.prepare());
                  if(estatusAlerta!=null) {
                      estatusAlerta.setsuccess(evento.isSetSuccess());
                      estatusAlerta.setSuccess(evento.isSetSuccess());
                      dao.update(estatusAlerta);
                  }

         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     public void saveInBD(ObtenerEstatusAlerta obtenerEstatusAlerta)
     {
         Dao dao;
         try {

             boolean isNew = true;
             List<ObtenerEstatusAlerta> registrosTemp;
             dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getObtenerEstatusAlertasDAO();

             try {
                 String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
                 dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getObtenerEstatusAlertasDAO();

                 QueryBuilder queryBuilder = dao.queryBuilder();
                 queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.ID, noEmpleado)
                         .and().eq(ObtenerEstatusAlerta.SUCCESS, false));

                 registrosTemp = dao.query(queryBuilder.prepare());

                 if(registrosTemp.size() == 0 && !registrosTemp.get(0).getIdEvento().equals(obtenerEstatusAlerta.getIdEvento())) {
                     Log.i("", "Ningún registro con id = " + obtenerEstatusAlerta.getIdEvento());
                 } else {
                     Log.i("", "Recuperado registro con id = " + registrosTemp.get(0).getIdEvento());
                     isNew = false;
                 }

             }catch(Exception e){
                 Log.e("", "No se hizo la búsqueda el registro");
             }

             if(isNew){
                 dao.create(obtenerEstatusAlerta);
             }
             else{
                 if(obtenerEstatusAlerta.isSuccess()){
                     dao.update(obtenerEstatusAlerta);
                 }

             }

         } catch (SQLException e) {
             Log.e("", "Error");
         }

     }
     public void sendNoEnviados(){
         Dao dao;
         DBHelper dbHelper = null;
         try {
             dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getObtenerEstatusAlertasDAO();
             String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
             QueryBuilder queryBuilder = dao.queryBuilder();
             queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.ID,noEmpleado).and().eq(ObtenerEstatusAlerta.SUCCESS, false));
             List<ObtenerEstatusAlerta> registros = dao.query(queryBuilder.prepare());

             queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.ID,noEmpleado).and().eq(ObtenerEstatusAlerta.SUCCESS, true));
             List<ObtenerEstatusAlerta> registrosEnviados = dao.query(queryBuilder.prepare());
            Log.i(TAG,"ENVIADOS SIZE"+registrosEnviados.size());

             if(registros.size()>0){

                 if(Utils.hasInternet(this))
                 {
                     int size=registros.size()>30? 30: registros.size();

                     Log.i(TAG,"NO ENVIADOS SIZE"+size);

                     for(int i=0;i<size;i++){
                         Evento evento=new Evento();
                         evento.setIdEvento(registros.get(i).getIdEvento());
                         evento.setIdNumEmpleado(registros.get(i).getId_numero_empleado());
                         evento.setFecha(registros.get(i).getFecha());
                         evento.setComentario(String.valueOf(registros.get(i).getBateria()));
                         evento.setEvento(String.valueOf(registros.get(i).getEvento()));
                         saveInServer(evento);
                     }
                 }

             }

         } catch (SQLException e)
         {
             e.printStackTrace();
         }


     }
     private void saveInServer(final Evento evento){
         ControllerAPI controllerAPI=new ControllerAPI(this);
         final SessionManager manager = new SessionManager(EventoServiceV2.this);
         controllerAPI.registrarEventoTelefono(evento).enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try {
                            Gson gson=new Gson();
                            String json=response.body().string();
                            ServerResponse serverResponse=gson.fromJson(json, ServerResponse.class);
                            //HAY UN ERROR CON LA SYNCRONIZACIÓN DEL TOKEN Y LA FECHA DEL DISPOSITIVOS
                            if(serverResponse.getServerUTCTime()!=null) {
                                manager.add(Constants.IS_ERROR_FECHA, true);
                                if(serverResponse.getMensajeError()!=null){
                                    evento.setSuccess(false);
                                }

                            }
                            else{
                                if(serverResponse.getServerTime()!=null){
                                    SecureDate secureDate=new SecureDate(EventoServiceV2.this);
                                    secureDate.saveServerDate(serverResponse.getServerTime());
                                    manager.add(Constants.IS_ERROR_FECHA,false);
                                }
                                if(serverResponse.getError()) {
                                    evento.setSuccess(false);
                                }
                                else {
                                    evento.setSuccess(true);
                                }

                            }
                            updateInBD(evento);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        evento.setSuccess(false);
                        updateInBD(evento);
                    }
             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

             }
         });
     }


}
