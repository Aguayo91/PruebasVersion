package com.gruposalinas.elektra.sociomas.UI.Activities.Jornada;
import com.google.android.gms.maps.model.LatLng;
import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.Utils.Enums.EnumJornada;

/**
 * Created by oemy9 on 18/11/2017.
 */

public interface JornadaPresenter extends BasePresenter {
    void ingresarAsistenciaManual(EnumJornada jornada, LatLng location);
}
