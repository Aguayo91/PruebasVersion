package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import android.app.Activity;
import android.content.Context;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.WebService.Model.Request.Horario.EditarHorarioRequest;

import java.util.Map;

/**
 * Created by GiioToledo on 17/11/17.
 */

public interface HorariosTrabajoPresenter extends BasePresenter {
    void notificaHorarioVariable(Context context, int tipoHorario);

    void ModificarListaHorarioEmpleado(Context context, Map<String, EditarHorarioRequest> mapHorario1, String horaentrada, String horaSalida);
    void ModificarListaHorarioEmpleado(Context context, Map<String, EditarHorarioRequest> mapHorario1, String horaentrada, String horaSalida,
                                       Map<String, EditarHorarioRequest> mapHorario2, String horaentrada2, String horaSalida2);

    void cargarHorariosEmpleado(Context context);
}
