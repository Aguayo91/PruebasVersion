package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias;
import android.content.Intent;
import android.util.Log;

import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.DataBaseModel.Plantilla;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Enums.EnumAsistencia;
import com.sociomas.core.Utils.Utils;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by oemy9 on 25/11/2017.
 */

public class MisAsistenciasPresenterImpl extends BasePresenterImpl implements MisAsistenciasPresenter, Subscriber<List<ResultadoAsistencia>> {


    protected static final String TAG = "MisAsistenciasPresenter";
    protected String DATE_FORMAT_PICKER="dd/MMMM/yyyy";
    protected SimpleDateFormat dateFormat;
    protected MisAsistenciasView view;
    protected Calendar calendarInicio, calendarFin;
    private String fechaInicio, fechaFin;
    private boolean isPlantilla;
    private Plantilla selectedItem;


    public MisAsistenciasPresenterImpl() {
        dateFormat=new SimpleDateFormat(DATE_FORMAT_PICKER, new Locale("es"));
    }

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(MisAsistenciasView)view;
        initCalendars();
    }

    /**
     * Inicia los calendarios de inicio y fin
     */
    private void initCalendars(){
        Calendar c=Calendar.getInstance();
        calendarInicio=Calendar.getInstance();
        calendarInicio.add(Calendar.DAY_OF_MONTH,-15);

        calendarFin=Calendar.getInstance();
        calendarFin.set(Calendar.MONTH,c.get(Calendar.MONTH));
        calendarFin.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH));

        fechaInicio=dateFormat.format(calendarInicio.getTime()).toUpperCase();
        view.showInicioFecha(fechaInicio);

        fechaFin=dateFormat.format(calendarFin.getTime()).toUpperCase();
        view.showFinFecha(fechaFin);
    }

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(Constants.IS_PLANTILLA)){
            isPlantilla=intent.getBooleanExtra(Constants.IS_PLANTILLA,false);
            view.setVisibilitySpinnerPlantilla(isPlantilla);
        }
        if(intent.hasExtra(ViewEvent.ENTRY)){
            selectedItem=(Plantilla)intent.getSerializableExtra(ViewEvent.ENTRY);
            view.setSelectedItem(selectedItem);
        }
    }

    @Override
    public void setCalendarInicio(int year, int month, int dayOfMonth) {
        calendarInicio=Calendar.getInstance();
        calendarInicio.set(year,month,dayOfMonth);
        fechaInicio=dateFormat.format(calendarInicio.getTime()).toUpperCase();
        view.showInicioFecha(fechaInicio);
    }

    @Override
    public void setCalendarFin(int year, int month, int dayOfMonth) {
        calendarFin=Calendar.getInstance();
        calendarFin.set(year,month,dayOfMonth);
        fechaFin=dateFormat.format(calendarFin.getTime()).toUpperCase();
        view.showFinFecha(fechaFin);
    }

    private boolean before(Calendar yesterday, Calendar today) {
        if(yesterday == today) return false;
        if(yesterday == null || today == null) return false;
        return  yesterday.get(Calendar.YEAR) < today.get(Calendar.YEAR) ? true :
                yesterday.get(Calendar.YEAR) == today.get(Calendar.YEAR) && yesterday.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public void initiBusquedaAsistencias(String numeroEmpleado,boolean asistenciaCorrecta, boolean faltas,
                                         boolean salidaTemprano, boolean llegasTarde, boolean fueraHoraLimite) {
        if(calendarInicio==null){
                onShowAlert("Debes de ingresar una fecha de inicio");
        }
        else if(calendarFin==null){
            onShowAlert("Debes de ingresar una fecha de fin");
        }
        else if(before(calendarFin,calendarInicio)){
            onShowAlert("La fecha de inicio no puede ser mayor a la de fin");
        }
        else{
            ArrayList<EnumAsistencia>listFiltros=new ArrayList<>();
            if(asistenciaCorrecta)
                listFiltros.add(EnumAsistencia.ASISTENCIA_CORRECTA);
            if(faltas)
                listFiltros.add(EnumAsistencia.FALTA);
            if(salidaTemprano)
                listFiltros.add(EnumAsistencia.SALIDA_ANTES_HORARIO);
            if(llegasTarde)
                listFiltros.add(EnumAsistencia.RETARDO);

            if(fueraHoraLimite)
                listFiltros.add(EnumAsistencia.ENTRADA_DESPUES_HORA_LIMITE);


            onShowProgress();
            MisAsistenciasInteractor interactor=new MisAsistenciasInteractor();
            String nEmpleadoSearch=!isPlantilla ? Utils.getNumeroEmpleado(ApplicationBase.getIntance().getApplicationContext()): numeroEmpleado;
            interactor.getAsistenciasAsync(nEmpleadoSearch,calendarInicio,calendarFin,false,listFiltros,this);
        }
    }

    @Override
    public Calendar getCalendarInicio() {
        return calendarInicio;
    }

    @Override
    public Calendar getCalendarFin() {
        return calendarFin;
    }

    @Override
    public void onSubscribe(Subscription s) {
        Log.d(TAG, "onSubscribe: subcribe");
    }

    @Override
    public void onNext(List<ResultadoAsistencia> listAsistencias) {
        if(listAsistencias!=null){
            view.setListAsistencias(listAsistencias,fechaInicio,fechaFin);
        }
        onHideProgress();
    }

    @Override
    public void onError(Throwable t) {
        onHideProgress();
    }

    @Override
    public void onComplete() {
        onHideProgress();
    }

    public interface  MisAsistenciasView extends BaseView{
         void showInicioFecha(String fechaInicio);
         void showFinFecha(String fechaFin);
         void setListAsistencias(List<ResultadoAsistencia>listAsistencias, String fechaInicio, String fechaFin);
         void setVisibilitySpinnerPlantilla(boolean visible);
         void setSelectedItem(Plantilla item);

    }
}
