package com.gruposalinas.elektra.sociomas.IO.Services;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.OrmLite.DBHelper;

import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sociomas.core.DataBaseModel.Actividad;
import com.sociomas.core.DataBaseModel.RegistroGPS;

import java.sql.SQLException;
import java.util.List;


public class ActivityIntentService extends IntentService {

    private String TAG="INTENT_SERVICEY";
    public ActivityIntentService(){
        super("ActivityIntentService");
    }
    private DBHelper dbHelper =null;
    private Dao dao;
    private Dao daoActividad;
    private String lastID=null;
    @Override
    protected void onHandleIntent(Intent intent) {
           Log.i(TAG, "INICIANDO ACTIVITY INENT");

        if(intent!=null) {
            try {
                this.dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getGpsDAO();
                this.daoActividad = DBHelper.getHelper(getApplicationContext(), dbHelper).getActividadDAO();
                this.lastID = Utils.getLastIDGPS(getApplicationContext());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ActivityService.pendingIntent != null) {
                ActivityService.pendingIntent.cancel();
            }
            if (ActivityRecognitionResult.hasResult(intent)) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                detectarActivities(result.getProbableActivities());
                Log.i(TAG, "TIENE  PROBABLES:" + String.valueOf(result.getProbableActivities().size()));
            } else {
                Log.i(TAG, "NO HE DETECTADO NINGUNA ACTIVIDAD");
            }
        }
    }
    private void detectarActivities(List<DetectedActivity> activityList){
            boolean detectado=false;
            for(DetectedActivity activity: activityList){
                Log.i(TAG,"CONFIDENCE"+String.valueOf( activity.getConfidence()));
                if(activity.getConfidence()>50) {
                    switch (activity.getType()) {
                        case DetectedActivity.IN_VEHICLE:
                            Log.i(TAG, Constants.VEHICULO);
                            this.updateInDB(Constants.VEHICULO);
                            break;
                        case DetectedActivity.ON_BICYCLE:
                            Log.i(TAG,Constants.BICICLETA);
                            this.updateInDB(Constants.BICICLETA);
                            break;
                        case DetectedActivity.ON_FOOT:
                            Log.i(TAG,Constants.CAMINANDO);
                            this.updateInDB(Constants.CAMINANDO);
                            break;
                        case DetectedActivity.RUNNING:
                            Log.i(TAG,Constants.CORRIENDO);
                            this.updateInDB(Constants.CORRIENDO);
                            break;
                        case DetectedActivity.TILTING:
                            Log.i(TAG,Constants.CAMINANDO_LENTO);
                            this.updateInDB(Constants.CAMINANDO_LENTO);
                            break;
                        case DetectedActivity.STILL:
                            Log.i(TAG,Constants.OFICINA_CASA);
                            this.updateInDB(Constants.OFICINA_CASA);
                            break;
                    }
                    detectado=true;
                }
            }
        if(!detectado){
            Log.i(TAG,Constants.DESCONOCIDA);
            this.updateInDB(Constants.DESCONOCIDA);
        }
        stopSelf();
    }
    private void updateInDB(String tipo){
        if(Utils.getLastIDGPS(getApplicationContext())!=null){
                try {

                    QueryBuilder queryBuilder = dao.queryBuilder();
                    queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.ID_ACTIVIDAD,lastID));
                    List<RegistroGPS> listGps = dao.query(queryBuilder.prepare());
                    if(listGps!=null){
                        if(listGps.size()>0){
                            QueryBuilder queryBuilderGPS=dao.queryBuilder();
                            queryBuilderGPS.setWhere(queryBuilderGPS.where().eq(RegistroGPS.ID,listGps.get(0).getId()));
                            List<RegistroGPS>listGpsID= dao.query(queryBuilderGPS.prepare());
                            if (listGps != null) {
                                for (RegistroGPS registroGPS : listGpsID) {
                                    Actividad actividad = new Actividad();
                                    actividad.setFecha(registroGPS.getFecha());
                                    actividad.setHora(registroGPS.getHora());
                                    actividad.setNumEmpleado(registroGPS.getNumEmpleado());
                                    actividad.setJsonDate(registroGPS.getJsonDate());
                                    actividad.setIdGps(registroGPS.getId_actividad());
                                    actividad.setTipo(tipo);
                                    daoActividad.create(actividad);
                                }
                            } else {
                                //listGpsID Null there is no records
                            }
                        }
                    }


                }
                catch (SQLException ex){
                    Log.i(TAG,ex.getMessage());
                }

        }

    }




}
