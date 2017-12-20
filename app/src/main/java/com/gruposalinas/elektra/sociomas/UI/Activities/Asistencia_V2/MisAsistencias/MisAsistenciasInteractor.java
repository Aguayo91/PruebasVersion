package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias;
import android.content.Context;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.MVP.BaseInteractor;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackAsistencias;
import com.sociomas.core.WebService.Controllers.Movilidad.ControllerAPI;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaRequest;
import com.sociomas.core.WebService.Model.Response.Asistencia.AsistenciaResponse;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import org.reactivestreams.Subscriber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 25/11/2017.
 */

public class MisAsistenciasInteractor extends BaseInteractor {
    private static final String TAG = "AsistenciasInteractor";
    private SimpleDateFormat dayFormat=new SimpleDateFormat(Constants.DAY_FORMAT);
    private SimpleDateFormat hourFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);


    public MisAsistenciasInteractor(){
        super(ApplicationBase.getIntance().getApplicationContext(), ApplicationBase.getIntance().getControllerAsistencia());
    }


    public void getAsistenciasHoy(final String numeroEmpleado, Calendar calendarInicio, Calendar calendarFin, final Subscriber<ResultadoAsistencia>subscriber){
        AsistenciaRequest request=new AsistenciaRequest();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        SecurityItems securityItemsLogeado=new SecurityItems(Utils.getNumeroEmpleado(getContext()));
        request.setNumeroEmpleado(securityItems.getIdEmployEncrypt());
        request.setEmpleadoSolicita(securityItemsLogeado.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setFechaInicio(dayFormat.format(calendarInicio.getTime()));
        request.setFechaFin(dayFormat.format(calendarFin.getTime()));
        request.setTodos(0);
        request.setNivel(1);
        getControllerAsistencia().obtenerReporteFecha(request).enqueue(new Callback<AsistenciaResponse>() {
            @Override
            public void onResponse(Call<AsistenciaResponse> call, Response<AsistenciaResponse> response) {
                    if(response.isSuccessful()) {
                        if (response.body().getDiaActual() != null) {
                            io.reactivex.Observable.fromIterable(response.body().getDiaActual()).filter(new Predicate<ResultadoAsistencia>() {
                                @Override
                                public boolean test(ResultadoAsistencia r) throws Exception {
                                    return r.getNumeroEmpleado().equalsIgnoreCase(numeroEmpleado);
                                }
                            }).toList().map(new Function<List<ResultadoAsistencia>, List<ResultadoAsistencia>>() {
                                @Override
                                public List<ResultadoAsistencia> apply(List<ResultadoAsistencia> list) throws Exception {
                                    //Modificamos el formato fecha json a uno normal
                                    List<ResultadoAsistencia> listR = new ArrayList<>();
                                    for (ResultadoAsistencia r : list) {
                                        if (r.getHoraEntrada() != null) {
                                            r.setFechaReporte(dayFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                            r.setHoraEntrada(hourFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                        }
                                        if (r.getHoraSalida() != null) {
                                            r.setHoraSalida(hourFormat.format(Utils.parseJsonString(r.getHoraSalida())));
                                        }
                                        listR.add(r);
                                    }
                                    return listR;
                                }
                            }).subscribe(new Consumer<List<ResultadoAsistencia>>() {
                                @Override
                                public void accept(List<ResultadoAsistencia> list) throws Exception {
                                    if (list != null && (!list.isEmpty())) {
                                        io.reactivex.Observable.fromIterable(list).take(1).subscribe(new Consumer<ResultadoAsistencia>() {
                                            @Override
                                            public void accept(ResultadoAsistencia r) throws Exception {
                                                subscriber.onNext(r);
                                                subscriber.onComplete();
                                            }
                                        });
                                    } else {
                                        subscriber.onError(new Throwable(getContext().getString(R.string.no_asistencia_empleado)));
                                        subscriber.onComplete();
                                    }
                                }
                            });
                        }
                    }
            }

            @Override
            public void onFailure(Call<AsistenciaResponse> call, Throwable t) {
                if(t!=null){
                    subscriber.onError(t);
                    subscriber.onComplete();
                }
            }
        });
    }


    /**
     *
     * Hace el request a al servicio para obtener  las asistencias
     * @param numeroEmpleado  Número de empleado el cual solicita
     * @param calendarInicio  Fecha de inicio
     * @param calendarFin     Fecha de fin
     * @param todos           ¿Se mostrarán los empleados de plantilla?
     * @param listFiltros     Define los filtros del listado
     */
    public void getAsistenciasAsync(final String numeroEmpleado, Calendar calendarInicio, Calendar calendarFin, boolean todos, final ArrayList<EnumAsistencia>listFiltros, final Subscriber<List<ResultadoAsistencia>>subscriber){

        AsistenciaRequest request=new AsistenciaRequest();
        SecurityItems securityItems = new SecurityItems(numeroEmpleado);
        request.setNumeroEmpleado(securityItems.getIdEmployEncrypt());
        request.setEmpleadoSolicita(securityItems.getIdEmployEncrypt());
        request.setToken(securityItems.getTokenEncrypt());
        request.setFechaInicio(dayFormat.format(calendarInicio.getTime()));
        request.setFechaFin(dayFormat.format(calendarFin.getTime()));
        request.setTodos(todos? 1: 0);
        request.setNivel(1);

        getControllerAsistencia().obtenerReporteFecha(request).enqueue(new Callback<AsistenciaResponse>() {
            @Override
            public void onResponse(Call<AsistenciaResponse> call, Response<AsistenciaResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getResultadoAsistencia() == null) {
                        subscriber.onError(new Throwable(ApplicationBase.getAppContext().getString(R.string.Error_Conexion)));
                        return;
                    }
                    io.reactivex.Observable.fromIterable(response.body().getResultadoAsistencia())
                            .filter(new Predicate<ResultadoAsistencia>() {
                                @Override
                                public boolean test(ResultadoAsistencia r) throws Exception {
                                    //Filtrar la lista de acuerdo a los parametros enviado en la vista
                                    for (EnumAsistencia item : listFiltros) {
                                        boolean res = r.getIdEstado() == item.getValue();
                                        if (res) {
                                            return res;
                                        }
                                    }
                                    return false;
                                }
                            }).filter(new Predicate<ResultadoAsistencia>() {
                        @Override
                        public boolean test(ResultadoAsistencia r) throws Exception {
                            //Se filtra de acuerdo al número de empleado
                            return r.getNumeroEmpleado().equals(numeroEmpleado);
                        }
                    }).toList().map(new Function<List<ResultadoAsistencia>, List<ResultadoAsistencia>>() {
                        @Override
                        public List<ResultadoAsistencia> apply(List<ResultadoAsistencia> list) throws Exception {
                            //Modificamos el formato fecha json a uno normal
                            List<ResultadoAsistencia>listR=new ArrayList<>();
                            for(ResultadoAsistencia r:list){
                                if (r.getHoraEntrada() != null) {
                                    r.setFechaReporte(dayFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                    r.setHoraEntrada(hourFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                }
                                if (r.getHoraSalida() != null) {
                                    r.setHoraSalida(hourFormat.format(Utils.parseJsonString(r.getHoraSalida())));
                                }
                                listR.add(r);
                            }
                            return listR;
                        }
                    }).subscribe(new Consumer<List<ResultadoAsistencia>>() {
                        @Override
                        public void accept(List<ResultadoAsistencia> resultadoAsistencias) throws Exception {
                            subscriber.onNext(resultadoAsistencias);
                            subscriber.onComplete();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<AsistenciaResponse> call, Throwable t) {
                if(t!=null){
                    subscriber.onError(t);
                    subscriber.onComplete();
                }
            }
        });

    }
}
