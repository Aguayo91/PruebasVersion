package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Carga;

import com.gruposalinas.elektra.sociomas.R;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.DataBaseModel.RegistroGPS;
import com.sociomas.core.Utils.DbUtils.GPSBDHelper;
import com.sociomas.core.WebService.Model.Request.Gafete.GafeteObtenerRequest;
import com.sociomas.core.WebService.Model.Response.Gafete.ResponseGafete;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by oemy9 on 07/11/2017.
 */

public class CargaGafeteInteractor {
    public Observable<ResponseGafete> obtenerImagenAsync(){
        GPSBDHelper gpsbdHelper=new GPSBDHelper(ApplicationBase.getIntance().getApplicationContext());
        RegistroGPS registroGPS=gpsbdHelper.getLastRegistro();
        GafeteObtenerRequest request=new GafeteObtenerRequest();
        if(registroGPS!=null) {
            request.setDec_laltitud(registroGPS.getLatitud());
            request.setDec_longitud(registroGPS.getLongitud());
        }
       return ApplicationBase.getIntance().getControllerAPI().obtenerGafeteRx(request);
    }
}
