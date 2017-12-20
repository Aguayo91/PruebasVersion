package com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.AsistenciaConsultas;
import android.content.Intent;

import com.gruposalinas.elektra.sociomas.UI.Activities.Asistencia_V2.MisAsistencias.MisAsistenciasPresenterImpl;
import com.sociomas.core.MVP.ViewEvent;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.WebService.Model.Response.Asistencia.ResultadoAsistencia;

import java.util.ArrayList;

/**
 * Created by oemy9 on 25/11/2017.
 */

public class AsistenciaConsultaPresenterImpl extends MisAsistenciasPresenterImpl implements  AsistenciaConsultasPresenter {

    @Override
    public void setArguments(Intent intent) {
        if(intent.hasExtra(ViewEvent.ENTRIES_LIST)){
            ArrayList<ResultadoAsistencia> asistencias=intent.getParcelableArrayListExtra(ViewEvent.ENTRIES_LIST);
            view.setListAsistencias(asistencias,intent.getStringExtra(Constants.SELECTED_FECHA_INICIO), intent.getStringExtra(Constants.SELECTED_FECHA_FIN));
        }
    }
}
