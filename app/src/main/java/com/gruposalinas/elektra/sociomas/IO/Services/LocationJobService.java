package com.gruposalinas.elektra.sociomas.IO.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.IO.Recievers.LocationAlarmReceiver;
import com.gruposalinas.elektra.sociomas.Utils.DeviceUtils;
import com.gruposalinas.elektra.sociomas.Utils.SupportUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.Utils.Constants;

/*
* TAREA PROGRAMADA  QUE SE ENCARGA DE LLAMAR AL  BROADCAST RECEIVER DE LOCATION ALARMRECEIVER
* */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LocationJobService extends JobService {
    private static final String TAG = "LocationJobService";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.w(TAG,"Registrando ubicación de usuario...");
        sendBroadcast(new Intent(this, LocationAlarmReceiver.class));
        Utils.runScheduleJob(this, jobParameters.getExtras().getBoolean(Constants.FROM_REBOOT, false));
        jobFinished(jobParameters, false);
        return true;
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }



}
