
package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.sociomas.core.Utils.Security.SecureDate;
import com.sociomas.core.WebService.CallBacks.CallBackTimezone;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerTimezone;

import java.util.Calendar;

/*CONSULTA LA HORA DE LA API DE TIMEZONE DB CUANDO EL USUARIO CAMBÍA LA HORA DE SU DISPOSITIVO*/
public class HoraCheckerService extends Service implements CallBackTimezone {

    private  boolean consultado=false;
    public static final String TAG="HORA_CHECKER";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(null!=intent) {
            ControllerTimezone controllerTimezone=new ControllerTimezone(this);
            controllerTimezone.setCallBackTimezone(this);
            Log.i(TAG, "LLAMANDO A SERVICIO DE HORA CHECKER");

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void OnSuccess(Calendar calendarApi) {
         /*FINALMENTE TRATA DE ELIMINAR LOS REGISTROS DEL GPS*/
        //IMPORTANTE ESTE MÉTODO SE PUSO EN ESTE SERVICIO PORQUE SE MANDA A LLAMAR POR PRIMERA VEZ CUANDO SE ENCIENDE
        //EL DISPOSITIVO
        GPSBDHelper gpsBDHelper = new GPSBDHelper(this);
        gpsBDHelper.checkIfEliminarRegistros();
    }

    @Override
    public void OnError(Throwable error) {

    }
}
