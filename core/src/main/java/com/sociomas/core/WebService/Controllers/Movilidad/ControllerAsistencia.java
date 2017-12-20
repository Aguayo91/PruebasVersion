package com.sociomas.core.WebService.Controllers.Movilidad;
import android.content.Context;

import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.WebService.CallBacks.CallBackAsistencias;
import com.sociomas.core.WebService.Controllers.ControllerBase;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaRequest;
import com.sociomas.core.WebService.Model.Response.Asistencia.AsistenciaResponse;
import com.sociomas.core.WebService.Services.APIAsistenciaInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.http.Body;

/**
 * Created by oemy9 on 10/04/2017.
 */
public class ControllerAsistencia extends ControllerBase implements APIAsistenciaInterface {
    private APIAsistenciaInterface service;
    private CallBackAsistencias callBackAsistencias;
    private SimpleDateFormat dayFormat = new SimpleDateFormat(Constants.DAY_FORMAT);
    private SimpleDateFormat hourFormat = new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);

    public ControllerAsistencia(Context context) {
        super(context, Constants.DOMAIN_URL_ASISTENCIA);
        this.service = retrofit.create(APIAsistenciaInterface.class);
    }

    public void setCallBackAsistencias(CallBackAsistencias callBackAsistencias) {
        this.callBackAsistencias = callBackAsistencias;
    }

    @Override
    public Call<AsistenciaResponse> obtenerReporte(@Body AsistenciaRequest request) {
        //SIN PARAMETRO DE FECHA
        if (request.getFechaInicio() == null || (request.getFechaInicio().isEmpty())) {
            //formato del día
            SimpleDateFormat diaFormato = new SimpleDateFormat(Constants.DAY_FORMAT);
            //tres meses hacia atras
            Calendar calendarTresMeses = Calendar.getInstance();
            calendarTresMeses.set(Calendar.DAY_OF_MONTH, 1);
            calendarTresMeses.add(Calendar.MONTH, -3);
            request.setFechaInicio(dayFormat.format(calendarTresMeses.getTime()));
            request.setFechaFin(dayFormat.format(new Date()));
        }
        SecurityItems securityItems = new SecurityItems(request.getNumeroEmpleado());
        request.setNumeroEmpleado(securityItems.getIdEmployEncrypt());
        request.setEmpleadoSolicita(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setTodos(1);
        request.setNivel(1);
        return service.obtenerReporte(request);
    }

    public Call<AsistenciaResponse>obtenerReporteFecha(AsistenciaRequest request){
        return service.obtenerReporte(request);
    }


}
