package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias;

import com.sociomas.core.MVP.BasePresenter;

import java.util.Calendar;

/**
 * Created by jromeromar on 23/11/2017.
 */

public interface MisAsistenciasPresenter extends BasePresenter {
    void setCalendarInicio(int year,int month,int  dayOfMonth);
    void setCalendarFin(int year, int month, int dayOfMonth);

    /**
     * Inicia la consulta al servicio con los siguientes parametros
     * para hacer el filtrado de la información
     * @param numeroEmpleado  Número de empleado que se consulta asistencias
     * @param asistenciaCorrecta  True si necesitas las asistencias correctas
     * @param faltas True si necesitas ver faltas
     * @param salidaTemprano Trues si necesitas salidas temprano
     * @param llegasTarde True si necesitas ver llegadas tarde
     */
    void initiBusquedaAsistencias(
            String numeroEmpleado,
            boolean asistenciaCorrecta,
            boolean faltas,boolean salidaTemprano, boolean llegasTarde, boolean fueraHoraLimite);


    /**
     *
     * @return La fecha de inicio
     */
    Calendar getCalendarInicio();

    /**
     *
     * @return La fecha de fin
     */
    Calendar getCalendarFin();


}
