package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.UI.Controls.Model.SearchBoxItem;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.CallBacks.CallBackAsistencias;
import com.sociomas.core.WebService.Model.Request.Asistencia.AsistenciaRequest;
import com.sociomas.core.WebService.Model.Response.Asistencia.AsistenciaResponse;
import com.sociomas.core.WebService.Model.Response.Asistencia.ExpandableGroupAsistencia;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy9 on 26/09/2017.
 */

public class AsistenciaInteractor {

    public static final String TAG=AsistenciaInteractor.class.getName();
    private SimpleDateFormat dayFormat=new SimpleDateFormat(Constants.DAY_FORMAT);
    private SimpleDateFormat hourFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);
    public static final int DIA_HOY=10;


    public void getAsistenciasAsync(final AsistenciaRequest request, final CallBackAsistencias callBackAsistencias){
        final HashMap<String,String> hashEmpleados=new HashMap<>();
        final HashMap<Integer,ArrayList<ResultadoAsistencia>>hashAsistenciasAgrupadas=new HashMap<>();
        final HashMap<Integer,ArrayList<ResultadoAsistencia>>hashAsistenciasHoy=new HashMap<>();
        final Calendar calendarDia=Calendar.getInstance();
        final Calendar calendarHoy=Calendar.getInstance();
        final String numeroEmpleadoBuscado=request.getNumeroEmpleado();
        final ArrayList<SearchBoxItem>listEmpleados=new ArrayList<>();
        ApplicationBase.getIntance().getControllerAsistencia().obtenerReporte(request).enqueue(new Callback<AsistenciaResponse>() {
            @Override
            public void onResponse(Call<AsistenciaResponse> call, Response<AsistenciaResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){

                        for(ResultadoAsistencia r: response.body().getResultadoAsistencia()) {
                            if (r.getHoraEntrada() != null) {
                                r.setFechaReporte(dayFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                r.setHoraEntrada(hourFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                            }
                            if (r.getHoraSalida() != null) {
                                r.setHoraSalida(hourFormat.format(Utils.parseJsonString(r.getHoraSalida())));
                            }
                                    /*REVISA SI EL HASHMAP CONTIENE EL DÍA"*/
                            try {
                                calendarDia.setTime(dayFormat.parse(r.getFechaReporte()));

                                int MES = calendarDia.get(Calendar.MONTH);
                                if (r.getNumeroEmpleado().equals(numeroEmpleadoBuscado)) {
                                    if (!hashAsistenciasAgrupadas.containsKey(MES)) {
                                        ArrayList<ResultadoAsistencia> listAsistencia = new ArrayList<>();
                                        //HEADER PARA ADAPTER
                                        ResultadoAsistencia rHeader = new ResultadoAsistencia();
                                        rHeader.setHeader(true);
                                        listAsistencia.add(rHeader);
                                        //ITEM  DE ASISTENCIA
                                        listAsistencia.add(r);
                                        hashAsistenciasAgrupadas.put(calendarDia.get(Calendar.MONTH), listAsistencia);
                                    } else {
                                        hashAsistenciasAgrupadas.get(MES).add(r);
                                    }
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //SE VERIFICA SI EXISTE EL ID DE EMPLEADO
                            if(!hashEmpleados.containsKey(r.getNumeroEmpleado())){
                                hashEmpleados.put(r.getNumeroEmpleado(),r.getNombreEmpleado());
                            }
                        }


                        String numeroEmpleado= Utils.getNumeroEmpleado(ApplicationBase.getIntance().getApplicationContext());
                        int position=0, positionMia=0;
                        //SE LLENA EL ARRAYLIST CON  EL HASHMAP DE EMPLEADOS
                        for(Map.Entry<String,String>item: hashEmpleados.entrySet()){
                            //REVISA SI EL NÚMERO DE EMPLEADO COINCIDE AL EMPLEADO LOGEADO
                            boolean isMia=item.getKey().equals(numeroEmpleado);
                            //AGREGA ITEMS A LA LISTA DE EMPLEADOS
                            if(!isMia) {
                                listEmpleados.add(new SearchBoxItem(item.getKey(), item.getValue()));
                            }
                            else{
                                listEmpleados.add(0,new SearchBoxItem(item.getKey(), ApplicationBase.getIntance().getManager().getString(Constants.SP_NAME)));
                            }
                            position++;
                        }

                        ArrayList<ExpandableGroupAsistencia>list=new ArrayList<>();

                        ArrayList<ResultadoAsistencia>listHoy=new ArrayList<>();
                        listHoy.add(0,new ResultadoAsistencia(true));
                        for(ResultadoAsistencia r: response.body().getDiaActual()){
                            if (r.getHoraEntrada() != null) {
                                r.setFechaReporte(dayFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                                r.setHoraEntrada(hourFormat.format(Utils.parseJsonString(r.getHoraEntrada())));
                            }
                            if (r.getHoraSalida() != null) {
                                r.setHoraSalida(hourFormat.format(Utils.parseJsonString(r.getHoraSalida())));
                            }
                        }
                        listHoy.addAll(response.body().getDiaActual());


                        for(Map.Entry<Integer,ArrayList<ResultadoAsistencia>> item: hashAsistenciasAgrupadas.entrySet()){
                            list.add(new ExpandableGroupAsistencia(item.getKey().toString(),item.getValue()));
                        }



                        if(callBackAsistencias!=null){
                            callBackAsistencias.OnSuccess(list,listHoy);
                            callBackAsistencias.OnResponseListEmpleado(listEmpleados);
                        }
                    }
                    else{
                        if(callBackAsistencias!=null){
                            callBackAsistencias.OnError(new Throwable(response.body().getMensajeError()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AsistenciaResponse> call, Throwable t) {
                callBackAsistencias.OnError(t);
            }
        });
    }
}
