package com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Justificar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.UI.Activities.JustificarV2.Listado.JustificacionSelectionInteractor;
import com.gruposalinas.elektra.sociomas.Utils.Adapters.AdapterUtils;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.WebService.CallBacks.CallBackAprobarRechazar;
import com.sociomas.core.WebService.Model.Response.Incidencia.ListadoIncidencias;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jmarquezu on 23/11/2017.
 */

public class JustificarIncidenciasPresenterImpl extends BasePresenterImpl implements JustificarIncidenciasPresenter {

    private JustificarIncidenciasView view;
    private ListadoIncidencias selectedIncidencia;
    private JustificacionSelectionInteractor interactor;
    private SimpleDateFormat dateFormatTo=new SimpleDateFormat(Constants.DAY_FORMAT);
    private SimpleDateFormat dateFormatFrom=new SimpleDateFormat(Constants.DATE_FORMAT);
    private SimpleDateFormat hourFormat=new SimpleDateFormat(Constants.HOUR_FORMAT_AM_PM);
    private Calendar c =Calendar.getInstance();
    private boolean isPlantilla;


    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(JustificarIncidenciasView)view;
        this.interactor=new JustificacionSelectionInteractor();
    }


    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(Constants.SELECTED_INCIDENCIA)){
            selectedIncidencia=(ListadoIncidencias)intent.getSerializableExtra(Constants.SELECTED_INCIDENCIA);
        }
        if(intent.hasExtra(Constants.IS_PLANTILLA)){
            isPlantilla=intent.getBooleanExtra(Constants.IS_PLANTILLA,false);
        }
    }

    @Override
    public void  obtenerInfoIncidencia(){

        if(selectedIncidencia!=null) {
            selectedIncidencia.setFecha(getDateFormat(selectedIncidencia.getFechaOcurrencia()));
            selectedIncidencia.setHoraEntrada(getHour(selectedIncidencia.getFechaOcurrencia()));
            selectedIncidencia.setHoraSalida(getHour(selectedIncidencia.getFechaSalidaOcurrencia()));
            selectedIncidencia.setDiaNombre(getDayName(selectedIncidencia.getFechaOcurrencia()));
            selectedIncidencia.setRecursoImagen(AdapterUtils.getHashIconosAsistencias().get(EnumAsistencia.fromString(selectedIncidencia.getIncidencia())));
            selectedIncidencia.setRecursoColor(AdapterUtils.getHashColoresAsistencas().get(EnumAsistencia.fromString(selectedIncidencia.getIncidencia())));
            obtenerMensajeIncidencia();
            view.setCurrentJustificacion(selectedIncidencia, isPlantilla);
        }

        else{
            onShowToast(R.string.no_info_incidencia);
        }

    }

    public void obtenerMensajeIncidencia(){
        EnumAsistencia enumAsistencia=EnumAsistencia.fromString(selectedIncidencia.getIncidencia());
        switch (enumAsistencia){
            case ENTRADA_DESPUES_HORA_LIMITE:
                view.showMensajeIncidencia(isPlantilla? getString(R.string.hora_limite_msj_plantilla):getString(R.string.hora_limite_msj) );
                break;
            case RETARDO:
                view.showMensajeIncidencia(isPlantilla? getString(R.string.retardo_msj_plantilla):   getString(R.string.retardo_msj));
                break;
            case FALTA:
                view.showMensajeIncidencia(isPlantilla? getString(R.string.falta_msj_plantilla):  getString(R.string.falta_msj));
                break;
            case SALIDA_ANTES_HORARIO:
                view.showMensajeIncidencia(isPlantilla ?getString(R.string.salida_antes_msj_plantilla): getString(R.string.salida_antes_msj));
                break;
        }
    }

    private String getString(@StringRes  int resource){
        return getView().getActivityInstance().getString(resource);
    }

    private Bundle getBundle(boolean rechazado){
        Bundle bundle=new Bundle();
        bundle.putBoolean(Constants.IS_PLANTILLA, isPlantilla);
        bundle.putSerializable(Constants.SELECTED_INCIDENCIA, selectedIncidencia);
        bundle.putBoolean(Constants.IS_RECHAZAR,rechazado);
        return bundle;
    }

    @Override
    public void onAutorizarRechazarIncidenciaPlantilla(final boolean autorizar, String comentario) {
        selectedIncidencia.setComentarioRechazo(comentario);
        interactor.rechazarAprobarAsync(selectedIncidencia, autorizar, new CallBackAprobarRechazar() {
            @Override
            public void OnSuccess(ServerResponse response) {
                view.hideDialogJustificar();
                view.sendJustificarEnviada(getBundle(!autorizar));
                onHideProgress();
            }

            @Override
            public void OnError(Throwable error) {
                view.hideDialogJustificar();
                onShowAlert(error.getMessage());
                onHideProgress();
            }
        });
    }


    @Override
    public void justificarIncidencia(String comentario) {
        onShowProgress();
        selectedIncidencia.setComentarios(comentario);
        interactor.justificarIncidencia(selectedIncidencia, new Subscriber<ServerResponse>() {
            @Override
            public void onSubscribe(Subscription s) {}

            @Override
            public void onNext(ServerResponse serverResponse) {
                if(!serverResponse.getError()){
                    view.hideDialogJustificar();
                    view.sendJustificarEnviada(getBundle(false));
                }
            }

            @Override
            public void onError(Throwable t) {
                view.hideDialogJustificar();
                onShowAlert(t.getMessage());
                onHideProgress();
            }

            @Override
            public void onComplete() {
                onHideProgress();
            }
        });

    }

    @Override
    public void onJefeSelected(String nombre, String numeroEmpleado) {
        selectedIncidencia.setEmpleado(numeroEmpleado);
        selectedIncidencia.setNombre(nombre);
        selectedIncidencia.setId_supervisor(numeroEmpleado);
        selectedIncidencia.setNombre_supervisor(nombre);
    }


    /**
     *Regresa el nombre del día de acuerdo a una fecha
     * @param inputDate
     * @return
     */
    public String getDayName(String inputDate){
        String day=null;
        try {
            c.setTime(dateFormatFrom.parse(inputDate));
            day= Utils.toUppperCaseFirst(new SimpleDateFormat("EEEE",new Locale("es")).format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }


    /**
     * Parseo de fechas  para obtener hora
     * @param inputDate
     * @return
     */
    public String getHour(String inputDate){
        String hour = null;
        if(inputDate!=null) {
            try {
                c.setTime(dateFormatFrom.parse(inputDate));
                hour = hourFormat.format(c.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return hour;
    }


    /**
     * Parseo de fecha para obtener la fecha sin la hora
     * @param inputDate
     * @return
     */
    public String getDateFormat(String inputDate) {
        String date=null;
        try {
           c.setTime(dateFormatFrom.parse(inputDate));
           date=dateFormatTo.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public ListadoIncidencias getSelectedIncidencia(){
        return this.selectedIncidencia;
    }



    public interface JustificarIncidenciasView extends BaseView {
        void setCurrentJustificacion(ListadoIncidencias item, boolean isPlantilla);
        void showMensajeIncidencia(String mensaje);
        void hideDialogJustificar();
        void sendJustificarEnviada(Bundle bundle);
    }
}
