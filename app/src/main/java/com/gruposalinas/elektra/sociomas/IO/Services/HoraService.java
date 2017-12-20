package com.gruposalinas.elektra.sociomas.IO.Services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gruposalinas.elektra.sociomas.UI.Activities.Inicio.InicioActivity;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.DateUtils;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.gruposalinas.elektra.sociomas.Utils.Date.TimeUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.DataBaseModel.RangoMonitoreo;
import com.sociomas.core.Utils.Enums.EnumAlarma;

import java.sql.SQLException;
import java.text.ParseException;

/*ENVIA ALARMAS DE HORA DE ENTRADA
* HORA DE SALIDA
* Y DIES MINUTOS DESPUÉS*/
public class HoraService extends Service {
    public static final String TAG="HORA_SERVICE";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null!=intent){
            try {
            //¿CUÁNDO FUE LA ÚLTIMA VEZ QUE LA APP ESTUVO EN FOREGROUND?
            DateUtils dateUtils = new DateUtils(this);
            if (dateUtils.getDiferenciaDias(Constants.ULTIMA_VEZ_APP_FOREGROUND) >= 2) {
                //REVISAMOS SI SE PUEDE ABRIR ALGÚN ACTIVITY
                Intent i = new Intent(this, InicioActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Constants.ULTIMA_VEZ_APP_FOREGROUND, true);
                startActivity(i);
            }
              TimeUtils timeUtils=new TimeUtils(getApplicationContext());
                RangoMonitoreo rangoMonitoreo = Utils.getHorarioByNumeroDia(this);
                if (rangoMonitoreo != null) {
                    String entrada = rangoMonitoreo.getHoraInicial();
                    String salida = rangoMonitoreo.getHoraFinal();
                    long milisegundosEntrada = timeUtils.getDiferenceInMillis(entrada);
                    long milisegundosSalida = timeUtils.getDiferenceInMillis(salida);
                    //NOTIFICACIONES HORA DE ENTRADA
                    if (milisegundosEntrada > 0) {
                       // Log.i(TAG,timeUtils.getAmigableFormat(entrada, true));
                        Utils.runAlarm(this, milisegundosEntrada, EnumAlarma.entrada); //ALARMA DE ENTRADA
                        Utils.runAlarm(this, timeUtils.getDiferenceInMillis(entrada, 10), EnumAlarma.diez_minutos); //ALARMA DIEZ MINUTOS DESPUÉS
                        stopSelf();
                    }
                    //NOTIFICACIÓN HORA DE SALIDA
                    else if (milisegundosSalida > 0) {
                     //   Log.i(TAG,timeUtils.getAmigableFormat(salida, false));
                        Utils.runAlarm(this, timeUtils.getDiferenceInMillis(salida), EnumAlarma.salida); //ALARMA DE SALIDA
                        stopSelf();
                    }
                    //HA PASADO LA HORA DE ENTRADA Y SALIDA VAMOS DE REGRESO
                    else if (milisegundosEntrada < 0 && milisegundosSalida < 0) {
                        /*¿PODEMOS APROVECHAR PARA VERIFICAR CUANTOS RECORDS HAY EN LA BD DE GPS
                        * Y DARLES DELETE?*/
                        GPSBDHelper gpsbdHelper = new GPSBDHelper(this);
                        //NO SE LIMINARON LOS REGISTROS
                        gpsbdHelper.checkIfEliminarRegistros();
                        stopSelf();
                    }
                }
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            catch (ParseException p){
                p.printStackTrace();
            }
            catch (Exception ex){
                ex.printStackTrace();;
            }

        }
        return  START_STICKY;

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
